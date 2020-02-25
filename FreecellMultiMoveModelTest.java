package cs3500.freecell.hw04;

import static org.junit.Assert.assertEquals;

import cs3500.freecell.hw02.FreecellModel;
import cs3500.freecell.hw02.PileType;
import cs3500.freecell.hw03.FreecellController;
import java.io.StringReader;
import org.junit.Test;

/**
 * Test for the Freecell Multi-Move Model.
 */
public class FreecellMultiMoveModelTest {

  FreecellModelCreator creator = new FreecellModelCreator();
  FreecellModel multiMoveModel = new FreecellMultiMoveModel();

  @Test(expected = IllegalArgumentException.class)
  public void testSingleMoveCreator() {
    FreecellModel singleMoveModel = creator.create(FreecellModelCreator.GameType.SINGLEMOVE);
    singleMoveModel.startGame(singleMoveModel.getDeck(), 4,
        2, false);
    singleMoveModel.move(PileType.CASCADE, 1, 12,
        PileType.CASCADE, 7);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMultiMoveCreator() {
    FreecellModel multiMoveModel = creator.create(FreecellModelCreator.GameType.MULTIMOVE);
    multiMoveModel.startGame(multiMoveModel.getDeck(), 4,
        2, false);
    multiMoveModel.move(PileType.CASCADE, 5, 13,
        PileType.CASCADE, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMultiMoveInvalidPileNum() {
    multiMoveModel.startGame(multiMoveModel.getDeck(), 5, 2, false);
    multiMoveModel.move(PileType.CASCADE, 7, 1,
        PileType.CASCADE, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMultiMoveInvalidCardIdx() {
    multiMoveModel.startGame(multiMoveModel.getDeck(), 5, 2, false);
    multiMoveModel.move(PileType.CASCADE, 3, -1, PileType.CASCADE, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMultiMoveInvalidDestPileNum() {
    multiMoveModel.startGame(multiMoveModel.getDeck(), 5, 2, false);
    multiMoveModel.move(PileType.CASCADE, 2, 1, PileType.CASCADE, 7);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMultiMoveColorDontAlternate() {
    multiMoveModel.startGame(multiMoveModel.getDeck(), 4, 2, false);
    multiMoveModel.move(PileType.CASCADE, 1, 13, PileType.CASCADE, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMultiMoveValuesDontDescend() {
    multiMoveModel.startGame(multiMoveModel.getDeck(), 4, 2, false);
    multiMoveModel.move(PileType.CASCADE, 1, 2, PileType.CASCADE, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testMultiMoveTooManyCardsMoved() {
    multiMoveModel.startGame(multiMoveModel.getDeck(), 8, 3, false);
    multiMoveModel.move(PileType.CASCADE, 0, 6, PileType.OPEN, 0);
    multiMoveModel.move(PileType.CASCADE, 1, 6, PileType.OPEN, 1);
    multiMoveModel.move(PileType.CASCADE, 2, 6, PileType.OPEN, 2);
    multiMoveModel.move(PileType.CASCADE, 7, 6, PileType.CASCADE, 1);
    // num of moves according to formula should be 4,
    // so given that this move method will throw an exception
    multiMoveModel.move(PileType.CASCADE, 6, 6, PileType.CASCADE, 0);
  }

  @Test
  public void testMultiMoveMultipleCards() {
    multiMoveModel.startGame(multiMoveModel.getDeck(), 52, 3, false);
    multiMoveModel.move(PileType.CASCADE, 0, 0, PileType.CASCADE, 6);
    multiMoveModel.move(PileType.CASCADE, 6, 0, PileType.CASCADE, 9);
    multiMoveModel.move(PileType.CASCADE, 9, 0, PileType.CASCADE, 14);

    String gameState = "F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "C1:\n"
        + "C2: A♥\n"
        + "C3: A♣\n"
        + "C4: A♠\n"
        + "C5: 2♦\n"
        + "C6: 2♥\n"
        + "C7:\n"
        + "C8: 2♠\n"
        + "C9: 3♦\n"
        + "C10:\n"
        + "C11: 3♣\n"
        + "C12: 3♠\n"
        + "C13: 4♦\n"
        + "C14: 4♥\n"
        + "C15: 4♣, 3♥, 2♣, A♦\n"
        + "C16: 4♠\n"
        + "C17: 5♦\n"
        + "C18: 5♥\n"
        + "C19: 5♣\n"
        + "C20: 5♠\n"
        + "C21: 6♦\n"
        + "C22: 6♥\n"
        + "C23: 6♣\n"
        + "C24: 6♠\n"
        + "C25: 7♦\n"
        + "C26: 7♥\n"
        + "C27: 7♣\n"
        + "C28: 7♠\n"
        + "C29: 8♦\n"
        + "C30: 8♥\n"
        + "C31: 8♣\n"
        + "C32: 8♠\n"
        + "C33: 9♦\n"
        + "C34: 9♥\n"
        + "C35: 9♣\n"
        + "C36: 9♠\n"
        + "C37: 10♦\n"
        + "C38: 10♥\n"
        + "C39: 10♣\n"
        + "C40: 10♠\n"
        + "C41: J♦\n"
        + "C42: J♥\n"
        + "C43: J♣\n"
        + "C44: J♠\n"
        + "C45: Q♦\n"
        + "C46: Q♥\n"
        + "C47: Q♣\n"
        + "C48: Q♠\n"
        + "C49: K♦\n"
        + "C50: K♥\n"
        + "C51: K♣\n"
        + "C52: K♠";

    assertEquals(gameState, multiMoveModel.getGameState());
  }

  @Test
  public void testMultiMoveSingleCard() {
    multiMoveModel.startGame(multiMoveModel.getDeck(), 6, 4, false);
    multiMoveModel.move(PileType.CASCADE, 2, 8, PileType.OPEN, 0);
    multiMoveModel.move(PileType.CASCADE, 2, 7, PileType.OPEN, 1);
    multiMoveModel.move(PileType.CASCADE, 4, 7, PileType.OPEN, 2);
    multiMoveModel.move(PileType.CASCADE, 2, 6, PileType.CASCADE, 4);

    String gameState = "F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1: K♣\n"
        + "O2: Q♦\n"
        + "O3: Q♣\n"
        + "O4:\n"
        + "C1: A♦, 2♣, 4♦, 5♣, 7♦, 8♣, 10♦, J♣, K♦\n"
        + "C2: A♥, 2♠, 4♥, 5♠, 7♥, 8♠, 10♥, J♠, K♥\n"
        + "C3: A♣, 3♦, 4♣, 6♦, 7♣, 9♦\n"
        + "C4: A♠, 3♥, 4♠, 6♥, 7♠, 9♥, 10♠, Q♥, K♠\n"
        + "C5: 2♦, 3♣, 5♦, 6♣, 8♦, 9♣, J♦, 10♣\n"
        + "C6: 2♥, 3♠, 5♥, 6♠, 8♥, 9♠, J♥, Q♠";

    assertEquals(gameState, multiMoveModel.getGameState());
  }

  @Test
  public void testMoveMultiController() {
    FreecellModel m = new FreecellMultiMoveModel();
    StringBuilder ap = new StringBuilder();
    StringReader rd = new StringReader("C3 9 O1 C3 8 O2 C5 8 O3 C3 7 C5");
    FreecellController c = new FreecellController(rd, ap);
    c.playGame(m.getDeck(), m, 6, 4, false);

    String gameState = "F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1: K♣\n"
        + "O2: Q♦\n"
        + "O3: Q♣\n"
        + "O4:\n"
        + "C1: A♦, 2♣, 4♦, 5♣, 7♦, 8♣, 10♦, J♣, K♦\n"
        + "C2: A♥, 2♠, 4♥, 5♠, 7♥, 8♠, 10♥, J♠, K♥\n"
        + "C3: A♣, 3♦, 4♣, 6♦, 7♣, 9♦\n"
        + "C4: A♠, 3♥, 4♠, 6♥, 7♠, 9♥, 10♠, Q♥, K♠\n"
        + "C5: 2♦, 3♣, 5♦, 6♣, 8♦, 9♣, J♦, 10♣\n"
        + "C6: 2♥, 3♠, 5♥, 6♠, 8♥, 9♠, J♥, Q♠";

    assertEquals(gameState, m.getGameState());
  }

  @Test
  public void testMove2To6Cards10CascadesSomeEmptyShouldWork() {
    multiMoveModel.startGame(multiMoveModel.getDeck(), 53, 3, false);
    multiMoveModel.move(PileType.CASCADE, 0, 0, PileType.CASCADE, 6);
    multiMoveModel.move(PileType.CASCADE, 6, 0, PileType.CASCADE, 9);
    multiMoveModel.move(PileType.CASCADE, 9, 0, PileType.CASCADE, 14);
    multiMoveModel.move(PileType.CASCADE, 14, 0, PileType.CASCADE, 17);
    multiMoveModel.move(PileType.CASCADE, 17, 0, PileType.CASCADE, 23);

    String gameState = "F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "C1:\n"
        + "C2: A♥\n"
        + "C3: A♣\n"
        + "C4: A♠\n"
        + "C5: 2♦\n"
        + "C6: 2♥\n"
        + "C7:\n"
        + "C8: 2♠\n"
        + "C9: 3♦\n"
        + "C10:\n"
        + "C11: 3♣\n"
        + "C12: 3♠\n"
        + "C13: 4♦\n"
        + "C14: 4♥\n"
        + "C15:\n"
        + "C16: 4♠\n"
        + "C17: 5♦\n"
        + "C18:\n"
        + "C19: 5♣\n"
        + "C20: 5♠\n"
        + "C21: 6♦\n"
        + "C22: 6♥\n"
        + "C23: 6♣\n"
        + "C24: 6♠, 5♥, 4♣, 3♥, 2♣, A♦\n"
        + "C25: 7♦\n"
        + "C26: 7♥\n"
        + "C27: 7♣\n"
        + "C28: 7♠\n"
        + "C29: 8♦\n"
        + "C30: 8♥\n"
        + "C31: 8♣\n"
        + "C32: 8♠\n"
        + "C33: 9♦\n"
        + "C34: 9♥\n"
        + "C35: 9♣\n"
        + "C36: 9♠\n"
        + "C37: 10♦\n"
        + "C38: 10♥\n"
        + "C39: 10♣\n"
        + "C40: 10♠\n"
        + "C41: J♦\n"
        + "C42: J♥\n"
        + "C43: J♣\n"
        + "C44: J♠\n"
        + "C45: Q♦\n"
        + "C46: Q♥\n"
        + "C47: Q♣\n"
        + "C48: Q♠\n"
        + "C49: K♦\n"
        + "C50: K♥\n"
        + "C51: K♣\n"
        + "C52: K♠\n"
        + "C53:";

    assertEquals(gameState, multiMoveModel.getGameState());
  }

  @Test
  public void testMoveToFoundationPileMultiMove() {
    FreecellModel m = new FreecellMultiMoveModel();
    StringBuilder ap = new StringBuilder();
    StringReader rd = new StringReader("C1 1 F2 C2 1 C7 C7 1 C10");
    FreecellController c = new FreecellController(rd, ap);
    c.playGame(m.getDeck(), m, 52, 3, false);

    String gameState = "F1:\n"
        + "F2: A♦\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "C1:\n"
        + "C2:\n"
        + "C3: A♣\n"
        + "C4: A♠\n"
        + "C5: 2♦\n"
        + "C6: 2♥\n"
        + "C7:\n"
        + "C8: 2♠\n"
        + "C9: 3♦\n"
        + "C10: 3♥, 2♣, A♥\n"
        + "C11: 3♣\n"
        + "C12: 3♠\n"
        + "C13: 4♦\n"
        + "C14: 4♥\n"
        + "C15: 4♣\n"
        + "C16: 4♠\n"
        + "C17: 5♦\n"
        + "C18: 5♥\n"
        + "C19: 5♣\n"
        + "C20: 5♠\n"
        + "C21: 6♦\n"
        + "C22: 6♥\n"
        + "C23: 6♣\n"
        + "C24: 6♠\n"
        + "C25: 7♦\n"
        + "C26: 7♥\n"
        + "C27: 7♣\n"
        + "C28: 7♠\n"
        + "C29: 8♦\n"
        + "C30: 8♥\n"
        + "C31: 8♣\n"
        + "C32: 8♠\n"
        + "C33: 9♦\n"
        + "C34: 9♥\n"
        + "C35: 9♣\n"
        + "C36: 9♠\n"
        + "C37: 10♦\n"
        + "C38: 10♥\n"
        + "C39: 10♣\n"
        + "C40: 10♠\n"
        + "C41: J♦\n"
        + "C42: J♥\n"
        + "C43: J♣\n"
        + "C44: J♠\n"
        + "C45: Q♦\n"
        + "C46: Q♥\n"
        + "C47: Q♣\n"
        + "C48: Q♠\n"
        + "C49: K♦\n"
        + "C50: K♥\n"
        + "C51: K♣\n"
        + "C52: K♠";

    assertEquals(gameState, m.getGameState());
  }
}
