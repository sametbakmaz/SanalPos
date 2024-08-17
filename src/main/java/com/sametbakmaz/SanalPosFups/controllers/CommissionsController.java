package com.sametbakmaz.SanalPosFups.controllers;

import com.sametbakmaz.SanalPosFups.common.QueryResponse;
import com.sametbakmaz.SanalPosFups.models.dto.CommissionsDTO;
import com.sametbakmaz.SanalPosFups.services.commissions.CommissionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commissions")
public class CommissionsController {

    private final CommissionsService commissionsService;

    @Autowired
    public CommissionsController(CommissionsService commissionsService) {
        this.commissionsService = commissionsService;
    }

    @PostMapping("/save/{fkBankId}")
    public ResponseEntity<QueryResponse<CommissionsDTO>> save(@PathVariable Long fkBankId, @RequestBody CommissionsDTO commissionsDTO) {
        // Path parametresinden gelen fkBankId'yi DTO'ya set ediyoruz
        commissionsDTO.setFkBankId(fkBankId);

        CommissionsDTO savedCommission = commissionsService.save(fkBankId,commissionsDTO);
        QueryResponse<CommissionsDTO> queryResponse = QueryResponse.createResponse(
                savedCommission != null, savedCommission, "Komisyon kaydı başarıyla oluşturuldu");
        return ResponseEntity.status(HttpStatus.CREATED).body(queryResponse);
    }

    @PutMapping("/update/{fkBankId}")
    public ResponseEntity<QueryResponse<CommissionsDTO>> update(@PathVariable Long fkBankId, @RequestBody CommissionsDTO commissionsDTO) {
        CommissionsDTO updatedCommission = commissionsService.update(fkBankId, commissionsDTO);
        QueryResponse<CommissionsDTO> queryResponse = QueryResponse.createResponse(
                updatedCommission != null, updatedCommission, "Komisyon kaydı başarıyla güncellendi");
        return ResponseEntity.status(HttpStatus.OK).body(queryResponse);
    }


    @DeleteMapping("/delete/{fkBankId}")
    public ResponseEntity<QueryResponse<Void>> delete(@PathVariable Long fkBankId) {
        commissionsService.delete(fkBankId);
        QueryResponse<Void> queryResponse = QueryResponse.createResponse(
                true, null, "Komisyon kaydı başarıyla silindi");
        return ResponseEntity.status(HttpStatus.OK).body(queryResponse);
    }

    @GetMapping("/list")
    public ResponseEntity<QueryResponse<List<CommissionsDTO>>> list() {
        List<CommissionsDTO> commissionsList = commissionsService.list();
        QueryResponse<List<CommissionsDTO>> queryResponse = QueryResponse.createResponse(
                !commissionsList.isEmpty(), commissionsList, commissionsList.isEmpty() ? "Kayıt bulunamadı" : "Komisyon kayıtları başarıyla listelendi");
        return ResponseEntity.status(HttpStatus.OK).body(queryResponse);
    }
}
