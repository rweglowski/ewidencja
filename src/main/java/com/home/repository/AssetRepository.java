package com.home.repository;

import com.home.domain.Asset;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * Spring Data JPA repository for the Asset entity.
 */
@SuppressWarnings("unused")
public interface AssetRepository extends JpaRepository<Asset,Long> {

    @Query("select distinct asset from Asset asset left join fetch asset.employees")
    List<Asset> findAllWithEagerRelationships();

    @Query("select asset from Asset asset left join fetch asset.employees where asset.id =:id")
    Asset findOneWithEagerRelationships(@Param("id") Long id);

    List<Asset> findByEndDateOfUse(LocalDate endDate);

}
