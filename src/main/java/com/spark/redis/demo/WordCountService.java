package com.spark.redis.demo;


import com.redislabs.provider.redis.ReadWriteConfig;
import com.redislabs.provider.redis.RedisConfig;
import com.redislabs.provider.redis.RedisContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class WordCountService {
    @Autowired
    private JavaSparkContext jsc;

    @Autowired
    private RedisConfig redisConfig;

    @Autowired
    private ReadWriteConfig readWriteConfig;

    @Autowired
    RedisContext redisContext;

    public Map<String, Long> getCount(List<String> wordList) {
            JavaRDD<String> words = jsc.parallelize(wordList);
            Map<String, Long> wordCounts = words.countByValue();
            return wordCounts;
    }

    public void redisPut()
    {
        JavaRDD<Tuple2<String, String>> rdd = jsc.parallelize(Arrays.asList(Tuple2.apply("myKey", "Hello")));
        int ttl = 100000000;
        redisContext.toRedisKV(rdd.rdd(), ttl, redisConfig, readWriteConfig);
    }


}
