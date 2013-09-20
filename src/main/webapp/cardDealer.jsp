<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
      <title>Card Dealer</title>
      <link href="card_images.css" rel="stylesheet" type="text/css" />
</head>

<body>
<%@ page import="net.teddoyle.CardDeckApp" %>
<center>
  <h2>Card Dealer</h2>
</center>
<p>
Cards Dealt:
</p>

   
   <br />
   
    <div class="cardBlock"
          style="width:40em;height:14em;background-color:#10dd20;">
       <%= CardDeckApp.checkNull((String)request.getAttribute("dealtCardsString")) %> 
    </div>
<p>
number of Cards Dealt: ${numCardsDealt}

<br />
Dealt Card List:  ${dealtCardList}
<br />
Test String: ${testStr}
<p>
   Select Card Action<p>
   <form method="POST" action="DealCard.do">
      <select name="action" size="1">
         <option value="DealCard">Deal a Card</option>
         <option value="LookAtTopCard">Look at Top Card</option>
         <option value="placeCardOnBottom">Place Card on Bottom</option>
      </select>
      <input type="hidden" name="prevVisit" value ="previously visited" />
      <input type="hidden" name="cardDeck"
           value= ${empty cardDeck ? '""' : cardDeck} />
          
      <br> Previous Load Test : ${prevLoaded} <br />

      <input type="hidden" name="cardsDealt"
                   value= ${empty DealtCards ? '""' : DealtCards} /> 

      <br /> <br />
      <center>
         <input type="SUBMIT">
      </center>
    </form>

</body>
</html>
