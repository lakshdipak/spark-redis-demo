package com.spark.redis.demo;

import com.redislabs.provider.redis.ReadWriteConfig;
import com.redislabs.provider.redis.RedisConfig;
import com.redislabs.provider.redis.RedisContext;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;


@Configuration
@PropertySource("classpath:application.properties")
public class SparkConfigRedis {

    @Autowired
    private Environment env;

    @Value("${app.name:spark-redis-test}")
    private String appName;

    @Value("${spark.home}")
    private String sparkHome;

    @Value("${master.uri:local}")
    private String masterUri;

    @Value("${spark.redis.host:localhost}")
    private String redisHost;

    @Value("${spark.redis.port:6379}")
    private String redisPort;

    @Bean
    public SparkConf sparkConf() {
        SparkConf sparkConf = new SparkConf()
                .setAppName(appName)
                .setSparkHome(sparkHome)
                .setMaster(masterUri)
                .set("spark.redis.host", redisHost)
                .set("spark.redis.port", redisPort);

        return sparkConf;
    }

    @Bean
    public RedisConfig redisConfig()
    {
        return RedisConfig.fromSparkConf(sparkConf());
    }

    @Bean
    public JavaSparkContext javaSparkContext() {
        return new JavaSparkContext(sparkConf());
    }

    @Bean
    public ReadWriteConfig readWriteConfig() {
        return  ReadWriteConfig.fromSparkConf(sparkConf());
    }

    @Bean
    public RedisContext redisContext(){
        return new RedisContext(javaSparkContext().sc());
    }

    @Bean
    public SparkSession sparkSession() {

        return SparkSession
                .builder()
                .sparkContext(javaSparkContext().sc())
                .appName("Java Spark Redis basic example")
                .getOrCreate();

    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

}