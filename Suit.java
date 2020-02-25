package cs3500.freecell.hw02;

/**
 * Enumeration of a suit of a standard card. There are four suits for cards: diamonds (♦), hearts
 * (♥), clubs (♣) and spades (♠). Diamonds and Heats are red (true), clubs and spades are black
 * (false)
 */
public enum Suit {
  DIAMONDS("♦"), HEARTS("♥"), CLUBS("♣"), SPADES("♠");

  private final String suit;

  Suit(String suit) {
    this.suit = suit;
  }

  /**
   * Gets the suit.
   *
   * @return true if suit is red, false if suit is black
   */
  public String getSuit() {
    return suit;
  }

  /**
   * Produces the value in string format.
   *
   * @return the value formatted as a string
   */
  public String toString() {
    switch (this) {
      case DIAMONDS:
        return "♦";
      case HEARTS:
        return "♥";
      case CLUBS:
        return "♣";
      case SPADES:
        return "♠";
      default:
        throw new IllegalArgumentException();
    }
  }
}
