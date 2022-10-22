package poker.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableSet;
import java.util.TreeSet;

import poker.error.HandSizeError;

/**
 * This class represents a full hand of five Poker cards. When the hand is initialized or changed, the hand's rank 
 * gets evaluated. CardHands can be compared (ranked) against each other to figure out which hand wins.
 *
 */
public class CardHand {

	private NavigableSet<Card> hand = new TreeSet<Card>();
	private Rank rank = Rank.HIGH_CARD;
	
	/************************ Constructors ****************************/
	
	/**
	 * Construct a hand of Poker cards out of five individual Card objects
	 * @param card1 card object #1
	 * @param card2 card object #2
	 * @param card3 card object #3
	 * @param card4 card object #4
	 * @param card5 card object #5
	 */
	public CardHand(final Card card1, final Card card2, final Card card3, final Card card4, final Card card5) {
		Card[] cards = new Card[5];
		cards[0] = card1;
		cards[1] = card2;
		cards[2] = card3;
		cards[3] = card4;
		cards[4] = card5;
		try {
			resetHand(cards);
		} catch (HandSizeError e) {
			// length of card array is guaranteed to be 5 here, so this error condition cannot occur 
		}
	}
	
	/**
	 * Construct a hand or Poker card out of a Card array. The array is expected to provide exactly five cards.
	 * 
	 * @param cards A Card array of length 5
	 * @throws HandSizeError is thrown when array has less or more than 5 Card objects
	 */
	public CardHand(final Card[] cards) throws HandSizeError {
		resetHand(cards);
	}
	
	/**
	 * Construct a hand or Poker card out of a Card list. The list is expected to provide exactly five cards.
	 * 
	 * @param cards A Card list of length 5
	 * @throws HandSizeError is thrown when the list has less or more than 5 Card objects
	 */
	public CardHand(final List<Card> cards) throws HandSizeError {
		Card[] cardsArray = cards.toArray(new Card[0]);
		resetHand(cardsArray);
	}
	
	/************************ Public methods ***********************/
	
	/**
	 * 
	 */
	public Card getHighestCard() {
		return hand.last();
	}
	
	/**
	 * Compares another hand to this object's hand and returns the winner. Also prints a message about the winning hand to 
	 * the standard output console.
	 * 
	 * @param otherHand The other hand to be ranked against the current object
	 * @return the winning hand
	 */
	public CardHand rankAgainst(CardHand otherHand) {
		
		int comparisonResult = this.getRank().compareTo(otherHand.getRank());
		
		// if both hands have the same rank, we need to compare again, this time for the relevant highest card values
		if(comparisonResult == 0) {
			
			switch(this.getRank()) {
				case FOUR_OF_A_KIND:
					
					Iterator<Card> iterator = hand.descendingIterator();
					int highestRelevantValueForThis = findHighestPairValue(iterator);
					iterator = otherHand.hand.descendingIterator();
					int highestRelevantValueForOther = findHighestPairValue(iterator);
					comparisonResult = highestRelevantValueForThis - highestRelevantValueForOther;					
					break;
					
				default:
					comparisonResult = this.getHighestCard().getIntValue() - otherHand.getHighestCard().getIntValue();
			}
		}

		if(comparisonResult > 0) {
			System.out.println("Hand 1 has won, holding a " + this.getRank() + " with cards " + this.toString());
			return this;
		} else if(comparisonResult < 0) {
			System.out.println("Hand 2 has won, holding a " + otherHand.getRank() + " with cards " + otherHand.toString());
			return otherHand;
		} else {
			return null;
		}
	}
	

	/**
	 * Get the current rank of the five cards on this hand.
	 * 
	 * @return The Rank that this hand currently evaluates to
	 */
	public Rank getRank() {
		return rank;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[ ");
		for ( Card card : hand ) {
			sb.append("(");
			sb.append(card.toString());
			sb.append(") ");
		}
		sb.append("]");
		return sb.toString();
	}
	
	/************************ Private methods *************************/
	
	/**
	 * Contract of this class: This method should be called internally whenever the card set in the `hand` variable changes 
	 */
	private void evaluate() {
		
		// initialize temp variables for maintaining state while iterating through cards
		Map<CardValue, Integer> values = new HashMap<CardValue, Integer>();
		
		Card previousCard = null;
		int potentialFlush = 1;
		boolean potentialStraight = true; 
		
		for (Card card : hand) {
			
			CardValue currentValue = card.getCardValue();
			if(values.containsKey(currentValue)) {
				values.put(currentValue, values.get(currentValue) + 1);
			} else {
				values.put(currentValue, 1);
			}
			
			if(null != previousCard && previousCard.getCardSuit() == card.getCardSuit()) {
				potentialFlush++;
			}
			
			if(null != previousCard && previousCard.getIntValue() != card.getIntValue() -1) {
				potentialStraight = false;
			}
			
			previousCard = card;
		}

		setRank(values, potentialFlush, potentialStraight);
	}

	public int findHighestPairValue(Iterator<Card> iterator) {
		Card previousCard = null;
		int highestRelevantValue = -1;
		while(iterator.hasNext()) {
			Card currentCard = iterator.next();
			if(null != previousCard && previousCard.getCardValue().equals(currentCard.getCardValue())) {
				highestRelevantValue = currentCard.getIntValue();
				break;
			} else {
				previousCard = currentCard;
			}
		}
		return highestRelevantValue;
	}
	
	private void resetHand(final Card[] cards) throws HandSizeError {
		hand.clear();
		if(cards.length == 5) {
			for(Card card : cards) {
				hand.add(card);
				evaluate();
			}
		} else {
			throw new HandSizeError(cards.length);
		}
	}
	
	private void setRank(Map<CardValue, Integer> values, int potentialFlush, boolean potentialStraight) {
		if(potentialStraight && potentialFlush == 5) {
			rank = Rank.STRAIGHT_FLUSH;
		} else if (potentialFlush == 5) {
			rank = Rank.FLUSH;
		} else if (potentialStraight) {
			rank = Rank.STRAIGHT;
		} else if (values.containsValue(4)) {
			rank = Rank.FOUR_OF_A_KIND;
		} else if (values.containsValue(3) && values.containsValue(2)) {
			rank = Rank.FULL_HOUSE;
		} else if (values.containsValue(3)) {
			rank = Rank.THREE_OF_A_KIND;
		} else if (values.containsValue(2)) {
			
			int numberOfPairs = 0;
			for(Entry<CardValue, Integer> entry : values.entrySet()) {
				if(entry.getValue() == 2) {
					numberOfPairs++;
				}
			}
			switch(numberOfPairs) {
				case 1:
					rank = Rank.PAIR;
					break;
				case 2:
					rank = Rank.TWO_PAIRS;
					break;
				default: // cannot occur
			}
		} else {
			rank = Rank.HIGH_CARD;
		}
	}

}
