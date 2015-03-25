package br.edu.ifba.gsort.inf623.bank.controller;

import java.util.List;

import br.edu.ifba.gsort.inf623.bank.dao.ClientDAO;
import br.edu.ifba.gsort.inf623.bank.model.Client;

/**
 * 
 * Classe responsável por controlar o acesso entre o resource e o DAO do client.
 * 
 * @author Jandson
 *
 */
public class ClientController {
	
	public List<Client> listAll() {
		List<Client> clients = ClientDAO.getInstance().listAll();
		return clients;
	}

}
