import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;
import java.util.Map;

public class SparkWordCount {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("WordCount").setMaster("local[*]");
        JavaSparkContext sc  =  new JavaSparkContext(conf);

        JavaRDD<String> lines =  sc.parallelize(Arrays.asList("This is a sample text to count words. Here are some more words to test."));
        JavaRDD<String> words = lines.flatMap(s -> Arrays.asList(s.split("\\W+")).iterator());

        Map<String, Long> wordCounts = words.countByValue();

        wordCounts.forEach((word, count) -> System.out.println(word + " : " + count));
        sc.close();
    }
}
