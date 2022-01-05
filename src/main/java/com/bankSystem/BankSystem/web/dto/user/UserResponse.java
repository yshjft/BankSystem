package com.bankSystem.BankSystem.web.dto.user;

import com.bankSystem.BankSystem.web.dto.BaseResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponse<T> extends BaseResponseDto {
    private UserResult result;

    @Builder
    public UserResponse(int status, String message, T userResponseDto) {
        super(status, message);
        this.result = UserResult.builder()
                .user(userResponseDto)
                .build();
    }

    @Getter
    @NoArgsConstructor
    private static class UserResult<T> {
        private T user;

        @Builder
        UserResult(T user) {
            this.user = user;
        }
    }
}
