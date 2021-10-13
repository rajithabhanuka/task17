package com.code.task17.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ErrorDto extends ResponseDto{

    private String message;
}
