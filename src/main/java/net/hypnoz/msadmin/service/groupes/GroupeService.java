package net.hypnoz.msadmin.service.groupes;

import net.hypnoz.msadmin.domain.Groupes;
import net.hypnoz.msadmin.dtos.GroupesDto;
import net.hypnoz.msadmin.mappers.GroupesMapper;
import net.hypnoz.msadmin.repository.GroupesRepository;
import net.hypnoz.msadmin.utils.validators.ValidationCommunUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GroupeService implements IGroupeService {
    private final Logger log = LoggerFactory.getLogger(GroupeService.class);

    private final GroupesMapper groupesMapper;
    private final GroupesRepository groupesRepository;

    public GroupeService(GroupesMapper groupesMapper, GroupesRepository groupesRepository) {
        this.groupesMapper = groupesMapper;
        this.groupesRepository = groupesRepository;
    }


    @Override
    public GroupesDto createGroupe(GroupesDto groupesDto) {
        log.debug("Request to create a group: {}", groupesDto);
        ValidationCommunUtils.validate(groupesDto);
        Groupes groupes = groupesMapper.toEntity(groupesDto);
        Groupes addedGroup = groupesRepository.save(groupes);
        log.debug("Group created: {}", addedGroup);
        return groupesMapper.toDto(addedGroup);
    }

    @Override
    public GroupesDto getGroupeById(Long id) {
        Groupes existingGroup = groupesRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Unable to find group with id: {}", id);
                    return new IllegalArgumentException("Group with given id not found");
                });
        return groupesMapper.toDto(existingGroup);
    }

    @Override
    public GroupesDto updateGroupe(GroupesDto groupesDto) {
        log.debug("Request to update group: {}", groupesDto);
        ValidationCommunUtils.validate(groupesDto);
        Groupes existingGroup = groupesRepository.findById(groupesDto.getId())
                .orElseThrow(() -> {
                    log.error("Unable to find group with id: {}", groupesDto.getId());
                    return new IllegalArgumentException("Group with given id not found");
                });
        existingGroup = groupesMapper.partialUpdate(groupesDto, existingGroup);
        existingGroup = groupesRepository.saveAndFlush(existingGroup);
        log.debug("Group updated: {}", groupesDto);
        return groupesMapper.toDto(existingGroup);
    }

    @Override
    public void deleteGroupe(Long id) {
        log.debug("Attempting to delete Structure with info: {}", id);
        Groupes existingStructure = groupesRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Failed to find Structure id: {}", id);
                    return new IllegalArgumentException("Structure with given id not found");
                });
        groupesRepository.delete(existingStructure);
        log.debug("Structure deleted with info: {}", existingStructure);
    }

    @Override
    public List<GroupesDto> getAllGroupeByStructure(Long sid) {
        List<GroupesDto> groupes = groupesRepository.findByStructures_Id(sid).stream()
                .map(groupesMapper::toDto)
                .toList();
        log.debug("Fetched all groups with structure id: {}", sid);
        return groupes;
    }
}