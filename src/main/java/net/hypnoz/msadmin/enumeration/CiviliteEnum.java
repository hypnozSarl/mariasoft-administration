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

package net.hypnoz.msadmin.enumeration;

public enum CiviliteEnum {
    MME("Mme", "Madame"),
    MLLE("Mlle", "Mademoiselle"),
    BEBE("Bébé", "Bébé"),
    ENFANT("Enfant", "Enfant"),
    M("M.", "Monsieur"),
    MME_ET_M("Mme et M.", "Madame et Monsieur"),
    CONSORTS("Consorts", "Consorts"),
    DR("Dr.", "Docteur"),
    PR("Pr.", "Professeur"),
    ME("Me", "Maître"),
    MSG("Msg", "Monseigneur"),
    COLONEL("Colonel", "Colonel"),
    CAPITAINE("Capitaine", "Capitaine"),
    COMMANDANT("Commandant", "Commandant"),
    LIEUTENANT("Lieutenant", "Lieutenant"),
    SERGENT("Sergent", "Sergent"),
    AUTRE("Autre", "Autre"),
    SE("Se.", "Son Excellence");

    private final String code;
    private final String libelle;

    CiviliteEnum(String code, String libelle) {
        this.code = code;
        this.libelle = libelle;
    }

    public String getCode() {
        return code;
    }

    public String getLibelle() {
        return libelle;
    }
}
