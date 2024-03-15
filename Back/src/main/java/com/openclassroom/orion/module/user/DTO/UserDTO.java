package com.openclassroom.orion.module.user.DTO;

import com.openclassroom.orion.module.subscription.dto.SubscriptionDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private Long id;
    private String username;
    private String email;
    private String password;
    private List<SubscriptionDTO> subscriptions;
}
