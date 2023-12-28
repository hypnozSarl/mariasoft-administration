/*
 *
 *  * Mariasoft - Enterprise Management Software
 *  * Copyright (c) 2023. Hypnoz Technologie. All rights reserved.
 *  *
 *  * This software and associated documentation files (the "Software") is the
 *  * proprietary and confidential information of Hypnoz Technologie and is subject
 *  * to a license agreement. The Software is protected by international copyright
 *  * and other intellectual property laws and treaties.
 *  *
 *  * No part of this Software may be copied, modified, distributed, or reproduced
 *  * in any form or by any means without prior written permission from Hypnoz Technologie.
 *  *
 *  * ANY USE OF THE SOFTWARE NOT EXPRESSLY PERMITTED BY THE TERMS OF THE LICENSE
 *  * AGREEMENT IS A VIOLATION OF COPYRIGHT LAW AND MAY RESULT IN SEVERE
 *  * CIVIL AND CRIMINAL PENALTIES.
 *
 */

package net.hypnoz.msadmin.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.*;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

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
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Groupes extends AbstractAuditingEntity<Long> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @NotEmpty
    @Column(unique = true, length = 10)
    private String grpCode;
    private String grpLibelle;

    @NotNull
    @ToString.Exclude
    @ManyToOne(cascade = {CascadeType.ALL})
    private Structures structures;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "cmm_groupe_moduleses",
            joinColumns = @JoinColumn(name = "groupes_id"),
            inverseJoinColumns = @JoinColumn(name = "moduleses_id"))
    private Set<Modules> moduleses = new LinkedHashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "cmm_groupe_applicationses",
            joinColumns = @JoinColumn(name = "groupes_id"),
            inverseJoinColumns = @JoinColumn(name = "applicationses_id"))
    private Set<Applications> applicationses = new LinkedHashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "cmm_groupe_fonctionnalites",
            joinColumns = @JoinColumn(name = "groupes_id"),
            inverseJoinColumns = @JoinColumn(name = "fonctionnalites_id"))
    private Set<Fonctionnalite> fonctionnalites = new LinkedHashSet<>();

}