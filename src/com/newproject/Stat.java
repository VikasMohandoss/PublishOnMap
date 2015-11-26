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
 * Servlet implementation class Stat
 */
public class Stat extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Stat() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		PrintWriter out=response.getWriter();
		String[] choice ={"football","cricket","basketball","soccer","golf"};
		response.setContentType("text/html");
		out.println("<html>");
		out.println("<head>");
		out.println("<script type='text/javascript' src='https://www.google.com/jsapi'></script>");
		out.println(" <script type='text/javascript'>");
		out.println("google.load('visualization', '1', {packages:['corechart']});");
		out.println("google.setOnLoadCallback(drawChart);");
		out.println(" function drawChart() {");
		out.println("var data = google.visualization.arrayToDataTable([");
		out.println("['Sports', 'Number of Tweets'],");
		try {
		       Class.forName("org.postgresql.Driver");
		       Connection c = DriverManager.getConnection("jdbc:postgresql://cloudpsql.ckvaekjxl9qc.us-west-2.rds.amazonaws.com:5432/TwitterData",
		              "postgre", "password");
		       Statement stmt;
		       stmt= c.createStatement();
		       ResultSet rs;
		       String sql;
		       for(int i=0;i<5;i++){
		    	   sql="select count(*) from twittergeolocation where lower(tweet) like '%"+choice[i]+"%'";
		    	   rs=stmt.executeQuery(sql);
		    	   rs.next();
		    	   out.println("['"+choice[i]+"',"+rs.getLong("count")+"],");
		    	   
		       }
		 }catch(Exception e){
		    	   System.out.println(e);
		       }
		out.println("]);");
		out.println("var options = {");
		out.println(" title: 'Number of tweets per sport',");
		out.println(" pieHole: 0.4,");
		out.println("};");
		out.println(" var chart = new google.visualization.PieChart(document.getElementById('donutchart'));");
    	out.println(" chart.draw(data, options);");
		out.println(" }");
		out.println(" </script>");
		out.println("</head>");
		out.println("<body>");
	    out.println("<div id='donutchart' style='width: 900px; height: 500px;'></div>");
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
