package uddipak.cabbooking.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import uddipak.cabbooking.dtos.DriverDto;
import uddipak.cabbooking.models.Driver;
import uddipak.cabbooking.repositories.DriverRepository;
import uddipak.cabbooking.services.DriverService;

import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/driver")
class DriverController {
    private final DriverService driverService;

    private DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping
    private ResponseEntity<Void> createDriver(@RequestBody DriverDto newDriverRequestDto, UriComponentsBuilder ucb) {
        Driver savedDriver = driverService.convertAndSave(newDriverRequestDto);
        URI locationOfDriver = ucb
                .path("driver/{id}")
                .buildAndExpand(savedDriver.id())
                .toUri();
        return ResponseEntity.created(locationOfDriver).build();
    }

    @GetMapping("/{requestedId}")
    private ResponseEntity<DriverDto> findById(@PathVariable Long requestedId) {
        Optional<DriverDto> driver = driverService.findDriver(requestedId);
        return driver.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}