package poker.model;

public class Card implements Comparable<Card> {

	private final CardSuit suit;
	private final CardValue value;
	
	public Card(final CardSuit suit, final CardValue value) {
		this.suit = suit;
		this.value = value;
	}

	public CardSuit getCardSuit() {
		return suit;
	}

	public CardValue getCardValue() {
		return value;
	}
	
	public int getIntValue() {
		return value.getIntValue();
	}

	public int compareTo(final Card other) {
		
		if(value.compareTo(other.value) == 0) {
			return suit.compareTo(other.suit);
		} else {
			return value.compareTo(other.value);
		}
	}
	
	@Override
	public boolean equals(Object otherObj) {
		
		if(this == otherObj) {
			return true;
		}
		
		if(null == otherObj || getClass() != otherObj.getClass()) {
			return false;
		}

		Card otherCard = (Card)otherObj;
		if(suit == otherCard.suit && value == otherCard.value) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return this.getCardSuit().getDescription() + " " + this.getCardValue().getDescription();
	}
}
