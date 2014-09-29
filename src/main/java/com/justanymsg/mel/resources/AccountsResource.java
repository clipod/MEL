package com.justanymsg.mel.resources;

import static com.justanymsg.mel.utils.Constants.ACCOUNT_NUMBER;
import static com.justanymsg.mel.utils.Constants.GROUP_ID;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.justanymsg.mel.execptions.MELException;
import com.justanymsg.mel.execptions.faults.BadRequest;
import com.justanymsg.mel.objectMappers.Account;
import com.justanymsg.mel.objectMappers.Contact;
import com.justanymsg.mel.objectMappers.Device;
import com.justanymsg.mel.objectMappers.Devices;
import com.justanymsg.mel.objectMappers.Group;
import com.justanymsg.mel.objectMappers.ScheduledMessage;
import com.justanymsg.mel.utils.Mappers;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Component("accountsResource")
@Path("v1/accounts")
@Produces({ MediaType.APPLICATION_JSON })
@Consumes({ MediaType.APPLICATION_JSON })
public class AccountsResource {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private Mappers mappers;

	@POST
	public Account createAccount(Account account) {
		System.out.println("Entering ------------------------------" + account.getFirstName());
		// throw new MELException(FaultUtils.createfault(new BadRequest(),
		// "test exception message", 100));
		mongoTemplate.insert(account, "Account123");
		return account;
	}

	@GET
	@Path("/{" + ACCOUNT_NUMBER + "}")
	public Account getAccount() {
		Account account = new Account();
		account.setFirstName("testFirst");
		account.setLastName("testLastName");
		return account;
	}

	@GET
	@Path("/{" + ACCOUNT_NUMBER + "}/search")
	public List<Account> searchContact(@QueryParam("searchType") String searchType, @QueryParam("searchValue") String searchValue) {
		if (mappers.getValue("AccountSearch", searchType) == null) {
			throw new MELException(new BadRequest(), "Invalid search type");
		}
		return new ArrayList<>();
	}

	// TODO: API to verify an account
	// TODO: API to verify a contact
	@POST
	@Path("/{" + ACCOUNT_NUMBER + "}/contacts")
	public Contact createContact(@PathParam(ACCOUNT_NUMBER) String accountNumber, Contact contact) {
		return contact;
	}

	@POST
	@Path("/{" + ACCOUNT_NUMBER + "}/profilePic")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadProfilePicture(@FormDataParam("file") InputStream fileInputStream, @FormDataParam("file") FormDataContentDisposition contentDispositionHeader) throws URISyntaxException {
		// String uploadedFileLocation = "c://uploadedFiles/" +
		// fileDetail.getFileName();
		//
		// saveToFile(uploadedInputStream, uploadedFileLocation);
		return Response.created(new URI("http://localhost:8008")).build();
	}

	@GET
	@Path("/{" + ACCOUNT_NUMBER + "}/profilePic")
	@Produces("image/png")
	public Response getProfilePicture(@PathParam(ACCOUNT_NUMBER) String accountNumber) {
		// Input stream
		return Response.ok(null).build();

	}

	@DELETE
	@Path("/{" + ACCOUNT_NUMBER + "}/profilePic")
	public void deleteProfilePic(@PathParam(ACCOUNT_NUMBER) String accountNumber) {
	}

	@PUT
	@Path("/{" + ACCOUNT_NUMBER + "}/devices")
	public Devices addDevice(Device device) {
		return new Devices();
	}

	@POST
	@Path("/{" + ACCOUNT_NUMBER + "}/scheduleMessage")
	public Response scheduleMessage(@PathParam(ACCOUNT_NUMBER) String accountNumber, ScheduledMessage scheduledMessage) {
		return Response.created(null).build();
	}

	@POST
	@Path("/{" + ACCOUNT_NUMBER + "}/groups")
	public Group createGroup(@PathParam(ACCOUNT_NUMBER) String accountNumber, Group group) {
		return new Group();
	}

	@GET
	@Path("/{" + ACCOUNT_NUMBER + "}/groups/{" + GROUP_ID + "}")
	public Group getGroup(@PathParam(ACCOUNT_NUMBER) String accountNumber, @PathParam(GROUP_ID) String groupId) {
		return new Group();
	}

}
