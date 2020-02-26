package com.derzhavets.controller;

import com.derzhavets.controller.model.Overtime;
import com.derzhavets.service.BonusServiceFeignClient;
import com.derzhavets.service.OvertimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "overtimes")
public class OvertimeController {

    private OvertimeService overtimeService;

    @Autowired
    private BonusServiceFeignClient bonusServiceFeignClient;

    @Autowired
    public OvertimeController(OvertimeService overtimeService) {
        this.overtimeService = overtimeService;
    }

    @RequestMapping(path = "", method = RequestMethod.POST, consumes = {"application/json"})
    public Overtime createOvertime(@RequestBody Overtime overtime) {
        Overtime overtimeCreated = overtimeService.createOvertime(overtime);

        //calculate and save bonus
        ResponseEntity<String> createBonusResponse =
                bonusServiceFeignClient.sendCalculateAndSaveBonusForOvertime(overtimeCreated);

        if (createBonusResponse.getStatusCode() != HttpStatus.OK) {
            log.debug("Unable to save bonus for overtime.");
        }

        return overtimeCreated;
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Overtime> findAllOvertimes() {
        return overtimeService.findAllOvertimes();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Overtime getOvertimeById(@PathVariable String id) {
        return overtimeService.findOvertimeById(id);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public Overtime updateOvertime(@PathVariable String id, @RequestBody Overtime overtimeToUpdate) {
        return overtimeService.updateOvertime(id, overtimeToUpdate);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public String removeOvertime(@PathVariable String id) {
        return overtimeService.removeOvertimeById(id);
    }
}