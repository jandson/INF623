import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;


public class StockListener implements MessageListener {  

	public void onMessage(Message message) {  
		try {  
			TextMessage textMessage = (TextMessage) message;
			System.out.println();
			System.out.println("===== INFORMAÇÕES (" + new Date() + ") =====");  
			System.out.println(textMessage.getText());
			System.out.println("==========");
			System.out.println();
		} catch(JMSException e) {
			System.out.println(e);
		}
	}

}  
