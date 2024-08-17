package com.sametbakmaz.SanalPosFups;

import com.sametbakmaz.SanalPosFups.models.dto.TransactionsDTO;
import com.sametbakmaz.SanalPosFups.models.entity.BanksEntity;
import com.sametbakmaz.SanalPosFups.models.entity.CommissionsEntity;
import com.sametbakmaz.SanalPosFups.models.entity.TransactionsEntity;
import com.sametbakmaz.SanalPosFups.repositories.BanksRepository;
import com.sametbakmaz.SanalPosFups.repositories.CommissionsRepository;
import com.sametbakmaz.SanalPosFups.repositories.TransactionsRepository;
import com.sametbakmaz.SanalPosFups.services.transactions.TransactionsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransactionsServiceImplTest {

    @Mock
    private CommissionsRepository commissionsRepository;

    @Mock
    private BanksRepository banksRepository;

    @Mock
    private TransactionsRepository transactionsRepository;

    @InjectMocks
    private TransactionsServiceImpl transactionsService;

    private BanksEntity bank;
    private CommissionsEntity commission;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        bank = new BanksEntity();
        bank.setId(1L);
        bank.setName("Test Bank");

        commission = new CommissionsEntity();
        commission.setFkBankId(bank.getId());
        commission.setRate(new BigDecimal("0.02"));
        commission.setStartDate(LocalDate.now().minusDays(1));
        commission.setEndDate(LocalDate.now().plusDays(1));
    }

    @Test
    void testTransaction_Success() {
        when(banksRepository.findAll()).thenReturn(List.of(bank));
        when(commissionsRepository.findAllByFkBankId(bank.getId())).thenReturn(List.of(commission));
        when(transactionsRepository.save(any(TransactionsEntity.class))).thenReturn(new TransactionsEntity());

        TransactionsDTO dto = new TransactionsDTO();
        dto.setAmount(new BigDecimal("1000"));

        TransactionsDTO result = transactionsService.transactions(dto);

        assertNotNull(result);
        assertEquals(new BigDecimal("1000"), result.getAmount());
        assertEquals(bank.getId(), result.getFkBankId());
        assertEquals(new BigDecimal("0.02"), result.getCommissionRate());
    }

    @Test
    void testTransaction_NoValidCommission() {
        when(banksRepository.findAll()).thenReturn(List.of(bank));
        when(commissionsRepository.findAllByFkBankId(bank.getId())).thenReturn(List.of());

        TransactionsDTO dto = new TransactionsDTO();
        dto.setAmount(new BigDecimal("1000"));

        Exception exception = assertThrows(RuntimeException.class, () -> transactionsService.transactions(dto));
        assertEquals("Geçerli bir komisyon oranı bulunamadı, işlem gerçekleştirilemedi.", exception.getMessage());
    }

    @Test
    void testTransaction_MultipleBanks() {
        BanksEntity anotherBank = new BanksEntity();
        anotherBank.setId(2L);
        anotherBank.setName("Another Bank");

        CommissionsEntity anotherCommission = new CommissionsEntity();
        anotherCommission.setFkBankId(anotherBank.getId());
        anotherCommission.setRate(new BigDecimal("0.01"));
        anotherCommission.setStartDate(LocalDate.now().minusDays(1));
        anotherCommission.setEndDate(LocalDate.now().plusDays(1));

        when(banksRepository.findAll()).thenReturn(List.of(bank, anotherBank));
        when(commissionsRepository.findAllByFkBankId(bank.getId())).thenReturn(List.of(commission));
        when(commissionsRepository.findAllByFkBankId(anotherBank.getId())).thenReturn(List.of(anotherCommission));
        when(transactionsRepository.save(any(TransactionsEntity.class))).thenReturn(new TransactionsEntity());

        TransactionsDTO dto = new TransactionsDTO();
        dto.setAmount(new BigDecimal("1000"));

        TransactionsDTO result = transactionsService.transactions(dto);

        assertNotNull(result);
        assertEquals(anotherBank.getId(), result.getFkBankId());
        assertEquals(new BigDecimal("0.01"), result.getCommissionRate());
    }
}
