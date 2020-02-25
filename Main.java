package cs3500.freecell.hw03;

import java.io.InputStreamReader;
import cs3500.freecell.hw02.FreecellModel;
import cs3500.freecell.hw04.FreecellMultiMoveModel;

/**
 * Run a Freecell game interactively on the console.
 */
public class Main {
  /**
   * Run a Freecell game interactively on the console.
   */
  public static void main(String[] args) {
    new FreecellController(new InputStreamReader(System.in),
        System.out).playGame(new FreecellModel().getDeck(), new FreecellMultiMoveModel(),
        6, 4, false);
  }
}

