package net.hypnoz.msadmin.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.hypnoz.msadmin.dtos.GroupesDto;
import net.hypnoz.msadmin.dtos.StructuresDto;
import net.hypnoz.msadmin.service.groupes.GroupeService;
import net.hypnoz.msadmin.web.rest.GroupesResource;
import net.hypnoz.msadmin.web.rest.errors.BadRequestAlertException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GroupesResource.class)
 class GroupesResourceTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GroupeService groupeService;
    @Test
     void shouldCreateGroupeWhenDataIsValid() throws Exception {
        // Arrange
        GroupesDto newGroupesDto = new GroupesDto();
        newGroupesDto.setGrpCode("Code");
        newGroupesDto.setGrpLibelle("Libelle code");
        newGroupesDto.setStructures(new StructuresDto());


        when(groupeService.createGroupe(any())).thenReturn(newGroupesDto);

        // Act and Assert
        mockMvc.perform(post("/api/groupes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newGroupesDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.grpCode", is(newGroupesDto.getGrpCode())))
                .andExpect(jsonPath("$.grpLibelle", is(newGroupesDto.getGrpLibelle())));
    }

    @Test
     void shouldReturnErrorWhenDataIsInvalid() throws Exception {
        // Arrange
        GroupesDto invalidGroupesDto = new GroupesDto();
        when(groupeService.createGroupe(any())).thenThrow(new BadRequestAlertException("A new Groupe cannot already have an ID", "groupeManagement", "idexists"));

        // Act and Assert
        mockMvc.perform(post("/api/groupes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(invalidGroupesDto)))
                .andExpect(status().isBadRequest());
    }
    @Test
     void shouldThrowBadRequestAlertExceptionWhenIdExists() throws Exception {
        GroupesDto groupesDto = new GroupesDto();
        groupesDto.setId(1L);
        groupesDto.setGrpCode("Code");
        groupesDto.setGrpLibelle("Libelle code");
        groupesDto.setStructures(new StructuresDto());

        mockMvc.perform(post("/api/groupes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(groupesDto)))
                .andExpect(status().isInternalServerError());
    }

   @Test
   void getGroupeById_ExistingId_ShouldReturnSuccess() throws Exception {
      // Arrange
      Long id = 1L;
      GroupesDto expectedGroupeDto = new GroupesDto();
      expectedGroupeDto.setId(id);
      expectedGroupeDto.setGrpCode("Code");
      expectedGroupeDto.setGrpLibelle("Libelle code");
      expectedGroupeDto.setStructures(new StructuresDto());

      when(groupeService.getGroupeById(id)).thenReturn(expectedGroupeDto);

      // Act and Assert
      mockMvc.perform(MockMvcRequestBuilders.get("/api/groupes/" + id))
              .andExpect(MockMvcResultMatchers.status().isAccepted())
              .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(expectedGroupeDto.getId().intValue())))
              .andExpect(MockMvcResultMatchers.jsonPath("$.grpCode", Matchers.is(expectedGroupeDto.getGrpCode())));
   }
   @Test
   void update_Groupe_With_ValidData_ShouldReturnSuccess() throws Exception {
      // Arrange
      GroupesDto updateGroupesDto = new GroupesDto();
      updateGroupesDto.setId(1L);
      updateGroupesDto.setGrpCode("Code");
      updateGroupesDto.setGrpLibelle("Libelle code");
      updateGroupesDto.setStructures(new StructuresDto());

      when(groupeService.updateGroupe(any(GroupesDto.class))).thenReturn(updateGroupesDto);

      // Act and Assert
      mockMvc.perform(MockMvcRequestBuilders.put("/api/groupes")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(new ObjectMapper().writeValueAsString(updateGroupesDto)))
              .andExpect(MockMvcResultMatchers.status().isOk())
              .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(updateGroupesDto.getId().intValue())))
              .andExpect(MockMvcResultMatchers.jsonPath("$.grpCode", Matchers.is(updateGroupesDto.getGrpCode())));
   }
   @Test
   void update_Groupe_Without_Id_ShouldReturnError() throws Exception {
      // Arrange
      GroupesDto updateGroupesDto = new GroupesDto();
      updateGroupesDto.setGrpCode("Code");
      updateGroupesDto.setGrpLibelle("Libelle code");
      updateGroupesDto.setStructures(new StructuresDto());

      when(groupeService.updateGroupe(updateGroupesDto))
              .thenThrow(new BadRequestAlertException("A update structure cannot already haven't an ID", "userManagement", "idnotexists"));

      // Act and Assert
      mockMvc.perform(MockMvcRequestBuilders.put("/api/groupes")
                      .contentType(MediaType.APPLICATION_JSON)
                      .content(new ObjectMapper().writeValueAsString(updateGroupesDto)))
              .andExpect(MockMvcResultMatchers.status().isInternalServerError());
   }
}
