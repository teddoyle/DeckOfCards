package net.teddoyle;

import java.util.Formatter;
import java.util.Locale;

//import javax.servlet.*;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.*;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletOutputStream;

import java.lang.annotation.Annotation;

import javax.servlet.annotation.*;
import javax.servlet.http.*;

// import javax.servlet.jsp.tagext.SimpleTagSupport;
// import javax.servlet.jsp.*;
import net.teddoyle.PrintCard;
import java.io.*;

// import net.teddoyle.CardCollection;

@WebServlet(name="net.teddoyle.CardDeckApp", urlPatterns={"/DealCard.do"})
public class CardDeckApp extends HttpServlet {
    public final static int DECK_SIZE = 52;
    private final static String SUIT_STRING = "CDHS";
    private final static String RANK_STRING = "A23456789TJQK";
    private final static String CARD_ENCODING =
	"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz012345";
    // CARD_ENCODING should only need 52 -- zero to 5 are added as extras

    private static final long serialVersionUID = 1;
    public CardCollection cardDeque = new CardCollection();
    public int currentCardPos;
    public CardCollection dealtCards = new CardCollection();
    public int numCardsDealt;

    public static String checkNull(String str)
    {
    	return str == null ? "" : str;
    }
    
    public String emptyDealtStr()
    {
	   return CARD_ENCODING.substring(0, 1);
    }

    /*    public String dealtCardsStr()
    {
        int i;
        String dealtCardsString = CARD_ENCODING.substring(numCardsDealt,
						   numCardsDealt + 1);

        // for (i = 0; i < numCardsDealt; i++)
	for (Iterator<String> itr = dealtCards.iterator(); itr.hasNext(); )
	{
            dealtCardsString = dealtCardsString.concat(
                                        cardToString(itr.next()));
        }
        // System.out.println(deckValue);
        return dealtCardsString;
    }

    public void getDealtCardsFromString(String str)
    {
        int i;

        dealtCards.clear();
        if (str == null || str.equals(""))
	{
           numCardsDealt = 0;
        }
        else {
           String codedNumCardsDealt = str.substring(0, 1);
           numCardsDealt = CARD_ENCODING.indexOf(codedNumCardsDealt);

           for (i = 1; i < numCardsDealt; i++)
           {
              dealtCards.add(stringToCard(str.substring(i, i+1)) );
           }
	}
    }
    */

    //   private final static String SUIT_STRING = "CDHS";
    //   private final static String RANK_STRING = "A23456789TJQK";
 
    public int getCardRank(String card)
    {
	return RANK_STRING.indexOf(card.substring(0, 1)) + 1;
    }
    public int getCardSuitIndex(String card)
    {
	return SUIT_STRING.indexOf(card.substring(1, 2) );
    }

    public void doGet(HttpServletRequest request,
		       HttpServletResponse response)
	throws IOException, ServletException{
    	doPost(request, response);
    }
    
    public void doPost(HttpServletRequest request,
		       HttpServletResponse response)
	throws IOException, ServletException{
    	final int MAX_CARDS_PER_ROW = 10;
    	boolean twoRows;
        int i;
        int horizontalSep;
        String vertPos;
        String horizPos;
        String dealtCardList = new String("");
        String testStr;
        String cardDealt;
	// String numCardsDealtStr =request.getParameter("numCardsDealt");
        String topCard;
        String cardsDealtStr = request.getParameter("cardsDealt");
        String cardDeckStr = request.getParameter("cardDeck");
        String command = request.getParameter("action");
        String location;
        String dealtCardGraphicAttr;
        String dealtCardAttr;
        String dealtCardValue;
	// out.println("<br> Command received " + command);
        // out.println("Jan 18, 2012 version");

        cardDeque.init();
        cardDeque.initCards();
        dealtCards.init();

        request.setAttribute("CardAction", command);
	//        String prevLoaded = request.getParameter("prevLoaded");
        //if (prevLoaded == null || !prevLoaded.equals("yes"))
        //{
	//    cardsDealtStr = emptyDealtStr();
	//}
        dealtCards.getCardsFromString(cardsDealtStr);
        request.setAttribute("prevLoaded", "yes");

        if (cardDeckStr == null || cardDeckStr.equals("") )
        {
	    // init();
           cardDeque.shuffleCards();
        }
        else
        {
            cardDeque.getCardsFromString(cardDeckStr);
        }
        

        if (command.equals("DealCard") )
	    {
	       cardDealt = (String)cardDeque.getCard();
           dealtCards.addCard(cardDealt);
           numCardsDealt++;
           request.setAttribute("cardDealt", cardDealt);
        }
        if (command.equals("LookAtTopCard") )
        {
	       topCard = (String)cardDeque.peekAtCard(currentCardPos);
           request.setAttribute("topCard", topCard );
        }
        request.setAttribute("numCardsDealt", Integer.toString(numCardsDealt));
        request.setAttribute("cardDeck", "\"" +
                          cardDeque.convertToString() + "\"");
        request.setAttribute("DealtCards", "\"" + 
                            dealtCards.convertToString() + "\"");

        // testStr = new String(formatter.format("Card%03d", 3).toString());
        testStr = String.format("Card%03d", 3);
        request.setAttribute("testStr", testStr);    

        dealtCardList = new String("");
        StringBuilder dealtCardsString = new StringBuilder("");
	//        for (i = 0; i < numCardsDealt; i++)


        if (numCardsDealt < 5 )    // Set the Horizontal seperation based on the number of cards dealt
           horizontalSep = 5; 
        else if (numCardsDealt < 8)
           horizontalSep = 4;
        else 
        	horizontalSep = 3;
        
        if (numCardsDealt < MAX_CARDS_PER_ROW) twoRows = false;
        else twoRows = true;
        for (i = 0; i < dealtCards.numCards(); i++)
       	{
        	if (twoRows == false)
        	{
               vertPos = "top:1.0em";
               horizPos = Integer.toString(i * horizontalSep + 1) + "em;"; 
        	}
        	else
        	{    // Two Row Display 
        		if (i < MAX_CARDS_PER_ROW)
        		{
        			vertPos = "top:0.5em";
        			horizPos = Integer.toString(i * horizontalSep + 1) + "em;";
        		}
        		else 
        	    {
        			vertPos ="top:6em";
        			horizPos = Integer.toString((i - MAX_CARDS_PER_ROW) 
        					             * horizontalSep + 1) + "em;";
        	    }
        	}
	    dealtCardValue = new String(dealtCards.peekAtCard(i));
            dealtCardGraphicAttr = String.format("GraphicCard%02d", i);
            location = "style=\"left:" + horizPos + vertPos + "\"";
            request.setAttribute(dealtCardGraphicAttr,
                 PrintCard.buildCard(getCardRank(dealtCardValue),
				     getCardSuitIndex(dealtCardValue),
                                     location ) );
            dealtCardsString.append(PrintCard.buildCard(
            		  getCardRank(dealtCardValue),
	                  getCardSuitIndex(dealtCardValue),
                      location ) );
            dealtCardAttr = String.format("CardDealt%02d", i);
	    // dealtCardValue = new String(cardToString(dealtCards.get(i)));
            dealtCardValue = new String(dealtCards.peekAtCard(i));
            request.setAttribute(dealtCardAttr, dealtCardValue);
	    //            dealtCards[i]  );
            dealtCardList = dealtCardList +
                dealtCardAttr + " = " + dealtCardValue + " ** ";
                //  formatter.format("CardDealt%02d", i).toString();
        }
        request.setAttribute("dealtCardsString", dealtCardsString.toString());
        request.setAttribute("dealtCardList", dealtCardList);
        RequestDispatcher view =
	    //   request.getRequestDispatcher("cardOutput.jsp");
	    request.getRequestDispatcher("cardDealer.jsp");
        view.forward(request, response);
    }

    public static void main(String args[])
    {

        CardDeckApp myCardDeck = new CardDeckApp();

        myCardDeck.cardDeque.init();
        myCardDeck.cardDeque.initCards();
        myCardDeck.cardDeque.printDeck();
        System.out.println(myCardDeck.cardDeque.convertToString());
        myCardDeck.cardDeque.shuffleCards();
        System.out.println(myCardDeck.cardDeque.convertToString());
        myCardDeck.cardDeque.printDeck();

        System.out.println(myCardDeck.cardDeque.convertToString());

    }
}

