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

public enum FormeJuridiqueEnum {
    EURL("Entreprise Unipersonnelle A Responsabilités Limitées"),
    CHU("Centre Hospitalier Universitaire"),
    SARL("Société A Responsabilités Limitées (LLC)"),
    SA("Société Anonyme"),
    SA_PUBLIQUE("Société Anonyme Publique"),
    GIE("Groupement d`Intérêt Economique"),
    ASSOCIATION("Association"),
    SP("Société Publique"),
    SCS("Société en Commandite Simple"),
    EI("Entreprise Individuelle"),
    SCI("Société Civile Immobilière"),
    ONG("Organisation Non Gouvernementale"),
    SNC("Société en Nom Collectif"),
    SASU("Société par Action Simplifiée Unipersonnelle"),
    SAS("Société par Action Simplifiée"),
    LTD("Private Limited Company"),
    LLP("Limited Liability Company"),
    PLC("Public Limited Company");

    private final String description;

    FormeJuridiqueEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
