package poker.model;

public enum CardSuit {
	C("club"), D("diamond"), H("heart"), S("spade");
	
	private final String description;
	
	private CardSuit(final String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return this.description;
	}
	
}
