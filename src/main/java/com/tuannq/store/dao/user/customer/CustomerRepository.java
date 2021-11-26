package com.tuannq.store.dao.user.customer;

import com.tuannq.store.dao.user.CommonUsersRepository;
import com.tuannq.store.entity.user.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
