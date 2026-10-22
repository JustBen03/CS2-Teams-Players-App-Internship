package com.example.cs2teams.repository;

import com.example.cs2teams.model.TeamMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamMembershipRepository extends JpaRepository<TeamMembership, Long> {

    @Query("select tm from TeamMembership tm where tm.team.id = :teamId and tm.active = true")
    List<TeamMembership> findActiveByTeamId(Long teamId);

    boolean existsByPlayer_Id(Long playerId);

    // NEW: player history ordered: active first, then recent first
    List<TeamMembership> findByPlayer_IdOrderByActiveDescStartDateDesc(Long playerId);
}
