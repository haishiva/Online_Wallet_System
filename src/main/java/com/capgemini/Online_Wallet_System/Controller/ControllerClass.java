package com.capgemini.Online_Wallet_System.Controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.capgemini.Online_Wallet_System.Bean.WalletAccount;
import com.capgemini.Online_Wallet_System.Bean.WalletUser;
import com.capgemini.Online_Wallet_System.Bean.AccountTransactions;
import com.capgemini.Online_Wallet_System.ServiceClass.ServiceClass;
import com.capgemini.exceptions.IdNotFoundException;

@RestController
@RequestMapping("/onlinewallet")
@CrossOrigin("http://localhost:4200")
public class ControllerClass {
	@Autowired
	private ServiceClass service;
	//Creating User account
	@PostMapping("/createuser")
	public ResponseEntity<String> createUser(@RequestBody WalletUser walletUser)
	{
		WalletUser user=service.addUser(walletUser);
		if (user == null) 
		{
			throw new IdNotFoundException("UserId already exist");
		} 
		else 
		{
			return new ResponseEntity<String>("User account created successfully", new HttpHeaders(), HttpStatus.OK);
		}		
	}
	//User Login
	@GetMapping("/userlogin/{userId}/{password}")
	public ResponseEntity<WalletUser> userLogin(@PathVariable("userId") int userId,@PathVariable("password") String password)
	{
		WalletUser login=service.userLogin(userId, password);
		if(login==null)
		{
			throw new IdNotFoundException("User does not exist");
		}
		else
		{
			return new ResponseEntity<WalletUser>(login, new HttpHeaders(), HttpStatus.OK);
		}
		
	}
	//Creating payment wallet account
	@PostMapping("/createaccount/{userId}")
	public ResponseEntity<String> createAccount(@PathVariable("userId") int userId,@RequestBody WalletAccount walletAccount)
	{
		String account=service.addAccount(userId,walletAccount);
		if (account == "Successfully created") 
		{
			return new ResponseEntity<String>("Wallet account created successfully", new HttpHeaders(), HttpStatus.OK);
		} 
		else 
		{
			throw new IdNotFoundException(account);
			
		}	
		
	}
	//Adding amount into wallet account
	@PostMapping("/addmoney/{userId}")
	public ResponseEntity<String> addMoney(@RequestBody WalletAccount accountDetails,@PathVariable("userId") int userId)
	{
		WalletAccount money=service.addMoney(accountDetails,userId);
		if (money == null) 
		{
			throw new IdNotFoundException("AccountId is Invalid");
		} 
		else 
		{
			return new ResponseEntity<String>("Money added successfully", new HttpHeaders(), HttpStatus.OK);
		}	
		
	}
	//To show payment wallet account balance
	@GetMapping("/accountbalance/{accountId}/{userId}")
	public ResponseEntity<Double> accountBalance(@PathVariable("accountId") int accountId,@PathVariable("userId") int userId)
	{
		double accountBalance=service.retriveBalance(accountId,userId);
		if(accountBalance==-1)
		{
			throw new IdNotFoundException("AccountId is Invalid");
		}
		else
		{
			return new ResponseEntity<Double>(accountBalance, new HttpHeaders(), HttpStatus.OK);
		}
	}
	//To transfer fund from one account to another account
	@GetMapping("/transferfund/{userId}/{senderaccountId}/{recieveraccountId}/{amount}")
	public ResponseEntity<String> transferFund(@PathVariable("userId") int userId,@PathVariable("senderaccountId") int senderAccountId,@PathVariable("recieveraccountId") int receiverAccountId,@PathVariable("amount") double amount)
	{
		String status=service.transferFunds(userId,senderAccountId, receiverAccountId, amount);
		return new ResponseEntity<String>(status, new HttpHeaders(), HttpStatus.OK);

		
	}
	//To retrive the transaction details of particular user
	@GetMapping("/transactiondetails/{accountId}")
	public ResponseEntity<Set<AccountTransactions>> transactionDetails(@PathVariable("accountId") int accountId)
	{
		Set<AccountTransactions> transactions=service.transactionDetails(accountId);
		if(transactions==null)
		{
			throw new IdNotFoundException("AccountId is Invalid");
		} 
		else 
		{
			return new ResponseEntity<Set<AccountTransactions>>(transactions, new HttpHeaders(), HttpStatus.OK);
		}	
	}

}
