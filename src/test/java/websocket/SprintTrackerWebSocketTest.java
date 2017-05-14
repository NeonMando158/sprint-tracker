package websocket;

import model.*;
import org.eclipse.jetty.websocket.*;
import org.junit.*;
import org.junit.runner.*;
import org.mockito.*;
import org.mockito.runners.*;
import repository.*;

import java.io.*;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SprintTrackerWebSocketTest {

	@Mock WebSocket.Connection connection;

	@Mock Clients clients;

	@Test
	public void when_a_connection_is_opened_should_register_to_the_client_list() {
		SprintTrackerWebSocket webSocket = new SprintTrackerWebSocket(clients);

		webSocket.onOpen(connection);

		verify(clients).registerOpenedWebSocket(webSocket);
	}

	@Test
	public void when_a_connection_is_closed_should_unregister_to_the_client_list() {
		SprintTrackerWebSocket webSocket = new SprintTrackerWebSocket(clients);

		webSocket.onOpen(connection);
		webSocket.onClose(0, "foo");

		verify(clients).unregisterClosedWebSocket(webSocket);
	}

	@Test
	public void when_a_card_is_added_should_send_message_through_the_connection() throws IOException {
		SprintTrackerWebSocket webSocket = new SprintTrackerWebSocket(clients);

		webSocket.onOpen(connection);
		Card card = new Card(0, "foo", "bar", null, 0);
		webSocket.cardAdded(card);

		verify(connection).sendMessage("{'added':{'id':0, 'title':'bar', 'state':'foo'}}".replace('\'', '"'));
	}

	@Test
	public void when_a_card_is_updated_should_send_message_through_the_connection() throws IOException {
		SprintTrackerWebSocket webSocket = new SprintTrackerWebSocket(clients);

		webSocket.onOpen(connection);
		webSocket.cardUpdated(new Card(0, "foo", "bar", null, 0));

		verify(connection).sendMessage("{'updated':{'id':0, 'title':'bar', 'state':'foo'}}".replace('\'', '"'));
	}

}
