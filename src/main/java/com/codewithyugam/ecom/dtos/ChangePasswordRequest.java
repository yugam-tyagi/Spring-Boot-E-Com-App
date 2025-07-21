package com.codewithyugam.ecom.dtos;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    public String oldPassword;
    public String newPassword;
}
