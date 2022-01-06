package com.bankSystem.BankSystem.web.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MetaData {
    private int page;
    private int page_count;
    private int per_page;
    private long total_count;

    @Builder
    public MetaData(int page, int page_count, int per_page, long total_count) {
        this.page = page;
        this.page_count = page_count;
        this.per_page = per_page;
        this.total_count = total_count;
    }
}
