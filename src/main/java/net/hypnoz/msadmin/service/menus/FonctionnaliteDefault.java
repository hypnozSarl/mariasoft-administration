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

package net.hypnoz.msadmin.service.menus;

import lombok.Getter;

@Getter
public enum FonctionnaliteDefault {
        M0_A1_F1("M0_A1_F1","Structures", "param/structure", "arrow_right", "M","M0_A1" ),
        M0_A1_F4("M0_A1_F4","Analytiques", "param/activites","arrow_right", "M","M0_A1" ),
        M0_A1_F5("M0_A1_F5","Habilitation","param/habilitation","arrow_right", "M","M0_A1" ),
        M0_A1_F6(" M0_A1_F6","Model Impression","param/mimpression","arrow_right", "M","M0_A1" ),
        M0_A2_F1("M0_A2_F1","Goupes","roles/groups","arrow_right","M","M0_A2" ),
        M0_A2_F2("M0_A2_F2","Utilisateurs","roles/users","arrow_right","M", "M0_A2"),
        M0_A2_F4("M0_A2_F4","Piste d'audit","roles/piste","arrow_right","M", "M0_A2"),
        ;

        private final String id;
        private final String label;
        private final String link;
        private final String icone;
        private final String type;
        private final String codeApplication;


    FonctionnaliteDefault(String id, String label, String link, String icone, String type, String codeApplication){
            this.label = label;
            this.link = link;
            this.icone = icone;
            this.type = type;
            this.id = id;
        this.codeApplication = codeApplication;
    }

    }
