<%
Integer counter = (Integer)session.getAttribute("counter");
if (counter == null) {
  counter = Integer.valueOf(0);
}
counter = Integer.valueOf(counter.intValue() + 1);
session.setAttribute("counter", counter);
%>

<p><%= counter %></p>

