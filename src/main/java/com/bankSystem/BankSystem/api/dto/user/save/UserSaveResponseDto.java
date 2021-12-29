package com.bankSystem.BankSystem.api.dto.user.save;

import com.bankSystem.BankSystem.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserSaveResponseDto {
    private Long id;
    private String name;
    private String email;

    public UserSaveResponseDto(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
    }
}
