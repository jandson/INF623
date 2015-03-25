package br.edu.ifba.gsort.inf623.bank.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import br.edu.ifba.gsort.inf623.bank.factory.ConnectionFactory;
import br.edu.ifba.gsort.inf623.bank.model.Client;

/**
 * 
 * Classe respons�vel por conter os m�todos de CRUD da tabela client
 * 
 * @author Jandson
 *
 */
public class ClientDAO extends ConnectionFactory{
	
	private static ClientDAO instance;
	
	/**
	 * 
	 * M�todo respons�vel por retornar uma inst�ncia do ClientDAO (Singleton)
	 * 
	 * @return ClientDAO
	 */
	public static ClientDAO getInstance() {
		if (instance == null) {
			instance = new ClientDAO();
		}
		return instance;
	}
	
	/**
	 * 
	 * M�todo respons�vel por listar todos os clientes do banco.
	 * 
	 * @return List<Client>
	 */
	public List<Client> listAll() {
		
		List<Client> clients = new ArrayList<Client>();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		connection = this.createConnection();
		try {
			
			String query = "SELECT * FROM client ORDER BY name";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {	
				Client client = new Client();
				client.setId(resultSet.getInt("id"));
				client.setAddress(resultSet.getString("address"));
				client.setCpf(resultSet.getString("cpf"));
				client.setName(resultSet.getString("name"));
				clients.add(client);
			}
			
		} catch (Exception e) {
			System.out.println("Failure to list clients: " + e);
			e.printStackTrace();
		} finally {
			this.closeConnection(connection, preparedStatement, resultSet);
		}
		
		return clients;
	}

}
