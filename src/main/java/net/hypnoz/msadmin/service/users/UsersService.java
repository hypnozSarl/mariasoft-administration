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

package net.hypnoz.msadmin.service.users;

import lombok.RequiredArgsConstructor;
import net.hypnoz.msadmin.domain.Groupes;
import net.hypnoz.msadmin.domain.Users;
import net.hypnoz.msadmin.dtos.UsersDto;
import net.hypnoz.msadmin.exceptions.ResourceNotFoundException;
import net.hypnoz.msadmin.mappers.UsersMapper;
import net.hypnoz.msadmin.repository.GroupesRepository;
import net.hypnoz.msadmin.repository.UsersRepository;
import net.hypnoz.msadmin.service.menus.MenuApplicatifService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static net.hypnoz.msadmin.utils.validators.ConstantLogger.GROUPE_NOT_FOUND_MSG;

@Service
@RequiredArgsConstructor
public class UsersService implements IUsersServices{

    private final Logger LOOGER = LoggerFactory.getLogger(UsersService.class);
    private final UsersMapper usersMapper;
    private final UsersRepository usersRepository;
    private final GroupesRepository groupesRepository;
    /**
     * @param usersDto
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public UsersDto createUsers(UsersDto usersDto) {
        Groupes groupes = groupesRepository.findById(usersDto.getGroupes().getId())
                .orElseThrow(()-> {LOOGER.error(GROUPE_NOT_FOUND_MSG, usersDto.getGroupes().getId());
        return new ResourceNotFoundException(GROUPE_NOT_FOUND_MSG);});
            Users userEntity = usersMapper.toEntity(usersDto);
            userEntity.setUsrPatronyme(usersDto.getUsrNom() + " " + usersDto.getUsrPrenom());
            userEntity.setGroupes(groupes);
            Users savedUser = usersRepository.save(userEntity);
            return usersMapper.toDto(savedUser);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public UsersDto getUsersById(Long id) {
        return null;
    }

    /**
     * @param usersDto
     * @return
     */
    @Override
    public UsersDto updateUsers(UsersDto usersDto) {
        return null;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public UsersDto deleteUsers(Long id) {
        return null;
    }

    /**
     * @param sid
     * @return
     */
    @Override
    public List<UsersDto> getAllUsers(Long sid) {
        return null;
    }

    /**
     * @param login
     * @return
     */
    @Override
    public UsersDto findByLogin(String login) {
        return null;
    }

    /**
     * @param id
     * @param usersDto
     * @param newpassword
     * @return
     */
    @Override
    public UsersDto updatePassword(Long id, UsersDto usersDto, String newpassword) {
        return null;
    }
}
