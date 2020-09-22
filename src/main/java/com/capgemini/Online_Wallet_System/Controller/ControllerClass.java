package com.capgemini.Online_Wallet_System.Controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.capgemini.Online_Wallet_System.ServiceClass.ServiceClass;

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
		return null;
			
	}
	//Creating payment wallet account
	@PostMapping("/createaccount")
	public ResponseEntity<String> createAccount(@RequestBody WalletAccount walletAccount)
	{
		return null;
		
	}
	//Adding amount into wallet account
	@PostMapping("/addmoney")
	public ResponseEntity<String> addMoney(@RequestBody WalletAccount accountDetails)
	{
		return null;
		
	}
	//To show payment wallet account balance
	@GetMapping("/accountbalance/{userId}")
	public ResponseEntity<WalletAccount> accountBalance(@PathVariable("userId") int userId)
	{
		return null;
		
	}
	//To transfer fund from one account to another account
	@GetMapping("/transferfund/{senderuserId}/{recieveraccountId}/{amount}")
	public ResponseEntity<String> transferFund(@PathVariable("senderuserId") int senderAccountId,@PathVariable("recieveraccountId") int receiverAccountId,@PathVariable("amount") double amount)
	{
		return null;
		
	}

}
