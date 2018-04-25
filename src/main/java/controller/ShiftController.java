package controller;

import entity.Employee;
import entity.Shift;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import service.ShiftService;

import java.net.URI;
import java.util.Collection;

@RestController
@RequestMapping("/api/v1/shifts")
public class ShiftController extends AbstractController<Shift> {

    private final ShiftService shiftService;

    @Autowired
    public ShiftController(ShiftService shiftService) {
        super(shiftService);
        this.shiftService = shiftService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Shift> getAll(){
        return shiftService.getAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> add(@RequestBody Shift shift){
        shiftService.create(shift);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(shift.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @RequestMapping(method = RequestMethod.GET,
                    value = "/{shiftId}")
    public Shift get(@PathVariable long shiftId){
        return shiftService.getById(shiftId);
    }

    @RequestMapping(method = RequestMethod.DELETE,
                    value = "/{shiftId}")
    public ResponseEntity<?> delete(@PathVariable long shiftId){
        this.validate(shiftId);
        shiftService.deleteById(shiftId);
        return ResponseEntity.ok("Deleted " + shiftId);
    }

    @RequestMapping(method = RequestMethod.PUT,
                    value = "/{shiftId}")
    public ResponseEntity<?> update(@PathVariable long shiftId,
                                    @RequestBody Shift shift){
        this.validate(shiftId);
        shiftService.updateById(shiftId, shift);
        return ResponseEntity.ok("Updated " + shiftId);
    }

    @RequestMapping(method = RequestMethod.GET,
                    value = "/{shiftId}/employees")
    public Collection<Employee> getEmployees(@PathVariable long shiftId){
        this.validate(shiftId);
        return shiftService.getById(shiftId).getEmployees();
    }
}
