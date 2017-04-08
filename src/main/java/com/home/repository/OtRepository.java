package com.home.repository;

import com.home.domain.Ot;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Ot entity.
 */
@SuppressWarnings("unused")
public interface OtRepository extends JpaRepository<Ot,Long> {

    @Query("select distinct ot from Ot ot left join fetch ot.employees")
    List<Ot> findAllWithEagerRelationships();

    @Query("select ot from Ot ot left join fetch ot.employees where ot.id =:id")
    Ot findOneWithEagerRelationships(@Param("id") Long id);

}
