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
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_fonctionalites")
public class UserFonctionalites implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private UserFonctionalityId id;

    @ManyToOne
    @MapsId("userId")
    private Users user;

    @ManyToOne
    @MapsId("fonctionalityId")
    private Fonctionnalite fonctionalite;

    private boolean ecriture;
    private boolean lecture;
    private boolean modification;
    private boolean suppression;
    private boolean impression;
    private boolean duplicated;
    private boolean transfert;
}

