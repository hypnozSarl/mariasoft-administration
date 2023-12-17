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
import net.hypnoz.msadmin.domain.Users;
import net.hypnoz.msadmin.dtos.UsersDto;
import net.hypnoz.msadmin.mappers.UsersMapper;
import net.hypnoz.msadmin.repository.UsersRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@RequiredArgsConstructor
public class UsersService implements IUsersServices{
    private final UsersMapper usersMapper;
    private final UsersRepository usersRepository;
    /**
     * @param usersDto
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public UsersDto createUsers(UsersDto usersDto) {
            Users userEntity = usersMapper.toEntity(usersDto);
            userEntity.setUsrPatronyme(usersDto.getUsrNom() + " " + usersDto.getUsrPrenom());
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
