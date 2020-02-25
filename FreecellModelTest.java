package cs3500.freecell.hw02;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import cs3500.freecell.hw02.Card.Color;
import java.util.List;
import java.util.ArrayList;

/**
 * Test the FreecellModel.
 */
public class FreecellModelTest {

  // Card example
  Card card1 = new Card(Value.JACK, Suit.HEARTS);
  Card card2 = new Card(Value.SIX, Suit.SPADES);

  // Freecell Model example
  private FreecellModel game = new FreecellModel();
  private List<Card> cardList = new ArrayList<Card>();

  @Test
  public void cardTest() {
    assertEquals(11, card1.getEnumValue().getValue());
    assertEquals(Color.RED, card1.getColor());
    assertEquals(6, card2.getEnumValue().getValue());
    assertEquals(Color.BLACK, card2.getColor());
  }

  @Test
  public void getDeckTest() {
    assertEquals(52, game.getDeck().size());
    assertEquals("A♦", game.getDeck().get(0).toString());
    assertEquals(true, game.getDeck().get(0).equals(new Card(Value.ACE, Suit.DIAMONDS)));
    assertEquals(2, game.getDeck().get(6).getEnumValue().getValue());
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameDeckSizeTest() {
    game.startGame(cardList, 4, 1, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameCascadePilesTest() {
    game.startGame(game.getDeck(), 2, 1, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void startGameOpenPilesTest() {
    game.startGame(game.getDeck(), 5, 0, true);
  }

  @Test
  public void moveToOpenPileTest() {
    game.startGame(game.getDeck(), 4, 1, false);
    game.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    assertEquals(13, game.getCard(PileType.OPEN,
        0, 0).getEnumValue().getValue());
  }

  @Test
  public void moveToFoundationPileTest() {
    game.startGame(game.getDeck(), 52, 4, false);
    game.move(PileType.CASCADE, 1, 0, PileType.FOUNDATION, 0);
    assertEquals(Value.ACE, game.getCard(PileType.FOUNDATION,
        0, 0).getEnumValue());
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveCascadePileInvalidSourcePileTest() {
    game.startGame(game.getDeck(), 3, 2, false);
    game.move(PileType.CASCADE, 4, 1, PileType.OPEN, 1);
    game.move(PileType.CASCADE, -1, 1, PileType.OPEN, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveCascadePileInvalidCardTest() {
    game.startGame(game.getDeck(), 3, 2, false);
    game.move(PileType.CASCADE, 5, 4, PileType.OPEN, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveOpenPileInvalidSourcePileTest() {
    game.startGame(game.getDeck(), 3, 2, false);
    game.move(PileType.OPEN, 3, 1, PileType.CASCADE, 2);
    game.move(PileType.OPEN, -1, 1, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveOpenInvalidCardTest() {
    game.startGame(game.getDeck(), 3, 2, false);
    game.move(PileType.OPEN, 1, 3, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void moveInvalidFoundationPileTest() {
    game.startGame(game.getDeck(), 4, 1, false);
    game.move(PileType.FOUNDATION, 1, 1, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void isValidMoveCascadePileTest() {
    game.startGame(game.getDeck(), 3, 2, false);
    game.move(PileType.OPEN, 1, 1, PileType.CASCADE, 4);
    game.move(PileType.CASCADE, 1, 1, PileType.CASCADE, -2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void isValidMoveCascadePileValueCardTest() {
    game.startGame(game.getDeck(), 3, 2, false);
    game.move(PileType.OPEN, 1, 7, PileType.CASCADE, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void isValidMoveCascadePileColorTest() {
    game.startGame(game.getDeck(), 3, 2, false);
    game.getCard(PileType.OPEN, 1, 1).getColor().equals(Color.RED);
    game.getCard(PileType.CASCADE, 2, 1).getColor().equals(Color.BLACK);
    game.move(PileType.OPEN, 1, 7, PileType.CASCADE, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void isValidMoveOpenPileTest() {
    game.startGame(game.getDeck(), 3, 2, false);
    game.move(PileType.CASCADE, 1, 1, PileType.OPEN, 4);
    game.move(PileType.CASCADE, 1, 1, PileType.OPEN, -2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void isValidMoveOpenPileFilledTest() {
    game.startGame(game.getDeck(), 3, 1, false);
    game.move(PileType.CASCADE, 1, 1, PileType.OPEN, 1);
    game.move(PileType.CASCADE, 2, 1, PileType.OPEN, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void isValidMoveFoundationPileTest() {
    game.startGame(game.getDeck(), 3, 2, false);
    game.move(PileType.CASCADE, 1, 1, PileType.FOUNDATION, 7);
    game.move(PileType.CASCADE, 1, 1, PileType.FOUNDATION, -2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void isValidMoveFoundationFirstCardTest() {
    game.startGame(game.getDeck(), 3, 2, false);
    game.move(PileType.CASCADE, 2, 1, PileType.FOUNDATION, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void isValidMoveFoundationPileValueCardTest() {
    game.startGame(game.getDeck(), 3, 2, false);
    game.move(PileType.OPEN, 1, 7, PileType.FOUNDATION, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void isValidMoveFoundationPileColorTest() {
    game.startGame(game.getDeck(), 3, 2, false);
    game.move(PileType.CASCADE, 1, 1, PileType.FOUNDATION, 1);
    game.getCard(PileType.FOUNDATION, 1, 1).getColor().equals(Color.RED);
    game.getCard(PileType.CASCADE, 2, 1).getColor().equals(Color.BLACK);
    game.move(PileType.CASCADE, 2, 1, PileType.FOUNDATION, 4);
  }

  @Test
  public void isGameOverFalseTest() {
    game.startGame(game.getDeck(), 4, 2, false);
    assertEquals(false, game.isGameOver());
  }

  @Test(expected = IllegalArgumentException.class)
  public void isGameOverExceptionTest() {
    game.startGame(cardList, 4, 1, false);
    game.isGameOver();
  }

  @Test
  public void getCardTest() {
    game.startGame(game.getDeck(), 4, 1, false);
    assertEquals(new Card(Value.ACE, Suit.HEARTS), game.getCard(PileType.CASCADE,
        1, 0));
    assertEquals(new Card(Value.FOUR, Suit.HEARTS), game.getCard(PileType.CASCADE,
        1, 3));
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCardGameNotStartTest() {
    game.startGame(game.getDeck(), 4, 1, false);
    game.getCard(PileType.CASCADE, -1, -2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCardNotValidCardTest() {
    game.startGame(game.getDeck(), 4, 1, true);
    game.getCard(PileType.CASCADE, 5, 2);
  }

  @Test
  public void getGameStateTest() {
    game.startGame(game.getDeck(), 4, 1, false);

    String gameState = "F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "C1: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
        + "C2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "C3: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
        + "C4: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠";

    assertEquals(gameState, game.getGameState());
  }
}