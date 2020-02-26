package com.derzhavets.repository;

import com.derzhavets.controller.Bonus;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BonusRepository extends MongoRepository<Bonus, String> {
    public Bonus findBonusEntityByOvertimeId(Integer overtimeId);
    public Bonus insert(Bonus bonus);
}
