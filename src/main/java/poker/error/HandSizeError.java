package poker.error;

/**
 * An exception that is thrown, when the CardHand being worked on has less or more than the expected five cards.
 *
 */
public class HandSizeError extends Exception {

	private static final long serialVersionUID = 1L;
	private int wrongNumberOfCards = -1;
	
	public HandSizeError(int numberOfCards) {
		super();
		this.wrongNumberOfCards = numberOfCards;
	}
	
	@Override
	public String getMessage() {
		return "Expected a hand to hold 5 cards. Received a hand with " + wrongNumberOfCards + "instead.";
	}
}
