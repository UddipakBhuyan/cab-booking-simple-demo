package uddipak.cabbooking.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import uddipak.cabbooking.models.Driver;

import java.util.List;
import java.util.Optional;

public interface DriverRepository extends CrudRepository<Driver, Long>, PagingAndSortingRepository<Driver, Long> {
    Optional<List<Driver>> findByNameAndLocation(String name, Integer locationX, Integer locationY);
}
