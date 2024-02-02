package uddipak.cabbooking.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import uddipak.cabbooking.dtos.AppUserDto;
import uddipak.cabbooking.dtos.DriverDto;
import uddipak.cabbooking.models.AppUser;
import uddipak.cabbooking.repositories.AppUserRepository;
import uddipak.cabbooking.services.AppUserServices;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/booking")
class AppUserController {
    private final AppUserServices appUserServices;

    private AppUserController(AppUserServices appUserServices) {
        this.appUserServices = appUserServices;
    }

    @PostMapping
    private ResponseEntity<Void> createUser(@RequestBody AppUserDto newAppUserDtoRequest, UriComponentsBuilder ucb) {
        AppUser savedAppUser = appUserServices.convertAndSave(newAppUserDtoRequest);
        URI locationOfNewUser = ucb
                .path("booking/{id}")
                .buildAndExpand(savedAppUser.id())
                .toUri();
        return ResponseEntity.created(locationOfNewUser).build();
    }

    @GetMapping("/{requestedId}")
    private ResponseEntity<AppUserDto> findById(@PathVariable Long requestedId) {
        Optional<AppUserDto> appUser = appUserServices.findAppUser(requestedId);
        return appUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/findDriver/{name}/{source}/{destination}")
    private ResponseEntity<List<DriverDto>> findDriver(@PathVariable String name, @PathVariable String source, @PathVariable String destination) {
        Optional<List<DriverDto>> nearestDriverList = appUserServices.findNearestDriver(name, source);
        return nearestDriverList.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}