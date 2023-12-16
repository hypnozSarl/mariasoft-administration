package net.hypnoz.msadmin.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Setter;
import net.hypnoz.msadmin.enumeration.FormeJuridiqueEnum;
import net.hypnoz.msadmin.enumeration.TypeSocieteEnum;
import net.hypnoz.msadmin.enumeration.ZoneCommercialEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.time.LocalDate;


@Setter
@Entity
@Table(name = "commun_structures")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Structures extends AbstractAuditingEntity<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "str_ecommerce_val")
    private Integer strEcommerceVal;
    @Column(name = "str_etat")
    private Integer strEtat;
    @Column(name = "str_mode")
    private Integer strMode;
    @Column(name = "str_maitre_cabinet")
    private Integer strMaitreCabinet;
    @NotNull(message = "La Rasison Social ne peut pas être null")
    @Size(max = 100)
    @Column(name = "str_raison_sociale",unique = true)
    private String strRaisonSociale;
    @Column(name = "str_Descriptif")
    private String strDescriptif;
    @NotNull(message = "Le Sigle ne peut pas être null")
    @Size(message = "Le sigle doit être compris entre 2 et 50 caractères", min = 2, max = 50)
    @Column(name = "str_Sigle",unique = true)
    private String strSigle;
    @Column(name = "str_nom_pays")
    private String strNomPays;
    @Column(name = "str_code_pays")
    private String strCodePays;
    @Column(name = "str_iso_pays")
    private String strIsoPays;
    @Column(name = "str_devise")
    private String strDevise;
    @Column(name = "str_site_devise")
    private Integer strSiteDevise;
    @Column(name = "str_Langue")
    private String strLangue;
    @Column(name = "str_zone_fiscale")
    private String strZoneFiscale;
    @Column(name = "str_zone_commerciale")
    private String strZoneCommerciale= ZoneCommercialEnum.UEMOA.getCode();
    @Column(name = "str_bilansocial")
    private String strBilanSocial;
    @Column(name = "str_formejuridique")
    private String strFormeJuridique= FormeJuridiqueEnum.EI.name();
    @Column(name = "str_type_entreprise")
    private String strtypEentreprise= TypeSocieteEnum.PRESTATION.name();
    @Column(name = "str_adresse")
    private String strAdresse;
    @Column(name = "str_rue")
    private String strRue;
    @Column(name = "str_lot")
    private String strLot;
    @Column(name = "str_porte")
    private String strPorte;
    @Column(name = "str_quartier")
    private String strQuartier;
    @Column(name = "str_ville")
    private String strVille;
    @Column(name = "str_commune")
    private String strCommune;
    @Column(name = "str_departement")
    private String strDepartement;
    @Column(name="str_code_postal")
    private String strCodePostal;
    @Column(name = "str_cedex")
    private String strCedex;
    @Column(name = "str_telephone")
    private String strTelephone;
    @Column(name = "str_fax")
    private String strFax;
    @Column(name = "str_activite_commerciale")
    private String strActiviteCommerciale;
    @Column(name = "str_logo")
    private String strLogo;
    @Column(name = "str_type_contact")
    private Integer strTypeContact;
    @Column(name = "str_responsable")
    private String strResponsable;
    @Column(name = "str_qualite_responsable")
    private String strQualiteResponsable;
    @Column(name = "str_capital")
    private String strCapital;

    private Boolean strStructure;
    private Boolean strChantier;
    private Boolean strMission;
    private Boolean strSite;
    private Boolean strRegion;
    private Boolean strUsine;
    private Boolean strActivite;
    private Integer strActiviteModeSasie;
    private Boolean strParc;
    private Integer strDossier;
    private Integer strNbCarDossier;
    private Integer strChainageAxes;
    private Boolean strAgent;
    private Boolean strCles;
    private Boolean strProjet;
    private String strQuart1DebutHeure;
    private String strQuart1DebutMinute;
    private String strQuart1FinHeure;
    private String strQuart1FinMinute;
    private String strQuart2FinHeure;
    private String strQuart2FinMinute;
    private String strQuart3FinHeure;
    private String strQuart3FinMinute;
    private String strBanqueDefaut;
    private LocalDate strDteDebMandat;
    private LocalDate strDteFinMandat;
    private Integer strEtatMandat;
    private String strSignature;

    @Override
    public Long getId() {
        return id;
    }

    public Integer getStrEcommerceVal() {
        return strEcommerceVal;
    }

    public Integer getStrEtat() {
        return strEtat;
    }

    public Integer getStrMode() {
        return strMode;
    }

    public Integer getStrMaitreCabinet() {
        return strMaitreCabinet;
    }

    public String getStrRaisonSociale() {
        return strRaisonSociale;
    }

    public String getStrDescriptif() {
        return strDescriptif;
    }

    public String getStrSigle() {
        return strSigle;
    }

    public String getStrNomPays() {
        return strNomPays;
    }

    public String getStrCodePays() {
        return strCodePays;
    }

    public String getStrIsoPays() {
        return strIsoPays;
    }

    public String getStrDevise() {
        return strDevise;
    }

    public Integer getStrSiteDevise() {
        return strSiteDevise;
    }

    public String getStrLangue() {
        return strLangue;
    }

    public String getStrZoneFiscale() {
        return strZoneFiscale;
    }

    public String getStrZoneCommerciale() {
        return strZoneCommerciale;
    }

    public String getStrBilanSocial() {
        return strBilanSocial;
    }

    public String getStrFormeJuridique() {
        return strFormeJuridique;
    }

    public String getStrtypEentreprise() {
        return strtypEentreprise;
    }

    public String getStrAdresse() {
        return strAdresse;
    }

    public String getStrRue() {
        return strRue;
    }

    public String getStrLot() {
        return strLot;
    }

    public String getStrPorte() {
        return strPorte;
    }

    public String getStrQuartier() {
        return strQuartier;
    }

    public String getStrVille() {
        return strVille;
    }

    public String getStrCommune() {
        return strCommune;
    }

    public String getStrDepartement() {
        return strDepartement;
    }

    public String getStrCodePostal() {
        return strCodePostal;
    }

    public String getStrCedex() {
        return strCedex;
    }

    public String getStrTelephone() {
        return strTelephone;
    }

    public String getStrFax() {
        return strFax;
    }

    public String getStrActiviteCommerciale() {
        return strActiviteCommerciale;
    }

    public String getStrLogo() {
        return strLogo;
    }

    public Integer getStrTypeContact() {
        return strTypeContact;
    }

    public String getStrResponsable() {
        return strResponsable;
    }

    public String getStrQualiteResponsable() {
        return strQualiteResponsable;
    }

    public String getStrCapital() {
        return strCapital;
    }

    public Boolean getStrStructure() {
        return strStructure;
    }

    public Boolean getStrChantier() {
        return strChantier;
    }

    public Boolean getStrMission() {
        return strMission;
    }

    public Boolean getStrSite() {
        return strSite;
    }

    public Boolean getStrRegion() {
        return strRegion;
    }

    public Boolean getStrUsine() {
        return strUsine;
    }

    public Boolean getStrActivite() {
        return strActivite;
    }

    public Integer getStrActiviteModeSasie() {
        return strActiviteModeSasie;
    }

    public Boolean getStrParc() {
        return strParc;
    }

    public Integer getStrDossier() {
        return strDossier;
    }

    public Integer getStrNbCarDossier() {
        return strNbCarDossier;
    }

    public Integer getStrChainageAxes() {
        return strChainageAxes;
    }

    public Boolean getStrAgent() {
        return strAgent;
    }

    public Boolean getStrCles() {
        return strCles;
    }

    public Boolean getStrProjet() {
        return strProjet;
    }

    public String getStrQuart1DebutHeure() {
        return strQuart1DebutHeure;
    }

    public String getStrQuart1DebutMinute() {
        return strQuart1DebutMinute;
    }

    public String getStrQuart1FinHeure() {
        return strQuart1FinHeure;
    }

    public String getStrQuart1FinMinute() {
        return strQuart1FinMinute;
    }

    public String getStrQuart2FinHeure() {
        return strQuart2FinHeure;
    }

    public String getStrQuart2FinMinute() {
        return strQuart2FinMinute;
    }

    public String getStrQuart3FinHeure() {
        return strQuart3FinHeure;
    }

    public String getStrQuart3FinMinute() {
        return strQuart3FinMinute;
    }

    public String getStrBanqueDefaut() {
        return strBanqueDefaut;
    }

    public LocalDate getStrDteDebMandat() {
        return strDteDebMandat;
    }

    public LocalDate getStrDteFinMandat() {
        return strDteFinMandat;
    }

    public Integer getStrEtatMandat() {
        return strEtatMandat;
    }

    public String getStrSignature() {
        return strSignature;
    }

}
