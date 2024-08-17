package com.sametbakmaz.SanalPosFups.services.banks;

import com.sametbakmaz.SanalPosFups.models.dto.BanksDTO;

import java.util.List;

public interface BankService {
    BanksDTO save(BanksDTO banksDTO);
    BanksDTO update(Long id, BanksDTO banksDTO);
    void delete(Long id);
    List<BanksDTO> list();
}
