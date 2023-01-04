package CompteAr.backend.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "saved_dates")
public class SavedDates {
    private Integer userId;
    private Date date;
    private String timeUnit;
    private String name;


    public SavedDates(Integer userId, Date date, String timeUnit, String name) {
        this.userId = userId;
        this.date = date;
        this.timeUnit = timeUnit;
        this.name = name;
    }

    public SavedDates() {
    }

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;
    public long getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Column(name = "date", nullable = false)
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
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

    public void setId(Integer userId) {this.userId = userId;}

    @Id
    public Integer getId() {
        return userId;
    }
}
