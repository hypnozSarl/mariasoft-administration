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

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "commun_module")
public class Modules implements Serializable {
    @Id
    private String id;
    private String name;
    private String host;
    @NotNull
    private String url;
    private String icon;
    private String active;
    private int ordre;
    @NotNull
    @ManyToMany
    @JoinTable(name = "commun_modules_structureses",
            joinColumns = @JoinColumn(name = "modules_id"),
            inverseJoinColumns = @JoinColumn(name = "structureses_id"))
    private List<Structures> structureses = new LinkedList<>();

    @PrePersist
    public void beforePersist() {
        this.active = "Y";
    }

}
