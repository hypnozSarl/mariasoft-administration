package net.hypnoz.msadmin.repository;

import net.hypnoz.msadmin.domain.Modules;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface ModulesRepository extends JpaRepository<Modules, String> {
    Set<Modules> findByStructureses_Id(Long id);
}