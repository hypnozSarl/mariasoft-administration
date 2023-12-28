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
public enum ModuleDefault {
    ADMINISTRATION("M0", "Administration", "localhost:9080", Constants.DASHBOARD),
    COMPATIBILITY("M1", "Compabitlité", "localhost:9082", Constants.DASHBOARD),
    GESTION_COMMERCIALE("M2", "Gestion commerciale", "localhost:9084", Constants.DASHBOARD),
    TRESORERIE("M3", "Trésorerie", "localhost:9086", Constants.DASHBOARD),
    PAIE("M4", "Paie", "localhost:9088", Constants.DASHBOARD),
    IMMOBILISATION("M5", "Immobilisation", "localhost:9090", Constants.DASHBOARD),
    ETATS_FINANCLIERS("M6", "Etats financliers", "localhost:9092", Constants.DASHBOARD),
    PARC("M7", "Parc", "localhost:9094", Constants.DASHBOARD);

    private final String id;
        private final String name;
        private final String host;
        private final String icon;

    ModuleDefault(String id, String name, String host, String icon) {
            this.id = id;
            this.name = name;
            this.host = host;
            this.icon = icon;
        }

    private static class Constants {
        public static final String DASHBOARD = "dashboard";
    }
}
