package net.teddoyle;

import java.util.HashSet;
import java.util.Iterator;

// import org.testng.annotations.BeforeTest;
// import org.testng.annotations.DataProvider;
// import org.testng.annotations.Test;
// import static org.testng.Assert.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.*;


public class CardCollectionShouldHaveTest
{
    public CardCollection theCardCollection;

    @Before
    public void AConstructor()
    {
        theCardCollection = new CardCollection();
        assertNotNull("CardCollection Default constructor Test Failed",
        		theCardCollection);
    }

    @Test
    public void initMethodThatInitsDataStructures()
    {
        theCardCollection.init();
        assertEquals("CardCollection Init Method Error -- "
                + "numCards should have zero cards",
        		0, theCardCollection.numCards() );
        String cardStr = theCardCollection.convertToString();
        assertEquals("After Card init - convertToString returns string with " + 
                CardCollection.DECK_SIZE * 2 + " characters.",
                0, cardStr.length() );
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
    			assertEquals("Iterator returns unexpected card",
    					itr.next(), rankStr.concat(suitArr[suitIndex]) );
    			// System.out.println(rankStr.concat(suitArr[suitIndex]));
    		}
    	}
    }
    /**
     * Checks to make sure that initCards does not return duplicate cards.
     */
    @Test
    public void initCardsReturnsDeckWithoutDuplicates()
    {
        HashSet<String> cardList = new HashSet<String>();
        String card;

        theCardCollection.init();
        theCardCollection.initCards();
        Iterator<String> itr = theCardCollection.iterator();
        
        while( itr.hasNext())	
        {
        	card = itr.next();
            assertNotNull(card, "NULL card found in CardCollection.");
            assertFalse("Duplicate Card found in CardCollection.",
                    cardList.contains(card));
            cardList.add(card);
        }
    }

    @Test(expected = IllegalStateException.class)
    public void throwAnExceptionIfCardIsAddedToFullDeck()
    {
       theCardCollection.addCard("6H");
       System.out.println("Just added card to deck :"
           + theCardCollection.convertToString());
    }

//    @Test
//    public void canAddCard()
//    {
//        theCardCollection.addCard("+");
//        assert theCardCollection.peekAtCard(theCardCollection.numCards()).equals("+") :
//                   "Added Card at end, but subsequent peek did not work";
//    }

    @Test
    public void canGetCard()
    {
        String cardRetrived = theCardCollection.getCard();
        String rankString = cardRetrived.substring(0, 1);
        String suitString = cardRetrived.substring(1, 2);

        assertTrue("Get Card returns invalid suit, returned card was: "
                + cardRetrived,
        		"CDHS".indexOf(suitString) >= 0);
        assertTrue("Get Card returns invalid rank, returned card was: "
        		+ cardRetrived, "A23456789TJQK".indexOf(rankString) >= 0);
    }

    public Object[][] cardStrToEncodedStrProvider()
    {
        return new Object[][] {
                { "AC", "A"}, {"2C", "B"}, {"3C", "C"}, {"4C", "D"}, {"5C", "E"},
                { "6C", "F"}, {"7C", "G"}, {"8C", "H"}, {"9C", "I"}, {"TC", "J"},
                { "JC", "K"}, {"QC", "L"}, {"KC", "M"},
                { "AD", "N"}, {"2D", "O"}, {"3D", "P"}, {"4D", "Q"}, {"5D", "R"},
                { "6D", "S"}, {"7D", "T"}, {"8D", "U"}, {"9D", "V"}, {"TD", "W"},
                { "JD", "X"}, {"QD", "Y"}, {"KD", "Z"},
                { "AH", "a"}, {"2H", "b"}, {"3H", "c"}, {"4H", "d"}, {"5H", "e"},
                { "6H", "f"}, {"7H", "g"}, {"8H", "h"}, {"9H", "i"}, {"TH", "j"},
                { "JH", "k"}, {"QH", "l"}, {"KH", "m"},
                { "AS", "n"}, {"2S", "o"}, {"3S", "p"}, {"4S", "q"}, {"5S", "r"},
                { "6S", "s"}, {"7S", "t"}, {"8S", "u"}, {"9S", "v"}, {"TS", "w"},
                { "JS", "x"}, {"QS", "y"}, {"KS", "z"}
        };
    }

    @Test
    public void properEncodingAndDecodingOfCards()    {
    	String rankAndSuit;
    	String encodedCard;
    	for (Object[] card : cardStrToEncodedStrProvider()){
    	    rankAndSuit = (String)card[0];
    	    encodedCard = (String)card[1];
            assertEquals(theCardCollection.encodedStringToCard(encodedCard), rankAndSuit);
            assertEquals(theCardCollection.stringToEncodedStr(rankAndSuit), encodedCard);
    	}
    }
    @Test(expected=IllegalArgumentException.class)
    public void addCardShouldThrowExceptionWhenPassedASingleCharString() {
    	CardCollection cards = new CardCollection();
    	cards.addCard("A");
    }
    @Test(expected=IllegalArgumentException.class)
    public void addCardShouldThrowExceptionWhenPassedANullRef() {
    	CardCollection cards = new CardCollection();
    	cards.addCard(null);
    }
    @Test(expected=IllegalArgumentException.class)
    public void addCardShouldThrowExceptionWhenPassedAnEmptyString() {
    	CardCollection cards = new CardCollection();
    	cards.addCard("");
    }
    @Test(expected=IllegalArgumentException.class)
    public void addCardShouldThrowExceptionWhenPassedAThreeCharString() {
    	CardCollection cards = new CardCollection();
    	cards.addCard("AC2");
    }
    @Test(expected=IllegalArgumentException.class)
    public void addCardShouldThrowExceptionWhenPassedAnInvalidSuit() {
    	CardCollection cards = new CardCollection();
    	cards.addCard("2E");
    }
    @Test(expected=IllegalArgumentException.class)
    public void addCardShouldThrowExceptionWhenPassedAnInvalidRank() {
    	CardCollection cards = new CardCollection();
    	cards.addCard("0H");
    }
    @Test
    public void allTheCardsInTheDeck()
    {
        int rankNum;
        int suitNum;
        int cardIndex;
        String suitList = "CDHS";
        String rankList = "A23456789TJQK";
        String encodedCard;
        String cardList = theCardCollection.convertToString();

        for (rankNum = 0; rankNum < rankList.length(); rankNum++)
        {
            for (suitNum = 0; suitNum < suitList.length(); suitNum++)
            {
                String testCard = new StringBuilder()
                		  .append(rankList.charAt(rankNum))
                          .append(suitList.charAt(suitNum)).toString();
                encodedCard = theCardCollection.stringToEncodedStr(testCard);
                assertTrue("Encoded card: " + encodedCard 
                		+ " does not have length of one.",
                		encodedCard.length() == 1);
                cardIndex = cardList.indexOf(encodedCard);
                assertTrue( "Card " + testCard + " Not found or found at " 
                        + "odd location in: " + cardList, cardIndex >= 0);
            }
        }

    }

    public int CountStringDifferences(String s1, String s2)
    {
        int numDifferences = 0;

        for (int i = 0; i < s1.length() && i < s2.length(); i++)
        {
            if (s1.charAt(i) != s2.charAt(i)) numDifferences++;
        }
        // Add to numDifferences difference in String length
        numDifferences += Math.abs(s1.length() - s2.length());
        return numDifferences;
    }

    @Test
    public void anEffectiveCardShuffle()
    {
        int differences;

        String deckPriorToShuffle = theCardCollection.convertToString();
        theCardCollection.shuffleCards();
        String deckAfterShuffle = theCardCollection.convertToString();

        differences = CountStringDifferences(deckPriorToShuffle, deckAfterShuffle);
        assertTrue("Cards not Sufficiently well sorted", differences > 10);
    }
}
