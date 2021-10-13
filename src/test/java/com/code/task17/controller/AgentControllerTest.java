package com.code.task17.controller;


import com.code.task17.dto.AgentDto;
import com.code.task17.dto.ErrorDto;
import com.code.task17.service.AgentDataService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

@WebMvcTest(SecondController.class)
public class AgentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AgentDataService service;

    @Mock
    private RestTemplate restTemplate;

    @Test
    public void testSecondControllerProgress() throws Exception {

        AgentDto agentDto = new AgentDto();
        agentDto.setId(10);
        agentDto.setName("Bhanuka");
        agentDto.setAge(29);

        Mockito.when(restTemplate.getForObject(Mockito.any(), Mockito.any())).thenReturn("progress");
        Mockito.when(restTemplate.getForObject(Mockito.any(), Mockito.any())).thenReturn(agentDto);
        Mockito.when(service.getAgentByRestRetry(Mockito.anyInt()))
                .thenReturn(ResponseEntity.ok(ErrorDto.builder().message("Something went wrong").build()));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/retry/agent/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("Something went wrong")));
    }

    @Test
    public void testSecondControllerNoError() throws Exception {

        AgentDto agentDto = new AgentDto();
        agentDto.setId(10);
        agentDto.setName("Bhanuka");
        agentDto.setAge(29);

        Mockito.when(restTemplate.getForObject(Mockito.any(), Mockito.any())).thenReturn("progress");
        Mockito.when(restTemplate.getForObject(Mockito.any(), Mockito.any())).thenReturn(agentDto);
        Mockito.when(service.getAgentByRestRetry(Mockito.anyInt()))
                .thenReturn(ResponseEntity.ok(agentDto));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/retry/agent/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string(Matchers.containsString("Bhanuka")));
    }

}
