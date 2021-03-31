package com.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.pojos.PaySlip;

public interface PaySlipRepository extends JpaRepository<PaySlip, Integer> {

}
