package repository;

import model.Card;

public class RepositoryAssertions {

  public static CardAssert assertThat(final Card actual) {
    return new CardAssert(actual);
  }

}
