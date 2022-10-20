package poker.model;

public class Card implements Comparable<Card> {

	private final CardSuit suit;
	private final CardValue value;
	
	public Card(final CardSuit suit, final CardValue value) {
		this.suit = suit;
		this.value = value;
	}

	public CardSuit getSuit() {
		return suit;
	}

	public CardValue getValue() {
		return value;
	}

	public int compareTo(final Card other) {
		
		if(suit.compareTo(other.suit) == 0) {
			return value.compareTo(other.value);
		} else {
			return suit.compareTo(other.suit);
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
}
