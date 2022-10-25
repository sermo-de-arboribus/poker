package poker.model;

/**
 * A class that represents a single card object in a poker deck. Cards are defined as a combination
 * of a CardSuit and a CardValue.
 *
 */
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
	
	/**
	 * 
	 * @return Returns the integer value of this card object. The integer value is either the number printed on the card,
	 * or 11 for a Jack, 12 for a Queen, 13 for a King, and 14 for an Ace
	 */
	public int getIntValue() {
		return value.getIntValue();
	}

	/**
	 * Cards are sorted first according to their natural order (i.e. the sequence prescribed by the enum CardValue, 
	 * which corresponds with the sequence that can be derived from getIntValue()); cards of the same value
	 * are then further sorted according to the suit order defined in the CardSuit enum.
	 */
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
		return suit == otherCard.suit && value == otherCard.value;
	}
	
	@Override
	public String toString() {
		return this.getCardSuit().getDescription() + " " + this.getCardValue().getDescription();
	}
}
