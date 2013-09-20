package net.teddoyle;

import java.io.*;

public class PrintCard {

    private final static int NUM_COLS = 3;
    private final static int NUM_ROWS = 7;

    private final static int COL_A = 0;
    private final static int COL_B = 1;
    private final static int COL_C = 2;

    private final static int TOP = 0;
    private final static int MIDDLE_TOP = 1;
    private final static int MIDDLE = 2;
    private final static int MIDDLE_BOTTOM = 3;
    private final static int BOTTOM = 4;

    public static String printAce(String suitCode, String color,
                                  String location)
    {
       String cardSource = "<div class=\"card\" " + location + "> \n" +
          "<div class=\"front " + color +"\">" +
          " <div class=\"index\">A<br />" + suitCode + "</div>" +
	" <div class=\"ace\">" + suitCode + " </div>\n   </div> \n</div>\n";
       return cardSource;
    }

    public static String printFaceCard(int rank, String suitCode,
                                       String color, String location)
    {
        String rankLetter = "0";
        String imageName = "<img class=\"face\" ";
       
        if (rank == 11) {
	    rankLetter = "J";
            imageName = imageName.concat(
		 "src=\"graphics/jack.gif\" alt=\"jack\" />");
        }
        if (rank == 12) {
	    rankLetter = "Q";
            imageName = imageName.concat(
		 "src=\"graphics/queen.gif\" alt=\"queen\" />");
        }
        if (rank == 13) {
	    rankLetter = "K";
            imageName = imageName.concat(
		 "src=\"graphics/king.gif\" alt=\"king\" />");
        }

	String cardSource =
"<div class=\"card\" " + location + " >\n" +
       // style=\"position:relative;\"> \n" +
  "   <div class=\"front " + color + "\"> \n <div class=\"index\">" +
        rankLetter + "<br />" + suitCode + "</div>\n" + imageName +
       "\n  <div class=\"spotA1\">" + suitCode + "</div> \n" +
	    "<div class=\"spotC5\">" + suitCode + "</div>\n  </div>" +
	    "</div>\n";
	return cardSource;
    }



    public static String buildCard(int rank, int suitIndex, String location)
    {
        int i;
        int row, col;
        String color;
	String[][] spotList;
	//    for (i = 0; i < NUM_COLS; i++)
        //        Spots[i] = new Array[NUMBER_NON_FACE_CARDS];
        String[] colLetter = new String[3];
        String[] rowNumber = new String[5];
        String[] suitCodeList =
	    {"&clubs;", "&diams;", "&hearts;", "&spades;"};
        String suitCode;
        String cardCode;
        int RANK_ARRAY_SIZE = 11;
        char rankChar;
        char[] rankArray;
        rankArray = new char[RANK_ARRAY_SIZE];

        spotList = new String[NUM_COLS][NUM_ROWS];

        if (suitIndex == 0 || suitIndex == 3)
            color = "black";
        else color = "red";

        suitCode = suitCodeList[suitIndex];
        if (rank == 1) return printAce(suitCode, color, location);

        if (rank > 10) return printFaceCard(rank, suitCode, color, location);

        for (i = 0; i < RANK_ARRAY_SIZE; i++)
        {
	    rankArray[i] = Character.forDigit(i, 10);
        }
        rankArray[10] = Character.forDigit(0, 10);
        rankChar = rankArray[rank];

        colLetter[COL_A] = "A";
        colLetter[COL_B] = "B";
        colLetter[COL_C] = "C";
        rowNumber[TOP] = "1";
        rowNumber[MIDDLE_TOP] = "2";
        rowNumber[MIDDLE] = "3";
        rowNumber[MIDDLE_BOTTOM] = "4";
        rowNumber[BOTTOM] = "5";
        
        cardCode = "<div class=\"card\" " + location + " >\n" +
                    "   <div class=\"front " + color + "\" >\n" +
	    " <div class=\"index\">" + Integer.toString(rank) +
                  "<br />" + suitCode + "</div>\n";

        spotList[COL_A][TOP] = "4567890";
        spotList[COL_B][TOP] = "23";
        spotList[COL_C][TOP] = "4567890";
        spotList[COL_A][MIDDLE_TOP] = "90";
        spotList[COL_B][MIDDLE_TOP] = "780";
        spotList[COL_C][MIDDLE_TOP] = "90";
        spotList[COL_A][MIDDLE] = "678";
        spotList[COL_B][MIDDLE] = "359";
        spotList[COL_C][MIDDLE] = "678";
        spotList[COL_A][MIDDLE_BOTTOM] = "90";
        spotList[COL_B][MIDDLE_BOTTOM] = "80";
        spotList[COL_C][MIDDLE_BOTTOM] = "90";
        spotList[COL_A][BOTTOM] = "4567890";
        spotList[COL_B][BOTTOM] = "23";
        spotList[COL_C][BOTTOM] = "4567890";

        for (col = COL_A; col <= COL_C; col++)
	{
	    for (row = TOP; row <= BOTTOM; row++)
	    {
                if (spotList[col][row].indexOf(rankChar) >= 0)
		    cardCode = cardCode.concat(
                   "<div class=\"spot" + colLetter[col] +
		       rowNumber[row] + "\">" + suitCode + "</div>\n");
	    }
        }
        cardCode = cardCode.concat("   </div>\n</div>");        
        return cardCode;
    }

    public static void main(String args[])
    {
        int rank;
        int suit;

        rank = Integer.parseInt(args[0]);
        suit = Integer.parseInt(args[1]);
        System.out.println(buildCard(rank, suit, "style=left:6em;"));
    }

} // end class PrintCard