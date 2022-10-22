package poker.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

public class CardTest {

	@Test
	void compareEqualCardsResultsInZero() {
		// arrange
		Card card1 = new Card(CardSuit.H, CardValue._6);
		Card card2 = new Card(CardSuit.H, CardValue._6);
		
		// act
		int result = card1.compareTo(card2);
		
		// assert
		assertTrue(card1.equals(card2));
		assertEquals(0, result);
	}
	
	@Test
	void orderClubsLowerThanDiamonds() {
		// arrange
		Card card1 = new Card(CardSuit.D, CardValue._5);
		Card card2 = new Card(CardSuit.C, CardValue._5);
		
		// act
		int result = card1.compareTo(card2);
		
		// assert
		assertFalse(card1.equals(card2));
		assertTrue(result > 0);
	}
	
	@Test
	void orderDiamondsLowerThanHearts() {
		// arrange
		Card card1 = new Card(CardSuit.H, CardValue.K);
		Card card2 = new Card(CardSuit.D, CardValue.K);
		
		// act
		int result = card1.compareTo(card2);
		
		// assert
		assertFalse(card1.equals(card2));
		assertTrue(result > 0);
	}
	
	@Test
	void orderHeartsLowerThanSpades() {
		// arrange
		Card card1 = new Card(CardSuit.S, CardValue._2);
		Card card2 = new Card(CardSuit.H, CardValue._2);
		
		// act
		int result = card1.compareTo(card2);
		
		// assert
		assertFalse(card1.equals(card2));
		assertTrue(result > 0);
	}
	
	@Test
	void ensureNaturalOrderOfCardsWithIdenticalSuit() {
		// arrange
		ArrayList<Card> cards = new ArrayList<Card>(Arrays.asList(
				new Card(CardSuit.C, CardValue.K),
				new Card(CardSuit.C, CardValue._3),
				new Card(CardSuit.C, CardValue._2),
				new Card(CardSuit.C, CardValue._6),
				new Card(CardSuit.C, CardValue.A),
				new Card(CardSuit.C, CardValue.J),
				new Card(CardSuit.C, CardValue._4),
				new Card(CardSuit.C, CardValue._5),
				new Card(CardSuit.C, CardValue.Q),
				new Card(CardSuit.C, CardValue.T),
				new Card(CardSuit.C, CardValue._9),
				new Card(CardSuit.C, CardValue._8),
				new Card(CardSuit.C, CardValue._7)));
		
		// act
		Collections.sort(cards);
		
		// assert
		for(int i = 1 ; i < cards.size(); i++) {
			assertTrue(cards.get(i - 1).getCardValue().getIntValue() + 1 == cards.get(i).getCardValue().getIntValue());
		}
	}
	
	@Test
	void ensureNaturalOrderOfCardsWithMixedSuit() {
		// arrange
		ArrayList<Card> cards = new ArrayList<Card>(Arrays.asList(
				new Card(CardSuit.C, CardValue.K),
				new Card(CardSuit.S, CardValue._3),
				new Card(CardSuit.H, CardValue._2),
				new Card(CardSuit.D, CardValue._6),
				new Card(CardSuit.D, CardValue.A),
				new Card(CardSuit.S, CardValue.J),
				new Card(CardSuit.C, CardValue._4),
				new Card(CardSuit.H, CardValue._5),
				new Card(CardSuit.H, CardValue.Q),
				new Card(CardSuit.S, CardValue.T),
				new Card(CardSuit.C, CardValue._9),
				new Card(CardSuit.C, CardValue._8),
				new Card(CardSuit.S, CardValue._7)));
		
		// act
		Collections.sort(cards);
		
		// assert
		for(int i = 1 ; i < cards.size(); i++) {
			assertTrue(cards.get(i - 1).getCardValue().getIntValue() + 1 == cards.get(i).getCardValue().getIntValue());
		}
	}
}
