package com.cavcav.redis_cache.dto;

import com.cavcav.redis_cache.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDto {
    private Long id;
    private String password;

    public User toEntity(UpdateUserDto updateUserDto) {
        return User.builder()
                .id(updateUserDto.getId())
                .password(updateUserDto.getPassword())
                .build();
    }
}
