package com.derzhavets.service;

import com.derzhavets.controller.Bonus;
import com.derzhavets.controller.Overtime;
import com.derzhavets.controller.Rate;
import com.derzhavets.repository.BonusRepository;
import com.derzhavets.repository.RatesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BonusService {
    @Autowired
    private RatesRepository ratesRepository;

    @Autowired
    private BonusRepository bonusRepository;

    public Rate findRateEntityByProjectId(Integer projectId) {
        return ratesRepository.findRateEntityByProjectId(projectId);
    }

    public Bonus calculateAndSaveBonus(Rate rate, Overtime overtime) {
        Double bonus = rate.getRate() * overtime.getHours();
        Bonus bonusEntity = new Bonus(bonus, overtime.getId());

        bonusEntity = bonusRepository.insert(bonusEntity);
        return bonusEntity;
    }

    public Bonus findBonusByOvertimeId(String overtimeId) {
        return bonusRepository.findBonusEntityByOvertimeId(Integer.valueOf(overtimeId));
    }
}
