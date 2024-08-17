package com.sametbakmaz.SanalPosFups.mappers;

import com.sametbakmaz.SanalPosFups.models.dto.BanksDTO;
import com.sametbakmaz.SanalPosFups.models.dto.CommissionsDTO;
import com.sametbakmaz.SanalPosFups.models.entity.BanksEntity;
import com.sametbakmaz.SanalPosFups.models.entity.CommissionsEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CommissionsMapper {

    CommissionsMapper INSTANCE = Mappers.getMapper(CommissionsMapper.class);
    CommissionsEntity toEntity(CommissionsDTO commissionsDTO);
    CommissionsDTO toDto(CommissionsEntity commissionsEntity);
    List<CommissionsEntity> toEntityList(List<CommissionsDTO> commissionsDTOList);
    List<CommissionsDTO> toDtoList(List<CommissionsEntity> commissionsEntityList);
}
