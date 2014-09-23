package com.justanymsg.mel.resources;

import static com.justanymsg.mel.utils.Constants.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;

import com.justanymsg.mel.execptions.MELException;
import com.justanymsg.mel.execptions.faults.BadRequest;
import com.justanymsg.mel.objectMappers.Account;
import com.justanymsg.mel.utils.FaultUtils;

@Component("accountsResource")
@Path("v1/accounts")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class AccountsResource {

	@POST
	public Account createAccount(Account account) {
		System.out.println("Enteirng ------------------------------"+ account.getFirstName());
		 throw new MELException(FaultUtils.createfault(new BadRequest(), "test exception message", 100));
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
