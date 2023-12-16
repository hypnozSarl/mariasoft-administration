package net.hypnoz.msadmin.service.groupes;

import net.hypnoz.msadmin.dtos.GroupesDto;

import java.util.List;

public interface IGroupeService {
    GroupesDto createGroupe(GroupesDto groupesDto);

    GroupesDto getGroupeById(Long id);

    GroupesDto updateGroupe(GroupesDto groupesDto);

    void deleteGroupe(Long id);

    List<GroupesDto> getAllGroupeByStructure(Long sid);
}
