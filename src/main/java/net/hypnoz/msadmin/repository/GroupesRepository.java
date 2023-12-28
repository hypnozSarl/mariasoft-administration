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

package net.hypnoz.msadmin.repository;

import net.hypnoz.msadmin.domain.Applications;
import net.hypnoz.msadmin.domain.Groupes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface GroupesRepository extends JpaRepository<Groupes, Long> {

    boolean existsByIdAndFonctionnalites_IdIn(Long id, Set<String> ids);

    boolean existsByIdAndApplicationses_IdIn(Long id, Set<String> ids);

    @Query("select (count(g) > 0) from Groupes g inner join g.moduleses moduleses where g.id = ?1 and moduleses.id in ?2")
    boolean existsByIdAndModuleses_IdIn(Long id, Set<String> ids);

    List<Groupes> findByStructures_Id(Long id);

}