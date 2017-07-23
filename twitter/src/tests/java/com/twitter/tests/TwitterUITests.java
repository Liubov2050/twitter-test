package com.twitter.tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.twitter.tests.base.TestBase;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;
import static com.codeborne.selenide.Selenide.*;
import static org.testng.Assert.*;

public class TwitterUITests extends TestBase{

        public static String testStatus = "Created for testing purpose only";
        public static String recentStatusId;
        public static Logger log = Logger.getLogger(TwitterUITests.class);

        @Test
        public void getHomeTimeline(){
                //Gets timeline tweets amount
                int timelineSize = $$("[data-item-type='tweet']").size();
                //Reads "created_at", "retweet_count" and "text" attributes from timeline
                for(int i=0; i < timelineSize; i++){
                        String createdAt = $$(".time").get(i).getText();
                        String retweetCount = $$(".ProfileTweet-action--retweet.u-hiddenVisually span").get(i).getAttribute("data-tweet-stat-count");
                        String text = $$(".TweetTextSize.js-tweet-text.tweet-text").get(i).getText();
                        log.info(createdAt + " " + retweetCount + " " + text);
                }
                log.info("getHomeTimeline executed");
        }

        @Test
        public void updateStatus(){
                //Updates status
                $("#tweet-box-home-timeline").sendKeys(testStatus);
                $(".home-tweet-box.tweet-box.component.tweet-user .TweetBoxToolbar .TweetBoxToolbar-tweetButton.tweet-button button").click();
                sleep(1000);

                log.info("Status posted");
                //Gets recent status text
                String recentStatus = $$(".TweetTextSize.js-tweet-text.tweet-text").get(0).getText();
                //Asserts that recent status text matches to the expected
                assertEquals(testStatus, recentStatus, "Recent status doesn't match to the test one");
                log.info("Posted status exists");

                recentStatusId = ($$(".js-stream-item.stream-item.stream-item").get(0).getAttribute("data-item-id"));
                log.info("Recent status Id: " + recentStatusId);
        }

        @Test (dependsOnMethods = {"updateStatus"})
        public void duplicateStatus(){
                //Tries to duplicate status
                $("#tweet-box-home-timeline").sendKeys(testStatus);
                $(".home-tweet-box.tweet-box.component.tweet-user .TweetBoxToolbar .TweetBoxToolbar-tweetButton.tweet-button button").click();
                //Gets message text
                String message = $(".message .message-inside .message-text").waitUntil(Condition.appears, 25000).getText();
                //Asserts that correct message displayed
                assertEquals("You have already sent this Tweet.", message, "Message displayed doesn't match to the expected");
                log.info("Message matches to the expected");
                refresh();
        }

        @Test(dependsOnMethods = {"updateStatus", "duplicateStatus"})
        public void destroyStatus() {
                SelenideElement recentStatus = $$(".js-stream-item.stream-item.stream-item").findBy(Condition.attribute("data-item-id", recentStatusId));
                recentStatus.find(".dropdown").click();
                recentStatus.find(".js-actionDelete button").click();
                $(".EdgeButton.EdgeButton--danger.delete-action").click();

                //Gets message text
                String message = $(".message .message-inside .message-text").waitUntil(Condition.appears, 25000).getText();
                //Asserts that correct message displayed
                assertEquals("Your Tweet has been deleted.", message, "Message displayed doesn't match to the expected");
                log.info("Message matches to the expected");
        }

    }


