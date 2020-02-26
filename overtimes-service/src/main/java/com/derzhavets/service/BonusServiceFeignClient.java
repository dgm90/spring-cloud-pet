package com.derzhavets.service;

import com.derzhavets.controller.model.Overtime;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "bonus-service", decode404 = true)
public interface BonusServiceFeignClient {

    @RequestMapping(method = {RequestMethod.POST}, value = {"/bonus/calculateBonusForOvertime"})
    public ResponseEntity<String> sendCalculateAndSaveBonusForOvertime(Overtime overtime);
}
