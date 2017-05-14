package resource;

import java.io.IOException;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.Card;

import org.codehaus.jettison.json.JSONException;

import repository.AllCards;

import com.google.common.collect.ImmutableMap;
import com.sun.jersey.api.container.httpserver.HttpServerFactory;
import com.sun.net.httpserver.HttpServer;

import config.VrandeshJerseyApplication;

@Path("/")
public class SprintTrackerServer {
	private static AllCards allCards = new AllCards();

	@GET
	@Path("cards.json")
	@Produces(MediaType.APPLICATION_JSON + "; charset=UTF-8")
	public Response cards() throws JSONException {
		return Response.ok(ImmutableMap.of("cards", allCards.list())).build();
	}

	@PUT
	@Path("card/{title}")
	@Produces({ MediaType.APPLICATION_JSON + "; charset=UTF-8", MediaType.TEXT_PLAIN + "; charset=UTF-8" })
	public Response addCard(@PathParam("title") String title, @PathParam("description") String description, @PathParam("estimate") int estimate) {
		try {
			Card newCard = allCards.add("START", title, description, estimate);
			return Response.status(Response.Status.CREATED).entity(newCard).build();
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}

	@PUT
	@Path("card")
	@Produces(MediaType.TEXT_PLAIN + "; charset=UTF-8")
	public Response addCardWithAnEmptyTitle() {
		return Response.status(Response.Status.BAD_REQUEST).entity("Ensure card title is added.").build();
	}

	@POST
	@Path("card/{id}/{state}")
	@Produces(MediaType.TEXT_PLAIN + "; charset=UTF-8")
	public Response changeCardState(@PathParam("id") int id, @PathParam("state") String state, @PathParam("description") String description, @PathParam("estimate") int estimate) {
		try {
			allCards.update(id, state, description, estimate);
			return Response.ok().build();
		} catch (Exception e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}

	@DELETE
	@Path("card/{id}")
	@Produces(MediaType.TEXT_PLAIN + "; charset=UTF-8")
	public Response deleteCard(@PathParam("id") int id) {
		try {
			allCards.delete(id);
			return Response.ok().build();
		} catch (IllegalArgumentException e) {
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}

	public static HttpServer start() throws IOException {
		allCards = new AllCards();
		HttpServer httpServer = HttpServerFactory.create("http://127.0.0.1:1580/", new VrandeshJerseyApplication());
		httpServer.start();
		return httpServer;
	}
}
