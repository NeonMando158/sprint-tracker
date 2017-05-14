package model;

public class Card {
	public final int id;
	public final String title;
	public final String state;
	public final String description;
	public final int estimate;

	public Card(int id, String stateId, String title, String description, int estimate) {
		this.id = id;
		this.title = title;
		this.state = stateId;
		this.description = description;
		this.estimate = estimate;
	}
}
