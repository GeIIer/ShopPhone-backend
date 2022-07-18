package com.example.demo.authorization.api.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    @NonNull
    private List<String> roles;
}
