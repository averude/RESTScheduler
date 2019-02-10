package controller;

import controllers.ShiftController;
import entity.Shift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import service.ShiftService;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/shifts")
public class ShiftControllerImpl implements ShiftController {

    private final ShiftService shiftService;

    @Autowired
    public ShiftControllerImpl(ShiftService shiftService) {
        this.shiftService = shiftService;
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Shift> getAll(@RequestHeader("Department-ID") long departmentId){
        return shiftService.findAllByDepartmentId(departmentId);
    }

    @Override
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Long> create(@RequestHeader("Department-ID") long departmentId,
                                       @Valid @RequestBody Shift shift){
        shiftService.save(shift);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(shift.getId()).toUri();
        return ResponseEntity.created(location).body(shift.getId());
    }

    @Override
    @RequestMapping(method = RequestMethod.GET,
                    value = "/{shiftId}")
    public Optional<Shift> get(@RequestHeader("Department-ID") long departmentId,
                               @PathVariable long shiftId){
        return shiftService.findById(shiftId);
    }

    @Override
    @RequestMapping(method = RequestMethod.DELETE,
                    value = "/{shiftId}")
    public ResponseEntity<?> delete(@RequestHeader("Department-ID") long departmentId,
                                    @PathVariable long shiftId){
        shiftService.deleteById(shiftId);
        return ResponseEntity.ok("Deleted " + shiftId);
    }

    @Override
    @RequestMapping(method = RequestMethod.PUT,
                    value = "/{shiftId}")
    public ResponseEntity<?> update(@RequestHeader("Department-ID") long departmentId,
                                    @PathVariable long shiftId,
                                    @Valid @RequestBody Shift shift){
        if (shiftId == shift.getId()) {
            shiftService.save(shift);
            return ResponseEntity.ok("Updated " + shiftId);
        } else {
            return ResponseEntity.unprocessableEntity()
                    .body("URI's ID doesn't match to Entity's ID");
        }

    }
}
