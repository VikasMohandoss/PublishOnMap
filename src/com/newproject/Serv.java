package com.newproject;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Serv
 */
public class Serv extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Serv() {
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
		String choice=request.getParameter("class");
		out.println("<title>Heatmaps  "+choice+"</title>");
		out.println("<style>");
		out.println("html, body {");
		out.println("height: 100%; margin: 0; padding: 0;");
		out.println("}");
		out.println("#map {");
		out.println(" height: 100%;");
		out.println("}");
		out.println("#floating-panel {");
		out.println("position: absolute;");
		out.println("top: 10px;");
		out.println(" left: 25%;");
		out.println(" z-index: 5;");
		out.println(" background-color: #fff;");
		out.println(" padding: 5px;");
		out.println(" border: 1px solid #999;");
		out.println(" text-align: center;");
		out.println("font-family: 'Roboto','sans-serif';");
		out.println("line-height: 30px;");
		out.println("padding-left: 10px;");
		out.println("}");
		out.println("  #floating-panel {");
		out.println(" background-color: #fff;");
		out.println(" border: 1px solid #999;");
		out.println(" left: 25%;");
		out.println(" padding: 5px;");
		out.println(" position: absolute;");
		out.println("top: 10px;");
		out.println("z-index: 5;");
		out.println("}");
		out.println("</style>");
	    out.println("</head>");
	    out.println("<body>");
	    out.println("<form action='Serv'> Class:<select name='class'>");
	    out.println("<option value='cricket'>Cricket</option>");
	    out.println("<option value='football'>Football</option>");
	    out.println("<option value='basketball'>Basketball</option>");
	    out.println("<option value='basketball'>Soccer</option>");
	    out.println("<option value='golf'>Golf</option>");
	    out.println("</select><input type='submit' value='Submit'><br></form>");
	    out.println("<div id='floating-panel'>");
	    out.println("<button onclick='toggleHeatmap()'>Toggle Heatmap</button>");
	    out.println("<button onclick='changeGradient()'>Change gradient</button>");
	    out.println("<button onclick='changeRadius()'>Change radius</button>");
	    out.println("<button onclick='changeOpacity()'>Change opacity</button>");
	    out.println("</div>");
	    out.println("<div id='map'></div>");
	    out.println("<script>");
	    out.println("var map, heatmap;");
	    out.println("function initMap() {");
	    out.println(" map = new google.maps.Map(document.getElementById('map'), {");
	    out.println(" zoom: 1,");
	    out.println(" center: {lat: 37.775, lng: -122.434},");
	    out.println("  mapTypeId: google.maps.MapTypeId.SATELLITE");
	    out.println(" });");
	    out.println("heatmap = new google.maps.visualization.HeatmapLayer({");
	    out.println("data: getPoints(),");
	    out.println(" map: map");
	    out.println(" });");
	    out.println(" }");
	    out.println("function toggleHeatmap() {");
	    out.println(" heatmap.setMap(heatmap.getMap() ? null : map);");
	    out.println(" }");
	    out.println("function changeGradient() {");
	    out.println("  var gradient = [");
	    out.println(" 'rgba(0, 255, 255, 0)',");
	    out.println("  'rgba(0, 255, 255, 1)',");
	    out.println("  'rgba(0, 191, 255, 1)',");
	    out.println(" 'rgba(0, 127, 255, 1)',");
	    out.println(" 'rgba(0, 63, 255, 1)',");
	    out.println(" 'rgba(0, 0, 255, 1)',");
	    out.println(" 'rgba(0, 0, 223, 1)',");
	    out.println(" 'rgba(0, 0, 191, 1)',");
	    out.println(" 'rgba(0, 0, 159, 1)',");
	    out.println(" 'rgba(0, 0, 127, 1)',");
	    out.println(" 'rgba(63, 0, 91, 1)',");
	    out.println(" 'rgba(127, 0, 63, 1)',");
	    out.println("  'rgba(191, 0, 31, 1)',");
	    out.println("  'rgba(255, 0, 0, 1)'");
	    out.println("]");
	    out.println(" heatmap.set('gradient', heatmap.get('gradient') ? null : gradient);");
	    out.println(" }");
        out.println("function changeRadius() {");
        out.println("heatmap.set('radius', heatmap.get('radius') ? null : 20);");
        out.println("	}");
        out.println("function changeOpacity() {");
	    out.println(" heatmap.set('opacity', heatmap.get('opacity') ? null : 0.2);");
    	out.println("}");
	    out.println("function getPoints() {");
	    out.println("return [");
	    try {
       Class.forName("org.postgresql.Driver");
       Connection c = DriverManager.getConnection("jdbc:postgresql://cloudpsql.ckvaekjxl9qc.us-west-2.rds.amazonaws.com:5432/TwitterData",
              "postgre", "password");
       Statement stmt;
       stmt= c.createStatement();
       String sql= "select latitude,longitude from twittergeolocation";
       if(choice!=null){
    	   sql="select latitude, longitude from twittergeolocation where lower(tweet) like '%"+choice+"%'";
       }
       ResultSet rs;
   	rs=stmt.executeQuery(sql);
   	while(rs.next()){
    		//System.out.println(rs.getDouble("latitude")+" "+rs.getDouble("longitude"));
    		//System.out.println("new google.maps.LatLng("+rs.getDouble("latitude")+","+rs.getDouble("longitude")+"),");
   	    	out.println("new google.maps.LatLng("+rs.getDouble("latitude")+","+rs.getDouble("longitude")+"),");
   	}
   	//System.System.out.println(rs.next());
       } catch (Exception e) {
       e.printStackTrace();
       System.err.println(e.getClass().getName()+": "+e.getMessage());
       System.exit(0);
        }
	out.println("];}");	
	out.println("</script>");
    out.println("<script async defer ");
    out.println("src='https://maps.googleapis.com/maps/api/js?key=AIzaSyB5PovHHPZniBHZrU8A3E4Etfek6Y3SA34&signed_in=true&libraries=visualization&callback=initMap'>");
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