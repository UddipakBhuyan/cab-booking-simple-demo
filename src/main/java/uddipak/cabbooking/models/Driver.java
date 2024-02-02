package uddipak.cabbooking.models;

import org.springframework.data.annotation.Id;
import uddipak.cabbooking.enums.Gender;

import java.util.List;

public record Driver(@Id Long id, String name, Gender gender, Integer age, String vehicle, List<Integer> location) {
    //vehicle can be separate entiyty?
}
