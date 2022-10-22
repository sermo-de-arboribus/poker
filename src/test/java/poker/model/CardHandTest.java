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
		
		@Test
		void ensureRankingForHighest() {
			// arrange
			CardHand hand1 = getHighCardHand(); // contains an Ace
			CardHand hand2 = new CardHand(new Card(CardSuit.D, CardValue.Q),
					new Card(CardSuit.C, CardValue.K), 
					new Card(CardSuit.S, CardValue._4),
					new Card(CardSuit.S, CardValue._2),
					new Card(CardSuit.H, CardValue._8));
			
			// act + assert
			assertTrue(hand1.rankAgainst(hand2) == hand1);
		}
		
		@Test
		void ensureRankingForStraightFlush() {
			// arrange
			CardHand hand1 = getStraightFlush(CardSuit.C); // highest is Queen
			CardHand hand2 = new CardHand(
					new Card(CardSuit.D, CardValue._3),
					new Card(CardSuit.D, CardValue._4), 
					new Card(CardSuit.D, CardValue._5),
					new Card(CardSuit.D, CardValue._6),
					new Card(CardSuit.D, CardValue._7));
			
			// act + assert
			assertTrue(hand1.rankAgainst(hand2) == hand1);
		}
		
		@Test
		void ensureRankingForFours() {
			CardHand hand1 = getFourOfAKind(); // four Threes
			CardHand hand2 = new CardHand(
					new Card(CardSuit.D, CardValue._8),
					new Card(CardSuit.D, CardValue._5), 
					new Card(CardSuit.C, CardValue._8),
					new Card(CardSuit.H, CardValue._8),
					new Card(CardSuit.S, CardValue._8));
			
			// act + assert
			assertTrue(hand1.rankAgainst(hand2) == hand2);
		}
		
		@Test
		void ensureRankingForFullHouse() {
			CardHand hand1 = getFullHouse(); // three Threes
			CardHand hand2 = new CardHand(
					new Card(CardSuit.D, CardValue.J),
					new Card(CardSuit.D, CardValue._8), 
					new Card(CardSuit.C, CardValue.J),
					new Card(CardSuit.H, CardValue.J),
					new Card(CardSuit.S, CardValue._8));
			
			// act + assert
			assertTrue(hand1.rankAgainst(hand2) == hand2);
		}
		
		@Test
		void ensureRankingForFlush() {
			CardHand hand1 = getFlush(); // highest is Ace
			CardHand hand2 = new CardHand(
					new Card(CardSuit.D, CardValue._3),
					new Card(CardSuit.D, CardValue.J), 
					new Card(CardSuit.D, CardValue.T),
					new Card(CardSuit.D, CardValue._2),
					new Card(CardSuit.D, CardValue._7));
			
			// act + assert
			assertTrue(hand1.rankAgainst(hand2) == hand1);
		}
		
		@Test
		void ensureRankingForStraight() {
			CardHand hand1 = getStraight(); // highest Queen
			CardHand hand2 = new CardHand(
					new Card(CardSuit.D, CardValue.J),
					new Card(CardSuit.C, CardValue._9), 
					new Card(CardSuit.S, CardValue.K),
					new Card(CardSuit.S, CardValue.Q),
					new Card(CardSuit.H, CardValue.T));
			
			// act + assert
			assertTrue(hand1.rankAgainst(hand2) == hand2);
		}
		
		@Test
		void ensureRankingForThreeOfAKind() {
			CardHand hand1 = getThreeOfAKind(); // three Aces
			CardHand hand2 = new CardHand(
					new Card(CardSuit.D, CardValue._4),
					new Card(CardSuit.C, CardValue._4), 
					new Card(CardSuit.S, CardValue.Q),
					new Card(CardSuit.S, CardValue.J),
					new Card(CardSuit.H, CardValue._4));
			
			// act + assert
			assertTrue(hand1.rankAgainst(hand2) == hand1);
		}
		
		@Test
		void ensureRankingForTwoPairs() {
			// arrange
			CardHand hand1 = getTwoPairs(); // two Aces, two Fours
			CardHand hand2 = new CardHand(
					new Card(CardSuit.D, CardValue.A),
					new Card(CardSuit.C, CardValue.A), 
					new Card(CardSuit.S, CardValue._7),
					new Card(CardSuit.S, CardValue._2),
					new Card(CardSuit.H, CardValue._7));
			CardHand hand3 = new CardHand(
					new Card(CardSuit.D, CardValue.J),
					new Card(CardSuit.C, CardValue.J), 
					new Card(CardSuit.S, CardValue._4),
					new Card(CardSuit.S, CardValue._2),
					new Card(CardSuit.H, CardValue._4));
			CardHand hand4 = new CardHand(
					new Card(CardSuit.D, CardValue.A),
					new Card(CardSuit.C, CardValue.A), 
					new Card(CardSuit.S, CardValue._7),
					new Card(CardSuit.S, CardValue._5),
					new Card(CardSuit.H, CardValue._7));
			
			// act + assert
			assertTrue(hand1.rankAgainst(hand2) == hand2);
			assertTrue(hand1.rankAgainst(hand3) == hand1);
			assertTrue(hand2.rankAgainst(hand3) == hand2);
			assertTrue(hand2.rankAgainst(hand4) == hand4);
		}
		
		@Test
		void ensureRankingForSinglePairs() {
			// arrange
			CardHand hand1 = getPair(); // pair of 4, highest Ace
			CardHand hand2 = new CardHand(
					new Card(CardSuit.D, CardValue.K),
					new Card(CardSuit.C, CardValue.J), 
					new Card(CardSuit.S, CardValue._4),
					new Card(CardSuit.S, CardValue._2),
					new Card(CardSuit.H, CardValue._4));
			CardHand hand3 = new CardHand(
					new Card(CardSuit.D, CardValue.K),
					new Card(CardSuit.C, CardValue.A), 
					new Card(CardSuit.S, CardValue._5),
					new Card(CardSuit.S, CardValue._2),
					new Card(CardSuit.H, CardValue._5));
			
			// act + assert
			assertTrue(hand1.rankAgainst(hand2) == hand1);
			assertTrue(hand2.rankAgainst(hand3) == hand3);
			assertTrue(hand1.rankAgainst(hand3) == hand3);
		}

		@Test
		void ensureRankingForHighestCard() {
			// arrange
			CardHand hand1 = getHighCardHand(); // highest: Ace
			CardHand hand2 = new CardHand(
					new Card(CardSuit.D, CardValue.Q),
					new Card(CardSuit.C, CardValue.T), 
					new Card(CardSuit.S, CardValue._4),
					new Card(CardSuit.S, CardValue._2),
					new Card(CardSuit.H, CardValue._8));
			
			// act + assert
			assertTrue(hand1.rankAgainst(hand2) == hand1);
		}
	}
}
