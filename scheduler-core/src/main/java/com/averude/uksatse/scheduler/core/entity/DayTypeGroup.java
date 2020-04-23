package com.averude.uksatse.scheduler.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

@Entity
@Table(
        name = "day_type_groups",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "day_type_groups_unique_constraint",
                        columnNames = {"name"}
                )
        }
)
public class DayTypeGroup implements HasId {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private String color;

    @JsonIgnore
    @OneToMany( mappedBy = "dayTypeGroup",
                fetch = FetchType.LAZY,
                cascade = CascadeType.ALL)
    private List<DayType> dayTypes = new LinkedList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<DayType> getDayTypes() {
        return dayTypes;
    }

    public void setDayTypes(List<DayType> dayTypes) {
        this.dayTypes = dayTypes;
    }

    public void addDayType(DayType dayType) {
        dayType.setDayTypeGroup(this);
        dayTypes.add(dayType);
    }

    public void removeDayType(DayType dayType) {
        dayType.setDayTypeGroup(null);
        dayTypes.remove(dayType);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DayTypeGroup that = (DayTypeGroup) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", DayTypeGroup.class.getSimpleName() + "{", "}")
                .add("id=" + id)
                .add("name='" + name + "'")
                .add("color='" + color + "'")
                .toString();
    }
}
