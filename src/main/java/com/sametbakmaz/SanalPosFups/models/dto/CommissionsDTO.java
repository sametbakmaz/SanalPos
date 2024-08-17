package com.sametbakmaz.SanalPosFups.models.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CommissionsDTO {
    @JsonIgnore
    private Long id;
    private BigDecimal rate;
    private LocalDate startDate;
    private LocalDate endDate;
    private Long fkBankId;

    public Long getFkBankId() {
        return fkBankId;
    }

    public void setFkBankId(Long fkBankId) {
        this.fkBankId = fkBankId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
