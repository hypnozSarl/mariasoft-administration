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

package net.hypnoz.msadmin.mappers;

import net.hypnoz.msadmin.domain.UserFonctionalites;
import net.hypnoz.msadmin.dtos.UserFonctionalitesDto;
import net.hypnoz.msadmin.mappers.FonctionnaliteMapper;
import net.hypnoz.msadmin.mappers.UserFonctionalityIdMapper;
import net.hypnoz.msadmin.mappers.UsersMapper;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {UserFonctionalityIdMapper.class, UsersMapper.class, FonctionnaliteMapper.class})
public interface UserFonctionalitesMapper {
    UserFonctionalites toEntity(UserFonctionalitesDto userFonctionalitesDto);

    UserFonctionalitesDto toDto(UserFonctionalites userFonctionalites);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    UserFonctionalites partialUpdate(UserFonctionalitesDto userFonctionalitesDto, @MappingTarget UserFonctionalites userFonctionalites);
}