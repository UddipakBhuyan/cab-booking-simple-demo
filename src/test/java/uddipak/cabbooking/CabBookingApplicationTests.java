package uddipak.cabbooking;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uddipak.cabbooking.enums.Gender;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CabBookingApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void shouldCreateANewAppUser() {
        AppUser newAppUser = new AppUser(null, "Abhishek", Gender.MALE, 23);
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
}