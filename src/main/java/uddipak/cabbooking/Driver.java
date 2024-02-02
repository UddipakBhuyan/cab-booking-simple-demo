package uddipak.cabbooking;

import org.springframework.data.annotation.Id;
import uddipak.cabbooking.enums.Gender;

import java.util.List;

record Driver(@Id Long id, String name, Gender gender, Integer age, String vehicleNo, List<Integer> location, String owner
) {
}
