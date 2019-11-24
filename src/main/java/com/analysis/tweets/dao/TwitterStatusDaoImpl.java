package com.analysis.tweets.dao;

import com.analysis.tweets.config.TwitterConfigurationInstance;
import com.analysis.tweets.model.StatusListPayload;
import org.springframework.stereotype.Repository;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Implementation logic for fetching twitter status
 */
@Repository
@SuppressWarnings("unused")
public class TwitterStatusDaoImpl implements TwitterStatusDao {

    /**
     * Implementation to fetch list of tweets from a particular hash tag or word
     *
     * @param source       :String
     * @param lastOffsetId : Long
     * @return StatusListPayload
     * @throws TwitterException: TwitterException
     * @implNote Session manager keep tracks for each session lastOffsetId
     */
    @Override
    public StatusListPayload fetchStatusFromSource(String source, Long lastOffsetId) throws TwitterException {
        Twitter twitter = TwitterConfigurationInstance.getTwitterInstance();
        Query query = new Query(source);
        if (lastOffsetId > 0)
            query.sinceId(lastOffsetId);
        QueryResult result = twitter.search(query);
        return new StatusListPayload(result.getTweets(), result.getMaxId());
    }

    /**
     * Implementation to fetch list of tweets from a particular username
     *
     * @param user         :String
     * @param lastOffsetId : Long
     * @return StatusListPayload
     * @throws TwitterException: TwitterException
     */
    @Override
    public StatusListPayload fetchStatusForUser(String user, Long lastOffsetId) throws TwitterException {
        Twitter twitter = TwitterConfigurationInstance.getTwitterInstance();
        return new StatusListPayload(twitter.getUserTimeline(user), lastOffsetId);
    }
}