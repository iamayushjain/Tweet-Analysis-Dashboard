import com.analysis.tweets.model.Sentiment;
import com.analysis.tweets.nlp.CoreSentimentalPipeline;
import org.junit.Assert;
import org.junit.Test;

public class CoreSentimentTest {
    CoreSentimentalPipeline coreSentimentalPipeline = new CoreSentimentalPipeline();

    @Test
    public void testCoreSentimentWeakPositive() {
        Assert.assertSame(coreSentimentalPipeline.getPrimarySentiment("you are doing great"), Sentiment.WEAK_POSITIVE);
    }

    @Test
    public void testCoreSentimentStrongPositive() {
        Assert.assertSame(coreSentimentalPipeline.getPrimarySentiment("This was the best movie I have ever seen"), Sentiment.STRONG_POSITIVE);
    }

    @Test
    public void testCoreSentimentNeutral() {
        Assert.assertSame(coreSentimentalPipeline.getPrimarySentiment("Varun talks in a condescending way"), Sentiment.NEUTRAL);
    }

    @Test
    public void testCoreSentimentWeakNegative() {
        Assert.assertSame(coreSentimentalPipeline.getPrimarySentiment("Shyam is not a great batsman"), Sentiment.WEAK_NEGATIVE);
    }

    @Test
    public void testCoreSentimentStrongNegative() {
        Assert.assertSame(coreSentimentalPipeline.getPrimarySentiment("This was the worst movie I have ever seen"), Sentiment.STRONG_NEGATIVE);
    }

}
