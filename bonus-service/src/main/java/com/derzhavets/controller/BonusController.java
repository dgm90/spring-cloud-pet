package com.derzhavets.controller;

import com.derzhavets.service.BonusService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.support.MessageBuilder;
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

    @Autowired
    private Source source;

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

        // send a message to rabbit mq
        source.output().send(MessageBuilder
                .withPayload(new Message(String.format("A new bonus record is saved with id [%s]", bonus.getId())))
                .build());

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

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Message {
        private String message;
    }
}
