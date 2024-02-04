package uddipak.cabbooking.services;

import org.springframework.stereotype.Service;
import uddipak.cabbooking.dtos.DriverDto;
import uddipak.cabbooking.entities.Driver;
import uddipak.cabbooking.repositories.DriverRepository;
import uddipak.cabbooking.transformers.DriverTransformer;

import java.util.Optional;

@Service
public class DriverService {
    private final DriverRepository driverRepository;
    private final DriverTransformer driverTransformer;

    public DriverService(DriverRepository driverRepository, DriverTransformer driverTransformer) {
        this.driverRepository = driverRepository;
        this.driverTransformer = driverTransformer;
    }

    public Driver convert(DriverDto newDriverRequestDto) {
        return driverTransformer.transform(newDriverRequestDto);
    }

    public DriverDto convert(Driver newDriverRequestDto) {
        return driverTransformer.transform(newDriverRequestDto);
    }

    public Driver convertAndSave(DriverDto newDriverRequestDto) {
        return driverRepository.save(driverTransformer.transform(newDriverRequestDto));
    }

    public Optional<DriverDto> findDriver(Long requestedId) {
        Optional<Driver> driverDto = driverRepository.findById(requestedId);
        return driverDto.map(driverTransformer::transform);
    }
}
