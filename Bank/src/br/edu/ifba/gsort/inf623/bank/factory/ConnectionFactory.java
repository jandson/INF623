package br.edu.ifba.gsort.inf623.bank.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 
 * Classe responsável por conter os métodos de acesso ao banco de dados.
 * 
 * @author Jandson
 *
 */
public class ConnectionFactory {

	private static final String DRIVER 	= "org.postgresql.Driver";
	
	private static final String URL 	= "jdbc:postgresql://localhost:5432/bank";
	
	private static final String USER 	= "postgres";
	
	private static final String PASS 	= "1234";
	
	
	
	/**
	 * 
	 * Método responsável por criar conexão com o banco.
	 * 
	 * @return Connection connection
	 */
	public Connection createConnection() {
		
		Connection connection = null;
		
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL, USER, PASS);
		} catch (Exception e) {
			System.out.println("Failure to create connection with database: " + URL);
			e.printStackTrace();
		}
		
		return connection;
	}
	
	
	/**
	 * 
	 * Método responsável por fechar conexão com o banco.
	 * 
	 * @param connection
	 * @param preparedStatement
	 * @param resultSet
	 */
	public void closeConnection(Connection connection, PreparedStatement preparedStatement, ResultSet resultSet) {
		
		try {
			if (connection != null) {connection.close();}
			if (preparedStatement != null) {preparedStatement.close();}
			if (resultSet != null) {resultSet.close();}
		} catch (Exception e) {
			System.out.println("Failure to close connection with database: " + URL);
			e.printStackTrace();
		}
		
	}
	
}
