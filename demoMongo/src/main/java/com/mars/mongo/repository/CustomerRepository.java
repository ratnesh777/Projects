package com.mars.mongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.mars.mongo.repository.entity.Customer;


@Repository
public interface CustomerRepository extends MongoRepository<Customer, String>//,QueryDslPredicateExecutor<Customer>
{

    Customer findByName(String name);

}
