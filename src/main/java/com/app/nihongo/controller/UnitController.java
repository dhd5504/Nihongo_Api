package com.app.nihongo.controller;

import com.app.nihongo.dto.UnitDTO;
import com.app.nihongo.service.unit.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/units")
public class UnitController {

    @Autowired
    private UnitService unitService;

    @GetMapping
    public ResponseEntity<List<UnitDTO>> getAllUnits(@RequestParam Integer userId) {
        return unitService.getAllUnitsWithStatus(userId);
    }
    @GetMapping("/by-level")
    public ResponseEntity<List<UnitDTO>> getUnitsByLevel(@RequestParam String level) {
        return unitService.getUnitsByLevel(level);
    }
    @GetMapping("/{unitId}")
    public ResponseEntity<UnitDTO> getUnitById(@PathVariable Integer unitId) {
        return unitService.getUnitById(unitId);
    }
}
