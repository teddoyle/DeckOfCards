package net.teddoyle;

// import org.junit.Assert.assertNotNull;
// import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.*;

import java.util.HashSet;
import java.util.Iterator;


// import junit.framework.TestCase;

public class CardCollectionJUnit {
	CardCollection theCardCollection;
	
	@Before
	public void AConstructor() {
	    theCardCollection = new CardCollection();
	    assertNotNull("CardCollection Default constructor Test Failed",
	        		theCardCollection);
	}
    @Test
    public void testDummyTest() {
    	
    }
	@Test
	public void testinitMethodThatInitsDataStructuresTest() {
	    int numCards;

	    theCardCollection.init();
	    numCards = theCardCollection.numCards();
	    assertTrue("CardCollection Init Method Error -- numCards < 0",
	       		numCards >= 0);
	    String cardStr = theCardCollection.convertToString();
	    assertEquals("After Card init - convertToString should return "
	    		+ "zero length String",
	    		cardStr.length(), CardCollection.DECK_SIZE);
	        // assert false : "Make sure that the test is being executed";
	}
	/**
	 * Tests to verify that initCards() returns cards in the fixed order.
	 */
	@Test
	public void initCardsReturnsUnshuffledDeck()
	{
	  	int rankIndex;
	    String[] suitArr = {"C", "D", "H", "S" };
	    int suitIndex;
	    String rankStr;
	    Iterator<String> itr;
	    
	    theCardCollection.init();
	    theCardCollection.initCards();
	    itr = theCardCollection.iterator();
	    for (suitIndex = 0; suitIndex < suitArr.length; suitIndex++) {
	    	for (rankIndex = 1; rankIndex < 14; rankIndex++) {
	    		switch (rankIndex) {
	    	        case 1:  rankStr = "A"; break;
	    		    case 10: rankStr = "T"; break;
	    		    case 11: rankStr = "J"; break;
	    		    case 12: rankStr = "Q"; break;
	    		    case 13: rankStr = "K"; break;
	    		    default: rankStr = Integer.toString(rankIndex);
	    		}
	    		assertEquals("Iterator Does not match expected String in "
	    	        + "a sorted Deck", itr.next(),
	    	        rankStr.concat(suitArr[suitIndex]) );
	    	//	System.out.println(rankStr.concat(suitArr[suitIndex]));
	    	}
	   	}
    }
	/**
	 * Checks to make sure that initCards does not return duplicate cards.
	 */
	@Test
	public void initCardsReturnsDeckWithoutDuplicates() {
	    HashSet<String> cardList = new HashSet<String>();
	    String card;

	    theCardCollection.init();
	    theCardCollection.initCards();
	    Iterator<String> itr = theCardCollection.iterator();
	        
	    while( itr.hasNext()) {
            card = itr.next();
	        assertNotNull(card, "NULL card found in CardCollection.");
	        assertFalse("Duplicate Card found in CardCollection.", 
	           		cardList.contains(card) );
	        cardList.add(card);
	    }
    }
}
