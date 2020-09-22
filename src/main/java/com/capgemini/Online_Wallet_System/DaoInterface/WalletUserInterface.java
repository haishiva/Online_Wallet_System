package com.capgemini.Online_Wallet_System.DaoInterface;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.Online_Wallet_System.Bean.WalletUser;

@Repository
public interface WalletUserInterface extends JpaRepository<WalletUser,Integer>{

}
