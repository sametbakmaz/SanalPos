package com.sametbakmaz.SanalPosFups.services.transactions;

import com.sametbakmaz.SanalPosFups.mappers.TransactionsMapper;
import com.sametbakmaz.SanalPosFups.models.dto.TransactionsDTO;
import com.sametbakmaz.SanalPosFups.models.entity.BanksEntity;
import com.sametbakmaz.SanalPosFups.models.entity.CommissionsEntity;
import com.sametbakmaz.SanalPosFups.models.entity.TransactionsEntity;
import com.sametbakmaz.SanalPosFups.repositories.BanksRepository;
import com.sametbakmaz.SanalPosFups.repositories.CommissionsRepository;
import com.sametbakmaz.SanalPosFups.repositories.TransactionsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionsServiceImpl implements TransactionsService {

    private final CommissionsRepository commissionsRepository;
    private final BanksRepository banksRepository;
    private final TransactionsRepository transactionsRepository;

    @Autowired
    public TransactionsServiceImpl(CommissionsRepository commissionsRepository, BanksRepository banksRepository, TransactionsRepository transactionsRepository) {
        this.commissionsRepository = commissionsRepository;
        this.banksRepository = banksRepository;
        this.transactionsRepository = transactionsRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(TransactionsService.class);

    @Override
    public TransactionsDTO transactions(TransactionsDTO transactionsDTO) {
        TransactionsEntity transactionEntity = calculateRateAndSaveTransaction(transactionsDTO.getAmount());
        return toDto(transactionEntity);
    }

    private TransactionsEntity calculateRateAndSaveTransaction(BigDecimal amount) {
        LocalDate today = LocalDate.now();

        Iterable<BanksEntity> banks = banksRepository.findAll();
        BanksEntity selectedBank = null;
        BigDecimal lowestRate = null;

        TransactionsDTO transactionsDTO = new TransactionsDTO();
        transactionsDTO.setAmount(amount);
        transactionsDTO.setTransactionDate(today);

        for (BanksEntity bank : banks) {
            List<CommissionsEntity> commissions = commissionsRepository.findAllByFkBankId(bank.getId());

            for (CommissionsEntity commission : commissions) {
                logger.info("Banka: {}, Komisyon Oranı: {}", bank.getName(), commission.getRate());

                if (!today.isBefore(commission.getStartDate()) && !today.isAfter(commission.getEndDate())) {
                    if (lowestRate == null || commission.getRate().compareTo(lowestRate) < 0) {
                        lowestRate = commission.getRate();
                        selectedBank = bank;
                    }
                }
            }
        }

        if (selectedBank != null && lowestRate != null) {
            BigDecimal commissionAmount = amount.multiply(lowestRate);
            logger.info("Seçilen Banka: {}, Uygulanan Komisyon Oranı: {}, Komisyon Tutarı: {}",
                    selectedBank.getName(), lowestRate, commissionAmount);

            transactionsDTO.setFkBankId(selectedBank.getId());
            transactionsDTO.setCommissionRate(lowestRate);
            transactionsDTO.setCommissionAmount(commissionAmount);

            TransactionsEntity transactionEntity = toEntity(transactionsDTO);
            transactionsRepository.save(transactionEntity);

            logProfitComparison(banks, selectedBank, amount, commissionAmount);

            return transactionEntity;
        } else {
            throw new InvalidParameterException("Geçerli bir komisyon oranı bulunamadı, işlem gerçekleştirilemedi.");
        }
    }

    private void logProfitComparison(Iterable<BanksEntity> banks, BanksEntity selectedBank, BigDecimal amount, BigDecimal commissionAmount) {
        for (BanksEntity bank : banks) {
            if (!bank.equals(selectedBank)) {
                Optional<CommissionsEntity> commissionOpt = commissionsRepository.findByFkBankId(bank.getId());

                if (commissionOpt.isPresent()) {
                    CommissionsEntity commission = commissionOpt.get();

                    if (!LocalDate.now().isBefore(commission.getStartDate()) && !LocalDate.now().isAfter(commission.getEndDate())) {
                        BigDecimal otherCommissionAmount = amount.multiply(commission.getRate());
                        BigDecimal savings = otherCommissionAmount.subtract(commissionAmount);
                        BigDecimal savingsPercentage = savings.divide(commissionAmount, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));

                        logger.info("Banka: {} ile karşılaştırıldığında %{} kar edildi. Diğer Bankanın Komisyon Oranı: {}, Komisyon Tutarı: {}",
                                bank.getName(), savingsPercentage, commission.getRate(), otherCommissionAmount);
                    }
                }
            }
        }
    }

    private TransactionsDTO toDto(TransactionsEntity transactionsEntity) {
        return TransactionsMapper.INSTANCE.toDto(transactionsEntity);
    }

    private TransactionsEntity toEntity(TransactionsDTO transactionsDTO) {
        return TransactionsMapper.INSTANCE.toEntity(transactionsDTO);
    }
}
