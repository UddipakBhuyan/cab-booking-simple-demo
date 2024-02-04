package uddipak.cabbooking.repositories;

import org.springframework.data.repository.CrudRepository;
import uddipak.cabbooking.entities.Trip;

public interface TripRepository extends CrudRepository<Trip, Long> {
}
