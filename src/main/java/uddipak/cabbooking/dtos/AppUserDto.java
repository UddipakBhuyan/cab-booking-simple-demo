package uddipak.cabbooking.dtos;

import org.springframework.data.annotation.Id;
import uddipak.cabbooking.enums.Gender;

public record AppUserDto(@Id Long id, String name, Gender gender, Integer age) {
    //age could be stored as DOB if it's needed more than validation
}
