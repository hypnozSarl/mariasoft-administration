package net.hypnoz.msadmin.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link net.hypnoz.msadmin.domain.Groupes}
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupesDto implements Serializable {
    Long id;
    @NotNull
    @NotEmpty
   @Size(max = 10)
    String grpCode;
    String grpLibelle;
    @NotNull
    StructuresDto structures;
}