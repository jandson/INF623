package br.edu.ifba.gsort.inf623.bank.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.edu.ifba.gsort.inf623.bank.controller.ClientController;
import br.edu.ifba.gsort.inf623.bank.model.Client;

/**
 * 
 * Classe responsável por conter os métodos de acesso aos serviços web do Cliente (REST).
 * 
 * @author Jandson
 *
 */
@Path("/client")
public class ClientResource {
	
	
	/**
	 * 
	 * Método responsável por prover o serviço que lista todos os clientes.
	 * 
	 * @return List<Client>
	 */
	@GET
	@Path("/listAll")
	@Produces("application/json")
	public List<Client> listAll() {
		ClientController ctrl = new ClientController();
		return ctrl.listAll();
	}
	
}
