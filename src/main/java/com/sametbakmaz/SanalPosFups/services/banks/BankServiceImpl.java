package com.sametbakmaz.SanalPosFups.services.banks;

import com.sametbakmaz.SanalPosFups.mappers.BanksMapper;
import com.sametbakmaz.SanalPosFups.models.dto.BanksDTO;
import com.sametbakmaz.SanalPosFups.models.entity.BanksEntity;
import com.sametbakmaz.SanalPosFups.repositories.BanksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BankServiceImpl implements BankService{

    private final BanksRepository banksRepository;

    @Autowired
    public BankServiceImpl(BanksRepository banksRepository) {
        this.banksRepository = banksRepository;
    }


    public BanksDTO save(BanksDTO banksDTO) {
        BanksEntity banksEntity = (toEntity(banksDTO));
        banksRepository.save(banksEntity);
        return banksDTO;
    }

    public BanksDTO update(Long id, BanksDTO banksDTO) {
        Optional<BanksEntity> existingBankOpt = banksRepository.findById(id);
        if (existingBankOpt.isPresent()) {
            BanksEntity existingBank = existingBankOpt.get();
            existingBank.setName(banksDTO.getName());
            banksRepository.save(existingBank);
            return toDto(existingBank);
        } else {
            throw new NoSuchElementException("Bank with id " + id + " not found");
        }
    }
    public void delete(Long id) {
        if (banksRepository.existsById(id)) {
            banksRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Bank with id " + id + " not found");
        }
    }
    public List<BanksDTO> list() {
        List<BanksEntity> banksEntities = banksRepository.findAll();
        List<BanksDTO> banksDTOs = new ArrayList<>();

        for (BanksEntity entity : banksEntities) {
            banksDTOs.add(toDto(entity));
        }

        return banksDTOs;
    }

    private BanksDTO toDto (BanksEntity banksEntity) {
        return BanksMapper.INSTANCE.toDto(banksEntity);
    }
    private BanksEntity toEntity ( BanksDTO banksDTO) {
        return BanksMapper.INSTANCE.toEntity(banksDTO);
    }
}
