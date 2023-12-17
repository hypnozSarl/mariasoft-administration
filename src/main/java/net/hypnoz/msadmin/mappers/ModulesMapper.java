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

import net.hypnoz.msadmin.domain.Modules;
import net.hypnoz.msadmin.dtos.ModulesDto;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING, uses = {StructuresMapper.class})
public interface ModulesMapper {
    Modules toEntity(ModulesDto modulesDto);

    ModulesDto toDto(Modules modules);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Modules partialUpdate(ModulesDto modulesDto, @MappingTarget Modules modules);
}