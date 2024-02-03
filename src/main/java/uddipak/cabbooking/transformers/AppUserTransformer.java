package uddipak.cabbooking.transformers;

import org.springframework.stereotype.Component;
import uddipak.cabbooking.dtos.AppUserDto;
import uddipak.cabbooking.models.AppUser;

@Component
public class AppUserTransformer {
    public AppUser tranform(AppUserDto newAppUserDtoRequest) {
        return new AppUser(newAppUserDtoRequest.id(), newAppUserDtoRequest.name(), newAppUserDtoRequest.gender(), newAppUserDtoRequest.age());
    }

    public AppUserDto transform(AppUser appUser) {
        return new AppUserDto(appUser.id(), appUser.name(), appUser.gender(), appUser.age());
    }
}
