package com.Springecom.EcomProject.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Springecom.EcomProject.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}