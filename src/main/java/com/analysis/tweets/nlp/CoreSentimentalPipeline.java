package com.analysis.tweets.nlp;

import com.analysis.tweets.model.Sentiment;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;


/**
 * basic core sentimental pipeline wrapper class
 */
public class CoreSentimentalPipeline {

    private StanfordCoreNLP pipeline;
    private static final Logger logger = LoggerFactory.getLogger(CoreSentimentalPipeline.class);

    public CoreSentimentalPipeline() {
        initPipeline();
    }

    // init load model StanfordCoreNLP and properties
    private void initPipeline() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        pipeline = new StanfordCoreNLP(props);
    }

    /**
     * Output Sentiment based on the input text
     * @param text : String
     * @return Sentiment
     */
    public Sentiment getPrimarySentiment(String text) {
        Sentiment mainSentiment = Sentiment.STRONG_NEGATIVE;
        if (text != null && text.length() > 0) {
            int longest = 0;
            Annotation annotation = pipeline.process(text);
            for (CoreMap sentence : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
                Tree tree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
                int sentiment = RNNCoreAnnotations.getPredictedClass(tree);
                String partText = sentence.toString();
                if (partText.length() > longest) {
                    mainSentiment = Sentiment.forNumber(sentiment);
                    longest = partText.length();
                }
            }
        }
        logger.info("Sentiment for " + text + " is " + mainSentiment);
        return mainSentiment;
    }
}