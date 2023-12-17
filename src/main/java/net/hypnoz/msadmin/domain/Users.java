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

package net.hypnoz.msadmin.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "commun_users")
@Where(clause = "flag_etat <> 'DELETED'")
@SQLDelete(sql = "UPDATE cmm_users SET flag_etat = 'DELETED' WHERE id = ?", check = ResultCheckStyle.COUNT)
public class Users extends AbstractAuditingEntity<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;
    @Column(name = "usr_civilite")
    private String usrCivilite;

    @Column(name = "usr_nom", length = 100)
    private String usrNom;

    @Column(name = "usr_prenom", length = 50)
    private String usrPrenom;
    @Column(name = "usr_patronyme")
    private String usrPatronyme;

    @Column(name = "usr_initiale", length = 10)
    private String usrInitiale;

    @Column(name = "usr_photo")
    private String usrPhoto;

    @Column(name = "usr_identifiant")
    private String usrIdentifiant;

    @Column(name = "usr_matricule")
    private String usrMatricule;
    @Column(name = "usr_password")
    private String usrPassword;
    @Column(name = "usr_login")
    private String usrLogin;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_address_id", referencedColumnName = "id")
    private CommunAdresse userAddress;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_communication_id", referencedColumnName = "id")
    private CommunCommunication userCommunication;

    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "groupes_id")
    private Groupes groupes;

}

