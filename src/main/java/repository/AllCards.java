package repository;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.isNullOrEmpty;
import static com.google.common.collect.Iterables.find;
import static com.google.common.collect.Lists.newArrayList;

import java.util.List;
import java.util.NoSuchElementException;

import model.Card;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;

public class AllCards {
    private List<Card> cards = newArrayList( //
            new Card(1, "START", "task 1", null, 10), //
            new Card(2, "DONE", "task 2", null, 20));
    private Integer lastId = 2;

    public List<Card> list() {
        return ImmutableList.copyOf(cards);
    }

    public Card add(String state, String title, String description, int estimate) {
        checkArgument(!isNullOrEmpty(title), "Please provide a Card title");
        checkArgument(forName(title) == null, "The card '%s' already exists.", title);
        Card card = new Card(++lastId, state, title, description, estimate);
        cards.add(card);
        
        return card;
    }

    public void update(final int id, final String state, final String description, final int estimate) {
        Card existingCard = forId(id);
        checkArgument(existingCard != null, "The Card #%s is not available.", id);
        cards.remove(existingCard);
        Card card = new Card(existingCard.id, state, existingCard.title, description, estimate);
        cards.add(card);
       
    }

    public void delete(int id) {
        Card existingCard = forId(id);
        checkArgument(existingCard != null, "The card number #%s is not available ", id);
        cards.remove(existingCard);
        
    }

    protected Card forName(final String title) {
        try {
            return find(cards, new Predicate<Card>() {
                public boolean apply(Card card) {
                    return card.title.equals(title);
                }
            });
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    private Card forId(final int id) {
        try {
            return find(cards, new Predicate<Card>() {
                public boolean apply(Card card) {
                    return card.id == id;
                }
            });
        } catch (NoSuchElementException e) {
            return null;
        }
    }
}
