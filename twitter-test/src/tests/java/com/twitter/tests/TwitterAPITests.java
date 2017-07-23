package com.twitter.tests;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import twitter4j.*;
import java.util.List;
import static org.testng.Assert.*;

public class TwitterAPITests {

        public static Logger log = Logger.getLogger(TwitterAPITests.class);
        public static long recentStatusId;
        public static Twitter twitter;
        public static String testStatus = "Created for testing purpose only";

        public static Twitter getInstance() throws TwitterException {
                twitter4j.Twitter twitter = new TwitterFactory().getInstance();
                User user = twitter.verifyCredentials();
                return twitter;
        }

        @Test
        public void getHomeTimeline() throws TwitterException {
                // Gets TestBase instance with default credentials
                twitter = getInstance();
                User user = twitter.verifyCredentials();

                //Gets home timeline
                List<Status> statuses = twitter.getHomeTimeline();
                log.info("Showing @" + user.getScreenName() + "'s home timeline.");
                //Reads "created_at", "retweet_count" and "text" attributes from timeline
                for (
                        Status status : statuses) {
                        log.info("@" + status.getCreatedAt() + " - " + status.getRetweetCount() + " - " + status.getText());
                }
        }

        @Test
        public void updateStatus() throws TwitterException {
                twitter = getInstance();
                //Updates status
                twitter.updateStatus(testStatus);
                log.info("Status posted");

                //Gets recent status text
                List<Status> statuses = twitter.getHomeTimeline();
                String recent = statuses.get(0).getText();
                //Asserts that recent status text matches to the expected
                assertEquals(testStatus, recent, "Recent status doesn't match to the test one");
                log.info("Posted status exists");

                //Returns recent status Id
                recentStatusId = statuses.get(0).getId();
                log.info("Recent status Id: " + recentStatusId);
        }

        @Test(dependsOnMethods = {"updateStatus"})
        public void duplicateStatus() throws TwitterException {
                twitter = getInstance();
                try {
                        //Tries to duplicate status
                        twitter.updateStatus(testStatus);
                        //Fails test if update was successful
                        fail("Exception wasn't thrown");
                } catch (TwitterException te){
                        //Asserts returned exception status code
                        assertEquals(te.getStatusCode(), 403);
                }
                log.info("Exception status code matches to the expected");
        }

        @Test(dependsOnMethods = {"updateStatus", "duplicateStatus"})
        public void destroyStatus() throws TwitterException {
                twitter = getInstance();
                //Destroys status by Id
                twitter.destroyStatus(recentStatusId);
                //Gets latest status from home timeline
                List<Status> statuses = twitter.getHomeTimeline();
                long recent = statuses.get(0).getId();
                //Asserts that desroyed status doesn't exist
                assertNotEquals(recent, recentStatusId, "Recent status Id shouldn't be equal to the test one");
                log.info("Posted status deleted");
        }
}


