package com.code.task17.service;

import com.code.task17.dto.AgentDto;
import com.code.task17.dto.ErrorDto;
import com.code.task17.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class AgentDataService {

    public ResponseEntity<String> getId(Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body("progress");
    }

    public ResponseEntity<AgentDto> getAgent(Integer id) {

        AgentDto agentDto = new AgentDto();
        agentDto.setId(1);
        agentDto.setName("Rajitha BHanuka");
        agentDto.setAge(29);

        return ResponseEntity
                .status(HttpStatus.OK).body(agentDto);
    }

    public ResponseEntity<ResponseDto> getAgentByRestRetry(Integer id) throws InterruptedException {

            final String urigetId = "http://localhost:8080/api/1";
            final String urigetAgent = "http://localhost:8080/api/agent/1";

            RestTemplate restTemplate = new RestTemplate();

            String status = restTemplate.getForObject(urigetId, String.class);

            if (status.equals("progress")) {

                for (int retries = 0; ; retries++) {

                    if (status.equals("progress")) {
                        if (retries <= 3) {
                            Thread.sleep(2000);
                            log.info("Retry attempt " + retries + " Status still "+ status);
                            status = restTemplate.getForObject(urigetId, String.class);
                            continue;
                        }else{
                            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                    .body(ErrorDto.builder().message("Something went wrong").build());
                        }
                    }
                }
            } else if (status.equals("success")) {
                log.info("status is success here");
                AgentDto agentDto = restTemplate.getForObject(urigetAgent, AgentDto.class);
                return ResponseEntity.status(HttpStatus.OK)
                        .body(agentDto);

            } else if (status.equals("error")){
                log.error("status is error here");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ErrorDto.builder().message("Something went wrong").build());
            }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorDto.builder().message("Something went wrong").build());

}

}
