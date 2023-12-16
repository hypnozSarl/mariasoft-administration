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
    private static final String LOG_CREATE_GROUP = "Request to create a group: {}";
    private static final String LOG_GROUP_CREATED = "Group created: {}";
    private static final String LOG_FIND_GROUP = "Unable to find group with id: {}";
    private static final String LOG_UPDATE_GROUP = "Request to update group: {}";
    private static final String LOG_GROUP_UPDATED = "Group updated: {}";
    private static final String LOG_DELETE_GROUP = "Attempting to delete Group with info: {}";
    private static final String LOG_FAILED_FIND_GROUP = "Failed to find Group id: {}";
    private static final String LOG_GROUP_DELETED = "Group deleted with info: {}";
    private static final String LOG_FETCHED_GROUPS = "Fetched all groups with group id: {}";

    public GroupeService(GroupesMapper groupesMapper, GroupesRepository groupesRepository) {
        this.groupesMapper = groupesMapper;
        this.groupesRepository = groupesRepository;
    }

    @Override
    public GroupesDto createGroupe(GroupesDto groupesDto) {
        log.debug(LOG_CREATE_GROUP, groupesDto);
        ValidationCommunUtils.validate(groupesDto);
        Groupes groupes = groupesMapper.toEntity(groupesDto);
        Groupes addedGroup = groupesRepository.save(groupes);
        log.debug(LOG_GROUP_CREATED, addedGroup);
        return groupesMapper.toDto(addedGroup);
    }

    @Override
    public GroupesDto getGroupeById(Long id) {
        Groupes existingGroup = groupesRepository.findById(id).orElseThrow(() -> {
            log.error(LOG_FIND_GROUP, id);
            return new IllegalArgumentException(LOG_FIND_GROUP);
        });
        return groupesMapper.toDto(existingGroup);
    }

    @Override
    public GroupesDto updateGroupe(GroupesDto groupesDto) {
        log.debug(LOG_UPDATE_GROUP, groupesDto);
        ValidationCommunUtils.validate(groupesDto);
        Groupes existingGroup = groupesRepository.findById(groupesDto.getId()).orElseThrow(() -> {
            log.error(LOG_FIND_GROUP, groupesDto.getId());
            return new IllegalArgumentException(LOG_FIND_GROUP);
        });
        existingGroup = groupesMapper.partialUpdate(groupesDto, existingGroup);
        existingGroup = groupesRepository.saveAndFlush(existingGroup);
        log.debug(LOG_GROUP_UPDATED, groupesDto);
        return groupesMapper.toDto(existingGroup);
    }

    @Override
    public void deleteGroupe(Long id) {
        log.debug(LOG_DELETE_GROUP, id);
        Groupes existingGroup = groupesRepository.findById(id).orElseThrow(() -> {
            log.error(LOG_FAILED_FIND_GROUP, id);
            return new IllegalArgumentException(LOG_FIND_GROUP);
        });
        groupesRepository.delete(existingGroup);
        log.debug(LOG_GROUP_DELETED, existingGroup);
    }

    @Override
    public List<GroupesDto> getAllGroupeByStructure(Long sid) {
        List<GroupesDto> groupes = groupesRepository.findByStructures_Id(sid).stream()
                .map(groupesMapper::toDto)
                .toList();
        log.debug(LOG_FETCHED_GROUPS, sid);
        return groupes;
    }
}