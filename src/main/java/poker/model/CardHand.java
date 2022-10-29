package poker.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;

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

		List<Card> cards = Arrays.asList(card1, card2, card3, card4, card5);
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
		List<Card> cardsList = List.of(cards);
		resetHand(cardsList);
	}
	
	/**
	 * Construct a hand or Poker card out of a Card list. The list is expected to provide exactly five cards.
	 * 
	 * @param cards A Card list of length 5
	 * @throws HandSizeError is thrown when the list has less or more than 5 Card objects
	 */
	public CardHand(final List<Card> cards) throws HandSizeError {
		resetHand(cards);
	}
	
	/************************ Public methods ***********************/
	
	/**
	 * Determines the value of the lowest (or only) pair on this hand.
	 * 
	 * @return The integer value of the lowest pair on this hand, or 0 if this hand has no pair at all
	 */
	public int findLowestPairValue() {
		return findFirstPairValue(new ArrayList<Card>(hand));
	}
	
	/**
	 * Determines the value of the highest (or only) pair on this hand.
	 * 
	 * @return The integer value of the highest pair on this hand, or 0 if this hand has no pair at all
	 */
	public int findHighestPairValue() {
	    return findFirstPairValue(StreamEx.ofReversed(new ArrayList<Card>(hand)).toList());
	}
	
	/**
	 * Determines the highest single card's value, where single cards are cards that are not part of
	 * a pair, a set of threes, or fours.
	 * 
	 * @return The value of the highest single card, or 0 if there is none in this hand 
	 */
	public int findHighestSingleCardValue() {
	    
	    return EntryStream.of(
    	    hand.stream()
    	        .collect(Collectors.groupingBy(Card::getIntValue, Collectors.counting())))
	        .filter((entry) -> entry.getValue() == 1)
	        .map((entry) -> entry.getKey())
	        .max(Integer::compare)
	        .orElse(0);
	}
	
	/**
	 * Determines the 
	 * @return
	 */
	public int findThreesValue() {
		// We only need to look at index position 3: The card at this position must be included in the set of Three of a Kind
		return hand.stream().collect(Collectors.toList()).get(3).getIntValue();
	}
	
	/**
	 * Returns the highest card in the current hand. If there are several cards with the same highest value, 
	 * the card with the highest ranked CardSuit (according to the natural ordering of the CardSuit enum) is
	 * returned
	 * @return see above
	 */
	public Card getHighestCard() {
		return hand.last();
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
	
	private int findFirstPairValue(List<Card> hand) {
	    return StreamEx.ofSubLists(hand, 2, 1)
	        .findFirst((subList) -> subList.get(0).getIntValue() == subList.get(1).getIntValue())
	        .map((subList) -> subList.get(0).getIntValue())
	        .orElse(0);
	}
	
	private void resetHand(final List<Card> cards) throws HandSizeError {
		
		if(cards.size() == 5) {
			hand.clear();
			hand.addAll(cards);
			evaluate();
		} else {
			throw new HandSizeError(cards.size());
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
