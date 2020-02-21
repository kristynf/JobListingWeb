package com.kristyn.kristynlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    JobRepository repository;

    @Override
    public void run(String... strings) throws Exception{
        Job job;
        job = new Job("Network Analyst", "Come run our Cisco network", "2020-02-21", "Mike Wizowski", "202-222-2100", null);
        repository.save(job);

        job = new Job("Web Developer", "Come make our website awesome", "2020-02-21", "Jane E. Doe", "202-222-2101", null);
        repository.save(job);

        job = new Job("Application Developer", "You: make great apps, We = need you!", "2020-02-21", "Jane Smithley", "202-222-2102", null);
        repository.save(job);
    }
}
