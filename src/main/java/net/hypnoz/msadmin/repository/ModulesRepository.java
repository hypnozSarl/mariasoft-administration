package net.hypnoz.msadmin.repository;

import net.hypnoz.msadmin.domain.Modules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface ModulesRepository extends JpaRepository<Modules, String> {
   @Query(value = "select ms from commun_modules_structureses ms where ms.structureses_id =: id ",nativeQuery = true)
    Set<Modules> findByStructureses_Id(@Param("id") Long id);

}