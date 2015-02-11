import java.rmi.Remote;
import java.rmi.RemoteException;


public interface iDSM extends Remote {
	
	void set (String label, Object value) throws RemoteException;
	
	String query (String label) throws RemoteException;

}
