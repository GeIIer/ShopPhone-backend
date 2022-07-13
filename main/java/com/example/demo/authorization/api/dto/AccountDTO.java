package com.example.demo.authorization.api.dto;

import com.example.demo.authorization.entities.RoleEntity;
import lombok.*;

import java.util.Collection;

@Data
@Builder(builderClassName = "Builder", toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    @NonNull
    private Long id;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private String email;
    @NonNull
    private String phoneNumber;
}
