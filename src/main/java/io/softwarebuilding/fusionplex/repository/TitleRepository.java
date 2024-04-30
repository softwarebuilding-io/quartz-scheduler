package io.softwarebuilding.fusionplex.repository;

import io.softwarebuilding.fusionplex.entity.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface TitleRepository extends JpaRepository<Title, UUID> {

    @Query("select t from Title  t where lower(t.name) = lower(:name)")
    Optional<Title> findByNameContainingIgnoreCase(@Param("name") String name);

}
