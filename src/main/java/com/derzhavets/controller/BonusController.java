package com.derzhavets.controller;

import com.derzhavets.service.BonusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "bonus")
public class BonusController {

    @Autowired
    private BonusService bonusService;

    @RequestMapping(path = "/calculateBonusForOvertime", method = RequestMethod.POST, consumes = {"application/json"})
    public ResponseEntity<String> calculateBonusForOvertime(@RequestBody Overtime overtime) {
        Rate rate = bonusService.findRateEntityByProjectId(overtime.getProjectId());

        if (rate == null) {
            String message = String.format("Unable to calculate the bonus. There is no rate for projectId: [%s]",
                    overtime.getProjectId());
            log.debug(message);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }

        Bonus bonus = bonusService.calculateAndSaveBonus(rate, overtime);
        return new ResponseEntity<>(bonus.getBonus().toString(), HttpStatus.OK);
    }

    @RequestMapping(path = "/{overtimeId}", method = RequestMethod.GET)
    public ResponseEntity<String> getBonusByOvertimeId(@PathVariable String overtimeId) {
        Bonus bonus = bonusService.findBonusByOvertimeId(overtimeId);

        if (bonus == null) {
            return new ResponseEntity<>(String.format("There is no rate for overtimeId: []", overtimeId),
                    HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bonus.getBonus().toString(), HttpStatus.OK);
    }
}
