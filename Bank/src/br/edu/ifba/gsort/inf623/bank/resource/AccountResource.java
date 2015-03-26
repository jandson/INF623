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
 * Classe responsável por conter os métodos de acesso aso serviços web da Conta (REST).
 * 
 * @author Jandson
 *
 */
@Path("/account")
public class AccountResource {

	/**
	 * 
	 * Método responsável por prover o serviço que lista todas as contas.
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
	 * Método responsável por criar uma nova conta.
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
	 * Método responsável por realizar um depósito numa conta.
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
	 * Método responsável por informar o saldo de uma conta.
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
	 * Método responsável por realizar um saque na conta.
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
