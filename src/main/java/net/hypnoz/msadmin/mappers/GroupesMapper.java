package net.hypnoz.msadmin.mappers;

import net.hypnoz.msadmin.domain.Groupes;
import net.hypnoz.msadmin.dtos.GroupesDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {StructuresMapper.class})
public interface GroupesMapper {
    Groupes toEntity(GroupesDto groupesDto);

    GroupesDto toDto(Groupes groupes);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Groupes partialUpdate(GroupesDto groupesDto, @MappingTarget Groupes groupes);

}