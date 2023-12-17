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

package net.hypnoz.msadmin.utils;

public final class MsConstants {
    public static final String DEFAULT_DOC_SERVEUR_NAME = "mariasoft";
    public static final String DEFAULT_DOC_SERVEUR_STRUCTURE = "structure";
    public static final String DEFAULT_DOC_SERVEUR_MODELS = "models";
    public static final String DEFAULT_DOC_SERVEUR_PIECES = "pieces";
    public static final String DEFAULT_DOC_SERVEUR_IMPRESSION = "impression";

    public static final String PARAM_CONFIG_PATH = "CONFIG_PATH";
    public static final String DIR_TEMP = "temp";
    public static final String DIR_LOG = "logs";
    public static final int HTTPSTATUS_FILE_NOT_FOUND = 900;
    public static final String TYPE_TIER_CLIENT="3";
    public static final String TYPE_TIER_FOURNISSEUR = "0";
    public static final String TYPE_TIER_COMMERCIALE = "4";
    public static final String TYPE_TIER_DESTINATAIRE ="7";
    public static final String TYPE_TIER_PROSPECTS = "2";
    public static final String TYPE_TIER_DIVERS="99";
    public static final String OPTIONS_STOKS = "OPTIONS_STOCKS";
    public static final String NATURE_TYPE_ACHAT_STOCK = "as";
    public static final String NATURE_TYPE_VENTES_STOCK = "vs";
    public static final String NATURE_TYPE_CAISSE = "cs";
    public static final String NATURE_TYPE_TIERS = "ts";
    public static final String NATURE_TYPE_PARCS ="prc";

    public static final String OPTIONS_ACHATS = "OPTIONS_ACHATS";

    public static final int TAILLE_RACINE_COMPTA_FOURNISSEUR = 4;

    private MsConstants() {
        // prevent instantiation
    }
}