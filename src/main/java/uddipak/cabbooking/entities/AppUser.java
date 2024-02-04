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
public class AppUser {
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
    //age could be stored as DOB if it's needed more than validation
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    LocalDateTime localDateTime;
    @OneToMany(mappedBy = "appUser")
    List<Trip> trips;
}
