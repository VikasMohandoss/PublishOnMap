package com.newproject;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
 
/** 
 * @ServerEndpoint gives the relative name for the end point
 * This will be accessed via ws://localhost:8080/EchoChamber/echo
 * Where "localhost" is the address of the host,
 * "EchoChamber" is the name of the package
 * and "echo" is the address to access this class from the server
 */
@ServerEndpoint("/echo2") 
public class Echo2 {
    /**
     * @OnOpen allows us to intercept the creation of a new session.
     * The session class allows us to send data to the user.
     * In the method onOpen, we'll let the user know that the handshake was 
     * successful.
     */
    @OnOpen
    public void onOpen(Session session){
        System.out.println(session.getId() + " has opened a connection"); 
        /*try {
            Class.forName("org.postgresql.Driver");
            Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TwitterData",
                   "postgres", "password");
            Statement stmt;
            stmt= c.createStatement();
            String sql= "select place,latitude,longitude,sentiment,tweet from twittergeolocation";
            ResultSet rs;
        	rs=stmt.executeQuery(sql);
        	while(rs.next()){
         		//System.out.println(rs.getDouble("latitude")+" "+rs.getDouble("longitude"));
         		//System.out.println("new google.maps.LatLng("+rs.getDouble("latitude")+","+rs.getDouble("longitude")+"),");
        	    	session.getBasicRemote().sendText(rs.getString("place")+","+rs.getDouble("latitude")+","+rs.getDouble("longitude")+","+rs.getString("sentiment"));
        	}
        	//System.System.out.println(rs.next());
            } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
             }*/
    }
 
    /**
     * When a user sends a message to the server, this method will intercept the message
     * and allow us to react to it. For now the message is read as a String.
     */
    @OnMessage
    public void onMessage(String message, Session session){
        System.out.println("Message from " + session.getId() + ": " + message);
        try {
            session.getBasicRemote().sendText(message);
            try {
                Class.forName("org.postgresql.Driver");
                Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TwitterData",
                       "postgres", "password");
                Statement stmt;
                stmt= c.createStatement();
                String sql= "select place,latitude,longitude,sentiment from twittergeolocation";
                if(message!=null){
             	   sql="select place,latitude, longitude,sentiment from twittergeolocation where lower(tweet) like '%"+message+"%'";
                }
                ResultSet rs;
            	rs=stmt.executeQuery(sql);
            	while(rs.next()){
             		//System.out.println(rs.getDouble("latitude")+" "+rs.getDouble("longitude"));
             		//System.out.println("new google.maps.LatLng("+rs.getDouble("latitude")+","+rs.getDouble("longitude")+"),");
            	    	session.getBasicRemote().sendText(rs.getString("place")+","+rs.getDouble("latitude")+","+rs.getDouble("longitude")+","+rs.getString("sentiment"));
            	}
            	//System.System.out.println(rs.next());
                } catch (Exception e) {
                e.printStackTrace();
                System.err.println(e.getClass().getName()+": "+e.getMessage());
                System.exit(0);
                 }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
 
    /**
     * The user closes the connection.
     * 
     * Note: you can't send messages to the client from this method
     */
    @OnClose
    public void onClose(Session session){
        System.out.println("Session " +session.getId()+" has ended");
    }
}
