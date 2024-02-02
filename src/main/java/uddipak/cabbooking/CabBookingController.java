package uddipak.cabbooking;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/booking")
class CabBookingController {
    private final CabBookingRepository cabBookingRepository;

    private CabBookingController(CabBookingRepository cabBookingRepository) {
        this.cabBookingRepository = cabBookingRepository;
    }

    @PostMapping
    private ResponseEntity<Void> createUser(@RequestBody AppUser newAppUserRequest, UriComponentsBuilder ucb) {
        AppUser savedAppUser = cabBookingRepository.save(newAppUserRequest);
        URI locationOfNewUser = ucb
                .path("booking/{id}")
                .buildAndExpand(savedAppUser.id())
                .toUri();
        return ResponseEntity.created(locationOfNewUser).build();
    }

    @GetMapping("/{requestedId}")
    private ResponseEntity<AppUser> findById(@PathVariable Long requestedId) {
        Optional<AppUser> appUser = findCashCard(requestedId);
        return appUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    private Optional<AppUser> findCashCard(Long requestedId) {
        return cabBookingRepository.findById(requestedId);
    }
}