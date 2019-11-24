package com.analysis.tweets.model;

import java.util.List;

/**
 * payload to be return from service
 */
public class TweetsListPayload {
    /**
     * response tweets object list
     */
    private List<Tweets> tweets;
    /**
     * max offset of each query
     */
    private Long lastSinceId;

    public TweetsListPayload(List<Tweets> tweets, Long lastSinceId) {
        this.tweets = tweets;
        this.lastSinceId = lastSinceId;
    }

    public List<Tweets> getTweetList() {
        return tweets;
    }

    public Long getLastSinceId() {
        return lastSinceId;
    }
}
