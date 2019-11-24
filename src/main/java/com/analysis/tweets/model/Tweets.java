package com.analysis.tweets.model;

/**
 * Tweets response skeleton class
 */
@SuppressWarnings("unused")
public class Tweets {
    private String userName;
    private String userDisplayName;
    private String text;
    private Sentiment sentiment;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserDisplayName() {
        return userDisplayName;
    }

    public void setUserDisplayName(String userDisplayName) {
        this.userDisplayName = userDisplayName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Sentiment getSentiment() {
        return sentiment;
    }

    public void setSentiment(Sentiment sentiment) {
        this.sentiment = sentiment;
    }

    public Tweets(String userName, String userDisplayName, String text, Sentiment sentiment) {
        this.userName = userName;
        this.userDisplayName = userDisplayName;
        this.text = text;
        this.sentiment = sentiment;
    }
}
