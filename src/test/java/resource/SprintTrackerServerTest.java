package resource;

import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.not;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sun.net.httpserver.HttpServer;

public class SprintTrackerServerTest {
	private HttpServer server;

	@Before
	public void startWebServer() throws IOException {
		server = SprintTrackerServer.start();
	}

	@After
	public void stopWebServer() {
		server.stop(0);
	}

	@Test
	public void should_retrieve_cards_as_JSON() {
		expect().body("cards.id", hasItems(1, 3)).and() //
				.body("cards.state", hasItems("START", "DONE")).and() //
				.body("cards.title", hasItems("task 1", "task 2")) //
				.body("cards.estimate", hasItems(10, 20)) //
				.body("cards.description", hasItems("task 1 description", "task 2 description")) //
				.header("Content-Type", endsWith("; charset=UTF-8")) //
				.when().get("http://127.0.0.1:1580/cards.json");
	}

	@Test
	public void should_change_card_state() {
		expect().statusCode(200).when().post("http://127.0.0.1:1580/card/1/WIP").thenReturn();
		expect().body("cards.state", hasItems("WIP", "WIP", "DONE")).and() //
				.when().get("http://127.0.0.1:1580/cards.json");
	}

	@Test
	public void should_add_card() {
		expect().body("id", equalTo(4)).and() //
				.body("state", equalTo("START")).and() //
				.body("title", equalTo("junit")) //
				.header("Content-Type", endsWith("; charset=UTF-8")) //
				.when().put("http://127.0.0.1:1580/card/junit").thenReturn();
		expect().body("card.title", hasItem("junit")) //
				.when().get("http://127.0.0.1:1580/cards.json");
	}

	@Test
	public void should_delete_card() {
		expect().statusCode(200).when().delete("http://127.0.0.1:1580/card/2").thenReturn();
		expect().body("cards.id", not(hasItem(2))).and() //
				.when().get("http://127.0.0.1:1580/cards.json");
	}

	@Test
	public void should_not_add_existing_card() {
		expect().statusCode(400).content(equalTo("The card 'task 1' already exists.")) //
				.header("Content-Type", endsWith("; charset=UTF-8")) //
				.when().put("http://127.0.0.1:1580/card/task 1").thenReturn();
	}

	@Test
	public void with_an_empty_title_should_not_add_card() {
		expect().statusCode(400).content(equalTo("Please provide a card title to add.")) //
				.header("Content-Type", endsWith("; charset=UTF-8")) //
				.when().put("http://127.0.0.1:1580/card/").thenReturn();
	}
}
