package com.bankSystem.BankSystem.web.error;

import com.bankSystem.BankSystem.web.dto.common.BaseResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ErrorResponse extends BaseResponseDto {
    private Error error;

    @Builder
    public ErrorResponse(int status, String message, String code, String detail) {
        super(status, message);
        this.error = Error.builder()
                .code(code)
                .detail(detail)
                .build();
    }

    @Getter
    @NoArgsConstructor
    public static class Error {
        private String code;
        private String detail;

        @Builder
        public Error(String code, String detail) {
            this.code = code;
            this.detail = detail;
        }
    }
}
