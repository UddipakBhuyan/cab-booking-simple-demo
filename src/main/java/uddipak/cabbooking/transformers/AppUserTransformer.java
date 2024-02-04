package uddipak.cabbooking.transformers;

import org.springframework.stereotype.Component;
import uddipak.cabbooking.dtos.AppUserDto;
import uddipak.cabbooking.entities.AppUser;

@Component
public class AppUserTransformer {
    public AppUser transform(AppUserDto newAppUserDtoRequest) {
        return new AppUser(newAppUserDtoRequest.id(), newAppUserDtoRequest.name(), newAppUserDtoRequest.gender(), newAppUserDtoRequest.age(), null, null);
    }

    public AppUserDto transform(AppUser appUser) {
        return new AppUserDto(appUser.getId(), appUser.getName(), appUser.getGender(), appUser.getAge());
    }
}
