package repository;

import com.google.common.collect.*;
import model.*;
import websocket.*;

import java.util.*;

import static com.google.common.collect.Sets.*;

public class Clients {

	private static final Clients instance = new Clients();

	private Set<SprintTrackerWebSocket> clients;

	private Clients() {
		clients = newHashSet();
	}

	public static Clients getInstance() {
		return instance;
	}

	public void registerOpenedWebSocket(SprintTrackerWebSocket sprintTrackerWebSocket) {
		clients.add(sprintTrackerWebSocket);
	}

	public void unregisterClosedWebSocket(SprintTrackerWebSocket sprintTrackerWebSocket) {
		clients.remove(sprintTrackerWebSocket);
	}

	public void notifyStoryAdded(Card card) {
		for (SprintTrackerWebSocket sprintTrackerWebSocket : ImmutableSet.copyOf(clients)) {
			sprintTrackerWebSocket.cardAdded(card);
		}
	}

	public void notifyStoryUpdated(Card card) {
		for (SprintTrackerWebSocket sprintTrackerWebSocket : ImmutableSet.copyOf(clients)) {
			sprintTrackerWebSocket.cardUpdated(card);
		}
	}

	public void notifyStoryDeleted(int cardId) {
		for (SprintTrackerWebSocket sprintTrackerWebSocket : ImmutableSet.copyOf(clients)) {
			sprintTrackerWebSocket.cardDeleted(cardId);
		}
	}
}
