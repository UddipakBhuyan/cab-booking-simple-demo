package uddipak.cabbooking.services;

import org.springframework.stereotype.Service;
import uddipak.cabbooking.dtos.AppUserDto;
import uddipak.cabbooking.dtos.DriverDto;
import uddipak.cabbooking.models.AppUser;
import uddipak.cabbooking.models.Driver;
import uddipak.cabbooking.repositories.AppUserRepository;
import uddipak.cabbooking.repositories.DriverRepository;
import uddipak.cabbooking.transformers.AppUserTransformer;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class AppUserServices {
    private AppUserRepository appUserRepository;
    private DriverRepository driverRepository;
    private DriverService driverService;
    private AppUserTransformer appUserTransformer;
    public Optional<List<DriverDto>> findNearestDriver(String name, String src) {
        int[] xy = finXY(src);
        Integer srcX = xy[0], srcY = xy[1];

        Optional<List<Driver>> driverList = driverRepository.findByNameAndLocation(name, srcX, srcY);
        //TODO
        //sort driverList
        //transform driver to driverDto
        driverList.stream().map(drivers -> driverService.convert(drivers));
        return driverList;
    }

    private int[] finXY(String src) {
        return Arrays.stream(src.replaceAll("()", "").split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    public AppUser convertAndSave(AppUserDto newAppUserDtoRequest) {
        return appUserRepository.save(appUserTransformer.tranform(newAppUserDtoRequest));
    }
    public Optional<AppUserDto> findAppUser(Long requestedId) {
        Optional<AppUser> appUserDto = appUserRepository.findById(requestedId);
        return appUserDto.map(driver -> appUserTransformer.transform(driver));
    }
}
