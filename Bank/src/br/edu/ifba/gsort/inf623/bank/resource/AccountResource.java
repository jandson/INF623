package br.edu.ifba.gsort.inf623.bank.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

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
	public Account createAccount(Account account) {
		AccountController ctrl = new AccountController();
		return ctrl.createAccount(account);
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
	public Account deposit(@QueryParam("accountId") Integer accountId, @QueryParam("value") Integer value) {
		AccountController ctrl = new AccountController();
		return ctrl.deposit(accountId, value);
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
	public String balance(@QueryParam("accountId") Integer accountId) {
		AccountController ctrl = new AccountController();
		return ctrl.getBalance(accountId).toString();
	}
	
	/**
	 * 
	 * M�todo respons�vel por realizar um saque na conta.
	 * 
	 * @param accountId - ID da conta.
	 * @param value - Quantia a ser sacada.
	 * @return Account - Dados da conta com o novo saldo.
	 */
	@POST
	@Path("/withdraw")
	@Produces("application/json")
	public Account withdraw(@QueryParam("accountId") Integer accountId, @QueryParam("value") Integer value) {
		AccountController ctrl = new AccountController();
		return ctrl.withdraw(accountId, value);
	}
	
}
