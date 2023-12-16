package net.hypnoz.msadmin.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;

@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cmm_groupe")
@Where(clause = "flag_etat <> 'DELETED'")
@SQLDelete(sql = "UPDATE commun_groupes SET flag_etat = 'DELETED' WHERE grp_id = ?", check = ResultCheckStyle.COUNT)
public class Groupes extends AbstractAuditingEntity<Long> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotEmpty
    @Column(unique = true, length = 10)
    private String grp_code;
    private String grpLibelle;

    @NotNull
    @ToString.Exclude
    @ManyToOne(cascade = {CascadeType.ALL})
    private Structures structures;
}
