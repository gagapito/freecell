package cs3500.freecell.hw03;

import cs3500.freecell.hw02.FreecellOperations;
import cs3500.freecell.hw02.PileType;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * The controller for interacting with a game of Freecell.
 */
public class FreecellController implements IFreecellController {

  private final Readable rd;
  private final Appendable ap;

  /**
   * The constructor for the Freecell Controller.
   *
   * @param rd the readable characters inputted by the console
   * @param ap the result of the character after going through the console
   */
  public FreecellController(Readable rd, Appendable ap) {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("Error: Readable and Appendable can't be null");
    }
    this.rd = rd;
    this.ap = ap;
  }

  @Override
  public void playGame(List deck, FreecellOperations model, int numCascades, int numOpens,
      boolean shuffle) {

    Scanner scan = new Scanner(this.rd);

    this.invalidParameters(deck, model, numCascades, numOpens);

    try {
      model.startGame(deck, numCascades, numOpens, shuffle);
    } catch (IllegalArgumentException iae) {
      this.gameMessage("Could not start game.");
      return;
    }

    this.gameMessage(model.getGameState() + "\n");

    while (scan.hasNext()) {

      String next = scan.next();

      // checks if the string indicates a quit game
      if (next.equals("q") || next.equals("Q")) {
        this.gameMessage("Game quit prematurely.");
        return;
      }

      // checks for valid source pile input
      if (next.length() < 2) {
        throw new IllegalArgumentException("Error: Invalid source pile.");
      }

      PileType sourcePileType = getPileType(next.charAt(0));

      if (!validIdx(next.substring(1))) {
        this.gameMessage("Error: Input a valid source pile.");
        next = scan.next();
      }

      int sourcePileNum = Integer.parseInt(next.substring(1));

      // check for the card index input
      next = scan.next();
      if (!validIdx(next)) {
        this.gameMessage("Error: Input a valid card index.");
        next = scan.next();
      }

      int cardIdx = Integer.parseInt(next);

      // check for valid destination pile input
      next = scan.next();
      if (next.length() < 2) {
        throw new IllegalArgumentException("Error: Invalid destination pile.");
      }

      PileType destPileType = getPileType(next.charAt(0));

      if (!validIdx(next.substring(1))) {
        this.gameMessage("Error: Input a valid destination pile.");
        next = scan.next();
      }

      int destPileNum = Integer.parseInt(next.substring(1));

      // moves the card
      try {
        model.move(sourcePileType, (sourcePileNum - 1),
            (cardIdx - 1), destPileType, (destPileNum - 1));
      } catch (IllegalStateException e) {
        this.gameMessage("Invalid move. Try again."
            + " " + "The input given is not valid character or number.");
        continue;
      }

      // return game state after move
      this.gameMessage(model.getGameState() + "\n");
    }

    // is the game finished
    if (model.isGameOver()) {
      this.gameMessage(model.getGameState() + "\n" + "Game over.");
      scan.close();
      return;
    }
  }

  private void invalidParameters(List deck, FreecellOperations model, int numCascades,
      int numOpens) {
    if (deck == null) {
      throw new IllegalArgumentException("Error: Invalid deck.");
    } else if (model == null) {
      throw new IllegalArgumentException("Error: Invalid model.");
    } else if (numCascades < 4 || numOpens < 1) {
      throw new IllegalArgumentException("Error: Invalid pile numbers.");
    }
  }

  private void gameMessage(String str) {
    try {
      this.ap.append(str + "\n");
    } catch (IOException e) {
      throw new IllegalStateException("Append failed.");
    }
  }

  private boolean validIdx(String s) {
    try {
      Integer.parseInt(s);
    } catch (NumberFormatException num) {
      return true;
    }
    return true;
  }

  private PileType getPileType(char pileType) {
    switch (pileType) {
      case 'C':
        return PileType.CASCADE;
      case 'F':
        return PileType.FOUNDATION;
      case 'O':
        return PileType.OPEN;
      default:
        throw new IllegalArgumentException("Invalid pile character. Try again.");
    }
  }
}
