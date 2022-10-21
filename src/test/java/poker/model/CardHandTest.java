package poker.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class CardHandTest {

	@Test
	void recognizeStraightFlush() {
		CardSuit suit = CardSuit.C;
		// arrange
		CardHand hand = new CardHand(
				new Card(suit, CardValue.T),
				new Card(suit, CardValue._8), 
				new Card(suit, CardValue.Q),
				new Card(suit, CardValue.J),
				new Card(suit, CardValue._9));
		
		// assert
		assertEquals(hand.getRank(), Rank.STRAIGHT_FLUSH);
	}
	
	@Test
	void recognizeFourOfAKind() {

		// arrange
		CardHand hand = new CardHand(
				new Card(CardSuit.D, CardValue._3),
				new Card(CardSuit.D, CardValue._8), 
				new Card(CardSuit.C, CardValue._3),
				new Card(CardSuit.H, CardValue._3),
				new Card(CardSuit.S, CardValue._3));
		
		// assert
		assertEquals(hand.getRank(), Rank.FOUR_OF_A_KIND);
	}
	
	@Test
	void recognizeFullHouse() {
		
		// arrange
		CardHand hand = new CardHand(
				new Card(CardSuit.D, CardValue._3),
				new Card(CardSuit.D, CardValue._8), 
				new Card(CardSuit.C, CardValue._3),
				new Card(CardSuit.H, CardValue._3),
				new Card(CardSuit.S, CardValue._8));
		
		// assert
		assertEquals(hand.getRank(), Rank.FULL_HOUSE);
	}
	
	@Test
	void recognizeFlush() {
		
		// arrange
		CardHand hand = new CardHand(
				new Card(CardSuit.D, CardValue._3),
				new Card(CardSuit.D, CardValue.A), 
				new Card(CardSuit.D, CardValue.T),
				new Card(CardSuit.D, CardValue._2),
				new Card(CardSuit.D, CardValue._7));
		
		// assert
		assertEquals(hand.getRank(), Rank.FLUSH);
	}
	
	@Test
	void recognizeStraight() {
		
		// arrange
		CardHand hand = new CardHand(
				new Card(CardSuit.D, CardValue.T),
				new Card(CardSuit.C, CardValue._8), 
				new Card(CardSuit.S, CardValue.Q),
				new Card(CardSuit.S, CardValue.J),
				new Card(CardSuit.H, CardValue._9));
		
		assertEquals(hand.getRank(), Rank.STRAIGHT);
	}
	
	@Test
	void recognizeThreeOfAKind() {
		
		// arrange
		CardHand hand = new CardHand(
				new Card(CardSuit.D, CardValue.A),
				new Card(CardSuit.C, CardValue.A), 
				new Card(CardSuit.S, CardValue.Q),
				new Card(CardSuit.S, CardValue.J),
				new Card(CardSuit.H, CardValue.A));
		
		// assert
		assertEquals(hand.getRank(), Rank.THREE_OF_A_KIND);
	}
	
	@Test
	void recognizeTwoPairs() {
		// arrange
		CardHand hand = new CardHand(
				new Card(CardSuit.D, CardValue.A),
				new Card(CardSuit.C, CardValue.A), 
				new Card(CardSuit.S, CardValue._4),
				new Card(CardSuit.S, CardValue._2),
				new Card(CardSuit.H, CardValue._4));
		
		// assert
		assertEquals(hand.getRank(), Rank.TWO_PAIRS);
	}
	
	@Test
	void recognizePairs() {
		// arrange
		CardHand hand = new CardHand(
				new Card(CardSuit.D, CardValue.K),
				new Card(CardSuit.C, CardValue.A), 
				new Card(CardSuit.S, CardValue._4),
				new Card(CardSuit.S, CardValue._2),
				new Card(CardSuit.H, CardValue._4));
		
		// assert
		assertEquals(hand.getRank(), Rank.PAIR);
	}
	
	@Test
	void recognizeHighCard() {
		// arrange
		CardHand hand = new CardHand(
				new Card(CardSuit.D, CardValue.Q),
				new Card(CardSuit.C, CardValue.A), 
				new Card(CardSuit.S, CardValue._4),
				new Card(CardSuit.S, CardValue._2),
				new Card(CardSuit.H, CardValue._8));
		
		// assert
		assertEquals(hand.getRank(), Rank.HIGH_CARD);
	}
	
}
