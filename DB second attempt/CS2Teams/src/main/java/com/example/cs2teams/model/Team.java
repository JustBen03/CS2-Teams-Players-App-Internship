package com.example.cs2teams.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private String country;
    private Integer worldRank;

    // Coach ca text (conform admin.html)
    private String coachName;

    // URL către logo-ul echipei
    private String logoUrl;

    // Legătura cu membership-urile (jucătorii activi/inactivi din echipă)
    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TeamMembership> memberships = new ArrayList<>();

    // --- constructors ---
    public Team() {}

    // --- getters & setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public Integer getWorldRank() { return worldRank; }
    public void setWorldRank(Integer worldRank) { this.worldRank = worldRank; }

    public String getCoachName() { return coachName; }
    public void setCoachName(String coachName) { this.coachName = coachName; }

    public String getLogoUrl() { return logoUrl; }
    public void setLogoUrl(String logoUrl) { this.logoUrl = logoUrl; }

    public List<TeamMembership> getMemberships() { return memberships; }
    public void setMemberships(List<TeamMembership> memberships) { this.memberships = memberships; }
}
