package com.newproject;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class NewServ
 */
public class NewServ extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewServ() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		out.println("<!DOCTYPE html>");
		out.println("<html>");
		out.println(" <head>");
		out.println("<meta charset='utf-8'>");
		out.println("<meta http-equiv='content-type' content='text/html; charset=UTF-8' />");
//		out.println("<meta name='viewport' content='width=device-width'>");
		out.println("<script src='http://maps.google.com/maps/api/js?sensor=false'");
		out.println("type='text/javascript'></script>");
		out.println("</head>");
		out.println("<body>");
		
		out.println(" <div>");
		out.println("  <input type='text' id='messageinput'/>");
		out.println("  </div>");
		out.println(" <div>");
		out.println("<button type='button' onclick='openSocket();' >Open</button>");
		out.println("<button type='button' onclick='send();' >Send</button>");
		out.println("<button type='button' onclick='closeSocket();' >Close</button>");
		out.println("  </div>");
		
		
		
		out.println("<!-- Server responses get written here -->");
		out.println("<div id='map' style='width: 1000px; height: 600px;'></div>");
		
		
		out.println("<script type='text/javascript'>");
		out.println("  var locations = [");
		out.println("   ['Bondi Beach', -33.890542, 151.274856, 4],");
		out.println("  ['Coogee Beach', -33.923036, 151.259052, 5],");
		out.println("  ['Cronulla Beach', -34.028249, 151.157507, 3],");
		out.println("  ['Manly Beach', -33.80010128657071, 151.28747820854187, 2],");
		out.println(" ['Maroubra Beach', -33.950198, 151.259302, 1]");
		out.println(" ];");

		out.println(" var map = new google.maps.Map(document.getElementById('map'), {");
		out.println("  zoom: 10,");
		out.println(" center: new google.maps.LatLng(-33.92, 151.25),");
		out.println(" mapTypeId: google.maps.MapTypeId.ROADMAP");
		out.println(" });");

		out.println("  var infowindow = new google.maps.InfoWindow();");

		out.println(" var marker, i;");

		out.println("for (i = 0; i < locations.length; i++) {  ");
		out.println("  marker = new google.maps.Marker({");
		out.println("   position: new google.maps.LatLng(locations[i][1], locations[i][2]),");
		out.println("  map: map");
		out.println(" });");

		out.println(" google.maps.event.addListener(marker, 'click', (function(marker, i) {");
		out.println(" return function() {");
		out.println(" infowindow.setContent(locations[i][0]);");
		out.println(" infowindow.open(map, marker);");
		out.println(" }");
		out.println(" })(marker, i));");
		out.println("}");
		out.println("</script>");
		
		out.println("<div id='messages'></div>");
	
		out.println("  <!-- Script to utilise the WebSocket -->");
		out.println(" <script type='text/javascript'>");
		out.println("var webSocket;");
		out.println(" var messages = document.getElementById('messages');");
		out.println(" function openSocket(){");
		// Ensures only one connection is open at a time
		out.println("if(webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED){");
		out.println("  writeResponse('WebSocket is already opened.');");
		out.println("   return;");
		out.println(" }");
		// Create a new instance of the websocket
		out.println("webSocket = new WebSocket('ws://localhost:8080/PublishOnMap/echo2');");

		/**
		 * Binds functions to the listeners for the websocket.
		 */
		out.println("webSocket.onopen = function(event){");
		// For reasons I can't determine, onopen gets called twice
		// and the first time event.data is undefined.
		// Leave a comment if you know the answer.
		out.println("if(event.data === undefined)");
		out.println("  return;");

		out.println(" writeResponse(event.data);");
		out.println("};");

		out.println("webSocket.onmessage = function(event){");
		out.println(" writeResponse('--->'+event.data);");
		out.println("};");

		out.println("webSocket.onclose = function(event){");
		out.println("writeResponse('Connection closed');");
		out.println("};");
		out.println("}");
		out.println("function send(){");
		out.println("var text = document.getElementById('messageinput').value;");
		out.println("webSocket.send(text);");
		out.println("}");

		out.println("function closeSocket(){");
		out.println("  webSocket.close();");
		out.println("}");
		
		out.println(" function writeResponse(text){");
		out.println("messages.innerHTML += '<br/>' + text;");
		out.println("var part= text.split(',');");

		out.println("  marker = new google.maps.Marker({");
		out.println("   position: new google.maps.LatLng(part[1], part[2]),");
		out.println("  map: map");
		out.println(" });");

		out.println(" google.maps.event.addListener(marker, 'click', (function(marker, i) {");
		out.println(" return function() {");
		out.println(" infowindow.setContent(part[0]);");
		out.println(" infowindow.open(map, marker);");
		out.println(" }");
		out.println(" })(marker, i));");
//		out.println("}");
//		out.println("}");
		out.println("</script>"); 
		out.println("</body>");
		out.println("</html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
