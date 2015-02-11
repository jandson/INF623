import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;


public class DSMServer extends UnicastRemoteObject implements iDSM {

	private static final long serialVersionUID = -7350994194858180654L;

	private static final int PORT = 3232;
	
	private HashMap<String, Object> _dsmHashMap;
	
	protected DSMServer() throws RemoteException {
		
		super();
		
		String address = "";
		try {
			address = InetAddress.getLocalHost().toString();
			Registry registry = LocateRegistry.createRegistry(PORT);
			registry.rebind("rmiDSMServer", this);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			throw new RemoteException("Não foi possível obter o Endereço do Servidor!");
		} catch (RemoteException e) {
			e.printStackTrace();
			throw e;
		}
		
		System.out.println("Endereço: " + address);
		System.out.println("Porta: " + PORT);
		
		_dsmHashMap = new HashMap<String, Object>();
		
	}

	@Override
	public void set(String label, Object value) throws RemoteException {
		System.console().writer().println("Set: " + label + " => " + value.toString());
		_dsmHashMap.put(label, value);
	}

	@Override
	public String query(String label) throws RemoteException {
		System.console().writer().println("Query: " + label);
		return _dsmHashMap.get(label).toString();
	}

	public static void main(String[] args) {
		
		try {
			new DSMServer();
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
	}

}
