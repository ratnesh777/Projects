package com.mars.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.mars.mongo.repository.entity.Customer;

public interface CustomerService {

	Customer createCustomer(Customer customer);

	Customer findCustomerById(String id);

	Page<Customer> findCustomerList(Integer page, Integer size, String sortParam, String sortDirection,
			String searchKey);

	Customer updateCustomer(Customer company);

	void deleteAll();

	void delete(List<String> asList);

}
