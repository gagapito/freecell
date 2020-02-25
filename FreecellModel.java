package cs3500.freecell.hw02;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.HashSet;
import java.util.Set;


/**
 * The model of Freecell that contains a standard deck.
 */
public class FreecellModel implements FreecellOperations<Card> {

  protected ArrayList<List<Card>> foundationPiles;
  protected ArrayList<List<Card>> cascadePiles;
  protected ArrayList<List<Card>> openPiles;

  /**
   * Constructor for FreecellModel.
   */
  public FreecellModel() {
    this.foundationPiles = new ArrayList<>();
    this.cascadePiles = new ArrayList<>();
    this.openPiles = new ArrayList<>();
  }

  @Override
  public List<Card> getDeck() {
    List<Card> deck = new ArrayList<Card>();
    for (Value v : Value.values()) {
      for (Suit s : Suit.values()) {
        deck.add(new Card(v, s));
      }
    }
    return deck;
  }

  private boolean hasGameStarted(List<Card> deck) {
    return deck != null;
  }

  @Override
  public void startGame(List<Card> deck, int numCascadePiles, int numOpenPiles, boolean shuffle) {
    if (this.isDuplicates(deck) || deck.size() != 52 || deck == null) {
      throw new IllegalArgumentException("Error: Invalid deck.");
    } else if (numCascadePiles < 4) {
      throw new IllegalArgumentException("Error: Freecell must have at least 4 cascade piles.");
    } else if (numOpenPiles < 1) {
      throw new IllegalArgumentException("Error: Freecell must have at least 1 open pile.");
    }

    // initialize the piles
    foundationPiles = new ArrayList<>(4);
    cascadePiles = new ArrayList<>(numCascadePiles);
    openPiles = new ArrayList<>(numOpenPiles);

    for (int i = 0; i < 4; i++) {
      ArrayList<Card> fPile = new ArrayList<Card>();
      foundationPiles.add(fPile);
    }

    for (int i = 0; i < numCascadePiles; i++) {
      ArrayList cPile = new ArrayList<Card>();
      this.cascadePiles.add(cPile);
    }

    for (int i = 0; i < numOpenPiles; i++) {
      ArrayList<Card> oPile = new ArrayList<Card>();
      this.openPiles.add(oPile);
    }

    if (shuffle) {
      shuffle(deck);
    }

    // deals out the cards in the cascade piles
    for (int i = 0; i < numCascadePiles; i++) {
      for (int j = i; j < deck.size(); j += numCascadePiles) {
        this.cascadePiles.get(i).add(deck.get(j));
      }
    }
  }

  private boolean isDuplicates(List<Card> deck) {
    final Set<Card> set = new HashSet<Card>();

    for (Card c : deck) {
      if (!set.add(c)) {
        return true;
      }
    }
    return false;
  }

  private void shuffle(List<Card> deck) {
    Random r = new Random();
    for (int i = 0; i < deck.size() - 1; i++) {
      int rand = r.nextInt(deck.size() - 1);
      Card temp = deck.get(i);
      deck.set(i, deck.get(rand));
      deck.set(rand, temp);
    }
  }

  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
      int destPileNumber) {
    if (!hasGameStarted(this.getDeck())) {
      throw new IllegalStateException("Freecell has not started.");
    } else if (source == null || destination == null) {
      throw new IllegalArgumentException("Error: Invalid arguments.");
    } else if (source.equals(PileType.FOUNDATION)) {
      throw new IllegalArgumentException("Error: Cannot move from foundation piles.");
    }

    Card moveCard = null;
    ArrayList<List<Card>> sourcePile = null;
    ArrayList<List<Card>> destPile = null;

    // gets the source
    if (source.equals(PileType.CASCADE)) {
      moveCard = cascadePiles.get(pileNumber).get(cardIndex);
      sourcePile = cascadePiles;
    } else if (source.equals(PileType.OPEN)) {
      moveCard = openPiles.get(pileNumber).get(cardIndex);
      sourcePile = openPiles;
    }

    // checks if the source is valid
    if (!isSourceValid(moveCard, source, pileNumber, cardIndex)) {
      throw new IllegalArgumentException("Error: Source is not valid.");
    }

    // checks if the destination is valid
    if (!isDestValid(moveCard, destination, destPileNumber)) {
      throw new IllegalArgumentException("Error: Destination is not valid.");
    }

    // gets the destination
    if (destination.equals(PileType.CASCADE)) {
      if (cascadePiles.get(destPileNumber).size() == 0) {
        sourcePile.get(pileNumber).remove(moveCard);
        cascadePiles.get(destPileNumber).add(moveCard);
      } else {
        destPile = cascadePiles;
      }
    } else if (destination.equals(PileType.OPEN)) {
      destPile = openPiles;
    } else if (destination.equals(PileType.FOUNDATION)) {
      if (foundationPiles.get(destPileNumber).isEmpty()) {
        if (moveCard.getEnumValue().getValue() == 1) {
          sourcePile.get(pileNumber).remove(moveCard);
          foundationPiles.get(destPileNumber).add(moveCard);
          return;
        } else {
          destPile = foundationPiles;
        }
      }
    }

    // do the move
    sourcePile.get(pileNumber).remove(moveCard);
    destPile.get(destPileNumber).add(moveCard);
  }

  private boolean isSourceValid(Card moveCard, PileType source, int pileNumber, int cardIndex) {

    switch (source) {
      case CASCADE:
        if ((cardIndex > cascadePiles.get(pileNumber).size()) || cardIndex < 0) {
          return false;
        } else {
          return moveCard.equals(cascadePiles.get(pileNumber)
              .get(cascadePiles.get(pileNumber).size() - 1));
        }

      case OPEN:
        if ((cardIndex > openPiles.get(pileNumber).size()) || cardIndex < 0) {
          return false;
        } else {
          return moveCard.equals(openPiles.get(pileNumber)
              .get(openPiles.get(pileNumber).size() - 1));
        }

      default:
        return true;
    }
  }

  private boolean isDestValid(Card moveCard, PileType destination, int destPileNumber) {

    switch (destination) {
      case CASCADE:
        if (destPileNumber >= cascadePiles.size() || destPileNumber < 0) {
          throw new IllegalArgumentException("Error: Invalid destination pile number.");
        } else if (cascadePiles.get(destPileNumber).size() == 0) {
          return true;
        }

        Card cascadePileTop = cascadePiles.get(destPileNumber)
            .get(cascadePiles.get(destPileNumber).size() - 1);

        if ((cascadePileTop.getEnumValue().getValue() - 1) != moveCard.getEnumValue()
            .getValue()) {
          throw new IllegalArgumentException(
              "Error: The value of the card must be one lower than the one on the top.");
        } else if (cascadePileTop.getColor().equals(moveCard.getColor())) {
          throw new IllegalArgumentException(
              "Error: The suit must be the opposite color of the one on top.");
        } else {
          return true;
        }

      case OPEN:
        if (destPileNumber >= openPiles.size() || destPileNumber < 0) {
          throw new IllegalArgumentException("Error: Invalid destination pile number.");
        } else if (openPiles.get(destPileNumber).size() > 0) {
          throw new IllegalArgumentException("Error: There is a card already in the open pile.");
        } else {
          return true;
        }

      case FOUNDATION:
        if (destPileNumber >= 4 || destPileNumber < 0) {
          throw new IllegalArgumentException("Error: Invalid destination pile number.");
        } else if (foundationPiles.get(destPileNumber).isEmpty()) {
          if (moveCard.getEnumValue().getValue() == 1) {
            return true;
          }
        }

        Card foundationPileTop = foundationPiles.get(destPileNumber)
            .get(foundationPiles.get(destPileNumber).size() - 1);

        if ((foundationPileTop.getEnumValue().getValue() - 1)
            == moveCard.getEnumValue().getValue()) {
          throw new IllegalArgumentException(
              "Error: The value of the card must be one lower than the one on the top.");
        } else if (!foundationPileTop.getEnumSuit().equals(moveCard.getEnumSuit())) {
          throw new IllegalArgumentException(
              "Error: The suit must be the same color of the one on top.");
        } else {
          return true;
        }

      default:
        return true;
    }
  }

  @Override
  public boolean isGameOver() {
    int finalCardNum = 0;

    for (int i = 0; i < foundationPiles.size(); i++) {
      finalCardNum = finalCardNum + foundationPiles.get(i).size();
    }
    return finalCardNum == 52;
  }

  @Override
  public Card getCard(PileType pile, int pileNumber, int cardIndex) {
    if (!hasGameStarted(this.getDeck())) {
      throw new IllegalStateException("Freecell has not started.");
    } else if (pileNumber < 0 || cardIndex < 0) {
      throw new IllegalArgumentException("Invalid inputs.");
    }

    Card currCard;

    switch (pile) {
      case FOUNDATION:
        if (this.isValidPile(pileNumber, this.foundationPiles)
            && this.isValidIndex(pileNumber, cardIndex, this.foundationPiles)) {
          currCard = foundationPiles.get(pileNumber).get(cardIndex);
        } else {
          throw new IllegalArgumentException("Cannot get card.");
        }
        break;

      case CASCADE:
        if (this.isValidPile(pileNumber, this.cascadePiles)
            && this.isValidIndex(pileNumber, cardIndex, this.cascadePiles)) {
          currCard = cascadePiles.get(pileNumber).get(cardIndex);
        } else {
          throw new IllegalArgumentException("Cannot get card.");
        }
        break;

      case OPEN:
        if (this.isValidPile(pileNumber, this.openPiles)
            && this.isValidIndex(pileNumber, cardIndex, this.openPiles)) {
          currCard = openPiles.get(pileNumber).get(cardIndex);
        } else {
          throw new IllegalArgumentException("Cannot get card.");
        }
        break;
      default:
        throw new IllegalArgumentException("Not a valid pile.");
    }
    return currCard;
  }

  private boolean isValidPile(int pileNumber, ArrayList<List<Card>> pile) {
    return pileNumber >= 0 && pileNumber < pile.size();
  }

  private boolean isValidIndex(int pileNumber, int cardIndex, ArrayList<List<Card>> pile) {
    return cardIndex >= 0 && cardIndex < pile.get(pileNumber).size();
  }

  @Override
  public String getGameState() {
    if (this.foundationPiles.isEmpty() && this.cascadePiles.isEmpty() && this.openPiles
        .isEmpty()) {
      return "";
    }

    String gameState = "";

    // foundation piles string
    for (int i = 0; i < 4; i++) {
      gameState = gameState + String.format("F%d:", i + 1);
      int pileLength = foundationPiles.get(i).size();
      for (int j = 0; j < pileLength; j++) {
        if (j == pileLength - 1) {
          gameState = gameState + " " + foundationPiles.get(i).get(j).toString();
        } else {
          gameState = gameState + " " + foundationPiles.get(i).get(j).toString() + ",";
        }
      }
      gameState = gameState + "\n";
    }

    // open piles string
    for (int i = 0; i < this.openPiles.size(); i++) {
      gameState = gameState + String.format("O%d:", i + 1);
      int pileLength = openPiles.get(i).size();
      for (int j = 0; j < pileLength; j++) {
        if (j == pileLength - 1) {
          gameState = gameState + " " + openPiles.get(i).get(j).toString();
        } else {
          gameState = gameState + " " + openPiles.get(i).get(j).toString() + ",";
        }
      }
      gameState = gameState + "\n";
    }

    // cascade piles string
    for (int i = 0; i < this.cascadePiles.size(); i++) {
      gameState = gameState + String.format("C%d:", i + 1);
      int pileLength = cascadePiles.get(i).size();
      for (int j = 0; j < pileLength; j++) {
        if (j == pileLength - 1) {
          gameState = gameState + " " + cascadePiles.get(i).get(j).toString();
        } else {
          gameState = gameState + " " + cascadePiles.get(i).get(j).toString() + ",";
        }
      }
      if (i == cascadePiles.size() - 1) {
        continue;
      } else {

        gameState = gameState + "\n";
      }
    }
    return gameState;
  }
}
