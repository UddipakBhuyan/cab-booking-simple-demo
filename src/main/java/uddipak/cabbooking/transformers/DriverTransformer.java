package uddipak.cabbooking.transformers;

import org.springframework.stereotype.Component;
import uddipak.cabbooking.dtos.DriverDto;
import uddipak.cabbooking.entities.Driver;

import java.util.Arrays;

@Component
public class DriverTransformer {
    public Driver transform(DriverDto newDriverRequestDto) {
        int[] xy = finXY(newDriverRequestDto.location());
        return new Driver(newDriverRequestDto.id(), newDriverRequestDto.name(), newDriverRequestDto.gender(), newDriverRequestDto.age(), newDriverRequestDto.vehicle(), xy[0], xy[1], null, null);
    }

    public DriverDto transform(Driver driver) {
        String location = "(" + driver.getLocationX() + ", " + driver.getLocationY() + ")";
        return new DriverDto(driver.getId(), driver.getName(), driver.getGender(), driver.getAge(), driver.getVehicle(), location);
    }

    private int[] finXY(String src) {
        return Arrays.stream(src.replaceAll("[()\\s]", "").split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
