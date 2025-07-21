package com.codewithyugam.ecom.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDto {
    @JsonProperty("user_id")
    private Long id;
    private String name;
    private String email;
//    @JsonFormat(pattern = "dd-mm-yyyy hh:mm:ss")
//    private LocalDateTime createdAt;
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    private String phoneNumber;
}
