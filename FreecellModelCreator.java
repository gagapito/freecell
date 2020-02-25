package cs3500.freecell.hw04;

import cs3500.freecell.hw02.FreecellModel;

/**
 * Creates a Freecell game model. There are two types of models that can be
 * created, a single move model or a multi-move model.
 */
public class FreecellModelCreator {
  /**
   * Type of models for a Freecell game. <br>
   *
   * <p> SingleMove: this model allows the player to move one card at a time. <br>
   * MultiMove: this model allows the player to move multiple cards from one cascade
   * pile to another cascade pile. <br> </p>
   */
  public enum GameType {
    SINGLEMOVE, MULTIMOVE
  }

  /**
   * Creates a single move or multi move model of Freecell.
   *
   * @param type the type of game of Freecell
   * @return the type of game of Freecell created either SINGLEMOVE or MULTIMOVE
   */
  public static FreecellModel create(GameType type) {
    if (type == GameType.MULTIMOVE) {
      return new FreecellMultiMoveModel();
    } else {
      return new FreecellModel();
    }
  }
}
