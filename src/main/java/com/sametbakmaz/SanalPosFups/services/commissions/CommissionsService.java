package com.sametbakmaz.SanalPosFups.services.commissions;

import com.sametbakmaz.SanalPosFups.models.dto.CommissionsDTO;

import java.util.List;

public interface CommissionsService {
    CommissionsDTO save(Long fkBankId, CommissionsDTO commissionsDTO);
    CommissionsDTO update(Long id, CommissionsDTO commissionsDTO);
    void delete(Long id);
    List<CommissionsDTO> list();
}
