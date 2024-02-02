package uddipak.cabbooking.models;

import org.springframework.data.annotation.Id;
import uddipak.cabbooking.enums.Gender;

public record Driver(@Id Long id, String name, Gender gender, Integer age, String vehicle, Integer locationX, Integer locationY) {
    //vehicle can be separate entiyty?
}
