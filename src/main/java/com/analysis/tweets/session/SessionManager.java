package com.analysis.tweets.session;

import java.util.HashMap;

/**
 * Session manager to track userId with maxSinceId(last offset id)
 * to be place in distributed system
 */
public class SessionManager {
    /**
     * @implSpec pair(sessionId, lastOffsetId)
     */
    public static HashMap<String, Long> userIdMapping = new HashMap<>();
}
