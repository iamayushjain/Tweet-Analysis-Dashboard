package com.analysis.tweets.model;

/**
 * <pre>
 * An enumeration of valid sentiment values for the sentiment classifier.
 * </pre>
 */
public enum Sentiment {
    /**
     * <code>STRONG_NEGATIVE = 0;</code>
     */
    STRONG_NEGATIVE(0),
    /**
     * <code>WEAK_NEGATIVE = 1;</code>
     */
    WEAK_NEGATIVE(1),
    /**
     * <code>NEUTRAL = 2;</code>
     */
    NEUTRAL(2),
    /**
     * <code>WEAK_POSITIVE = 3;</code>
     */
    WEAK_POSITIVE(3),
    /**
     * <code>STRONG_POSITIVE = 4;</code>
     */
    STRONG_POSITIVE(4),
    ;

    /**
     * <code>STRONG_NEGATIVE = 0;</code>
     */
    public static final int STRONG_NEGATIVE_VALUE = 0;
    /**
     * <code>WEAK_NEGATIVE = 1;</code>
     */
    public static final int WEAK_NEGATIVE_VALUE = 1;
    /**
     * <code>NEUTRAL = 2;</code>
     */
    public static final int NEUTRAL_VALUE = 2;
    /**
     * <code>WEAK_POSITIVE = 3;</code>
     */
    public static final int WEAK_POSITIVE_VALUE = 3;
    /**
     * <code>STRONG_POSITIVE = 4;</code>
     */
    public static final int STRONG_POSITIVE_VALUE = 4;

    private int value;

    private Sentiment(int value) {
        this.value = value;
    }

    public final int getNumber() {
        return value;
    }

    public static Sentiment forNumber(int value) {
        switch (value) {
            case 0:
                return STRONG_NEGATIVE;
            case 1:
                return WEAK_NEGATIVE;
            case 2:
                return NEUTRAL;
            case 3:
                return WEAK_POSITIVE;
            case 4:
                return STRONG_POSITIVE;
            default:
                return null;
        }
    }

}
