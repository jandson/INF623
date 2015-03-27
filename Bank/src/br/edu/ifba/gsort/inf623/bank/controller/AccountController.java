package br.edu.ifba.gsort.inf623.bank.controller;

import java.util.List;

import br.edu.ifba.gsort.inf623.bank.dao.AccountDAO;
import br.edu.ifba.gsort.inf623.bank.dao.ClientDAO;
import br.edu.ifba.gsort.inf623.bank.model.Account;

/**
 * 
 * Classe responsável por controlar o acesso entre o resource e o DAO do account.
 * 
 * @author Jandson
 *
 */
public class AccountController {
	
	public List<Account> listAll() throws Exception {
		List<Account> accounts = AccountDAO.getInstance().listAll();
		return accounts;
	}

	public Account createAccount(Account account) throws Exception {
		Boolean hasClient = ClientDAO.getInstance().hasClientByID(account.getId_client());
		Account newAccount = null;
		if (!hasClient) {
			throw new Exception("O Cadastro do cliente não foi encontrado para a criação da conta!");
		} else {
			newAccount = AccountDAO.getInstance().createAccount(account);
		}
		return newAccount;
	}

	public Account deposit(Integer accountId, Integer value) throws Exception {
		Account account = AccountDAO.getInstance().deposit(accountId, value);
		return account;
	}
	
	public Integer getBalance(Integer accountId) throws Exception {
		Integer balance = AccountDAO.getInstance().getBalance(accountId);
		return balance;
	}
	
	public Account withdraw(Integer accountId, Integer value) throws Exception {
		Account account = AccountDAO.getInstance().withdraw(accountId, value);
		return account;
	}

}
