package com.codewithyugam.ecom.mappers;

import com.codewithyugam.ecom.dtos.RegisterUserRequest;
import com.codewithyugam.ecom.dtos.UpdateUserRequest;
import com.codewithyugam.ecom.dtos.UserDto;
import com.codewithyugam.ecom.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
//    @Mapping(target="createdAt", expression = "java(java.time.LocalDateTime.now())")
    UserDto toDto(User user);
    User toEntity(RegisterUserRequest request);
    void updateUser(UpdateUserRequest request, @MappingTarget User user);
}
