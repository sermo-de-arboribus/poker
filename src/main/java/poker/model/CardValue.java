package poker.model;

public enum CardValue {

	_2(2, "2"),
	_3(3, "3"),
	_4(4, "4"),
	_5(5, "5"),
	_6(6, "6"),
	_7(7, "7"),
	_8(8, "8"),
	_9(9, "9"),
	T(10, "10"),
	J(11, "Jack"),
	Q(12, "Queen"),
	K(13, "King"),
	A(14, "Ace");
	
	private final int value;
	private final String description;
	
	private CardValue(final int value, final String description) {
		this.value = value;
		this.description = description;
	}
	
	public int getIntValue() {
		return this.value;
	}
	
	public String getDescription() {
		return this.description;
	}
}
