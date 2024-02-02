package uddipak.cabbooking.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import uddipak.cabbooking.models.Driver;
import uddipak.cabbooking.repositories.DriverRepository;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/driver")
class DriverController {
    private final DriverRepository driverRepository;

    private DriverController(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @PostMapping
    private ResponseEntity<Void> createDriver(@RequestBody Driver newDriverRequest, UriComponentsBuilder ucb) {
        Driver savedDriver = driverRepository.save(newDriverRequest);
        URI locationOfDriver = ucb
                .path("driver/{id}")
                .buildAndExpand(savedDriver.id())
                .toUri();
        return ResponseEntity.created(locationOfDriver).build();
    }

    @GetMapping("/{requestedId}")
    private ResponseEntity<Driver> findById(@PathVariable Long requestedId) {
        Optional<Driver> driver = findDriver(requestedId);
        return driver.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    private Optional<Driver> findDriver(Long requestedId) {
        return driverRepository.findById(requestedId);
    }
}