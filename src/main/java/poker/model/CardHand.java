package poker.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;

import poker.error.ExpectedSingleCardValueException;
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
	 * Returns the highest card in the current hand. If there are several cards with the same highest value, 
	 * the card with the highest ranked CardSuit (according to the natural ordering of the CardSuit enum) is
	 * returned
	 * @return see above
	 */
	public Card getHighestCard() {
		return hand.last();
	}
	
	/**
	 * Compares another hand to this object's hand and returns the winner. Also prints a message about the winning hand to 
	 * the standard output console.
	 * 
	 * @param otherHand The other hand to be ranked against the current object
	 * @return the winning hand object, or null if no precedence is defined for the two given hands
	 */
	public CardHand rankAgainst(CardHand otherHand) {
		
		int comparisonResult = this.getRank().compareTo(otherHand.getRank());
		
		// if both hands have the same rank, we need to compare again, this time for the relevant highest card values
		if(comparisonResult == 0) {
			
			int highestRelevantValueForThis, highestRelevantValueForOther;
			
			switch(this.getRank()) {
			
				case PAIR:
					comparisonResult = compareTwoHighestPairs(otherHand);
					
					if (comparisonResult == 0) {
						comparisonResult = compareHighestSingleCard(otherHand, comparisonResult);	
					}
					
					break;
					
				case TWO_PAIRS:
					
					comparisonResult = compareTwoHighestPairs(otherHand);
					
					if (comparisonResult == 0) {
						comparisonResult = compareSecondHighestPairs(otherHand);
						
						if(comparisonResult == 0) {
							comparisonResult = compareHighestSingleCard(otherHand, comparisonResult);							
						}
					}
					
					break;
					
				case THREE_OF_A_KIND:
				case FULL_HOUSE:
					highestRelevantValueForThis = findHighestThreesValue(this.hand);
					highestRelevantValueForOther = findHighestThreesValue(otherHand.hand);
					comparisonResult = highestRelevantValueForThis - highestRelevantValueForOther;
					break;

				case FOUR_OF_A_KIND:

				comparisonResult = compareTwoHighestPairs(otherHand);					
					break;
				
				// default ranking works for Flush, Straight Flush, Straight, and High Card ranks
				default:
					comparisonResult = this.getHighestCard().getIntValue() - otherHand.getHighestCard().getIntValue();
			}
		}

		if(comparisonResult > 0) {
			System.out.println("Hand 1 has won, holding a " + this.getRank() + " with cards " + this.toString() + " against " + otherHand.toString());
			return this;
		} else if(comparisonResult < 0) {
			System.out.println("Hand 2 has won, holding a " + otherHand.getRank() + " with cards " + otherHand.toString() + " against " + this.toString());
			return otherHand;
		} else {
			System.out.println("There is no precedence defined for the two given hands " + this.toString() + " and " + otherHand.toString() + ".\nShould we call it a draw?");
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
	
	private int compareHighestSingleCard(CardHand otherHand, int comparisonResult) {
		int highestSingleCardForThis;
		int highestSingleCardForOther;
		try {
			highestSingleCardForThis = findHighestSingleCardValue(this.hand);
			highestSingleCardForOther = findHighestSingleCardValue(otherHand.hand);
			comparisonResult = highestSingleCardForThis - highestSingleCardForOther;
		} catch (ExpectedSingleCardValueException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return comparisonResult;
	}

	private int compareSecondHighestPairs(CardHand otherHand) {
		int comparisonResult;
		int highestRelevantValueForThis;
		int highestRelevantValueForOther;
		highestRelevantValueForThis = findSecondHighestPairValue(this.hand);
		highestRelevantValueForOther = findSecondHighestPairValue(otherHand.hand);
		comparisonResult = highestRelevantValueForThis - highestRelevantValueForOther;
		return comparisonResult;
	}

	private int compareTwoHighestPairs(CardHand otherHand) {
		int comparisonResult;
		int highestRelevantValueForThis;
		int highestRelevantValueForOther;
		highestRelevantValueForThis = findHighestPairValue(this.hand);
		highestRelevantValueForOther = findHighestPairValue(otherHand.hand);
		comparisonResult = highestRelevantValueForThis - highestRelevantValueForOther;
		return comparisonResult;
	}
	
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
	
	private int findHighestPairValue(NavigableSet<Card> hand) {
	    return findFirstPairValue(StreamEx.ofReversed(new ArrayList<Card>(hand)).toList());
	}
	
	private int findHighestSingleCardValue(NavigableSet<Card> hand) throws ExpectedSingleCardValueException {
	    
	    return EntryStream.of(
    	    hand.stream()
    	        .collect(Collectors.groupingBy(Card::getIntValue, Collectors.counting())))
	        .filter((entry) -> entry.getValue() == 1)
	        .map((entry) -> entry.getKey())
	        .max(Integer::compare)
	        .orElse(0);
	}
	
	private int findSecondHighestPairValue(NavigableSet<Card> hand) {
		return findFirstPairValue(new ArrayList<Card>(hand));
	}
	
	private int findHighestThreesValue(NavigableSet<Card> hand) {
		// We only need to look at index position 3: The card at this position must be included in the set of Three of a Kind
		int i = 1;
		Iterator<Card> iterator = hand.iterator();
		while(i < 3 && iterator.hasNext()) {
			iterator.next();
			i++;
		}
		
		return iterator.next().getIntValue();
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
