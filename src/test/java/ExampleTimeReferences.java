import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class ExampleTimeReferences implements Serializable {


    private static final long serialVersionUID = -6496075426619626270L;

    private Date date;

    private LocalDate localDate;

    private LocalDateTime localDateTime;

    private Calendar calendar;


    public ExampleTimeReferences() {
    }

    public ExampleTimeReferences(Date date, LocalDate localDate, LocalDateTime localDateTime, Calendar calendar) {
        this.date = date;
        this.localDate = localDate;
        this.localDateTime = localDateTime;
        this.calendar = calendar;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public LocalDate getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDate localDate) {
        this.localDate = localDate;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }
}
