package com.example.cs2teams.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class TeamMembership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    private Team team;

    @ManyToOne(optional = false)
    private Player player;

    private LocalDate startDate = LocalDate.now();
    private LocalDate endDate;   // null = still active
    private boolean active = true;
    private boolean igl;         // is this player the IGL?

    // getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Team getTeam() { return team; }
    public void setTeam(Team team) { this.team = team; }

    public Player getPlayer() { return player; }
    public void setPlayer(Player player) { this.player = player; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public boolean isIgl() { return igl; }
    public void setIgl(boolean igl) { this.igl = igl; }
}
