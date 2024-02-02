package uddipak.cabbooking.transformers;

import uddipak.cabbooking.dtos.DriverDto;
import uddipak.cabbooking.models.Driver;

import java.util.Arrays;

public class DriverTransformer {
    public Driver transform(DriverDto newDriverRequestDto) {
        int[] xy = finXY(newDriverRequestDto.location());
        return new Driver(newDriverRequestDto.id(), newDriverRequestDto.name(), newDriverRequestDto.gender(), newDriverRequestDto.age(), newDriverRequestDto.vehicle(), xy[0], xy[1]);
    }

    public DriverDto transform(Driver driver) {
        String location = "(" + driver.locationX() + ", " + driver.locationY() + ")";
        return new DriverDto(driver.id(), driver.name(), driver.gender(), driver.age(), driver.vehicle(), location);
    }
    private int[] finXY(String src) {
        return Arrays.stream(src.replaceAll("()", "").split(","))
                .mapToInt(Integer::parseInt)
                .toArray();
    }
}
