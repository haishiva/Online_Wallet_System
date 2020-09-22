package com.capgemini.Online_Wallet_System.ServiceClass;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.Online_Wallet_System.Bean.AccountTransactions;
import com.capgemini.Online_Wallet_System.Bean.WalletAccount;
import com.capgemini.Online_Wallet_System.Bean.WalletUser;
import com.capgemini.Online_Wallet_System.DaoInterface.AccountTransactionsInterface;
import com.capgemini.Online_Wallet_System.DaoInterface.WalletAccountInterface;
import com.capgemini.Online_Wallet_System.DaoInterface.WalletUserInterface;

@Service
@Transactional
public class ServiceClass {
	@Autowired
	AccountTransactionsInterface transactionsDao;
	@Autowired
	WalletAccountInterface walletAccountDao;
	@Autowired
	WalletUserInterface walletUserDao;
	//Adding User details into the database
	public WalletUser addUser(WalletUser user)
	{
		return walletUserDao.save(user);
	}
	//Creating wallet account to a particular user
	public WalletAccount addAccount(WalletAccount account)
	{
		return walletAccountDao.save(account);
	}
	//Adding money to user wallet account
	public WalletAccount addMoney(WalletAccount walletAccount)
	{
		return walletAccountDao.save(walletAccount);
	}
	//To show User wallet account balance
	public WalletAccount retriveBalance(int userId)
	{
		return walletAccountDao.getOne(userId);
	}
	//To transfer funds from one account to another account
	public String transferFunds(int senderUserId,int receiverAccountId,double amount)
	{
		if(walletAccountDao.existsById(receiverAccountId))
		{
			WalletUser sender=walletUserDao.getOne(senderUserId);
			WalletAccount senderAccount=sender.getWalletAccount();
			if(senderAccount.getAccountBalance()>amount)
			{
				//Amount debited from sender account
				double money=senderAccount.getAccountBalance()-amount;
				senderAccount.setAccountBalance(money);
				walletAccountDao.save(senderAccount);
				//Amount credited into receiver account
				WalletAccount receiverAccount=walletAccountDao.getOne(receiverAccountId);
				double money1=receiverAccount.getAccountBalance()+amount;
				receiverAccount.setAccountBalance(money1);
				walletAccountDao.save(receiverAccount);
				//creating sender account transaction details
				LocalDateTime now = LocalDateTime.now();  
				AccountTransactions senderTransaction=new AccountTransactions();
				senderTransaction.setAccountBalance(senderAccount.getAccountBalance());
				senderTransaction.setAmount(amount);
				senderTransaction.setDateOfTransaction(now);
				senderTransaction.setDiscription("Amount debited");
				senderTransaction.setWalletAccount(senderAccount);
				transactionsDao.save(senderTransaction);
				//creating receiver account transaction details
				AccountTransactions receiverTransaction=new AccountTransactions();
				receiverTransaction.setAccountBalance(receiverAccount.getAccountBalance());
				receiverTransaction.setAmount(amount);
				receiverTransaction.setDateOfTransaction(now);
				receiverTransaction.setDiscription("Amount credited");
				receiverTransaction.setWalletAccount(receiverAccount);
				transactionsDao.save(receiverTransaction);
				return "SuccessFully Transfered";	
			}
			else
			{
				return "Insufficient account balance";
			}
		}
		else
		{
			return "Receiver AccountId does not exist";
		}
	}

}
