package net.hypnoz.msadmin.web.rest;

import net.hypnoz.msadmin.dtos.GroupesDto;
import net.hypnoz.msadmin.service.groupes.GroupeService;
import net.hypnoz.msadmin.web.rest.errors.BadRequestAlertException;
import net.hypnoz.msadmin.web.rest.errors.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Content;

import java.net.URI;
import java.text.MessageFormat;
import java.util.List;

@RestController
@RequestMapping("/api/groupes")

public class GroupesResource{
    private static final String ENTITY_NAME = "groupes";
    private final Logger log = LoggerFactory.getLogger(GroupesResource.class);
    @Value("${mariasoft.clientApp.name}")
    private String applicationName;
    private final GroupeService groupeService;

    public GroupesResource(GroupeService groupeService) {
        this.groupeService = groupeService;
    }


    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Creation Groupe with Success",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = GroupesDto.class)))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<GroupesDto> createGroupe(@RequestBody GroupesDto groupesDto)  {
        log.debug("REST request to save Structure : {}", groupesDto);
        try {
            if(groupesDto.getId() != null) {
                throw new BadRequestAlertException("A new Groupe cannot already have an ID", "groupeManagement", "idexists");
            }
            GroupesDto result = groupeService.createGroupe(groupesDto);
            return ResponseEntity.created(new URI(MessageFormat.format("/api/groupes/{0}", result.getId())))
                    .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getGrpCode()))
                    .body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(groupesDto);
        }
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Getting Groupe with Success", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GroupesDto.class)))
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GroupesDto getGroupeById(@PathVariable Long id) {
        log.debug("REST request to get Groupe : {}", id);
        return groupeService.getGroupeById(id);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Updating Groupe with Success", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GroupesDto.class)))
    })
    @PutMapping
    public GroupesDto updateGroupe(@RequestBody GroupesDto groupesDto) {
        log.debug("REST request to update Groupe : {}", groupesDto);
        return groupeService.updateGroupe(groupesDto);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Deleting Groupe with Success")
    })
    @DeleteMapping("/{id}")
    public void deleteGroupe(@PathVariable Long id) {
        log.debug("REST request to delete Groupe : {}", id);
        groupeService.deleteGroupe(id);
    }

    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Getting all Groupes by Structure with Success", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GroupesDto.class)))
    })
    @GetMapping("/structure/{sid}")
    public List<GroupesDto> getAllGroupeByStructure(@PathVariable Long sid) {
        log.debug("REST request to get Groupes by Structure : {}", sid);
        return groupeService.getAllGroupeByStructure(sid);
    }
}