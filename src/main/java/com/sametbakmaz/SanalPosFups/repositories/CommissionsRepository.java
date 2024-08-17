package com.sametbakmaz.SanalPosFups.repositories;

import com.sametbakmaz.SanalPosFups.models.entity.CommissionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CommissionsRepository extends JpaRepository<CommissionsEntity, Long> {

    List<CommissionsEntity> findAllByFkBankId(Long fkBankId);

    Optional<CommissionsEntity> findByFkBankId(Long fkBankId);

    boolean existsByFkBankId(Long fkBankId);
}
