package com.example.cs2teams.controller;

import com.example.cs2teams.model.Player;
import com.example.cs2teams.model.Team;
import com.example.cs2teams.model.TeamMembership;
import com.example.cs2teams.repository.PlayerRepository;
import com.example.cs2teams.repository.TeamMembershipRepository;
import com.example.cs2teams.repository.TeamRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PageController {

    private final TeamRepository teamRepo;
    private final PlayerRepository playerRepo;
    private final TeamMembershipRepository memRepo;

    public PageController(TeamRepository teamRepo,
                          PlayerRepository playerRepo,
                          TeamMembershipRepository memRepo) {
        this.teamRepo = teamRepo;
        this.playerRepo = playerRepo;
        this.memRepo = memRepo;
    }

    // Admin page
    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("teams", teamRepo.findAll());
        model.addAttribute("team", new Team());
        model.addAttribute("player", new Player());
        return "admin";
    }

    // Create team
    @PostMapping("/teams/add")
    public String addTeam(@ModelAttribute Team team) {
        teamRepo.save(team);
        return "redirect:/admin";
    }

    // Create player + add to team (no stats)
    @PostMapping("/players/addToTeam")
    public String addPlayerToTeam(@RequestParam Long teamId,
                                  @RequestParam String nickname,
                                  @RequestParam(required = false) String fullName,
                                  @RequestParam(required = false) String country,
                                  @RequestParam(required = false) String role,
                                  @RequestParam(required = false) Integer age,
                                  @RequestParam(required = false) String photoUrl,
                                  @RequestParam(required = false) String countryCode,
                                  @RequestParam(defaultValue = "false") boolean igl) {

        Player p = new Player();
        p.setNickname(nickname);
        p.setFullName(fullName);
        p.setCountry(country);
        p.setRole(role);
        p.setAge(age);
        p.setPhotoUrl(photoUrl);
        p.setCountryCode(countryCode);
        p = playerRepo.save(p);

        Team team = teamRepo.findById(teamId).orElseThrow();
        TeamMembership tm = new TeamMembership();
        tm.setTeam(team);
        tm.setPlayer(p);
        tm.setActive(true);
        tm.setIgl(igl);
        memRepo.save(tm);

        return "redirect:/admin";
    }

    // Remove membership (and delete player if no other memberships remain)
    @PostMapping("/teams/{teamId}/remove/{membershipId}")
    public String remove(@PathVariable Long teamId,
                         @PathVariable Long membershipId) {
        TeamMembership tm = memRepo.findById(membershipId).orElseThrow();
        Player player = tm.getPlayer();

        memRepo.delete(tm);

        boolean playerStillReferenced = memRepo.existsByPlayer_Id(player.getId());
        if (!playerStillReferenced) {
            playerRepo.delete(player);
        }

        return "redirect:/admin";
    }
}
