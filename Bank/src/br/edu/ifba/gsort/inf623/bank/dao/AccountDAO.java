package br.edu.ifba.gsort.inf623.bank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifba.gsort.inf623.bank.factory.ConnectionFactory;
import br.edu.ifba.gsort.inf623.bank.model.Account;

/**
 * 
 * Classe responsável por conter os métodos de CRUD da tabela de account
 * 
 * @author Jandson
 *
 */
public class AccountDAO extends ConnectionFactory{
	
	private static AccountDAO instance;
	
	/**
	 * 
	 * Método responsável por retornar uma instância do AccountDAO (Singleton)
	 * 
	 * @return AccountDAO
	 */
	public static AccountDAO getInstance() {
		if (instance == null) {
			instance = new AccountDAO();
		}
		return instance;
	}
	
	/**
	 * 
	 * Método responsável por listar todas as contas do banco
	 * 	
	 * @return List<Account>
	 */
	public List<Account> listAll() {
		List<Account> accounts = new ArrayList<Account>();
		String query = "SELECT * FROM account ORDER BY id";
		
		Connection connection = this.createConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {			
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Account account = new Account();
				account.setId(resultSet.getInt("id"));
				account.setId_client(resultSet.getInt("id_client"));
				account.setType(resultSet.getInt("type"));
				account.setBalance(resultSet.getInt("balance"));
				accounts.add(account);
			}
		} catch (Exception e) {
			System.out.println("Failure to list accounts: " + e);
			e.printStackTrace();
		} finally {
			this.closeConnection(connection, preparedStatement, resultSet);
		}
		
		return accounts;
	}

	/**
	 * 
	 * Método responsável por criar uma nova conta.
	 * 
	 * @param Account - Dados da conta a ser criada.
	 * @return Account - Dados da conta criada com o ID.
	 */
	public Account createAccount(Account account) {
		String query = "INSERT INTO account (id_client, type, balance) VALUES (?, ?, ?)";
		
		Connection connection = this.createConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {		
			preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setInt(1, account.getId_client());
			preparedStatement.setInt(2, account.getType());
			preparedStatement.setInt(3, account.getBalance());
			int affectedRows = preparedStatement.executeUpdate();
			if (affectedRows == 0) {
				System.out.println("Create account failed, no rows affected!");
			}
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				account.setId(resultSet.getInt(1));
			} else {
				System.out.println("Creating account failed, no ID obtained!");
			}
		} catch (Exception e) {
			System.out.println("Failure to create account: " + account.toString());
			e.printStackTrace();
		} finally {
			this.closeConnection(connection, preparedStatement, resultSet);
		}
		
		return account;
	}

	/**
	 * 
	 * Método responsável por realizar um depósito numa conta
	 * 
	 * @param accountId - ID da conta.
	 * @param value - Quantia a ser depositada.
	 * @return Account - Dados da conta com o novo Saldo.
	 */
	public Account deposit(Integer accountId, Integer value) {
		Account account = null;
		String query = "SELECT * FROM account WHERE id = ? LIMIT 1 FOR UPDATE";
		
		Connection connection = this.createConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			
			preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			preparedStatement.setInt(1, accountId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.first()) {
				
				Integer newBalance = value + resultSet.getInt("balance");
				resultSet.updateInt("balance", newBalance);
				resultSet.updateRow();
				
				account = new Account();
				account.setId(resultSet.getInt("id"));
				account.setId_client(resultSet.getInt("id_client"));
				account.setType(resultSet.getInt("type"));
				account.setBalance(newBalance);
				
			} else {
				System.out.println("Cannot found account by id: " + accountId.toString());
			}
			
		} catch (Exception e) {
			System.out.println("Failure to deposit " + value.toString() + " in account: " + accountId.toString());
			e.printStackTrace();
		} finally {
			this.closeConnection(connection, preparedStatement, resultSet);
		}
		
		return account;
	}
	
	/**
	 * 
	 * Método responsável por obter o saldo de uma conta
	 * 
	 * @param accountId - ID da conta.
	 * @return Integer - Saldo.
	 */
	public Integer getBalance(Integer accountId) {
		Integer balance = null;
		String query = "SELECT * FROM account WHERE id = ? LIMIT 1";
		
		Connection connection = this.createConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, accountId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				balance = resultSet.getInt("balance");
			} else {
				System.out.println("Cannot found account by id: " + accountId.toString());
			}
		} catch (Exception e) {
			System.out.println("Failure to get balance by account: " + accountId.toString());
			e.printStackTrace();
		} finally {
			this.closeConnection(connection, preparedStatement, resultSet);
		}
		
		return balance;
	}
	
	/**
	 * 
	 * Método responsável por realizar um saque na conta
	 * 
	 * @param accountId - ID da conta.
	 * @param value - Quantia a ser sacada.
	 * @return Account - Dados da conta com o novo Saldo.
	 */
	public Account withdraw(Integer accountId, Integer value) {
		Account account = null;
		String query = "SELECT * FROM account WHERE id = ? LIMIT 1 FOR UPDATE";
		
		Connection connection = this.createConnection();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			
			preparedStatement = connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			preparedStatement.setInt(1, accountId);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.first()) {
				Integer newBalance = resultSet.getInt("balance") - value;
				if (newBalance >= 0) { 
					resultSet.updateInt("balance", newBalance);
					resultSet.updateRow();
					account = new Account();
					account.setId(resultSet.getInt("id"));
					account.setId_client(resultSet.getInt("id_client"));
					account.setType(resultSet.getInt("type"));
					account.setBalance(newBalance);
				} else {
					System.out.println("Insufficient balance to carry out the withdrawal " + value + " in the account: " + accountId);
				}
			} else {
				System.out.println("Cannot found account by id: " + accountId.toString());
			}
			
		} catch (Exception e) {
			System.out.println("Failure to deposit " + value.toString() + " in account: " + accountId.toString());
			e.printStackTrace();
		} finally {
			this.closeConnection(connection, preparedStatement, resultSet);
		}
		
		return account;
	}

}
