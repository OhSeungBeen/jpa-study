package step04.domain;

import java.time.LocalDateTime;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity {
    private LocalDateTime modifiedDate;
    private LocalDateTime registeredDate;
}
