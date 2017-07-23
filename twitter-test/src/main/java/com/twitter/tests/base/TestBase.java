package com.twitter.tests.base;

import com.codeborne.selenide.Screenshots;
import com.google.common.io.Files;
import org.apache.log4j.Logger;
import org.junit.After;
import org.testng.annotations.*;
import ru.yandex.qatools.allure.annotations.Attachment;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class TestBase {

    public static Logger log = Logger.getLogger(TestBase.class);

    @After
    public void takeScreenshot() throws IOException {
        screenshot();
    }
    @Attachment(type = "image/png")
    public byte[] screenshot() throws IOException {
        File screenshot = Screenshots.takeScreenShotAsFile();
        return Files.toByteArray(screenshot);
    }

    @BeforeClass
    public void openPageAndLogIn(){
        open("https://twitter.com/?lang=en");
        log.info("Page opened.");
        //Log in
        $("#signin-email").sendKeys("test80404@gmail.com");
        $("#signin-password").sendKeys("fortest456");
        $$("[type='submit']").findBy(text("Log in")).click();
        //Assert that user is logged in
        $(".u-textInheritColor.js-nav").shouldHave(text("Ann Smith"));
        log.info("User is logged in");
    }

    @AfterClass
    public void complete() throws IOException {
        close();
        log.info("Test completed");
    }

}

