package poker.error;

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
