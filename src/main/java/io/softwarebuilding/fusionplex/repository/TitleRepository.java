package io.softwarebuilding.fusionplex.repository;

import io.softwarebuilding.fusionplex.entity.Title;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TitleRepository extends JpaRepository<Title, UUID> {

}
