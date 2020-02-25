package cs3500.freecell.hw04;

import cs3500.freecell.hw02.FreecellModel;
import cs3500.freecell.hw02.PileType;
import cs3500.freecell.hw02.Card;
import java.util.List;

/**
 * Model of a multi-move game of Freecell.
 * This model contains all of the methods of Freecell as well as supports a version of the game
 * where the player can move more than one card.
 */
public class FreecellMultiMoveModel extends FreecellModel {

  /**
   * Constructor for a multi-move Freecell model.
   */
  public FreecellMultiMoveModel() {
    super();
  }

  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
      int destPileNumber) {

    if (source == PileType.CASCADE && destination == PileType.CASCADE) {

      if (invalidParameters(pileNumber, cardIndex, destPileNumber)) {
        throw new IllegalArgumentException("Error: Invalid parameters.");
      }

      this.isValidBuild(cascadePiles.get(pileNumber), cardIndex,
          cascadePiles.get(destPileNumber).get(cascadePiles.get(destPileNumber).size() - 1));

      this.maxNumCardsMove(cascadePiles.get(pileNumber).size() - cardIndex);

      for (int i = cardIndex; i < cascadePiles.get(pileNumber).size(); i++) {
        Card currCard = cascadePiles.get(pileNumber).get(i);
        cascadePiles.get(destPileNumber).add(currCard);
        cascadePiles.get(pileNumber).remove(i);
        i -= 1;
      }
    } else {
      super.move(source, pileNumber, cardIndex, destination, destPileNumber);
    }
  }

  private boolean invalidParameters(int pileNumber, int cardIndex, int destPileNumber) {
    return pileNumber >= cascadePiles.size() || cardIndex < 0
        || destPileNumber >= cascadePiles.size();
  }

  private void isValidBuild(List<Card> currPile, int cardIndex, Card destCard) {

    Card currCard;
    Card nextCard;

    for (int i = cardIndex - 1; i < currPile.size() - 2; i++) {
      if (i == (cardIndex - 1)) {
        currCard = destCard;
        nextCard = currPile.get(cardIndex);
      } else {
        currCard = currPile.get(i);
        nextCard = currPile.get(i + 1);
      }

      if (currCard.getColor().equals(nextCard.getColor())) {
        throw new IllegalArgumentException("Error: Colors must alternate between cards.");
      } else if (currCard.getEnumValue().getValue() - nextCard.getEnumValue().getValue() != 1) {
        throw new IllegalArgumentException("Error: Values must be descending.");
      }
    }
  }

  private void maxNumCardsMove(int numCards) {

    int numOpenPiles = 0;
    int numCascadePiles = 0;

    for (int i = 0; i < openPiles.size(); i++) {
      if (openPiles.get(i).isEmpty()) {
        numOpenPiles++;
      }
    }

    for (int i = 0; i < cascadePiles.size(); i++) {
      if (cascadePiles.get(i).isEmpty()) {
        numCascadePiles++;
      }
    }

    double maxNumCards = ((numOpenPiles + 1) * Math.pow(2, numCascadePiles));

    if (numCards > maxNumCards) {
      throw new IllegalArgumentException("Error: Too many cards are being moved.");
    }
  }
}
