package net.teddoyle;

import java.util.Random;
import java.util.ArrayList;
import java.util.Iterator;


public class CardCollection implements Iterable<String>  {
    public final static int DECK_SIZE = 52;
    private final static String SUIT_STRING = "CDHS";
    private final static String RANK_STRING = "A23456789TJQK";
    private final static int SUIT_POS = 1;
    private final static int RANK_POS = 0;

    // private int numCards;
    private int posInCollection;
    private ArrayList<String> theCards;
    private final static String CARD_ENCODING =
	"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz012345";

    public CardCollection()
    {
	    theCards = new ArrayList<String>(DECK_SIZE);
        posInCollection = 0;
//        iteratorPos = 0;
//        removeValid = false; // Call to remove() is not valid until
//                             // next() has been called
        initCards();
    }

    public void init()
    {
        // create an empty CardCollection
	    theCards = new ArrayList<String>(DECK_SIZE);
        posInCollection = 0;
    }
    
    @Override
    public int hashCode() {
    	return theCards.get(1).hashCode() * 213  + 
    		   theCards.get(2).hashCode() * 79 + theCards.get(3).hashCode(); 
    }

    /**
     * Creates an unshuffled deck of cards (i.e. AC to KC, AD to KD,
     *  AH to KH, and AS to KS)  
     */
    public void initCards()
    {       
       // Fill theCards array with cards in order
	   int i, suitIndex;
       String rank;
       posInCollection = 0;

       for (i = 0; i < DECK_SIZE; i++)
       {
           rank = new String(RANK_STRING.substring(i % 13, (i%13) + 1));
           suitIndex = i / (DECK_SIZE / 4);
           theCards.add(rank + SUIT_STRING.charAt(suitIndex) );
       }
    }

    public void shuffleCards() // Randomize the collection
    {
	    Random numberStream = new Random();
        int currPos, randLoc;
        String temp;

        for (currPos = 0; currPos < DECK_SIZE; currPos++)
	    {
            randLoc = currPos + numberStream.nextInt(DECK_SIZE - currPos);
	        temp = theCards.get(currPos);
            theCards.set(currPos, theCards.get(randLoc));
            theCards.set(randLoc, temp);
        }  
    }

    public void addCard(String str)  // Add card (as Encoded Char) to End of list
    {
    	if (str == null) throw new IllegalArgumentException();
    	if (str.length() != 2) throw new IllegalArgumentException();
    	if (RANK_STRING.indexOf(str.charAt(RANK_POS)) == -1)
    		throw new IllegalArgumentException();
     	if (SUIT_STRING.indexOf(str.charAt(SUIT_POS)) == -1)
    		throw new IllegalArgumentException();
     	if (DECK_SIZE == theCards.size()) 
     		throw new IllegalStateException("Deck is already full.");
        theCards.add(str);
    }

    public String getCard()  // return card and update position
    {
        return theCards.get(posInCollection++);
    }
    public String peekAtCard(int i)  // return card and update position
    {
        return theCards.get(i);
    }

    public int numCards() // return the number of cards in the collection
    {
        return theCards.size();
    }

    @Override
    public CardCollectionIterator iterator()
    {
        return new CardCollectionIterator(this);
    }

    public String stringToEncodedStr(String card)
    {
        Character suit = new Character(card.charAt(SUIT_POS)); // , SUIT_POS + 1));
	    // new String(card.substring(SUIT_POS, SUIT_POS + 1));
        Character rank = new Character(card.charAt(RANK_POS)); // , RANK_POS + 1) );
        int index = SUIT_STRING.indexOf(suit) * 13 +
                             (RANK_STRING.indexOf(rank));

        return new Character(CARD_ENCODING.charAt(index)).toString(); 
    }
   
    public String encodedStringToCard(String encodedCard)
    {
        int cardIndex = CARD_ENCODING.indexOf(encodedCard);
        int rank = cardIndex % 13;
        int suit = cardIndex / 13;

        return new Character(RANK_STRING.charAt(rank)).toString() +
			new Character(SUIT_STRING.charAt(suit)).toString();
    }

    public String convertToString()
    {
       // Start the DeckValue String at the current Card Position
       StringBuilder cardStr = new StringBuilder(
               CARD_ENCODING.charAt(posInCollection));

	   for (Iterator<String> itr = theCards.iterator(); itr.hasNext(); )
	   {
	       cardStr.append(stringToEncodedStr(itr.next()));
	   }
  
       return cardStr.toString();
    }

    public void getCardsFromString(String str)
    {
        int i;
        String codedPos;

        theCards.clear();
        if (str.length() == 0)
        {
           posInCollection = 0;
        }
        else
	    {
           codedPos = str.substring(0, 1);
           posInCollection = CARD_ENCODING.indexOf(codedPos);

           for (i = 1; i < str.length(); i++)
           {
              theCards.add(encodedStringToCard(str.substring(i, i + 1)) );
           }
        }
    }

    public void printDeck()
    {
	//    Iterator<String> itr = cardDeque;
       int cardsPrinted = 0;

	   for (Iterator<String> itr = theCards.iterator(); itr.hasNext(); )
	   {
	      System.out.print(itr.next() + "  ");
	      cardsPrinted++;
          if (cardsPrinted % 13 == 0) System.out.println();
	   }
    }

    public static void main(String argv[])
    {
	   CardCollection cards = new CardCollection();
       int cardsPrinted = 0;
	//  Iterable<String> cardIterator =  cards.iterator();

       for (String c : cards )
       {
          System.out.println(c + "  ");
          cardsPrinted++;
          if (cardsPrinted % 13 == 0) System.out.println();
       }       
    }

} // end class CardCollection
