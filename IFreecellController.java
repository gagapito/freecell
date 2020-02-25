package cs3500.freecell.hw03;

import cs3500.freecell.hw02.FreecellOperations;
import java.util.List;

/**
 * An interface that provides a controller to use to play a game of Freecell.
 *
 * @param <Card> the cards of Freecell
 */
public interface IFreecellController<Card> {

  /**
   * The primary method for playing the game.
   *
   * @param deck the deck of cards
   * @param model the game of Freecell to be played
   * @param numCascades the number of cascade piles
   * @param numOpens the number of open piles
   * @param shuffle to determine if the deck is shuffled or not
   * @throws IllegalStateException controller is not valid
   */
  void playGame(List<Card> deck, FreecellOperations<Card> model, int numCascades,
      int numOpens, boolean shuffle);
}
