package controller;

import controllers.PatternUnitController;
import entity.PatternUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import service.PatternUnitService;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/patterns/{patternId}/units")
public class PatternUnitControllerImpl implements PatternUnitController {

    private final PatternUnitService patternUnitService;

    @Autowired
    PatternUnitControllerImpl(PatternUnitService patternUnitService) {
        this.patternUnitService = patternUnitService;
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public Iterable<PatternUnit> getAll(@RequestHeader("Department-ID") long departmentId,
                                        @PathVariable long patternId) {
        return this.patternUnitService.findAllByPatternIdOrderByOrderId(patternId);
    }

    @Override
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestHeader("Department-ID") long departmentId,
                                    @PathVariable long patternId,
                                    @RequestBody PatternUnit unit) {
        if (patternId == unit.getPatternId()) {
            this.patternUnitService.save(unit);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest().path("/{id}")
                    .buildAndExpand(unit.getId()).toUri();
            return ResponseEntity.created(location).body(unit.getId());
        } else {
            return ResponseEntity.unprocessableEntity()
                    .body("URI's ID doesn't match to Entity's ID");
        }

    }

    @Override
    @RequestMapping(method = RequestMethod.GET,
                    value = "{unitId}")
    public Optional<PatternUnit> get(@RequestHeader("Department-ID") long departmentId,
                                     @PathVariable long patternId,
                                     @PathVariable long unitId) {
        return this.patternUnitService.findById(unitId);
    }

    @Override
    @RequestMapping(method = RequestMethod.PUT,
                    value = "{unitId}")
    public ResponseEntity<?> update(@RequestHeader("Department-ID") long departmentId,
                                    @PathVariable long patternId,
                                    @PathVariable long unitId,
                                    @RequestBody PatternUnit unit) {
        if (unitId == unit.getId() && patternId == unit.getPatternId()) {
            this.patternUnitService.save(unit);
            return ResponseEntity.ok("Unit with ID:" + unitId +
                    " was successfully updated");
        } else {
            return ResponseEntity.unprocessableEntity()
                    .body("URI's ID doesn't match to Entity's ID");
        }
    }

    @Override
    @RequestMapping(method = RequestMethod.DELETE,
                    value = "{unitId}")
    public ResponseEntity<?> delete(@RequestHeader("Department-ID") long departmentId,
                                    @PathVariable long patternId,
                                    @PathVariable long unitId) {
        this.patternUnitService.deleteById(unitId);
        return new ResponseEntity<>("Unit with ID:" + unitId +
                " was successfully deleted", HttpStatus.NO_CONTENT);
    }
}
