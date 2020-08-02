package com.averude.uksatse.scheduler.statistics.wrapper;

import com.averude.uksatse.scheduler.core.entity.WorkDay;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.StringJoiner;

public class WorkDayWrapper {
    @Getter
    private final WorkDay workDay;

    @Setter
    @Getter
    private boolean holiday;

    public WorkDayWrapper(WorkDay workDay, boolean holiday) {
        this.workDay = workDay;
        this.holiday = holiday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkDayWrapper that = (WorkDayWrapper) o;
        return this.hashCode() == that.hashCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(workDay.getDayTypeId(), workDay.getStartTime(), workDay.getEndTime(),
                workDay.getBreakStartTime(), workDay.getBreakEndTime(), holiday);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", WorkDayWrapper.class.getSimpleName() + "[", "]")
                .add("workDay=" + workDay)
                .toString();
    }
}
