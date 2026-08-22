package com.Springecom.EcomProject.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Springecom.EcomProject.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long>{

}