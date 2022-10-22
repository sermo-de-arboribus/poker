package poker.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeSet;

import poker.error.HandSizeError;

public class CardHand {

	private SortedSet<Card> hand = new TreeSet<Card>();
	private Rank rank = Rank.HIGH_CARD;
	
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
	
	public CardHand(final Card[] cards) throws HandSizeError {
		resetHand(cards);
	}
	
	public CardHand(final List<Card> cards) throws HandSizeError {
		Card[] cardsArray = cards.toArray(new Card[0]);
		resetHand(cardsArray);
	}
	
	public Rank getRank() {
		return rank;
	}
	
	/**
	 * Contract of this class: This method should be called internally whenever the card set in the `hand` variable changes 
	 */
	private void evaluate() {
		
		// initialize temp variables for maintaining state while iterating through cards
		Map<CardValue, Integer> values = new HashMap<CardValue, Integer>();
		List<Integer> intValues = new ArrayList<Integer>(); 
		
		Card previousCard = null;
		int potentialFlush = 1;
		
		for (Card card : hand) {
			
			CardValue currentValue = card.getCardValue();
			if(values.containsKey(currentValue)) {
				values.put(currentValue, values.get(currentValue) + 1);
			} else {
				values.put(currentValue, 1);
			}
			
			intValues.add(currentValue.getIntValue());
			
			if(null != previousCard && previousCard.getCardSuit() == card.getCardSuit()) {
				potentialFlush++;
			}
			
			previousCard = card;
		}
		
		Collections.sort(intValues);
		boolean potentialStraight = true;
		for (int i = 1; i < intValues.size(); i++) {
			if(intValues.get(i-1) != intValues.get(i) - 1) {
				potentialStraight = false;
			}
		}
		
		setRank(values, potentialFlush, potentialStraight);
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
