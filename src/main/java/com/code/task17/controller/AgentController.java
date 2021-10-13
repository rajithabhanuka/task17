package com.code.task17.controller;


import com.code.task17.dto.AgentDto;
import com.code.task17.service.AgentDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api")
public class AgentController {

    private final AgentDataService dataService;

    public AgentController(AgentDataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<String> getId(@PathVariable(value = "id") Integer id){
        return dataService.getId(1);
    }

    @GetMapping(value = "/agent/{id}")
    public ResponseEntity<AgentDto> getAgent(@PathVariable(value = "id") Integer id) throws InterruptedException {
        return dataService.getAgent(1);
    }
}
