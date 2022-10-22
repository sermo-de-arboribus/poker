# Poker

A small library for evaluating two poker hands against each other.

## Usage

The basic unit of this class is a CardHand object. A CardHand object holds five cards. Every CardHand objects
maintains the current rank it has, e. g. FLUSH, or TWO_PAIRS, which can be read by calling getRank() on a 
CardHand object.

Two CardHand objects can be ranked against each other to determine a winner. The syntax for this is

```java
CardHand result = hand1.rankAgainst(hand2);
```

## Testing

The library comes with unit tests for ensuring the ranking of hands against each other is correctly determining 
the winner. You can see these tests in action by cloning this repo and then running the tests with Maven: 

```
mvn test
```
