package com.spark.redis.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api")
@RestController
public class SparkRedisController {
    @Autowired
    private WordCountService service;


    @Autowired
    private PersonServiceRedis personServiceRedis;


    @RequestMapping(method = RequestMethod.POST, path = "/createPerson")
    public  void save(@RequestBody List<Person> persons) {

         personServiceRedis.savePerson(persons);
    }

    @RequestMapping("redisPut")
    public ResponseEntity<String> redisPut() {
        service.redisPut();
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    @RequestMapping("getPerson")
    public ResponseEntity<List<Person>>  getPerson(@RequestParam(required = true) List<String> names) {

        return new ResponseEntity<List<Person>>(personServiceRedis.readPerson( names), HttpStatus.OK);
    }




}
