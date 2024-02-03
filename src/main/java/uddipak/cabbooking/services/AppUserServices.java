package uddipak.cabbooking.services;

import org.springframework.stereotype.Service;
import uddipak.cabbooking.dtos.AppUserDto;
import uddipak.cabbooking.dtos.DriverDto;
import uddipak.cabbooking.models.AppUser;
import uddipak.cabbooking.models.Driver;
import uddipak.cabbooking.repositories.AppUserRepository;
import uddipak.cabbooking.repositories.DriverRepository;
import uddipak.cabbooking.transformers.AppUserTransformer;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AppUserServices {
    private final AppUserRepository appUserRepository;
    private final DriverRepository driverRepository;
    private final DriverService driverService;
    private final AppUserTransformer appUserTransformer;

    public AppUserServices(AppUserRepository appUserRepository, DriverRepository driverRepository, DriverService driverService, AppUserTransformer appUserTransformer) {
        this.appUserRepository = appUserRepository;
        this.driverRepository = driverRepository;
        this.driverService = driverService;
        this.appUserTransformer = appUserTransformer;
    }

    public Optional<List<DriverDto>> findNearestDriver(String name, String src) {
        int[] xy = finXY(src);
        Integer srcX = xy[0], srcY = xy[1];

        Iterable<Driver> driverList = driverRepository.findAll();
//                findByNameAndLocationXAndLocationY(name, srcX, srcY);
        return Optional.of(StreamSupport.stream(driverList.spliterator(), true)
                .map(driver -> new AbstractMap.SimpleEntry<>(driver, calculateDistance(driver.locationX(), driver.locationY(), srcX, srcY)))
                .filter(entry -> entry.getValue() <= 5)
                .sorted(Comparator.comparingDouble(AbstractMap.SimpleEntry::getValue))
                .map(AbstractMap.SimpleEntry::getKey)
                .map(driverService::convert)
                .collect(Collectors.toList()));
    }

    private int[] finXY(String src) {
        return Arrays.stream(src.replaceAll("[()\\s]", "").split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    public AppUser convertAndSave(AppUserDto newAppUserDtoRequest) {
        return appUserRepository.save(appUserTransformer.tranform(newAppUserDtoRequest));
    }

    public Optional<AppUserDto> findAppUser(Long requestedId) {
        Optional<AppUser> appUserDto = appUserRepository.findById(requestedId);
        return appUserDto.map(appUserTransformer::transform);
    }

    public double calculateDistance(Integer x1, Integer y1, Integer x2, Integer y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}
