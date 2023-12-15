package net.hypnoz.msadmin.mappers;

import net.mariasoft.administrations.domain.Structures;
import net.mariasoft.administrations.dtos.StructuresDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface StructuresMapper {

    Structures toEntity(StructuresDto structuresDto);

    StructuresDto toDto(Structures structures);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Structures partialUpdate(StructuresDto structuresDto, @MappingTarget Structures structures);
}