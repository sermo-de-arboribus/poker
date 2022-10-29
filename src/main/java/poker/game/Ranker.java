package poker.game;

import poker.model.CardHand;

public class Ranker {

	/**
	 * Compares two Poker card hands with each other and returns the winner. Also prints a 
	 * message about the winning hand to the standard output console.
	 * 
	 * @param hand1 The first hand to be ranked
	 * @param hand2 The second hand to be ranked
	 * @return the winning hand object, or null if no precedence is defined for the two given hands
	 */
	public CardHand rankHands(CardHand hand1, CardHand hand2) {
		
		int comparisonResult = hand1.getRank().compareTo(hand2.getRank());
		
		// if both hands have the same rank, we need to compare again, this time for the relevant highest card values
		if(comparisonResult == 0) {
			
			int highestRelevantValueForThis, highestRelevantValueForOther;
			
			switch(hand1.getRank()) {
			
				case PAIR:
					comparisonResult = compareTwoHighestPairs(hand1, hand2);
					
					if (comparisonResult == 0) {
						comparisonResult = compareHighestSingleCard(hand1, hand2, comparisonResult);	
					}
					
					break;
					
				case TWO_PAIRS:
					
					comparisonResult = compareTwoHighestPairs(hand1, hand2);
					
					if (comparisonResult == 0) {
						comparisonResult = compareSecondHighestPairs(hand1, hand2);
						
						if(comparisonResult == 0) {
							comparisonResult = compareHighestSingleCard(hand1, hand2, comparisonResult);							
						}
					}
					
					break;
					
				case THREE_OF_A_KIND:
				case FULL_HOUSE:
					highestRelevantValueForThis = hand1.findThreesValue();
					highestRelevantValueForOther = hand2.findThreesValue();
					comparisonResult = highestRelevantValueForThis - highestRelevantValueForOther;
					break;

				case FOUR_OF_A_KIND:

				comparisonResult = compareTwoHighestPairs(hand1, hand2);					
					break;
				
				// default ranking works for Flush, Straight Flush, Straight, and High Card ranks
				default:
					comparisonResult = hand1.getHighestCard().getIntValue() - hand2.getHighestCard().getIntValue();
			}
		}

		if(comparisonResult > 0) {
			System.out.println("Hand 1 has won, holding a " + hand1.getRank() + " with cards " + hand1.toString() + " against " + hand2.toString());
			return hand1;
		} else if(comparisonResult < 0) {
			System.out.println("Hand 2 has won, holding a " + hand2.getRank() + " with cards " + hand2.toString() + " against " + hand1.toString());
			return hand2;
		} else {
			System.out.println("There is no precedence defined for the two given hands " + hand1.toString() + " and " + hand2.toString() + ".\nShould we call it a draw?");
			return null;
		}
	}
	
	/************************ Private methods *************************/
	
	private int compareHighestSingleCard(CardHand hand1, CardHand hand2, int comparisonResult) {
		int highestSingleCardForThis;
		int highestSingleCardForOther;

		highestSingleCardForThis = hand1.findHighestSingleCardValue();
		highestSingleCardForOther = hand2.findHighestSingleCardValue();
		comparisonResult = highestSingleCardForThis - highestSingleCardForOther;

		return comparisonResult;
	}

	private int compareSecondHighestPairs(CardHand hand1, CardHand hand2) {
		int highestRelevantValueForThis = hand1.findLowestPairValue();
		int highestRelevantValueForOther = hand2.findLowestPairValue();
		return highestRelevantValueForThis - highestRelevantValueForOther;
	}

	private int compareTwoHighestPairs(CardHand hand1, CardHand hand2) {
		int highestRelevantValueForThis = hand1.findHighestPairValue();
		int highestRelevantValueForOther = hand2.findHighestPairValue();
		return highestRelevantValueForThis - highestRelevantValueForOther;
	}

}
