package com.analysis.tweets.controllers;

import com.analysis.tweets.service.TweetsServiceImpl;
import com.analysis.tweets.model.BaseResponse;
import com.analysis.tweets.model.Tweets;
import com.analysis.tweets.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.TwitterException;

import java.util.List;

/**
 * Controller to return the desired list of tweets
 * if the response is big try using protobuf to return compress json
 */
@RestController
@Slf4j
@SuppressWarnings("unused")
public class TweetsController {

    private final String STREAM_TWEETS_WITH_SENTIMENTS_API = "/tweets_sentiments/";

    @Autowired
    private TweetsServiceImpl tweetsServiceImpl;

    /**
     * handle test case such as empty source etc
     *
     * @param source: String
     * @return data: List<Tweets>
     * @todo add sliding window for every external request
     */
    @RequestMapping(
            value = STREAM_TWEETS_WITH_SENTIMENTS_API,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public BaseResponse streamTweetsFromSource(@RequestParam(value = "source") String source) throws TwitterException {
        if (source.isEmpty())
            return getInvalidParamsResponse();

        List<Tweets> playLists = tweetsServiceImpl.getDataFromTags(source, new Long("0")).getTweetList();
        BaseResponse response = new BaseResponse();
        response.setStatusCode(Constants.SUCCESS_STATUS_CODE);
        response.setData(playLists);
        return response;
    }

    /**
     * response in case of error
     *
     * @return response: {@link BaseResponse}
     */
    private BaseResponse getInvalidParamsResponse() {
        BaseResponse response = new BaseResponse();
        response.setStatusCode(Constants.INVALID_PARAMS_STATUS_CODE);
        response.setData(null);
        return response;
    }
}
