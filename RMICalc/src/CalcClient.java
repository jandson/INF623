

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CalcClient implements Runnable {

	private String 			_serverAddress;
	
	private int 			_serverPort;
	
	private iRemoteCalc 	_remoteCalc;
	
	public CalcClient (String serverAddress, int serverPort) {
		
		_serverAddress = serverAddress;
		_serverPort = serverPort;
		
		try {
			Registry registry = LocateRegistry.getRegistry(_serverAddress, _serverPort);
			_remoteCalc = (iRemoteCalc) registry.lookup("rmiCalcServer");
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		CalcClient calcClient = new CalcClient(args[0], Integer.parseInt(args[1]));
		new Thread(calcClient).start();
	
	}

	@Override
	public void run() {
		Float arg1 = this.readArg();
		char operation = this.readOperation();
		Float arg2 = this.readArg();
		Float result = new Float(0);
		
		try {
			switch (operation) {
				case '+': result = _remoteCalc.sum(arg1, arg2); break;
				case '-': result = _remoteCalc.sub(arg1, arg2); break;
				case '*': result = _remoteCalc.mult(arg1, arg2); break;
				case '/': result = _remoteCalc.div(arg1, arg2); break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.console().writer().println(result);
	}



	private char readOperation() {
		String input = System.console().readLine("Insira o operador: ");
		return input.charAt(0);
	}

	private Float readArg() {
		String input = System.console().readLine("Insira um número: ");
		return new Float(input);
	}}
