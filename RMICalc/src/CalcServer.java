

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class CalcServer extends UnicastRemoteObject implements iRemoteCalc {
	
	private static final long serialVersionUID = 769905755697382515L;	
	
	private static final int PORT = 3232;
	
	public CalcServer() throws RemoteException {
		
		super();
		
		String address = "";
		try {
			address = InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
			throw new RemoteException("Não foi possível obter o Endereço do Servidor!");
		}
		
		try {
			Registry registry = LocateRegistry.createRegistry(PORT);
			registry.rebind("rmiCalcServer", this);
		} catch (RemoteException e) {
			e.printStackTrace();
			throw e;
		}
		
		System.out.println("Endereço: " + address);
		System.out.println("Porta: " + PORT);
		
	}

	@Override
	public Float sum(Float a, Float b) throws RemoteException {
		System.console().writer().println("Somando: " + a + " + " + b);
		return a + b;
	}

	@Override
	public Float sub(Float a, Float b) throws RemoteException {
		System.console().writer().println("Subtraindo: " + a + " + " + b);
		return a - b;
	}

	@Override
	public Float mult(Float a, Float b) throws RemoteException {
		System.console().writer().println("Multiplicando: " + a + " + " + b);
		return a * b;
	}

	@Override
	public Float div(Float a, Float b) throws Exception {
		System.console().writer().println("Dividindo: " + a + " + " + b);
		return a / b;
	}

	public static void main(String[] args) {

		try {
			new CalcServer();
		} catch (RemoteException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
	}

}
