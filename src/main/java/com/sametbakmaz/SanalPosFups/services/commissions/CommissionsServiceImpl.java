package com.sametbakmaz.SanalPosFups.services.commissions;

import com.sametbakmaz.SanalPosFups.mappers.CommissionsMapper;
import com.sametbakmaz.SanalPosFups.models.dto.CommissionsDTO;
import com.sametbakmaz.SanalPosFups.models.entity.CommissionsEntity;
import com.sametbakmaz.SanalPosFups.repositories.BanksRepository;
import com.sametbakmaz.SanalPosFups.repositories.CommissionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CommissionsServiceImpl implements CommissionsService {

    private final CommissionsRepository commissionsRepository;
    private final BanksRepository banksRepository;

    @Autowired
    public CommissionsServiceImpl(CommissionsRepository commissionsRepository, BanksRepository banksRepository) {
        this.commissionsRepository = commissionsRepository;
        this.banksRepository = banksRepository;
    }

    @Override
    public CommissionsDTO save(Long fkBankId, CommissionsDTO commissionsDTO) {
        if (fkBankId == null) {
            throw new InvalidParameterException("Banka ID'si (fkBankId) boş olamaz.");
        }

        boolean bankExists = banksRepository.existsById(fkBankId);

        if (!bankExists) {
            throw new InvalidParameterException("Komisyon eklenmek istenen banka mevcut değil.");
        }

        boolean exists = commissionsRepository.existsByFkBankId(fkBankId);

        if (exists) {
            throw new InvalidParameterException("Aynı bankId ile bir komisyon kaydı zaten mevcut.");
        }

        CommissionsEntity commissionsEntity = toEntity(commissionsDTO);
        commissionsEntity.setFkBankId(fkBankId); 
        commissionsRepository.save(commissionsEntity);

        return toDto(commissionsEntity);
    }

    @Override
    public CommissionsDTO update(Long fkBankId, CommissionsDTO commissionsDTO) {

        CommissionsEntity existingCommission = commissionsRepository.findByFkBankId(fkBankId)
                .orElseThrow(() -> new NoSuchElementException("Komisyon kaydı bulunamadı."));

        if (!fkBankId.equals(existingCommission.getFkBankId())) {
            throw new InvalidParameterException("fkBankId değiştirilemez.");
        }

        existingCommission.setRate(commissionsDTO.getRate());
        existingCommission.setStartDate(commissionsDTO.getStartDate());
        existingCommission.setEndDate(commissionsDTO.getEndDate());

        commissionsRepository.save(existingCommission);
        return toDto(existingCommission);
    }

    @Override
    public void delete(Long fkBankId) {
        CommissionsEntity existingCommission = commissionsRepository.findByFkBankId(fkBankId)
                .orElseThrow(() -> new NoSuchElementException("Komisyon kaydı bulunamadı."));
        commissionsRepository.delete(existingCommission);
    }

    @Override
    public List<CommissionsDTO> list() {
        List<CommissionsEntity> commissionsEntities = commissionsRepository.findAll();
        List<CommissionsDTO> commissionsDTOs = new ArrayList<>();

        for (CommissionsEntity entity : commissionsEntities) {
            commissionsDTOs.add(toDto(entity));
        }

        return commissionsDTOs;
    }

    private CommissionsDTO toDto(CommissionsEntity commissionsEntity) {
        return CommissionsMapper.INSTANCE.toDto(commissionsEntity);
    }

    private CommissionsEntity toEntity(CommissionsDTO commissionsDTO) {
        return CommissionsMapper.INSTANCE.toEntity(commissionsDTO);
    }
}
