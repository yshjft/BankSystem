package com.bankSystem.BankSystem.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class PageAttributeDto {
    @NotNull
    @Min(0)
    private int page;

    @NotNull
    @Min(5)
    private int perPage;

    @Builder
    public PageAttributeDto(int page, int perPage) {
        this.page = page;
        this.perPage = perPage;
    }
}
