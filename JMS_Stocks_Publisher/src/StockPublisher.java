import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Scanner;

import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.InitialContext;


public class StockPublisher {
	
	private static HashMap<String,String> stockHashMap = new HashMap<>();

	public static void main(String[] args) {

//		try {
//
//			InitialContext context = new InitialContext();  
//			TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) context.lookup("StockConnectionFactory");  
//			TopicConnection topicConnection = topicConnectionFactory.createTopicConnection();  
//			topicConnection.start();  
//			TopicSession topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);  
//			Topic topic = (Topic) context.lookup("Stocks");  
//			TopicPublisher topicPublisher = topicSession.createPublisher(topic);  
//			TextMessage textMessage = topicSession.createTextMessage();
//			BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));  
//			while(true) {  
//				System.out.println("Enter Msg, end to terminate:");  
//				String s=buffer.readLine();  
//				if (s.equals("end"))  
//					break;  
//				textMessage.setText(s);  
//				topicPublisher.publish(textMessage);  
//				System.out.println("Message successfully sent.");  
//			}  
//			topicConnection.close();
//			
//		} catch(Exception e) {
//			System.out.println(e);
//		}
		
		showMainMenu();

	}

	private static void showMainMenu() {
		System.out.println();
		System.out.println("===== MENU PRINCIPAL =====");
		System.out.println("1 - Cadastro de empresas;");
		System.out.println("2 - Informações sobre Ações;");
		System.out.println("3 - Sair;");
		System.out.println();
		System.out.print("Opção: ");
		
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		int option = in.nextInt();
		
		switch (option) {
		case 1: showBussinessMenu(); break;
		case 2: showStocksMenu(); break; 
		default: 
			System.out.println("Opção inválida!");
			showMainMenu();
			break;
		}
	}

	private static void showBussinessMenu() {
		System.out.println();
		System.out.println("===== CADASTRO DE EMPRESAS =====");
		System.out.println("1 - Listar;");
		System.out.println("2 - Adicionar;");
		System.out.println("3 - Remover;");
		System.out.println("4 - Voltar ao Menu Principal;");
		System.out.println();
		System.out.print("Opção: ");
		
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		int option = in.nextInt();
		
		switch (option) {
		case 1: listBussiness(); break;
		case 2: addBussiness(); break;
		case 3: removeBussiness(); break;		
		case 4: showMainMenu(); break;		
		default: 
			System.out.println("Opção inválida!");
			showStocksMenu();
			break;
		}
	}

	private static void removeBussiness() {
		System.out.println();
		System.out.println("***** REMOVENDO EMPRESA *****");
		System.out.print("Nome da Empresa: ");
		
		BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
		String bussinessName = null;
		try {
			bussinessName = buffer.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		if (isValidBussinessName(bussinessName)) {
			System.out.println();
			System.out.println("Deseja remover o cadastro da empresa " + bussinessName + "? [ S | N ] ");
			
			char option = 0;
			try {
				option = (char) System.in.read();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			switch (option) {
				case 's': 
					stockHashMap.remove(bussinessName);
					System.out.println("Cadastro da Empresa " + bussinessName + " removido com SUCESSO!");
					
				default: 
					showBussinessMenu(); 
					break;
			}
		} else {
			System.out.print("Empresa não encontrada!");
		}
		showBussinessMenu();
	}

	private static void addBussiness() {
		System.out.println();
		System.out.println("***** ADICIONANDO EMPRESAS *****");
		System.out.print("Quantidade: ");
		
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		int amount = in.nextInt();
		
		BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
		String bussinessName = null;
		while (amount > 0) {
			System.out.println();
			System.out.print("Nome da Empresa: ");
			
			try {
				bussinessName = buffer.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if (isValidBussinessName(bussinessName)) {
				stockHashMap.put(bussinessName, "");
				amount--;
			} else {
				System.out.print("NOME INVÁLIDO! Tente novamente.");
			}
		}
		
		showBussinessMenu();
	}

	private static boolean isValidBussinessName(String bussinessName) {
		return !stockHashMap.containsKey(bussinessName);
	}

	private static void listBussiness() {
		System.out.println();
		System.out.println("***** LISTA DE EMPRESAS *****");
		for (String bussiness : stockHashMap.keySet()){
			System.out.println(bussiness);
		}
		System.out.println("**********");
		showBussinessMenu();
	}

	private static void showStocksMenu() {
		System.out.println();
		System.out.println("===== INFORMAÇÕES SOBRE AÇÕES =====");
		System.out.println("1 - Listar;");
		System.out.println("2 - Cadastrar;");
		System.out.println("3 - Publicar;");
		System.out.println("4 - Voltar ao Menu Principal;");
		System.out.println();
		System.out.print("Opção: ");
		
		@SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
		int option = in.nextInt();
		
		switch (option) {
		case 1: listStocks(); break;
		case 2: addStock(); break;
		case 3: publish(); break;	
		case 4: showMainMenu(); break;
		default: 
			System.out.println("Opção inválida!");
			showStocksMenu();
			break;
		}
	}

	private static void publish() {
		try {
			InitialContext context = new InitialContext();  
			TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) context.lookup("StockConnectionFactory");  
			TopicConnection topicConnection = topicConnectionFactory.createTopicConnection();  
			topicConnection.start();  
			TopicSession topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);  
			Topic topic = (Topic) context.lookup("Stocks");  
			TopicPublisher topicPublisher = topicSession.createPublisher(topic);  
			TextMessage textMessage = topicSession.createTextMessage();
			
			System.out.println();
			System.out.println("***** PUBLICANDO INFORMAÇÕES SOBRE AÇÕES *****");
			
			textMessage.setText(stockHashMap.toString());
			topicPublisher.publish(textMessage);
			topicConnection.close();
			
			System.out.println("Informações publicadas com SUCESSO!");
			System.out.println("**********");
			
		} catch(Exception e) {
			System.out.println(e);
		} 
		showStocksMenu();
	}

	private static void addStock() {
		System.out.println();
		System.out.println("***** CADASTRO DE AÇÕES *****");
		BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
		for (HashMap.Entry<String, String> stock : stockHashMap.entrySet()) {
			System.out.print(stock.getKey() + ": ");
			try {
				stock.setValue(buffer.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("**********");	
		showStocksMenu();
	}

	private static void listStocks() {
		System.out.println();
		System.out.println("***** LISTA DE AÇÕES *****");
		for (HashMap.Entry<String, String> stock : stockHashMap.entrySet()) {
			System.out.println(stock.getKey() + " - " + stock.getValue());
		}
		System.out.println("**********");
		showStocksMenu();
	}

}
