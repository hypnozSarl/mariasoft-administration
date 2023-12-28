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
public enum ApplicationDefault {
        M0_A0("M0_A0","Tableau de bord", "/dashboard","M0"),
        M0_A1("M0_A1","Paramètre généreaux", "param/dashboard","M0"),
        M0_A2("M0_A2","Utilisateurs & rôles", "roles/dashboard","M0"),
        M1_A0("M1_A0","Tableau de bord", "/dashboard","M1"),
        M1_A1("M1_A1","Traitements", "/dashboard","M1"),
        M1_A2("M1_A2","Interrogations", "/dashboard","M1"),
        M1_A9("M1_A9","Editions", "/dashboard","M1"),
        M1_A10("M1_A10","Paramètres", "/dashboard","M1"),
        M2_A0("M2_A0","Tableau de bord", "/dashboard","M2"),
        M2_A1("M2_A1","Fichiers", "/dashboard","M2"),
        M2_A2("M2_A2","Achats", "/dashboard","M2"),
        M2_A3("M2_A3","Stock", "/dashboard","M2"),
        M2_A4("M2_A4","Ventes", "/dashboard","M2"),
        M2_A5("M2_A5","Interrogations", "/dashboard","M2"),
        M2_A9("M2_A9","Editions", "/dashboard","M2"),
        M2_A10("M2_A10","Paramètres", "/dashboard","M2"),
        M3_A0("M3_A0","Tableau de bord", "/dashboard","M3"),
        M3_A1("M3_A1","Reglement", "/dashboard","M3"),
        M3_A2("M3_A2","Paiement", "/dashboard","M3"),
        M3_A3("M3_A3","Caisse", "/dashboard","M3"),
        M3_A4("M3_A4","Traite", "/dashboard","M3"),
        M3_A9("M3_A9","Editions", "/dashboard","M3"),
        M3_A10("M3_A10","Paramètre", "param/dashboard","M3"),
        M4_A0("M4_A0","Tableau de bord", "/dashboard","M4"),
        M4_A9("M4_A9","Editions", "/dashboard","M4"),
        M4_A10("M4_A10","Paramètre",  "/dashboard","M4"),
        M7_A0("M7_A0","Tableau de bord", "/dashboard","M7"),
        M7_A1("M7_A1","Gestion Flotte", "/dashboard","M7"),
        M7_A2("M7_A2","Documents Administratifs", "/dashboard","M7"),
        M7_A3("M7_A3","Alertes Generales", "/dashboard","M7"),
        M7_A4("M7_A4","Mise en Disposition", "/dashboard","M7"),
        M7_A5("M7_A5","Gestion de Reparation", "/dashboard","M7"),
        M7_A6("M7_A6","Gestion de stock", "/dashboard","M7"),
        M7_A7("M7_A7","Gestion de consommation", "/dashboard","M7"),
        M7_A10("M7_A10","Paramètre", "param/dashboard","M7");

        private final String id;
        private final String label;
        private final String url;
        private  String module;


    ApplicationDefault(String id, String libelle, String url, String module) {
        this.id = id;
        this.label = libelle;
        this.url = url;
        this.module = module;
    }
}
