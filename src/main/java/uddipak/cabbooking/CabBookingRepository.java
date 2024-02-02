package uddipak.cabbooking;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

interface CabBookingRepository extends CrudRepository<AppUser, Long>, PagingAndSortingRepository<AppUser, Long> {
}
