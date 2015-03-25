package br.edu.ifba.gsort.inf623.bank.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.edu.ifba.gsort.inf623.bank.controller.AccountController;
import br.edu.ifba.gsort.inf623.bank.model.Account;

/**
 * 
 * Classe respons�vel por conter os m�todos de acesso aso servi�os web da Conta (REST).
 * 
 * @author Jandson
 *
 */
@Path("/account")
public class AccountResource {

	/**
	 * 
	 * M�todo respons�vel por prover o servi�o que lista todas as contas.
	 * 
	 * @return
	 */
	@GET
	@Path("/listAll")
	@Produces("application/json")
	public List<Account> listAll() {
		AccountController ctrl = new AccountController();
		return ctrl.listAll();
	}
	
}
