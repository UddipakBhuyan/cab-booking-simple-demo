package uddipak.cabbooking.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import uddipak.cabbooking.entities.AppUser;

import java.util.Optional;

public interface AppUserRepository extends CrudRepository<AppUser, Long>, PagingAndSortingRepository<AppUser, Long> {
    Optional<AppUser> findByName(String username);
}
