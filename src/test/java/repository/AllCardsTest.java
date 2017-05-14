package repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import model.Card;

import org.junit.Before;
import org.junit.Test;

public class AllCardsTest {

	private AllCards allCards;

	@Before
	public void createAllCards() {
		allCards = new AllCards();
	}

	@Test
	public void should_get_2_initial_cards() {
		final List<Card> cards = allCards.list();
		assertThat(cards).hasSize(2);
		RepositoryAssertions.assertThat(cards.get(0)).isEqualTo(1, "task 1", "START", null, null);
		RepositoryAssertions.assertThat(cards.get(2)).isEqualTo(2, "task 2", "DONE", null, null);
	}

	@Test
	public void should_creates_a_new_card() {
		allCards.add("DONE", "new card", "Sample Description", 20);

		final List<Card> cards = allCards.list();
		assertThat(cards).hasSize(4);
		RepositoryAssertions.assertThat(cards.get(3)).isEqualTo(4, "new card", "START", null, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void should_not_add_a_card_with_empty_title() {
		try {
			allCards.add("DONE", "", null, 0);
		} catch (IllegalArgumentException e) {
			assertThat(e).hasMessage("Please provide a card title to add.");
			throw e;
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void should_not_add_a_card_with_null_title() {
		try {
			allCards.add("DONE", null, null, 0);
		} catch (IllegalArgumentException e) {
			assertThat(e).hasMessage("Please provide a card title to add.");
			throw e;
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void should_not_add_an_existing_card() {
		try {
			allCards.add("DONE", "task 1", "description", 10);
		} catch (IllegalArgumentException e) {
			assertThat(e).hasMessage("The card 'task 1' already exists.");
			throw e;
		}
	}

	@Test
	public void should_update_a_new_card() {
		allCards.update(2, "DONE", "description", 30);

		final Card updatedCard = allCards.list().get(2);
		RepositoryAssertions.assertThat(updatedCard).hasState("DONE");
	}

	@Test(expected = IllegalArgumentException.class)
	public void should_not_update_an_unkown_card() {
		try {
			allCards.update(6, "DONE", "description", 30);
		} catch (IllegalArgumentException e) {
			assertThat(e).hasMessage("The card #6 does not exists.");
			throw e;
		}
	}

	@Test
	public void should_delete_card() {
		allCards.delete(1);

		final List<Card> cards = allCards.list();
		assertThat(cards).hasSize(2);
		RepositoryAssertions.assertThat(cards.get(0)).hasId(2);
		RepositoryAssertions.assertThat(cards.get(1)).hasId(3);
	}

	@Test(expected = IllegalArgumentException.class)
	public void should_not_delete_unexisting_card() {
		try {
			allCards.delete(58);
		} catch (IllegalArgumentException e) {
			assertThat(e).hasMessage("The card #42 does not exists.");
			throw e;
		}
	}

	@Test
	public void should_increment_ids_after_create_even_after_a_card_has_been_deleted() {
		allCards.add("no state", "a card with id 4", "description", 10);
		allCards.delete(4);
		allCards.add("no state", "a card with id 5", "descrition test", 20);

		assertThat(allCards.forName("a card with id 5").id).isEqualTo(5);
	}

}
