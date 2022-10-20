package poker.model;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import poker.error.HandSizeError;

public class CardHand {

	private Set<Card> hand = new TreeSet<Card>();
	
	public CardHand(final Card card1, final Card card2, final Card card3, final Card card4, final Card card5) {
		hand.add(card1);
		hand.add(card2);
		hand.add(card3);
		hand.add(card4);
		hand.add(card5);
	}
	
	public CardHand(final Card[] cards) throws HandSizeError {
		if(cards.length == 5) {
			for(Card card : cards) {
				hand.add(card);
			}			
		} else {
			throw new HandSizeError(cards.length);
		}
	}
	
	public CardHand(final List<Card> cards) throws HandSizeError {
		if(cards.size() == 5) {
			for(Card card : cards) {
				hand.add(card);
			}			
		} else {
			throw new HandSizeError(cards.size());
		}
	}
}
