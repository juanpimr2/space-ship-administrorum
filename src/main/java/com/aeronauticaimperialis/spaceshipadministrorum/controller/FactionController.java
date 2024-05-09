package com.aeronauticaimperialis.spaceshipadministrorum.controller;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.aeronauticaimperialis.spaceshipadministrorum.dto.Faction;
import com.aeronauticaimperialis.spaceshipadministrorum.repository.FactionRepository;

@RestController
@RequestMapping("/factions")
public class FactionController {

    @Autowired
    private FactionRepository factionRepository;

    @GetMapping
    public List<Faction> getAllFactions() {
        return factionRepository.findAll();
    }

    @PostMapping
    public Faction createFaction(@RequestBody Faction faction) {
        return factionRepository.save(faction);
    }
}
