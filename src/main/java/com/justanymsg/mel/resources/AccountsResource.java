package com.justanymsg.mel.resources;

import static com.justanymsg.mel.utils.Constants.ACCOUNT_NUMBER;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.justanymsg.mel.objectMappers.Account;

@Component("accountsResource")
@Path("v1/accounts")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class AccountsResource {
	
	@Autowired
	private MongoTemplate mongoTemplate;

	@POST
	public Account createAccount(Account account) {
		System.out.println("Enteirng ------------------------------"+ account.getFirstName());
//		 throw new MELException(FaultUtils.createfault(new BadRequest(), "test exception message", 100));
		 mongoTemplate.insert(account,"Account123");
		 return account;
	}
	
	@GET
	@Path("/{" + ACCOUNT_NUMBER + "}")
	public Account getAccount()
	{	
		Account account = new Account();
		account.setFirstName("testFirst");
		account.setLastName("testLastName");
		return account;
	}
}
