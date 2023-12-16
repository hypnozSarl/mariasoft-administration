package net.hypnoz.msadmin.service.structres;


import jakarta.validation.*;
import net.hypnoz.msadmin.domain.Structures;
import net.hypnoz.msadmin.dtos.StructuresDto;
import net.hypnoz.msadmin.mappers.StructuresMapper;
import net.hypnoz.msadmin.repository.StructuresRepository;
import net.hypnoz.msadmin.utils.OsUtils;
import net.hypnoz.msadmin.utils.validators.ValidationCommunUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
public class StructureService implements IStructureService {
    private final Logger log = LoggerFactory.getLogger(StructureService.class);

    private final StructuresRepository structuresRepository;
    private final StructuresMapper structuresMapper;

    public StructureService(StructuresRepository structuresRepository, StructuresMapper structuresMapper) {
        this.structuresRepository = structuresRepository;
        this.structuresMapper = structuresMapper;;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public StructuresDto creationStructure(StructuresDto structuresDto) {
        ValidationCommunUtils.validate(structuresDto);
        log.debug("Attempting to create Structure with info: {}", structuresDto);
        var structures = structuresMapper.toEntity(structuresDto);
        structures = structuresRepository.saveAndFlush(structures);
        log.debug("Structure created successfully with info: {}", structuresDto);
        return structuresMapper.toDto(structures);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public StructuresDto updateStructure(StructuresDto structuresDto) {
        log.debug("Attempting to update Structure with info: {}", structuresDto);
        ValidationCommunUtils.validate(structuresDto);
        Structures existingStructure = structuresRepository.findById(structuresDto.getId())
                .orElseThrow(() -> {
                    log.error("Failed to find Structure id: {}", structuresDto.getId());
                    throw new IllegalArgumentException("Structure with given id not found");
                });
        existingStructure = structuresMapper.partialUpdate(structuresDto, existingStructure);
        existingStructure = structuresRepository.saveAndFlush(existingStructure);
        log.debug("Structure updated successfully with info: {}", structuresDto);
        return structuresMapper.toDto(existingStructure);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void deleteStructure(Long sid) {
        log.debug("Attempting to delete Structure with info: {}", sid);
        Structures existingStructure = structuresRepository.findById(sid)
                .orElseThrow(() -> {
                    log.error("Failed to find Structure id: {}", sid);
                    throw new IllegalArgumentException("Structure with given id not found");
                });
        structuresRepository.delete(existingStructure);
        log.debug("Structure deleted with info: {}", existingStructure);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class, readOnly = true)
    public StructuresDto getStructure(Long sid) {
        log.debug("Attempting to get Structure by id: {}", sid);
        Structures structures = structuresRepository.findById(sid)
                .orElseThrow(() -> {
                    log.error("Failed to find Structure id: {}", sid);
                    return new IllegalArgumentException("Structure with given id not found");
                });
        log.debug("Structure found by id: {}", sid);
        return structuresMapper.toDto(structures);
    }

    /**
     * @param structuresDto
     * @param file          The logo file.
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void uploadStructureLogo(StructuresDto structuresDto, MultipartFile file) {
        log.debug("Attempting to upload a logo for structure ID: {}", structuresDto.getId());

        Structures existingStructure = validateStructureExists(structuresDto.getId());

        Path logoPath = prepareLogoPath(file,structuresDto.getId());

        try {
            deleteExistingLogo(logoPath);

            saveLogo(logoPath, file);

            log.debug("Logo uploaded successfully for structure ID: {}", structuresDto.getId());
        } catch (IOException e) {
            log.error("Error while uploading logo for structure ID: {}", structuresDto.getId(), e);
            throw new RuntimeException("Could not upload logo for structure ID: " + structuresDto.getId(), e);
        }
    }

    private Structures validateStructureExists(Long id) {
        return structuresRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Failed to find Structure id: {}", id);
                    return new IllegalArgumentException("Structure with given id not found");
                });
    }

    private Path prepareLogoPath(MultipartFile file, Long id) {
        Path dir = Paths.get(OsUtils.getOsHomeDir()+ "/structure" + id + "/image/logos/");
        if (!Files.exists(dir)) {
            createLogoDirectory(dir);
        }
        return dir.resolve(StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename())));
    }

    private void createLogoDirectory(Path dir) {
        try {
            Files.createDirectories(dir);
            log.debug("Directory 'structures-logo' didn't exist and was created");
        } catch (IOException e) {
            log.error("Error while creating 'structures-logo' directory", e);
            throw new RuntimeException("Could not create directory for the logos", e);
        }
    }

    public void deleteExistingLogo(Path logoPath) throws IOException {
        if (Files.exists(logoPath)) {
            Files.delete(logoPath);
            log.debug("Previous logo has been deleted");
        }
    }

    private void saveLogo(Path logoPath, MultipartFile file) throws IOException {
        Files.copy(file.getInputStream(), logoPath);
    }


}