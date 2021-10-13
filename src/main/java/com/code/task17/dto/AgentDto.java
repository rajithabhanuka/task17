package com.code.task17.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgentDto extends ResponseDto{

    private int id;

    private String name;

    private int age;
}
