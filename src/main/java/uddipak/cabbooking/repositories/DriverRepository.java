package uddipak.cabbooking.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import uddipak.cabbooking.models.Driver;

public interface DriverRepository extends CrudRepository<Driver, Long>, PagingAndSortingRepository<Driver, Long> {
}
