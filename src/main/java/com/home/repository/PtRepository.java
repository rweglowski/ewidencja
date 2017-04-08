package com.home.repository;

import com.home.domain.Pt;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Pt entity.
 */
@SuppressWarnings("unused")
public interface PtRepository extends JpaRepository<Pt,Long> {

    @Query("select distinct pt from Pt pt left join fetch pt.recipients")
    List<Pt> findAllWithEagerRelationships();

    @Query("select pt from Pt pt left join fetch pt.recipients where pt.id =:id")
    Pt findOneWithEagerRelationships(@Param("id") Long id);

}
