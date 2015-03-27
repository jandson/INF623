package br.edu.ifba.gsort.inf623.bank.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.edu.ifba.gsort.inf623.bank.controller.ClientController;
import br.edu.ifba.gsort.inf623.bank.model.Client;

/**
 * 
 * Classe respons�vel por conter os m�todos de acesso aos servi�os web do Cliente (REST).
 * 
 * @author Jandson
 *
 */
@Path("/client")
public class ClientResource {
	
	
	/**
	 * 
	 * M�todo respons�vel por prover o servi�o que lista todos os clientes.
	 * 
	 * @return List<Client>
	 */
	@GET
	@Path("/listAll")
	@Produces("application/json")
	public Response listAll() {
		List<Client> clients = null;
		try {
			clients = new ClientController().listAll();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
		return Response.status(Status.OK).entity(new GenericEntity<List<Client>>(clients){}).build();
	}
	
}
