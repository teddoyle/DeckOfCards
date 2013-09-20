package net.teddoyle;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class CardCollectionIterator implements Iterator<String> {

	int positionInCollection;
	boolean canRemove;
	CardCollection cardDeck;
	
	public CardCollectionIterator(CardCollection cards) {
		super();
		canRemove = false;
		cardDeck = cards;
		positionInCollection = 0;
	}
	@Override
	public boolean hasNext() {
		return positionInCollection < CardCollection.DECK_SIZE;
	}

	@Override
	public String next() {
		canRemove = true;
		if (hasNext()) return cardDeck.peekAtCard(positionInCollection++); 
		else throw new NoSuchElementException();
	}

	@Override
	public void remove() {
	    throw new UnsupportedOperationException();
	}
}
