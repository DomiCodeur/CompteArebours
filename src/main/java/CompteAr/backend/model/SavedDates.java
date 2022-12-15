package CompteAr.backend.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "saved_dates")
public class SavedDates {
    private long userId;
    private String date;
    private String timeUnit;
    private String name;
    private Long id;

    public SavedDates(long userId, String date, String timeUnit, String name) {
        this.userId = userId;
        this.date = date;
        this.timeUnit = timeUnit;
        this.name = name;
    }

    public SavedDates() {
    }

    @Column(name = "user_id", nullable = false)
    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }

    @Column(name = "date", nullable = false)
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }

    @Column(name = "time_unit", nullable = false)
    public String getTimeUnit() {
        return timeUnit;
    }
    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    public Long getId() {
        return id;
    }
}
