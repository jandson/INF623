package br.edu.ifba.gsort.inf623.bank.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

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
	public Response listAll() {
		List<Account> accounts = null;
		try {
			accounts = new AccountController().listAll();
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
//		return Response.status(Status.OK).entity(accounts).build();
		return Response.status(Status.OK).entity(new GenericEntity<List<Account>>(accounts){}).build();
	}
	
	/**
	 * 
	 * M�todo respons�vel por criar uma nova conta.
	 * 
	 * @param Account - Dados da conta a ser criada.
	 * @return Account - Dados da conta criada com ID.
	 */
	@POST
	@Path("/create")
	@Consumes("application/json")
	@Produces("application/json")
	public Response createAccount(Account account) {
		Account newAccount = null;
		try {
			newAccount = new AccountController().createAccount(account);
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
		return Response.status(Status.OK).entity(newAccount).build();
	}
	
	/**
	 * 
	 * M�todo respons�vel por realizar um dep�sito numa conta.
	 * 
	 * @param accountId - ID da conta.
	 * @param value - Quantia a ser depositada.
	 * @return Account - Dados da conta com o novo saldo.
	 */
	@POST
	@Path("/deposit")
	@Produces("application/json")
	public Response deposit(@QueryParam("accountId") Integer accountId, @QueryParam("value") Integer value) {
		Account account = null;
		try {
			account = new AccountController().deposit(accountId, value);
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
		return Response.status(Status.OK).entity(account).build();
	}
	
	/**
	 * 
	 * M�todo respons�vel por informar o saldo de uma conta.
	 * 
	 * @param accountId - ID da conta.
	 * @return String - Saldo da conta.
	 */
	@GET
	@Path("/getBalance")
	@Produces("application/json")
	public Response balance(@QueryParam("accountId") Integer accountId) {
		Integer balance;
		try {
			balance = new AccountController().getBalance(accountId);
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
		return Response.status(Status.OK).entity(balance.toString()).build();
	}
	
	/**
	 * 
	 * M�todo respons�vel por realizar um saque na conta.
	 * 
	 * @param accountId - ID da conta.
	 * @param value - Quantia a ser sacada.
	 * @return Account - Dados da conta com o novo saldo.
	 * @throws Exception 
	 */
	@POST
	@Path("/withdraw")
	@Produces("application/json")
	public Response withdraw(@QueryParam("accountId") Integer accountId, @QueryParam("value") Integer value) {
		Account account = null;
		try {
			account = new AccountController().withdraw(accountId, value);
		} catch (Exception e) {
			return Response.status(Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
		return Response.status(Status.OK).entity(account).build();
	}
	
}
