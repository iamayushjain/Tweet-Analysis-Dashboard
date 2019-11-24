package com.analysis.tweets.utils;

/**
 * Wrapper class for keeping project constant values
 */
@SuppressWarnings("unused")
public class Constants {
    public static final int EXPLORE_MAX_LIMIT = 15;
    public static final int REFRESH_RATE = 10000;
    public static final int SUCCESS_STATUS_CODE = 200;
    public static final int INVALID_PARAMS_STATUS_CODE = 400;
    public static final int MAX_TWEETS_LIMIT = 15;
    public static final String TOPIC_BROKER = "/topic";
    public static final String TOPIC_BROKER_SOURCE = "/source";
    public static final String TOPIC_BROKER_SOURCE_DELIMITER = ".";
    public static final String WEB_SOCKET_ENDPOINT = "/ws";
    public static final String APPLICATION_DESTINATION_PREFIX = "/app";
}
