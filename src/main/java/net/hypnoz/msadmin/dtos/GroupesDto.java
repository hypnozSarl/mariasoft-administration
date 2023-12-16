package net.hypnoz.msadmin.dtos;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link net.hypnoz.msadmin.domain.Groupes}
 */
@Value
public class GroupesDto implements Serializable {
    Long id;
    String grpCode;
    String grpLibelle;
    StructuresDto structures;
}