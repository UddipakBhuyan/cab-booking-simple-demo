package uddipak.cabbooking.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import uddipak.cabbooking.entities.Driver;
import uddipak.cabbooking.enums.TripStatus;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends CrudRepository<Driver, Long>, PagingAndSortingRepository<Driver, Long> {
    Optional<List<Driver>> findByNameAndLocationXAndLocationY(String name, Integer locationX, Integer locationY);

    Optional<Driver> findByName(String driverName);

    @Query("SELECT d from Driver d LEFT JOIN FETCH d.trips t where t.tripStatus is null or t.tripStatus <> :status")
    Iterable<Driver> findAllDriversWithStatusNot(TripStatus status);
}
