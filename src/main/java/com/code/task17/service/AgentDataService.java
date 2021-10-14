package com.code.task17.service;

import com.code.task17.dto.AgentDto;
import com.code.task17.dto.ErrorDto;
import com.code.task17.dto.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class AgentDataService {

    @Value("${agent.id.url}")
    private String uriGetId;

    @Value("${agent.get.url}")
    private String uriGetAgent;

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

        RestTemplate restTemplate = new RestTemplate();

        String status = restTemplate.getForObject(uriGetId, String.class);

        if (status.equals("progress")) {

            for (int retries = 0; ; retries++) {

                if (status.equals("progress")) {
                    if (retries <= 3) {
                        Thread.sleep(2000);
                        log.info("Retry attempt " + retries + " Status still " + status);
                        status = restTemplate.getForObject(uriGetId, String.class);
                        continue;
                    } else {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                .body(ErrorDto.builder().message("Something went wrong").build());
                    }
                }
            }
        } else if (status.equals("success")) {
            log.info("status is success here");
            AgentDto agentDto = restTemplate.getForObject(uriGetAgent, AgentDto.class);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(agentDto);

        } else if (status.equals("error")) {
            log.error("status is error here");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorDto.builder().message("Something went wrong").build());
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorDto.builder().message("Something went wrong").build());

    }

    private int mayFailedCallTimes;

    @Retryable(value = HttpStatusCodeException.class,
            maxAttempts = 5, backoff = @Backoff(delay = 1000))
    public ResponseEntity<ResponseDto> getAgentByRestRetryTest(Integer id){

        mayFailedCallTimes++;

        RestTemplate restTemplate = new RestTemplate();

        String status = restTemplate.getForObject(uriGetId, String.class);

        if (mayFailedCallTimes <= 3) {

            log.info("Retrying " + mayFailedCallTimes + " Times");

            status = restTemplate.getForObject(uriGetId, String.class);

        }

        if (status.equals("progress")) {

        }else if(status.equals("error")) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorDto.builder().message("Something went wrong").build());
        }else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ErrorDto.builder().message("Something went wrong").build());
        }

        throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @Recover
    public String recover(HttpServerErrorException exception) {
        return "Please try after some time!!";
    }
}
