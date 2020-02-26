package com.derzhavets.repository;

import com.derzhavets.controller.Rate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RatesRepository extends MongoRepository<Rate, String> {
    public Rate findRateEntityByProjectId(Integer projectId);
    public List<Rate> findAll();
}
