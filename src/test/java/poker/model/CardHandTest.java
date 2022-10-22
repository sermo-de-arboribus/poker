package poker.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static poker.test.helpers.CardHandTestUtils.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CardHandTest {

	@Nested
	@DisplayName("Tests for the getRank() method")
	class GetRankTests {
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
	
	@Nested
	@DisplayName("Testing the rankAgainst(CardHand other) method")
	class RankAgainstTests {
		
		@Test
		void ensureRankingSequence() {
			// arrange
			List<CardHand> hands = new ArrayList<CardHand>();
			hands.add(getHighCardHand());
			hands.add(getPair());
			hands.add(getTwoPairs());
			hands.add(getThreeOfAKind());
			hands.add(getStraight());
			hands.add(getFlush());
			hands.add(getFullHouse());
			hands.add(getFourOfAKind());
			hands.add(getStraightFlush(CardSuit.C));

			// act + assert
			for(int i = 1; i < hands.size(); i++ ) {
				// use == operator to test for object identity
				CardHand hand1 = hands.get(i-i);
				CardHand hand2 = hands.get(i);
				assertTrue(hand1.rankAgainst(hand2) == hand2);
			}
		}
	}
	
}
