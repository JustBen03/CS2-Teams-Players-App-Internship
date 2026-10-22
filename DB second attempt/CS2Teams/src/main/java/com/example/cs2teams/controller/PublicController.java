package com.example.cs2teams.controller;

import com.example.cs2teams.model.Player;
import com.example.cs2teams.model.TeamMembership;
import com.example.cs2teams.repository.TeamMembershipRepository;
import com.example.cs2teams.repository.TeamRepository;
import com.example.cs2teams.repository.PlayerRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class PublicController {
    private final TeamRepository teamRepo;
    private final PlayerRepository playerRepo;
    private final TeamMembershipRepository memRepo;

    public PublicController(TeamRepository teamRepo, PlayerRepository playerRepo, TeamMembershipRepository memRepo) {
        this.teamRepo = teamRepo;
        this.playerRepo = playerRepo;
        this.memRepo = memRepo;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("teams", teamRepo.findAll());
        return "index"; // public read-only page
    }

    // NEW: player profile page
    @GetMapping("/players/{id}")
    public String playerPage(@PathVariable Long id, Model model) {
        Player p = playerRepo.findById(id).orElseThrow();
        List<TeamMembership> history = memRepo.findByPlayer_IdOrderByActiveDescStartDateDesc(id);
        model.addAttribute("player", p);
        model.addAttribute("history", history);
        return "player";
    }
}
