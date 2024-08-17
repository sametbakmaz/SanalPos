package com.sametbakmaz.SanalPosFups.repositories;

import com.sametbakmaz.SanalPosFups.models.entity.TransactionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepository extends JpaRepository<TransactionsEntity, Long> {
}
