package net.hypnoz.msadmin.service.users;



import net.hypnoz.msadmin.dtos.UsersDto;

import java.util.List;

public interface IUsersServices {
    UsersDto createUsers(UsersDto usersDto);

    UsersDto getUsersById(Long id);

    UsersDto updateUsers(UsersDto usersDto);

    UsersDto deleteUsers(Long id);

    List<UsersDto> getAllUsers(Long sid);

    UsersDto findByLogin(String login);

    UsersDto updatePassword(Long id, UsersDto usersDto,String newpassword);

}