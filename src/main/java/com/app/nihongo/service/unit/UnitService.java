package com.app.nihongo.service.unit;

import com.app.nihongo.dto.UnitDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UnitService {
    ResponseEntity<List<UnitDTO>> getAllUnits();

    ResponseEntity<List<UnitDTO>> getUnitsByLevel(String level);

    ResponseEntity<UnitDTO> getUnitById(Integer unitId);

    ResponseEntity<List<UnitDTO>> getAllUnitsWithStatus(Integer userId);


    boolean userLessonStatusExist(Integer userId);
}
