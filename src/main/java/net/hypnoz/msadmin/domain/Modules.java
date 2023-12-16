package net.hypnoz.msadmin.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "commun_module")
public class Modules implements Serializable {
    @Id
    private String modCode;
    private String modLibelle;
    private String modDescription;
    @NotNull
    private String url;
    private String iconClass;
    private String active;
    private int ordre;
    @ManyToMany
    @JoinTable(name = "commun_users_structureses",
            joinColumns = @JoinColumn(name = "modules_modCode"),
            inverseJoinColumns = @JoinColumn(name = "structureses_id"))
    private Set<Structures> structureses = new LinkedHashSet<>();

    @PrePersist
    public void beforePersist() {
        this.active = "Y";
    }

}
