package uddipak.cabbooking.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import uddipak.cabbooking.enums.Gender;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(nullable = false)
    String name;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Gender gender;
    @Column(nullable = false)
    Integer age;
    @Column(nullable = false)
    String vehicle;
    //vehicle can be separate entiyty?
    Integer locationX;
    Integer locationY;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    LocalDateTime localDateTime;
    @OneToMany(mappedBy = "driver")
    List<Trip> trips;
}
