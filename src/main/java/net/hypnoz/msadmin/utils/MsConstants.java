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