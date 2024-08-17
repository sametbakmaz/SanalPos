package com.sametbakmaz.SanalPosFups.mappers;

import com.sametbakmaz.SanalPosFups.models.dto.TransactionsDTO;
import com.sametbakmaz.SanalPosFups.models.entity.TransactionsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransactionsMapper {
    TransactionsMapper INSTANCE = Mappers.getMapper(TransactionsMapper.class);
    TransactionsEntity toEntity(TransactionsDTO transactionsDTO);
    TransactionsDTO toDto(TransactionsEntity transactionsEntity);
}
