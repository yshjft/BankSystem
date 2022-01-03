package com.bankSystem.BankSystem.api.dto.user.join;

import com.bankSystem.BankSystem.api.dto.common.BaseResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserJoinResponse extends BaseResponseDto {
    private UserResult result;

    @Builder
    public UserJoinResponse(int status, String message, UserJoinResponseDto userJoinResponseDto) {
        super(status, message);
        this.result = UserResult.builder()
                .user(userJoinResponseDto)
                .build();
    }

    @Getter
    @NoArgsConstructor
    public static class UserResult {
        private UserJoinResponseDto user;

        @Builder
        public UserResult(UserJoinResponseDto user) {
            this.user = user;
        }
    }
}
