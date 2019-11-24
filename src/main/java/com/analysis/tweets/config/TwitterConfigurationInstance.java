package com.analysis.tweets.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Singleton class for twitter configuration
 */
@Component
@SuppressWarnings("unused")
public class TwitterConfigurationInstance extends TwitterFactory {

    private static String consumerKey;
    private static String consumerSecret;
    private static String accessToken;
    private static String accessTokenSecret;

    @Value("${ConsumerKey}")
    public void setConsumerKey(String consumerKeyValue) {
        consumerKey = consumerKeyValue;
    }

    @Value("${ConsumerSecret}")
    public void setConsumerSecret(String consumerSecretValue) {
        consumerSecret = consumerSecretValue;
    }

    @Value("${AccessToken}")
    public void setAccessToken(String accessTokenValue) {
        accessToken = accessTokenValue;
    }

    @Value("${AccessTokenSecret}")
    public void setAccessTokenSecret(String accessTokenSecretValue) {
        accessTokenSecret = accessTokenSecretValue;
    }

    private static Twitter instance;
    private static final Logger logger = LoggerFactory.getLogger(TwitterConfigurationInstance.class);

    private TwitterConfigurationInstance() {
    }

    /**
     * Thread Safe Singleton instance generation
     *
     * @return Twitter
     */
    public static Twitter getTwitterInstance() {
        if (instance == null) {
            synchronized (TwitterConfigurationInstance.class) {
                if (instance == null) {
                    logger.info("Init Twitter Client");
                    ConfigurationBuilder cb = new ConfigurationBuilder();
                    cb.setDebugEnabled(true)
                            .setOAuthConsumerKey(consumerKey)
                            .setOAuthConsumerSecret(consumerSecret)
                            .setOAuthAccessToken(accessToken)
                            .setOAuthAccessTokenSecret(accessTokenSecret);
                    TwitterFactory tf = new TwitterFactory(cb.build());
                    instance = tf.getInstance();
                }
            }
        }
        return instance;
    }
}