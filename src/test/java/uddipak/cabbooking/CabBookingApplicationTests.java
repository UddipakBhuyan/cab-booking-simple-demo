package uddipak.cabbooking;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.logging.Logger;
import org.junit.platform.commons.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uddipak.cabbooking.dtos.AppUserDto;
import uddipak.cabbooking.dtos.DriverDto;
import uddipak.cabbooking.enums.Gender;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CabBookingApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(CabBookingApplicationTests.class);

    @Test
    void shouldCreateANewAppUser() {
        AppUserDto newAppUser = new AppUserDto(null, "Abhishek", Gender.MALE, 23);
        ResponseEntity<Void> createResponse = restTemplate.postForEntity("/booking", newAppUser, Void.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        URI locationOfNewAppUser = createResponse.getHeaders().getLocation();
        ResponseEntity<String> getResponse = restTemplate.getForEntity(locationOfNewAppUser, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldReturnAppUserWhenDataIsSaved() {
        ResponseEntity<String> response = restTemplate.getForEntity("/booking/99", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Number id = documentContext.read("$.id");
        assertThat(id).isEqualTo(99);

        String name = documentContext.read("$.name");
        assertThat(name).isEqualTo("Kavya");

        Integer age = documentContext.read("$.age");
        assertThat(age).isEqualTo(44);

        String gender = documentContext.read("$.gender");
        assertThat(gender).isEqualTo("FEMALE");
    }

    @Test
    void shouldNotReturnAppUserWithAnUnknownId() {
        ResponseEntity<String> response = restTemplate.getForEntity("/booking/1000", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isBlank();
    }

    @Test
    void shouldCreateADriver() {
        DriverDto newDriver = new DriverDto(null, "Driver1", Gender.MALE, 22, "Swift, KA-01-12345", "(10, 1)");
        ResponseEntity<Void> createResponse = restTemplate.postForEntity("/driver", newDriver, Void.class);
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        URI locationOfNewDriver = createResponse.getHeaders().getLocation();
        ResponseEntity<String> getResponse = restTemplate.getForEntity(locationOfNewDriver, String.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void shouldReturnDriverWhenDataIsSaved() {
        ResponseEntity<String> response = restTemplate.getForEntity("/driver/44", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        DocumentContext documentContext = JsonPath.parse(response.getBody());
        Number id = documentContext.read("$.id");
        assertThat(id).isEqualTo(44);

        String name = documentContext.read("$.name");
        assertThat(name).isEqualTo("Mahesh");

        Integer age = documentContext.read("$.age");
        assertThat(age).isEqualTo(34);

        String gender = documentContext.read("$.gender");
        assertThat(gender).isEqualTo("MALE");

        String vehicleDetails = documentContext.read("$.vehicle");
        assertThat(vehicleDetails).isEqualTo("Swift, KA-01-12345");

        String location = documentContext.read("$.location");
        assertThat(location).isEqualTo("(12, 1)");
    }

    @Test
    void shouldNotReturnDriverWithAnUnknownId() {
        ResponseEntity<String> response = restTemplate.getForEntity("/driver/1000", String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isBlank();
    }

    @Test
    void shouldReturnListOfDriversSortedByDistance() {
//        ResponseEntity<String> response = restTemplate.getForEntity("/booking/findDriver/Abhishek/%280%2C0%29/%2820%2C1%29", String.class);
        ResponseEntity<String> response = restTemplate.getForEntity("/booking/findDriver/Abhishek/(10,0)/(20,1)", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        logger.info(response::getBody);
    }
    //TODO: not all test cases covered
}