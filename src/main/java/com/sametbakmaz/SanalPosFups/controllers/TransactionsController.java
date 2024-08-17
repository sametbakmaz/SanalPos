package com.sametbakmaz.SanalPosFups.controllers;

import com.sametbakmaz.SanalPosFups.common.QueryResponse;
import com.sametbakmaz.SanalPosFups.models.dto.TransactionsDTO;
import com.sametbakmaz.SanalPosFups.services.transactions.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionsController {

    private final TransactionsService transactionsService;

    @Autowired
    public TransactionsController(TransactionsService transactionsService) {
        this.transactionsService = transactionsService;
    }

    @PostMapping("/process")
    public ResponseEntity<QueryResponse<TransactionsDTO>> processTransaction(@RequestParam BigDecimal amount) {
        TransactionsDTO transactionsDTO = new TransactionsDTO();
        transactionsDTO.setAmount(amount);

        TransactionsDTO result = transactionsService.transactions(transactionsDTO);

        QueryResponse<TransactionsDTO> queryResponse = QueryResponse.createResponse(
                result != null, result, "İşlem başarıyla gerçekleştirildi"
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(queryResponse);
    }
    @PostMapping("/calculate-commissions")
    public ResponseEntity<QueryResponse<List<TransactionsDTO>>> calculateCommissions(@RequestParam BigDecimal amount) {
        List<TransactionsDTO> result = transactionsService.calculateCommissions(amount);

        QueryResponse<List<TransactionsDTO>> queryResponse = QueryResponse.createResponse(
                !result.isEmpty(), result, result.isEmpty() ? "Komisyon oranı bulunamadı" : "Komisyonlar başarıyla hesaplandı"
        );
        return ResponseEntity.status(HttpStatus.OK).body(queryResponse);
    }
}
