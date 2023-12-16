package net.hypnoz.msadmin.repository;

import net.hypnoz.msadmin.domain.Groupes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupesRepository extends JpaRepository<Groupes, Long> {
    List<Groupes> findByStructures_Id(Long id);
}