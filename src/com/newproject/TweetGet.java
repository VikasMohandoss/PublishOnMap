package com.newproject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;
/**
 * <p>This is a code example of Twitter4J Streaming API - sample method support.<br>
 * Usage: java twitter4j.examples.PrintSampleStream<br>
 * </p>
 *
 * @author Yusuke Yamamoto - yusuke at mac.com
 */
public final class TweetGet {
    /**
     * Main entry of this application.
     *
     * @param args
     */
    public static void main(String[] args) throws TwitterException {
    	//just fill this
    	 ConfigurationBuilder cb = new ConfigurationBuilder();
         cb.setDebugEnabled(true)
           .setOAuthConsumerKey("NxLtgCpDvSbKgOu0zKL5iEwnl")
           .setOAuthConsumerSecret("A47kzWZ9rV7qSoLwMFP3wg4qEbQKvwGv6yDuVeiF6OS8Y3kjCx")
           .setOAuthAccessToken("704558463-J8tM2aH7NKmpKyIdgc9afyIzDO3XNdKiBdG8Bs2z")
           .setOAuthAccessTokenSecret("TdSZrMkDK4lK5wkM9D8lvIZmcJn6kHL13G1aEX41MuI6o");
        
      
       TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
       StatusListener listener = new StatusListener() {
           @Override
           public void onStatus(Status status) 
           {
        	   
           	if(status.getGeoLocation()!=null)
           	{
           		String temp,temp2,temp3;
           		temp=status.getText();
           	    temp=temp.replace("'", "");
           	    temp2=status.getPlace().getName();
           	    temp2=temp2.replace("'", "");
           	    temp3=status.getUser().getScreenName();
           	    temp3=temp3.replace("'", "");
           		String sql="insert into twittergeolocation(username,userid,tweet,place,latitude,longitude) values('"+
           				temp3+"',"+status.getUser().getId()+",'"+temp+"','"+
           				temp2+"',"+status.getGeoLocation().getLatitude()+","+status.getGeoLocation().getLongitude()+")";
           		System.out.println(sql);
            	         try {
            	            Class.forName("org.postgresql.Driver");
            	           Connection c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TwitterData",
            	               "postgres", "password");
            	           Statement stmt;
            	            stmt= c.createStatement();
                    		stmt.executeUpdate(sql);
          
            	         } catch (Exception e)
            	         {
            	            e.printStackTrace();
            	            System.err.println(e.getClass().getName()+": "+e.getMessage());
            	            System.exit(0);
            	         }

           	}
           
           }

           @Override
         public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
             System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
         }

         @Override
         public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
             System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
         }

         @Override
         public void onScrubGeo(long userId, long upToStatusId) {
             System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
         }

         @Override
         public void onStallWarning(StallWarning warning) {
             System.out.println("Got stall warning:" + warning);
         }

         @Override
         public void onException(Exception ex) {
             ex.printStackTrace();
         }
     };

       
       FilterQuery fq = new FilterQuery();   
       String keywords[] = {"football","cricket","basketball","soccer","golf"};
       fq.track(keywords);
       twitterStream.addListener(listener);
       twitterStream.filter(fq);  
       //twitterStream.sample();

    }
}