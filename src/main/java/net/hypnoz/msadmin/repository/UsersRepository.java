package net.hypnoz.msadmin.repository;

import net.mariasoft.administrations.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
}