package net.hypnoz.msadmin.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link net.hypnoz.msadmin.domain.Modules}
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ModulesDto implements Serializable {
    String id;
    @NotNull
    String name;
    @NotNull
    String host;
    @NotNull
    String icon;
    @NotNull
    String url;
    String iconClass;
    String active;
    int ordre;
    @NotNull
    private List<StructuresDto> structureses;
}