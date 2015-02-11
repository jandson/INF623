import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class DSMClient {

	private iDSM _dsmServer;
	
	public DSMClient (String serverAddress, int serverPort) {
		
		try {
			Registry registry = LocateRegistry.getRegistry(serverAddress, serverPort);
			_dsmServer = (iDSM) registry.lookup("rmiDSMServer");
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		DSMClient dsmClient = new DSMClient(args[0], Integer.parseInt(args[1]));
		
		while (true) {
			char option = System.console().readLine("Deseja ler ou escrever na DSM? [r|w]: ").charAt(0);
			switch (option) {
				case 'r': dsmClient.readDSM(); break;
				case 'w': dsmClient.writeDSM(); break;
				default: System.exit(0); break;
			}
			System.console().writer().println("==========================================================");
		}
	
	}

	private void writeDSM() {
		String label = System.console().readLine("Label: ");
		String value = System.console().readLine("Value: ");
		
		try {
			_dsmServer.set(label, value);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	private void readDSM() {
		String label = System.console().readLine("Label: ");
		
		try {
			System.console().writer().println(_dsmServer.query(label));
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
