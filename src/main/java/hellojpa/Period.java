package hellojpa;

import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Embeddable //임베디드 타입 인것을 알려줘야 함
public class Period {
    private LocalDateTime startDate;
    private LocalDateTime endDate;




    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}
