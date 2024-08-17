package com.sametbakmaz.SanalPosFups.controllers;

import com.sametbakmaz.SanalPosFups.common.QueryResponse;
import com.sametbakmaz.SanalPosFups.models.dto.BanksDTO;
import com.sametbakmaz.SanalPosFups.services.banks.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/banks")
public class BanksController {
    private final BankService bankService;

    @Autowired
    public BanksController(BankService bankService) {
        this.bankService = bankService;
    }
    @PostMapping(value = "/save")
    public ResponseEntity<QueryResponse<BanksDTO>> save(@RequestBody BanksDTO banksDTO) {
        BanksDTO createdBanks = bankService.save(banksDTO);
        QueryResponse<BanksDTO> queryResponse = QueryResponse.createResponse(createdBanks != null, createdBanks,"Kayıt Başarılı");
        return ResponseEntity.status(HttpStatus.CREATED).body(queryResponse);
    }
    @PutMapping(value = "/update/{id}")
    public ResponseEntity<QueryResponse<BanksDTO>> update(@PathVariable Long id, @RequestBody BanksDTO banksDTO) {
        BanksDTO updatedBanks = bankService.update(id, banksDTO);
        QueryResponse<BanksDTO> queryResponse = QueryResponse.createResponse(
                updatedBanks != null, updatedBanks, "Güncelleme Başarılı");
        return ResponseEntity.status(HttpStatus.OK).body(queryResponse);
    }
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<QueryResponse<Void>> delete(@PathVariable Long id) {
        bankService.delete(id);
        QueryResponse<Void> queryResponse = QueryResponse.createResponse(
                true, null, "Silme İşlemi Başarılı");
        return ResponseEntity.status(HttpStatus.OK).body(queryResponse);
    }
    @GetMapping(value = "/list")
    public ResponseEntity<QueryResponse<List<BanksDTO>>> list() {
        List<BanksDTO> banksList = bankService.list();
        QueryResponse<List<BanksDTO>> queryResponse = QueryResponse.createResponse(
                !banksList.isEmpty(), banksList, banksList.isEmpty() ? "Kayıt Bulunamadı" : "Liste Başarıyla Getirildi");
        return ResponseEntity.status(HttpStatus.OK).body(queryResponse);
    }
}