package uddipak.cabbooking.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import uddipak.cabbooking.models.AppUser;
import uddipak.cabbooking.repositories.AppUserRepository;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/booking")
class AppUserController {
    private final AppUserRepository appUserRepository;

    private AppUserController(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @PostMapping
    private ResponseEntity<Void> createUser(@RequestBody AppUser newAppUserRequest, UriComponentsBuilder ucb) {
        AppUser savedAppUser = appUserRepository.save(newAppUserRequest);
        URI locationOfNewUser = ucb
                .path("booking/{id}")
                .buildAndExpand(savedAppUser.id())
                .toUri();
        return ResponseEntity.created(locationOfNewUser).build();
    }

    @GetMapping("/{requestedId}")
    private ResponseEntity<AppUser> findById(@PathVariable Long requestedId) {
        Optional<AppUser> appUser = findAppUser(requestedId);
        return appUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    private Optional<AppUser> findAppUser(Long requestedId) {
        return appUserRepository.findById(requestedId);
    }
}