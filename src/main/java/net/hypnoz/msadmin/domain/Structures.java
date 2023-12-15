package net.hypnoz.msadmin.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import net.mariasoft.administrations.enumeration.FormeJuridiqueEnum;
import net.mariasoft.administrations.enumeration.TypeSocieteEnum;
import net.mariasoft.administrations.enumeration.ZoneCommercialEnum;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.io.Serializable;
import java.time.LocalDate;


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

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getStrEcommerceVal() {
        return strEcommerceVal;
    }

    public void setStrEcommerceVal(Integer strEcommerceVal) {
        this.strEcommerceVal = strEcommerceVal;
    }

    public Integer getStrEtat() {
        return strEtat;
    }

    public void setStrEtat(Integer strEtat) {
        this.strEtat = strEtat;
    }

    public Integer getStrMode() {
        return strMode;
    }

    public void setStrMode(Integer strMode) {
        this.strMode = strMode;
    }

    public Integer getStrMaitreCabinet() {
        return strMaitreCabinet;
    }

    public void setStrMaitreCabinet(Integer strMaitreCabinet) {
        this.strMaitreCabinet = strMaitreCabinet;
    }

    public String getStrRaisonSociale() {
        return strRaisonSociale;
    }

    public void setStrRaisonSociale(String strRaisonSociale) {
        this.strRaisonSociale = strRaisonSociale;
    }

    public String getStrDescriptif() {
        return strDescriptif;
    }

    public void setStrDescriptif(String strDescriptif) {
        this.strDescriptif = strDescriptif;
    }

    public String getStrSigle() {
        return strSigle;
    }

    public void setStrSigle(String strSigle) {
        this.strSigle = strSigle;
    }

    public String getStrNomPays() {
        return strNomPays;
    }

    public void setStrNomPays(String strNomPays) {
        this.strNomPays = strNomPays;
    }

    public String getStrCodePays() {
        return strCodePays;
    }

    public void setStrCodePays(String strCodePays) {
        this.strCodePays = strCodePays;
    }

    public String getStrIsoPays() {
        return strIsoPays;
    }

    public void setStrIsoPays(String strIsoPays) {
        this.strIsoPays = strIsoPays;
    }

    public String getStrDevise() {
        return strDevise;
    }

    public void setStrDevise(String strDevise) {
        this.strDevise = strDevise;
    }

    public Integer getStrSiteDevise() {
        return strSiteDevise;
    }

    public void setStrSiteDevise(Integer strSiteDevise) {
        this.strSiteDevise = strSiteDevise;
    }

    public String getStrLangue() {
        return strLangue;
    }

    public void setStrLangue(String strLangue) {
        this.strLangue = strLangue;
    }

    public String getStrZoneFiscale() {
        return strZoneFiscale;
    }

    public void setStrZoneFiscale(String strZoneFiscale) {
        this.strZoneFiscale = strZoneFiscale;
    }

    public String getStrZoneCommerciale() {
        return strZoneCommerciale;
    }

    public void setStrZoneCommerciale(String strZoneCommerciale) {
        this.strZoneCommerciale = strZoneCommerciale;
    }

    public String getStrBilanSocial() {
        return strBilanSocial;
    }

    public void setStrBilanSocial(String strBilanSocial) {
        this.strBilanSocial = strBilanSocial;
    }

    public String getStrFormeJuridique() {
        return strFormeJuridique;
    }

    public void setStrFormeJuridique(String strFormeJuridique) {
        this.strFormeJuridique = strFormeJuridique;
    }

    public String getStrtypEentreprise() {
        return strtypEentreprise;
    }

    public void setStrtypEentreprise(String strtypEentreprise) {
        this.strtypEentreprise = strtypEentreprise;
    }

    public String getStrAdresse() {
        return strAdresse;
    }

    public void setStrAdresse(String strAdresse) {
        this.strAdresse = strAdresse;
    }

    public String getStrRue() {
        return strRue;
    }

    public void setStrRue(String strRue) {
        this.strRue = strRue;
    }

    public String getStrLot() {
        return strLot;
    }

    public void setStrLot(String strLot) {
        this.strLot = strLot;
    }

    public String getStrPorte() {
        return strPorte;
    }

    public void setStrPorte(String strPorte) {
        this.strPorte = strPorte;
    }

    public String getStrQuartier() {
        return strQuartier;
    }

    public void setStrQuartier(String strQuartier) {
        this.strQuartier = strQuartier;
    }

    public String getStrVille() {
        return strVille;
    }

    public void setStrVille(String strVille) {
        this.strVille = strVille;
    }

    public String getStrCommune() {
        return strCommune;
    }

    public void setStrCommune(String strCommune) {
        this.strCommune = strCommune;
    }

    public String getStrDepartement() {
        return strDepartement;
    }

    public void setStrDepartement(String strDepartement) {
        this.strDepartement = strDepartement;
    }

    public String getStrCodePostal() {
        return strCodePostal;
    }

    public void setStrCodePostal(String strCodePostal) {
        this.strCodePostal = strCodePostal;
    }

    public String getStrCedex() {
        return strCedex;
    }

    public void setStrCedex(String strCedex) {
        this.strCedex = strCedex;
    }

    public String getStrTelephone() {
        return strTelephone;
    }

    public void setStrTelephone(String strTelephone) {
        this.strTelephone = strTelephone;
    }

    public String getStrFax() {
        return strFax;
    }

    public void setStrFax(String strFax) {
        this.strFax = strFax;
    }

    public String getStrActiviteCommerciale() {
        return strActiviteCommerciale;
    }

    public void setStrActiviteCommerciale(String strActiviteCommerciale) {
        this.strActiviteCommerciale = strActiviteCommerciale;
    }

    public String getStrLogo() {
        return strLogo;
    }

    public void setStrLogo(String strLogo) {
        this.strLogo = strLogo;
    }

    public Integer getStrTypeContact() {
        return strTypeContact;
    }

    public void setStrTypeContact(Integer strTypeContact) {
        this.strTypeContact = strTypeContact;
    }

    public String getStrResponsable() {
        return strResponsable;
    }

    public void setStrResponsable(String strResponsable) {
        this.strResponsable = strResponsable;
    }

    public String getStrQualiteResponsable() {
        return strQualiteResponsable;
    }

    public void setStrQualiteResponsable(String strQualiteResponsable) {
        this.strQualiteResponsable = strQualiteResponsable;
    }

    public String getStrCapital() {
        return strCapital;
    }

    public void setStrCapital(String strCapital) {
        this.strCapital = strCapital;
    }

    public Boolean getStrStructure() {
        return strStructure;
    }

    public void setStrStructure(Boolean strStructure) {
        this.strStructure = strStructure;
    }

    public Boolean getStrChantier() {
        return strChantier;
    }

    public void setStrChantier(Boolean strChantier) {
        this.strChantier = strChantier;
    }

    public Boolean getStrMission() {
        return strMission;
    }

    public void setStrMission(Boolean strMission) {
        this.strMission = strMission;
    }

    public Boolean getStrSite() {
        return strSite;
    }

    public void setStrSite(Boolean strSite) {
        this.strSite = strSite;
    }

    public Boolean getStrRegion() {
        return strRegion;
    }

    public void setStrRegion(Boolean strRegion) {
        this.strRegion = strRegion;
    }

    public Boolean getStrUsine() {
        return strUsine;
    }

    public void setStrUsine(Boolean strUsine) {
        this.strUsine = strUsine;
    }

    public Boolean getStrActivite() {
        return strActivite;
    }

    public void setStrActivite(Boolean strActivite) {
        this.strActivite = strActivite;
    }

    public Integer getStrActiviteModeSasie() {
        return strActiviteModeSasie;
    }

    public void setStrActiviteModeSasie(Integer strActiviteModeSasie) {
        this.strActiviteModeSasie = strActiviteModeSasie;
    }

    public Boolean getStrParc() {
        return strParc;
    }

    public void setStrParc(Boolean strParc) {
        this.strParc = strParc;
    }

    public Integer getStrDossier() {
        return strDossier;
    }

    public void setStrDossier(Integer strDossier) {
        this.strDossier = strDossier;
    }

    public Integer getStrNbCarDossier() {
        return strNbCarDossier;
    }

    public void setStrNbCarDossier(Integer strNbCarDossier) {
        this.strNbCarDossier = strNbCarDossier;
    }

    public Integer getStrChainageAxes() {
        return strChainageAxes;
    }

    public void setStrChainageAxes(Integer strChainageAxes) {
        this.strChainageAxes = strChainageAxes;
    }

    public Boolean getStrAgent() {
        return strAgent;
    }

    public void setStrAgent(Boolean strAgent) {
        this.strAgent = strAgent;
    }

    public Boolean getStrCles() {
        return strCles;
    }

    public void setStrCles(Boolean strCles) {
        this.strCles = strCles;
    }

    public Boolean getStrProjet() {
        return strProjet;
    }

    public void setStrProjet(Boolean strProjet) {
        this.strProjet = strProjet;
    }

    public String getStrQuart1DebutHeure() {
        return strQuart1DebutHeure;
    }

    public void setStrQuart1DebutHeure(String strQuart1DebutHeure) {
        this.strQuart1DebutHeure = strQuart1DebutHeure;
    }

    public String getStrQuart1DebutMinute() {
        return strQuart1DebutMinute;
    }

    public void setStrQuart1DebutMinute(String strQuart1DebutMinute) {
        this.strQuart1DebutMinute = strQuart1DebutMinute;
    }

    public String getStrQuart1FinHeure() {
        return strQuart1FinHeure;
    }

    public void setStrQuart1FinHeure(String strQuart1FinHeure) {
        this.strQuart1FinHeure = strQuart1FinHeure;
    }

    public String getStrQuart1FinMinute() {
        return strQuart1FinMinute;
    }

    public void setStrQuart1FinMinute(String strQuart1FinMinute) {
        this.strQuart1FinMinute = strQuart1FinMinute;
    }

    public String getStrQuart2FinHeure() {
        return strQuart2FinHeure;
    }

    public void setStrQuart2FinHeure(String strQuart2FinHeure) {
        this.strQuart2FinHeure = strQuart2FinHeure;
    }

    public String getStrQuart2FinMinute() {
        return strQuart2FinMinute;
    }

    public void setStrQuart2FinMinute(String strQuart2FinMinute) {
        this.strQuart2FinMinute = strQuart2FinMinute;
    }

    public String getStrQuart3FinHeure() {
        return strQuart3FinHeure;
    }

    public void setStrQuart3FinHeure(String strQuart3FinHeure) {
        this.strQuart3FinHeure = strQuart3FinHeure;
    }

    public String getStrQuart3FinMinute() {
        return strQuart3FinMinute;
    }

    public void setStrQuart3FinMinute(String strQuart3FinMinute) {
        this.strQuart3FinMinute = strQuart3FinMinute;
    }

    public String getStrBanqueDefaut() {
        return strBanqueDefaut;
    }

    public void setStrBanqueDefaut(String strBanqueDefaut) {
        this.strBanqueDefaut = strBanqueDefaut;
    }

    public LocalDate getStrDteDebMandat() {
        return strDteDebMandat;
    }

    public void setStrDteDebMandat(LocalDate strDteDebMandat) {
        this.strDteDebMandat = strDteDebMandat;
    }

    public LocalDate getStrDteFinMandat() {
        return strDteFinMandat;
    }

    public void setStrDteFinMandat(LocalDate strDteFinMandat) {
        this.strDteFinMandat = strDteFinMandat;
    }

    public Integer getStrEtatMandat() {
        return strEtatMandat;
    }

    public void setStrEtatMandat(Integer strEtatMandat) {
        this.strEtatMandat = strEtatMandat;
    }

    public String getStrSignature() {
        return strSignature;
    }

    public void setStrSignature(String strSignature) {
        this.strSignature = strSignature;
    }
}
