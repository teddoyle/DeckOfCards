<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
      <title>Card Dealer</title>
      <link href="card_images.css" rel="stylesheet" type="text/css" />
</head>

<body>
<div style="text-align:center">
  <h2>Card Dealer</h2>
</div>
<p>
Cards Dealt:
</p>
   <div class="cardBlock"
          style="width:40em;height:7em;background-color:#10dd20;">
      <%! String Card0; %>
      <% Card0 = (String)request.getAttribute("GraphicCard00"); %>
      <%= Card0 == null ? "" : Card0 %>

      <%! String Card1; %>
      <% Card1 = (String)request.getAttribute("GraphicCard01"); %>
      <%= Card1 == null ? "" : Card1 %>

      <%! String Card2; %>
      <% Card2 = (String)request.getAttribute("GraphicCard02"); %>
      <%= Card2 == null ? "" : Card2 %>

      <%! String Card3; %>
      <% Card3 = (String)request.getAttribute("GraphicCard03"); %>
      <%= Card3 == null ? "" : Card3 %>

      <%! String Card4; %>
      <% Card4 = (String)request.getAttribute("GraphicCard04"); %>
      <%= Card4 == null ? "" : Card4 %>


   </div>
<p>
number of Cards Dealt: ${numCardsDealt}
</p>
     <div style="text-align:center">Cards in your hand</div>
       <table style="text-align:center">
         <tr>
            <td>Card 1: </td>  <td>CardDealt00</td> <td>${CardDealt00}</td>
         </tr>
         <tr>
            <td>Card 2: </td>  <td>CardDealt01</td> <td>${CardDealt01}</td>
         </tr>
         <tr>
            <td>Card 3: </td>  <td>CardDealt02</td> <td>${CardDealt02}</td>
         </tr>
       </table>
<br>
Dealt Card List:  ${dealtCardList}
<br>
Test String: ${testStr}
<p>
   Select Card Action<p>
   <form method="POST" action="/playCards">
      <select name="action" size="1">
         <option value="DealCard">Deal a Card</option>
         <option value="LookAtTopCard">Look at Top Card</option>
         <option value="placeCardOnBottom">Place Card on Bottom</option>
      </select>
      <input type="hidden" name="prevVisit" value ="previously visited" />
      <input type="hidden" name="cardDeck"
           value= ${empty cardDeck ? '""' : cardDeck} />
          
     <!--  < % = (String)request.getAttribute("cardDeck") % > -->
      <br> Previous Load Test : ${prevLoaded} <br />
      <c:if test="${prevLoaded=='yes' }" >
         <br />
          Previuosly Loaded
         <br />
      </c:if>
      <input type="hidden" name="cardsDealt"
                   value= ${empty DealtCards ? '""' : DealtCards} /> 

      <br /> <br />
      <div style="text-align:center">
         <input type="SUBMIT">
      </div>
    </form>

    <c:if test="${prevVisit != 'previously visited'}" >
       <br />
         Previous Visit: ${prevVisit}
       <br />
         Top card is: ${topCard}
       <br />
         Card dealt: ${cardDealt}
       <br />
         Current Deck: ${cardDeck}
       <br />
    </c:if>

</body>
</html>
