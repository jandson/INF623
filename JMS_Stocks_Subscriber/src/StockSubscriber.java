import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;


public class StockSubscriber {

	public static void main(String[] args) {

		try {  
			
			InitialContext context = new InitialContext();  
			TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) context.lookup("StockConnectionFactory");  
			TopicConnection topicConnection = topicConnectionFactory.createTopicConnection();  
			topicConnection.start();  
			TopicSession topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);  
			Topic topic = (Topic) context.lookup("Stocks");  
			TopicSubscriber topicSubscriber = topicSession.createSubscriber(topic);  

			StockListener stockListener = new StockListener();  
			topicSubscriber.setMessageListener(stockListener);  

			System.out.println("Cliente está pronto!");  
			System.out.println("Utilize 'Ctrl + C' para sair.");
			System.out.print("Aguardando informações sobre Ações.");
			
			while(true){                  
				Thread.sleep(1000);
				System.out.print(".");
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}  
	}

}
