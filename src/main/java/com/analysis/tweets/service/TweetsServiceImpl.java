package com.analysis.tweets.service;

import com.analysis.tweets.dao.TwitterStatusDao;
import com.analysis.tweets.model.StatusListPayload;
import com.analysis.tweets.model.Tweets;
import com.analysis.tweets.model.TweetsListPayload;
import com.analysis.tweets.nlp.CoreSentimentalPipeline;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.TwitterException;

import java.util.stream.Collectors;

/**
 * Service implementation for fetching tweets
 */
@Service
@SuppressWarnings("unused")
public class TweetsServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(TweetsServiceImpl.class);
    @Autowired
    private TwitterStatusDao twitterStatusDao;
    private CoreSentimentalPipeline coreSentimentalPipeline = new CoreSentimentalPipeline();

    public TweetsListPayload getDataFromTags(String source, long lastOffsetTime) throws TwitterException {
        StatusListPayload statusListPayload;
        if (source.startsWith("@"))
            statusListPayload = twitterStatusDao.fetchStatusForUser(source, lastOffsetTime);
        else
            statusListPayload = twitterStatusDao.fetchStatusFromSource(source, lastOffsetTime);

        logger.info("-----------------------------------------------------------");
        for (Status status : statusListPayload.getStatusList()) {
            logger.info("@" + status.getUser().getScreenName() + ":" + status.getText());
        }
        return transformPlayListEntityToBO(statusListPayload);
    }

    //convert entity to pojo class
    private TweetsListPayload transformPlayListEntityToBO(StatusListPayload playListEntityList) {
        return new TweetsListPayload(playListEntityList.getStatusList().stream()
                .map(entity -> new Tweets(entity.getUser().getScreenName(), entity.getUser().getName(), entity.getText(),
                        coreSentimentalPipeline.getPrimarySentiment(entity.getText())))
                .collect(Collectors.toList()), playListEntityList.getLastSinceId());
    }
}
