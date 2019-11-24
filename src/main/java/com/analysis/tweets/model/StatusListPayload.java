package com.analysis.tweets.model;

import twitter4j.Status;

import java.util.List;

/**
 * payload to be return from dao
 */
public class StatusListPayload {
    /**
     * twitter status object list
     */
    private List<Status> statusList;
    /**
     * max offset of each query
     */
    private Long lastSinceId;


    public StatusListPayload(List<Status> status, Long lastSinceId) {
        this.statusList = status;
        this.lastSinceId = lastSinceId;
    }

    public List<Status> getStatusList() {
        return statusList;
    }

    public Long getLastSinceId() {
        return lastSinceId;
    }
}
