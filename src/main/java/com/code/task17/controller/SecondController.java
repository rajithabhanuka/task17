package com.code.task17.controller;

import com.code.task17.dto.AgentDto;
import com.code.task17.dto.ResponseDto;
import com.code.task17.service.AgentDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "retry")
public class SecondController {

    private final AgentDataService agentDataService;

    public SecondController(
            AgentDataService agentDataService) {
        this.agentDataService = agentDataService;
    }

    @GetMapping(value = "/agent/{id}")
    public ResponseEntity<ResponseDto> getAgentRetry(@PathVariable(value = "id") Integer id)
            throws InterruptedException {

        return agentDataService.getAgentByRestRetry(id);

    }
}
