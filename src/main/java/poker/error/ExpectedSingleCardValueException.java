package poker.error;

/**
 * Exception that is thrown when a CardHand is expecting to have at least one card with a single, unique value, 
 * i.e. when the hand is a PAIR, TWO_PAIRS, THREE_OF_A_KIND, FOUR_OF_A_KIND, etc., but does not find one
 *
 */
public class ExpectedSingleCardValueException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public ExpectedSingleCardValueException() {
		super();
	}
	
	@Override
	public String getMessage() {
		return "Expected a hand with exactly one card having a single value (and the others to be pairs or fours. But no such single card was found";
	}
	
}
