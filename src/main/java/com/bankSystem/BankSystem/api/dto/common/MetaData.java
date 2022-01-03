package com.bankSystem.BankSystem.api.dto.common;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MetaData {
    private int offset;
    private int page_count;
    private int total_count;

    public MetaData(int offset, int page_count, int total_count) {
        this.offset = offset;
        this.page_count = page_count;
        this.total_count = total_count;
    }
}
