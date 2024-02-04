package uddipak.cabbooking.services;

import org.springframework.stereotype.Service;
import uddipak.cabbooking.dtos.AppUserDto;
import uddipak.cabbooking.dtos.DriverDto;
import uddipak.cabbooking.enums.TripStatus;
import uddipak.cabbooking.entities.AppUser;
import uddipak.cabbooking.entities.Driver;
import uddipak.cabbooking.entities.Trip;
import uddipak.cabbooking.repositories.AppUserRepository;
import uddipak.cabbooking.repositories.DriverRepository;
import uddipak.cabbooking.repositories.TripRepository;
import uddipak.cabbooking.transformers.AppUserTransformer;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AppUserServices {
    private final AppUserRepository appUserRepository;
    private final DriverRepository driverRepository;
    private final TripRepository tripRepository;
    private final DriverService driverService;
    private final AppUserTransformer appUserTransformer;

    public AppUserServices(AppUserRepository appUserRepository, DriverRepository driverRepository, TripRepository tripRepository, DriverService driverService, AppUserTransformer appUserTransformer) {
        this.appUserRepository = appUserRepository;
        this.driverRepository = driverRepository;
        this.tripRepository = tripRepository;
        this.driverService = driverService;
        this.appUserTransformer = appUserTransformer;
    }

    public Optional<List<DriverDto>> findNearestDriver(String username, String src) {
        int[] xy = finXY(src);
        Integer srcX = xy[0], srcY = xy[1];
        AppUser existingUser = appUserRepository.findByName(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with name:" + username));

        Iterable<Driver> driverList = driverRepository.findAllDriversWithStatusNot(TripStatus.ONGOING);
//                findByNameAndLocationXAndLocationY(name, srcX, srcY);
        return Optional.of(StreamSupport.stream(driverList.spliterator(), true)
                .map(driver -> new AbstractMap.SimpleEntry<>(driver, calculateDistance(driver.getLocationX(), driver.getLocationY(), srcX, srcY)))
                .filter(entry -> entry.getValue() <= 5)
                .sorted(Comparator.comparingDouble(AbstractMap.SimpleEntry::getValue))
                .map(AbstractMap.SimpleEntry::getKey)
                .map(driverService::convert)
                .collect(Collectors.toList()));
    }

    private int[] finXY(String src) {
        // TODO: use @JsonView instead of this
        return Arrays.stream(src.replaceAll("[()\\s]", "").split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    public AppUser convertAndSave(AppUserDto newAppUserDtoRequest) {
        return appUserRepository.save(appUserTransformer.transform(newAppUserDtoRequest));
    }

    public Optional<AppUserDto> findAppUser(Long requestedId) {
        Optional<AppUser> appUserDto = appUserRepository.findById(requestedId);
        return appUserDto.map(appUserTransformer::transform);
    }

    public double calculateDistance(Integer x1, Integer y1, Integer x2, Integer y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public void bookDriver(String username, String driverName) {
        AppUser existingUser = appUserRepository.findByName(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with name:" + username));
        Driver existingDriver = driverRepository.findByName(driverName)
                .orElseThrow(() -> new IllegalArgumentException("Driver not found with id: " + driverName));

        Trip trip = new Trip(null, existingUser, existingDriver, "some place", "some other place", TripStatus.ONGOING, null);


        // TODO: add finally or something for exception handling
        tripRepository.save(trip);
//        appUserRepository.save(existingUser);
//        driverRepository.save(existingDriver);
    }
}