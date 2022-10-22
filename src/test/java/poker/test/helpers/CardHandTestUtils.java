package poker.test.helpers;

import poker.model.Card;
import poker.model.CardHand;
import poker.model.CardSuit;
import poker.model.CardValue;

public class CardHandTestUtils {

	public static CardHand getFourOfAKind() {
		return new CardHand(
				new Card(CardSuit.D, CardValue._3),
				new Card(CardSuit.D, CardValue._8), 
				new Card(CardSuit.C, CardValue._3),
				new Card(CardSuit.H, CardValue._3),
				new Card(CardSuit.S, CardValue._3));
	}
	
	public static CardHand getFlush() {
		return new CardHand(
				new Card(CardSuit.D, CardValue._3),
				new Card(CardSuit.D, CardValue.A), 
				new Card(CardSuit.D, CardValue.T),
				new Card(CardSuit.D, CardValue._2),
				new Card(CardSuit.D, CardValue._7));
	}
	
	public static CardHand getFullHouse() {
		return new CardHand(
				new Card(CardSuit.D, CardValue._3),
				new Card(CardSuit.D, CardValue._8), 
				new Card(CardSuit.C, CardValue._3),
				new Card(CardSuit.H, CardValue._3),
				new Card(CardSuit.S, CardValue._8));
	}
	
	public static CardHand getHighCardHand() {
		return new CardHand(
				new Card(CardSuit.D, CardValue.Q),
				new Card(CardSuit.C, CardValue.A), 
				new Card(CardSuit.S, CardValue._4),
				new Card(CardSuit.S, CardValue._2),
				new Card(CardSuit.H, CardValue._8));
	}
	
	public static CardHand getPair() {
		return new CardHand(
				new Card(CardSuit.D, CardValue.K),
				new Card(CardSuit.C, CardValue.A), 
				new Card(CardSuit.S, CardValue._4),
				new Card(CardSuit.S, CardValue._2),
				new Card(CardSuit.H, CardValue._4));
	}
	
	public static CardHand getStraight() {
		return new CardHand(
				new Card(CardSuit.D, CardValue.T),
				new Card(CardSuit.C, CardValue._8), 
				new Card(CardSuit.S, CardValue.Q),
				new Card(CardSuit.S, CardValue.J),
				new Card(CardSuit.H, CardValue._9));
	}
	
	public static CardHand getStraightFlush(CardSuit suit) {
		return new CardHand(
				new Card(suit, CardValue.T),
				new Card(suit, CardValue._8), 
				new Card(suit, CardValue.Q),
				new Card(suit, CardValue.J),
				new Card(suit, CardValue._9));
	}
	
	public static CardHand getThreeOfAKind() {
		return new CardHand(
				new Card(CardSuit.D, CardValue.A),
				new Card(CardSuit.C, CardValue.A), 
				new Card(CardSuit.S, CardValue.Q),
				new Card(CardSuit.S, CardValue.J),
				new Card(CardSuit.H, CardValue.A));
	}
	
	public static CardHand getTwoPairs() {
		return new CardHand(
				new Card(CardSuit.D, CardValue.A),
				new Card(CardSuit.C, CardValue.A), 
				new Card(CardSuit.S, CardValue._4),
				new Card(CardSuit.S, CardValue._2),
				new Card(CardSuit.H, CardValue._4));
	}
}
