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

package net.hypnoz.msadmin.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StructuresDto implements Serializable {
    Long id;
    Integer strEcommerceVal;
    Integer strEtat;
    Integer strMode;
    Integer strMaitreCabinet;
    @NotNull(message = "La Rasison Social ne peut pas être null")
    @Size(max = 100,min = 2,message = "La Rasison Social doit être compris entre 2 et 100 caractères")
    String strRaisonSociale;
    String strDescriptif;
    @NotNull(message = "Le Sigle ne peut pas être null")
    @Size(message = "Le sigle doit être compris entre 2 et 50 caractères", min = 2, max = 50)
    String strSigle;
    String strNomPays;
    String strCodePays;
    String strIsoPays;
    String strDevise;
    Integer strSiteDevise;
    String strLangue;
    String strZoneFiscale;
    String strZoneCommerciale;
    String strBilanSocial;
    String strFormeJuridique;
    String strtypEentreprise;
    String strAdresse;
    String strRue;
    String strLot;
    String strPorte;
    String strQuartier;
    String strVille;
    String strCommune;
    String strDepartement;
    String strCodePostal;
    String strCedex;
    String strTelephone;
    String strFax;
    String strActiviteCommerciale;
    String strLogo;
    Integer strTypeContact;
    String strResponsable;
    String strQualiteResponsable;
    String strCapital;
    Boolean strStructure;
    Boolean strChantier;
    Boolean strMission;
    Boolean strSite;
    Boolean strRegion;
    Boolean strUsine;
    Boolean strActivite;
    Integer strActiviteModeSasie;
    Boolean strParc;
    Integer strDossier;
    Integer strNbCarDossier;
    Integer strChainageAxes;
    Boolean strAgent;
    Boolean strCles;
    Boolean strProjet;
    String strQuart1DebutHeure;
    String strQuart1DebutMinute;
    String strQuart1FinHeure;
    String strQuart1FinMinute;
    String strQuart2FinHeure;
    String strQuart2FinMinute;
    String strQuart3FinHeure;
    String strQuart3FinMinute;
    String strBanqueDefaut;
    LocalDate strDteDebMandat;
    LocalDate strDteFinMandat;
    Integer strEtatMandat;
    String strSignature;
}