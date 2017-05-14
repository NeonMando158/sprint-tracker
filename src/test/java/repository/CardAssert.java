package repository;

import static org.assertj.core.api.Assertions.assertThat;
import model.Card;

import org.assertj.core.api.AbstractAssert;

public class CardAssert extends AbstractAssert<CardAssert, Card> {

  protected CardAssert(Card actualCard) {
    super(actualCard, CardAssert.class);
  }

  public CardAssert isEqualTo(int expectedId, String expectedTitle, String expectedState, String expectedDescription, String expectedEstimate) {
    isNotNull();
    assertThat(actual.id).isEqualTo(expectedId);
    assertThat(actual.title).isEqualTo(expectedTitle);
    assertThat(actual.state).isEqualTo(expectedState);
    assertThat(actual.description).isEqualTo(expectedDescription);
    assertThat(actual.estimate).isEqualTo(expectedEstimate);
    return this;
  }

  public CardAssert hasId(int expectedId) {
    isNotNull();
    assertThat(actual.id).isEqualTo(expectedId);
    return this;
  }

  public CardAssert hasState(String expectedState) {
    isNotNull();
    assertThat(actual.state).isEqualTo(expectedState);
    return this;
  }
  
  public CardAssert hasEstimate(String expectedEstimate) {
	    isNotNull();
	    assertThat(actual.estimate).isEqualTo(expectedEstimate);
	    return this;
	  }

}
