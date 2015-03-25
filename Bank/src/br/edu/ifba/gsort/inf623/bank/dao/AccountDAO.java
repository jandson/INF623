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
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		connection = this.createConnection();
		
		try {
			
			String query = "SELECT * FROM account ORDER BY id";
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

}
