# Poker

A small library for evaluating two poker hands against each other.

## Usage

The basic unit of this class is a CardHand object. A CardHand object holds five cards. Every CardHand object
maintains the current rank it has, e. g. FLUSH, or TWO_PAIRS, which can be read by calling getRank() on a 
CardHand object.

Two CardHand objects can be ranked against each other using an object of the Ranker class. The Ranker class
method `rankHands` takes two hands as a parameter and return the winning hand:

```java
Ranker ranker = new Ranker();
CardHand result = ranker.rankHands(hand1, hand2);
```

## Testing

The library comes with unit tests for ensuring the ranking of hands against each other is correctly determining 
the winner. You can see these tests in action by cloning this repo and then running the tests with Maven: 

```
mvn test
```

## Implementation note

Although Poker cards have no order defined on suits (e. g. a diamond 5 is not more or less "valuable"
than a spade 5), this implementation does internally use a sort order not only for card values, but also
for card suits. This allows us to use the data structure of a SortedSet internally, which in turn gives
us a natural and relatively efficient way of determining the rank of a hand, while the determining of a 
winner when comparing two hands with each other does not necessarily benefit from this.
We assumed here that in a real-life Poker computer game, the determination of a single hand's rank needs
to be done more often than the ranking of hands against each other: The single hand's rank needs to be
evaluated and displayed on the player's screen, whenever she exchanged some cards. The ranking of different
player's hands against each other only needs to be done once per game, at the end of it. That's why we 
optimized the single hand's rank evaluation by using a sort order for cards that is "stricter" than the 
sort order of the Poker game's specifications. 