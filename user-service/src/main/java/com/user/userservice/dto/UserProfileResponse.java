package com.user.userservice.dto;
import lombok.*;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserProfileResponse {
    private UUID userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
}
