package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    @Size(  max = 20,
            min = 3,
            message = "{}")
    @Column(nullable = false)
    private String firstName;

    @NotNull
    @Size(  max = 20,
            min = 3,
            message = "{}")
    @Column(nullable = false)
    private String secondName;

    @NotNull(message = "{}")
    @Column(name = "position_id",
            nullable = false)
    private long positionId;

    @Column(name = "shift_id",
            nullable = true)
    private Long shiftId;

    @OneToMany( mappedBy = "employeeId",
                cascade = CascadeType.ALL,
                fetch = FetchType.EAGER,
                orphanRemoval = true)
    private List<@NotNull @Valid Schedule> scheduleList = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public long getPositionId() {
        return positionId;
    }

    public void setPositionId(long positionId) {
        this.positionId = positionId;
    }

    public Long getShiftId() {
        return shiftId;
    }

    public void setShiftId(Long shiftId) {
        this.shiftId = shiftId;
    }

    @JsonIgnore // Don't know why, but field annotation doesn't work.
    public List<Schedule> getSchedule() {
        return scheduleList;
    }

    public void setSchedule(List<Schedule> scheduleList) {
        this.scheduleList = scheduleList;
    }

    public void addSchedule(Schedule schedule){
        schedule.setEmployeeId(this.getId());
        scheduleList.add(schedule);
    }

    public void removeSchedule(Schedule schedule){
        schedule.setEmployeeId(0);
        scheduleList.remove(schedule);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id &&
                Objects.equals(firstName, employee.firstName) &&
                Objects.equals(secondName, employee.secondName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, secondName);
    }
}
