package poker.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static poker.test.helpers.CardHandTestUtils.*;

import org.junit.jupiter.api.Test;

public class CardHandTest {

	@Test
	void recognizeStraightFlush() {
		CardSuit suit = CardSuit.C;
		// arrange
		CardHand hand = getStraightFlush(suit);
		
		// assert
		assertEquals(hand.getRank(), Rank.STRAIGHT_FLUSH);
	}
	
	@Test
	void recognizeFourOfAKind() {

		// arrange
		CardHand hand = getFourOfAKind();
		
		// assert
		assertEquals(hand.getRank(), Rank.FOUR_OF_A_KIND);
	}
	
	@Test
	void recognizeFullHouse() {
		
		// arrange
		CardHand hand = getFullHouse();
		
		// assert
		assertEquals(hand.getRank(), Rank.FULL_HOUSE);
	}
	
	@Test
	void recognizeFlush() {
		
		// arrange
		CardHand hand = getFlush();
		
		// assert
		assertEquals(hand.getRank(), Rank.FLUSH);
	}
	
	@Test
	void recognizeStraight() {
		
		// arrange
		CardHand hand = getStraight();
		
		assertEquals(hand.getRank(), Rank.STRAIGHT);
	}
	
	@Test
	void recognizeThreeOfAKind() {
		
		// arrange
		CardHand hand = getThreeOfAKind();
		
		// assert
		assertEquals(hand.getRank(), Rank.THREE_OF_A_KIND);
	}
	
	@Test
	void recognizeTwoPairs() {
		// arrange
		CardHand hand = getTwoPairs();
		
		// assert
		assertEquals(hand.getRank(), Rank.TWO_PAIRS);
	}
	
	@Test
	void recognizePairs() {
		// arrange
		CardHand hand = getPair();
		
		// assert
		assertEquals(hand.getRank(), Rank.PAIR);
	}
	
	@Test
	void recognizeHighCard() {
		// arrange
		CardHand hand = getHighCardHand();
		
		// assert
		assertEquals(hand.getRank(), Rank.HIGH_CARD);
	}
	
	
}
