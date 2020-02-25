package cs3500.freecell.hw02;

/**
 * Enumeration of a value of a standard card. There are thirteen values for cards: ACE(1), 2-10,
 * JACK(J), QUEEN(Q), KING(K). Aces are considered "low" or less than one.
 */
public enum Value {
  ACE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8),
  NINE(9), TEN(10), JACK(11), QUEEN(12), KING(13);

  private final int value;

  Value(int value) {
    this.value = value;
  }

  /**
   * Gets the value in int form.
   *
   * @return the int of the value
   */
  public int getValue() {
    return value;
  }

  /**
   * Produces the value in string format.
   *
   * @return the value formatted as a string
   */
  public String toString() {
    switch (this) {
      case ACE:
        return "A";
      case JACK:
        return "J";
      case QUEEN:
        return "Q";
      case KING:
        return "K";
      default:
        return Integer.toString(value);
    }
  }
}
