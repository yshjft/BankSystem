package com.bankSystem.BankSystem.api.dto.user;

import com.bankSystem.BankSystem.api.dto.common.BaseResponseDto;
import com.bankSystem.BankSystem.api.dto.user.get.UserGetResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponse<T> extends BaseResponseDto {
    private UserResult result;


    // 추상 클래스
    @Builder
    public UserResponse(int status, String message, T userResponseDto) {
        super(status, message);
        this.result = UserResult.builder()
                .user(userResponseDto)
                .build();
    }

    // generics
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
