package dao;

import entity.Schedule;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;

@Repository
public class ScheduleDAOImpl extends AbstractDAO<Schedule> implements ScheduleDAO {

    @Autowired
    public ScheduleDAOImpl(SessionFactory sessionFactory) {
        super(Schedule.class);
        this.setSessionFactory(sessionFactory);
    }

    @Override
    public Collection<Schedule> getMonth(long employeeId,
                                         LocalDate date) {
        return getCurrentSession()
                .createQuery("SELECT s FROM Schedule s " +
                        "where s.employeeId = :employeeId " +
                        "and month(s.date) = :month " +
                        "and year(s.date) = :year", Schedule.class)
                .setParameter("employeeId", employeeId)
                .setParameter("month", date.getMonthValue())
                .setParameter("year", date.getYear())
                .getResultList();
    }

    @Override
    public Collection<Schedule> getByDate(long employeeId,
                                          LocalDate from,
                                          LocalDate to) {
        return getCurrentSession()
                .createQuery("SELECT s FROM Schedule s " +
                        "where s.employeeId = :employeeId " +
                        "and s.date >= :from " +
                        "and s.date <= :to ", Schedule.class)
                .setParameter("employeeId", employeeId)
                .setParameter("from", from)
                .setParameter("to", to)
                .getResultList();
    }
}
