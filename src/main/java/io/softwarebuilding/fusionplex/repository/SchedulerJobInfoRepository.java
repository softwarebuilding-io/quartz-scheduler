package io.softwarebuilding.fusionplex.repository;

import io.softwarebuilding.fusionplex.entity.SchedulerJobInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface SchedulerJobInfoRepository extends JpaRepository<SchedulerJobInfo, UUID> {

}
