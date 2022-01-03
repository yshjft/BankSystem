package com.bankSystem.BankSystem.api.error;

import com.bankSystem.BankSystem.api.dto.common.BaseResponseDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ErrorsResponse extends BaseResponseDto {
    private String code;
    private List<Error> errors;

    @Builder
    public ErrorsResponse(int status, String message, String code) {
        super(status, message);
        this.code = code;
        this.errors = new ArrayList<>();
    }

    public void addError(String field, Object value, String detail) {
        this.errors.add(Error.builder()
                .field(field)
                .value(value)
                .detail(detail)
                .build()
        );
    }

    @Getter
    @NoArgsConstructor
    public static class Error {
        private String field;
        private Object value;
        private String detail;

        @Builder
        public Error(String field, Object value, String detail) {
            this.field = field;
            this.value = value;
            this.detail = detail;
        }
    }
}
