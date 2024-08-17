package com.sametbakmaz.SanalPosFups.services.Transactions;

import com.sametbakmaz.SanalPosFups.models.dto.TransactionsDTO;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsService {
    TransactionsDTO transactions(TransactionsDTO transactionsDTO);
}
