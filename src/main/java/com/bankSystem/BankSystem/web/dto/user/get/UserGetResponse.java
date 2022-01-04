package com.bankSystem.BankSystem.web.dto.user.get;

import com.bankSystem.BankSystem.web.dto.common.BaseResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserGetResponse extends BaseResponseDto {
    private UserResult result;

    @Builder
    public UserGetResponse(int status, String message, UserGetResponseDto userGetResponseDto) {
        super(status, message);
        this.result = UserResult.builder()
                .user(userGetResponseDto)
                .build();
    }

    @Getter
    @NoArgsConstructor
    public static class UserResult {
        private UserGetResponseDto user;

        @Builder
        public UserResult(UserGetResponseDto user) {
            this.user = user;
        }
    }
}
