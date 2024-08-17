package com.sametbakmaz.SanalPosFups.services.transactions;

import com.sametbakmaz.SanalPosFups.models.dto.TransactionsDTO;
import com.sametbakmaz.SanalPosFups.models.entity.BanksEntity;
import com.sametbakmaz.SanalPosFups.models.entity.TransactionsEntity;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface TransactionsService {
    TransactionsDTO transactions(TransactionsDTO transactionsDTO);
    TransactionsEntity calculateRateAndSaveTransaction(BigDecimal amount);
    void logProfitComparison(Iterable<BanksEntity> banks, BanksEntity selectedBank, BigDecimal amount, BigDecimal commissionAmount);
    List<TransactionsDTO> calculateCommissions(BigDecimal amount);
}
