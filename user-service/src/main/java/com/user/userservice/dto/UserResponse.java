package com.user.userservice.dto;
import java.util.UUID;
import lombok.*;

@Data
@AllArgsConstructor
public class UserResponse {
    private UUID userId;
    private String username;
}
