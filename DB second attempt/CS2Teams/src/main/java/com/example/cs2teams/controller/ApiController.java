package com.example.cs2teams.controller;

import com.example.cs2teams.model.*;
import com.example.cs2teams.repository.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {
    private final TeamRepository teamRepo;
    private final PlayerRepository playerRepo;
    private final TeamMembershipRepository memRepo;

    public ApiController(TeamRepository teamRepo, PlayerRepository playerRepo, TeamMembershipRepository memRepo) {
        this.teamRepo = teamRepo; this.playerRepo = playerRepo; this.memRepo = memRepo;
    }

    @PostMapping("/teams")
    public Team createTeam(@RequestBody Team t) {
        return teamRepo.save(t);
    }

    @GetMapping("/teams")
    public List<Team> allTeams() {
        return teamRepo.findAll();
    }

    @PostMapping("/players")
    public Player createPlayer(@RequestBody Player p) {
        return playerRepo.save(p);
    }

    @GetMapping("/players")
    public List<Player> allPlayers() {
        return playerRepo.findAll();
    }

    @PostMapping("/teams/{teamId}/addPlayer/{playerId}")
    public TeamMembership addPlayer(@PathVariable Long teamId,
                                    @PathVariable Long playerId,
                                    @RequestParam(defaultValue = "false") boolean igl) {
        Team team = teamRepo.findById(teamId).orElseThrow();
        Player player = playerRepo.findById(playerId).orElseThrow();

        TeamMembership tm = new TeamMembership();
        tm.setTeam(team);
        tm.setPlayer(player);
        tm.setActive(true);
        tm.setIgl(igl);
        return memRepo.save(tm);
    }

    @GetMapping("/teams/{teamId}/roster")
    public List<TeamMembership> roster(@PathVariable Long teamId) {
        return memRepo.findActiveByTeamId(teamId);
    }
}
