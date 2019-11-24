package com.analysis.tweets.controllers;

import com.analysis.tweets.model.TweetsListPayload;
import com.analysis.tweets.service.TweetsServiceImpl;
import com.analysis.tweets.session.SessionManager;
import com.analysis.tweets.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

/**
 * Live tweets dashboard controller
 */
@RestController
@SuppressWarnings("unused")
public class LiveTweetsController {

    private final String LIVE_TWEETS_API = "/live/{source}";


    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private TweetsServiceImpl tweetsServiceImpl;

    /**
     * Web socket implementation for live tweets
     *
     * @param source         : String
     * @param headerAccessor : {@link SimpMessageHeaderAccessor}
     * @throws Exception : {@link twitter4j.TwitterException}
     * @implNote Session Id help us to determine whether the socket connection is open or not.
     * Session Manager contains last sinceId for each user query
     */
    @MessageMapping({LIVE_TWEETS_API})
    public void liveTweets(@DestinationVariable String source, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        String sessionId = headerAccessor.getSessionId();
        while ((sessionId != null && !sessionId.isEmpty()) && SessionManager.userIdMapping.containsKey(sessionId)) {

            TweetsListPayload tweetsListPayload = tweetsServiceImpl.getDataFromTags(source, SessionManager.userIdMapping.get(sessionId));
            simpMessagingTemplate.convertAndSend(Constants.TOPIC_BROKER + Constants.TOPIC_BROKER_SOURCE + Constants.TOPIC_BROKER_SOURCE_DELIMITER + source, tweetsListPayload.getTweetList());

            synchronized (this) {
                if (!SessionManager.userIdMapping.containsKey(sessionId))
                    break;
                SessionManager.userIdMapping.put(sessionId, tweetsListPayload.getLastSinceId());
            }
            // simulated delay
            Thread.sleep(Constants.REFRESH_RATE);
        }
    }
}
