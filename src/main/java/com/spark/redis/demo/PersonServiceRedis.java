package com.spark.redis.demo;

import com.redislabs.provider.redis.ReadWriteConfig;
import com.redislabs.provider.redis.RedisConfig;
import org.apache.spark.sql.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.apache.spark.sql.functions.col;

@Service
public class PersonServiceRedis {
    @Autowired
    private SparkSession sparkSession;



    public boolean savePerson(List <Person> personList) {

        Dataset<Row> df = sparkSession.createDataFrame(personList, Person.class);
        df.write()
                .format("org.apache.spark.sql.redis")
                .option("table", "person")
                .option("key.column", "id")
                .mode(SaveMode.Overwrite)
                .save();
        return true;
    }

    public List<Person> readPerson(List <String> personList) {
        Dataset<Row> df = sparkSession.read().format("org.apache.spark.sql.redis") .option("table", "person")
                .option("key.column", "id").load();

        List<Row> personsList=df.collectAsList();

        return personsList.parallelStream().map(new Function<Row, Person>() {
            @Override
            public Person apply(Row row) {
                return new Person(row.getLong(1),row.getString(2), row.getInt(0));
            }
        }).collect(Collectors.toList());

    }
}
