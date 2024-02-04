package uddipak.cabbooking.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import uddipak.cabbooking.dtos.AppUserDto;
import uddipak.cabbooking.dtos.DriverDto;
import uddipak.cabbooking.entities.AppUser;
import uddipak.cabbooking.services.AppUserServices;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/booking")
class AppUserController {
    private static final Logger logger = LoggerFactory.getLogger(AppUserController.class);
    private final AppUserServices appUserServices;

    private AppUserController(AppUserServices appUserServices) {
        this.appUserServices = appUserServices;
    }

    @PostMapping
    private ResponseEntity<Void> createUser(@RequestBody AppUserDto newAppUserDtoRequest, UriComponentsBuilder ucb) {
        AppUser savedAppUser = appUserServices.convertAndSave(newAppUserDtoRequest);
        URI locationOfNewUser = ucb
                .path("booking/{id}")
                .buildAndExpand(savedAppUser.getId())
                .toUri();
        return ResponseEntity.created(locationOfNewUser).build();
    }

    @GetMapping("/{requestedId}")
    private ResponseEntity<AppUserDto> findById(@PathVariable Long requestedId) {
        Optional<AppUserDto> appUser = appUserServices.findAppUser(requestedId);
        return appUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/findDriver/{name}/{source}/{destination}")
    private ResponseEntity<?> findDriver(@PathVariable String name, @PathVariable String source, @PathVariable String destination) {
        List<DriverDto> nearestDriverList = appUserServices.findNearestDriver(name, source);
        if (nearestDriverList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No drivers near you");
        }
        return ResponseEntity.ok(nearestDriverList);
    }

    @GetMapping("/bookDriver/{username}/{driverName}")
    private ResponseEntity<String> bookDriver(@PathVariable String username, @PathVariable String driverName) {
        try {
            appUserServices.bookDriver(username, driverName);
            return ResponseEntity.ok("Trip Booked");
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}