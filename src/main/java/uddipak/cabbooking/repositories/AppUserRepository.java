package uddipak.cabbooking.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import uddipak.cabbooking.models.AppUser;

public interface AppUserRepository extends CrudRepository<AppUser, Long>, PagingAndSortingRepository<AppUser, Long> {
}
