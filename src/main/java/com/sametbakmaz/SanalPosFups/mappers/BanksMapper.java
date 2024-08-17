package com.sametbakmaz.SanalPosFups.mappers;

import com.sametbakmaz.SanalPosFups.models.dto.BanksDTO;
import com.sametbakmaz.SanalPosFups.models.entity.BanksEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface BanksMapper {
    BanksMapper INSTANCE = Mappers.getMapper(BanksMapper.class);
    BanksEntity toEntity(BanksDTO banksDTO);
    BanksDTO toDto(BanksEntity banksEntity);
    List<BanksEntity> toEntityList(List<BanksDTO> banksDTOList);
    List<BanksDTO> toDtoList(List<BanksEntity> banksEntityList);
}
