package jp.programmers.examples.twitter;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.User;

public class HelloTwitter {
    public static void main(String[] args) throws Exception {
        Twitter twitter = new TwitterFactory().getInstance();
        User user = twitter.showUser("twitter");
        System.out.println(twitter.getUserTimeline(user.getId()));
    }
}
