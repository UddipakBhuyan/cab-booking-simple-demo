package uddipak.cabbooking.dtos;

import org.springframework.data.annotation.Id;
import uddipak.cabbooking.enums.Gender;

public record DriverDto(@Id Long id, String name, Gender gender, Integer age, String vehicle, String location) {
    //vehicle can be separate entiyty?
}
