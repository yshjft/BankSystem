package com.bankSystem.BankSystem.web.dto.user;

import com.bankSystem.BankSystem.web.dto.common.BaseResponseDto;
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
