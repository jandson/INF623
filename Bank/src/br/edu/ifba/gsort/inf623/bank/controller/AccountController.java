package br.edu.ifba.gsort.inf623.bank.controller;

import java.util.List;

import br.edu.ifba.gsort.inf623.bank.dao.AccountDAO;
import br.edu.ifba.gsort.inf623.bank.model.Account;

/**
 * 
 * Classe responsável por controlar o acesso entre o resource e o DAO do account.
 * 
 * @author Jandson
 *
 */
public class AccountController {
	
	public List<Account> listAll() {
		List<Account> accounts = AccountDAO.getInstance().listAll();
		return accounts;
	}

	public Account createAccount(Account account) {
		Account newAccount = AccountDAO.getInstance().createAccount(account);
		return newAccount;
	}

	public Account deposit(Integer accountId, Integer value) {
		Account account = AccountDAO.getInstance().deposit(accountId, value);
		return account;
	}

}
