package uddipak.cabbooking.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import uddipak.cabbooking.enums.TripStatus;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @JoinColumn
    @ManyToOne
    @JsonBackReference
    AppUser appUser;
    @JoinColumn
    @ManyToOne
    @JsonBackReference
    Driver driver;
    String source;
    String destination;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    TripStatus tripStatus;
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    LocalDateTime localDateTime;
}
