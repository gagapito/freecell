package cs3500.freecell.hw02;

import java.util.Objects;

/**
 * Card represents a standard card.
 */
public class Card {

  /**
   * Fields of a card.
   *
   * @param value is the value of the card
   * @param suit is the suit of the card
   */
  private final Value value;
  private final Suit suit;

  public Card(Value value, Suit suit) {
    this.value = value;
    this.suit = suit;
  }

  /**
   * Produces the cards in string format.
   *
   * @return card string format
   */
  public String toString() {
    return this.value.toString() + this.suit.toString();
  }

  /**
   * There are two types of colors for a card Red: the color red, Black: the color black.
   */
  public enum Color {
    RED, BLACK;
  }

  /**
   * Gets the color of the card.
   *
   * @return the color of the card depending on the suit
   */
  public Color getColor() {
    if (this.suit == Suit.HEARTS || this.suit == Suit.DIAMONDS) {
      return Color.RED;
    } else {
      return Color.BLACK;
    }
  }

  /**
   * Produces the enum value of the card.
   *
   * @return the enum value
   */
  public Value getEnumValue() {
    return value;
  }

  /**
   * Produces the enum suit of the card.
   *
   * @return the enum suit
   */
  public Suit getEnumSuit() {
    return suit;
  }

  /**
   * Determining equality of objects.
   *
   * @return true if the object types are the same, false otherwise
   */
  @Override
  public boolean equals(Object o) {
    if (o instanceof Card) {
      Card newObject = (Card) o;
      return newObject.value == this.value
          && newObject.suit == this.suit;
    } else {
      return false;
    }
  }

  /**
   * Produces the hashcode of the object.
   *
   * @return the hashcode value as an int
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.value, this.suit);
  }
}
