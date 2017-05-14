package websocket;

import static java.lang.String.format;

import java.io.IOException;

import model.Card;

import org.eclipse.jetty.websocket.WebSocket;

import repository.Clients;

public class SprintTrackerWebSocket implements WebSocket.OnTextMessage {

    private Connection connection;
    private Clients clients;

    public SprintTrackerWebSocket(Clients clients) {
        this.clients = clients;
    }

    public void onMessage(String data) {
        // Don't care about client messages.
    }

    public void onOpen(Connection connection) {
        this.connection = connection;
        clients.registerOpenedWebSocket(this);
    }

    public void onClose(int closeCode, String message) {
        connection = null;
        clients.unregisterClosedWebSocket(this);
    }

    public void cardAdded(Card card) {
        String jsonString = "{'added':{'id':%d, 'title':'%s', 'state':'%s'}}".replace('\'', '"');
        try {
            connection.sendMessage(format(jsonString, card.id, card.title, card.state));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cardUpdated(Card card) {
        String jsonString = "{'updated':{'id':%d, 'title':'%s', 'state':'%s'}}".replace('\'', '"');
        try {
            connection.sendMessage(format(jsonString, card.id, card.title, card.state));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cardDeleted(int cardId) {
        String jsonString = "{'deleted':{'id':%d}}".replace('\'', '"');
        try {
            connection.sendMessage(format(jsonString, cardId));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
