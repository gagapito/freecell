package cs3500.freecell.hw03;

import static org.junit.Assert.assertEquals;
import cs3500.freecell.hw02.FreecellModel;
import org.junit.Test;
import java.io.StringReader;

/**
 * Tests the FreecellController class.
 */
public class FreecellControllerTest {

  @Test
  public void quitGame() {
    FreecellModel m = new FreecellModel();
    StringBuilder ap = new StringBuilder();
    StringReader rd = new StringReader("q");
    FreecellController c = new FreecellController(rd, ap);
    c.playGame(m.getDeck(), m, 4, 1, false);

    String gameState = "F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "C1: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
        + "C2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "C3: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
        + "C4: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠";

    assertEquals(gameState + "\n" + "\n" + "Game quit prematurely." + "\n",
        ap.toString());
  }

  @Test
  public void testMoveToOpenPile() {
    FreecellModel m = new FreecellModel();
    StringBuilder ap = new StringBuilder();
    StringReader rd = new StringReader("C1 13 O1");
    FreecellController c = new FreecellController(rd, ap);
    c.playGame(m.getDeck(), m, 4, 1, false);

    String gameState = "F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "C1: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
        + "C2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "C3: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
        + "C4: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠";

    String gameState2 = "F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1: K♦\n"
        + "C1: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦\n"
        + "C2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "C3: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
        + "C4: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠";

    assertEquals(gameState + "\n" + "\n" + gameState2 + "\n" + "\n", ap.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidNumCascadesInput() {
    FreecellModel m = new FreecellModel();
    StringBuilder ap = new StringBuilder();
    StringReader rd = new StringReader("C1 1 O1");
    FreecellController c = new FreecellController(rd, ap);
    c.playGame(m.getDeck(), m, 2, 1, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidNumOpensInput() {
    FreecellModel m = new FreecellModel();
    StringBuilder ap = new StringBuilder();
    StringReader rd = new StringReader("q");
    FreecellController c = new FreecellController(rd, ap);
    c.playGame(m.getDeck(), m, 4, -1, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSourcePileInput() {
    FreecellModel m = new FreecellModel();
    StringBuilder ap = new StringBuilder();
    StringReader rd = new StringReader("c1 1 O1");
    FreecellController c = new FreecellController(rd, ap);
    c.playGame(m.getDeck(), m, 4, 1, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidPileNumber() {
    FreecellModel m = new FreecellModel();
    StringBuilder ap = new StringBuilder();
    StringReader rd = new StringReader("C1 -1 O1");
    FreecellController c = new FreecellController(rd, ap);
    c.playGame(m.getDeck(), m, 4, 1, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDestinationPileInput() {
    FreecellModel m = new FreecellModel();
    StringBuilder ap = new StringBuilder();
    StringReader rd = new StringReader("C1 1 Q2");
    FreecellController c = new FreecellController(rd, ap);
    c.playGame(m.getDeck(), m, 4, 1, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMalformedInputsCompleteGame() {
    FreecellModel m = new FreecellModel();
    StringBuilder ap = new StringBuilder();
    StringReader rd = new StringReader("c1 -1 p1");
    FreecellController c = new FreecellController(rd, ap);
    c.playGame(m.getDeck(), m, 4, 1, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInputsProcessedOneAtATimeNotBatch() {
    FreecellModel m = new FreecellModel();
    StringBuilder ap = new StringBuilder();
    StringReader rd = new StringReader("x");
    FreecellController c = new FreecellController(rd, ap);
    c.playGame(m.getDeck(), m, 4, 1, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testQuitCardIndex() {
    FreecellModel m = new FreecellModel();
    StringBuilder ap = new StringBuilder();
    StringReader rd = new StringReader("C1 q O1");
    FreecellController c = new FreecellController(rd, ap);
    c.playGame(m.getDeck(), m, 4, 1, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInputBCardIndex() {
    FreecellModel m = new FreecellModel();
    StringBuilder ap = new StringBuilder();
    StringReader rd = new StringReader("c1 B p1");
    FreecellController c = new FreecellController(rd, ap);
    c.playGame(m.getDeck(), m, 4, 1, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testOutofBoundsDestinationPileIndex() {
    FreecellModel m = new FreecellModel();
    StringBuilder ap = new StringBuilder();
    StringReader rd = new StringReader("C1 13 O7");
    FreecellController c = new FreecellController(rd, ap);
    c.playGame(m.getDeck(), m, 4, 1, false);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInValidCascadesAndInValidOpenPilesFromController() {
    FreecellModel m = new FreecellModel();
    StringBuilder ap = new StringBuilder();
    StringReader rd = new StringReader("C2 13 O2");
    FreecellController c = new FreecellController(rd, ap);
    c.playGame(m.getDeck(), m, 3, -1, false);
  }
}

