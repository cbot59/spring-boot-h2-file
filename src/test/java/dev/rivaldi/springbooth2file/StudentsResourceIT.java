package dev.rivaldi.springbooth2file;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.core.TypeReferences;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class StudentsResourceIT {

    @LocalServerPort
    private int port;

    @Autowired
    private RestOperations restOperations;

    private static String selfHref;

    @Test
    @Order(1)
    void postStudents() {
        var request = RequestEntity.post(URI.create("http://localhost:%d/students".formatted(port)))
            .accept(MediaTypes.HAL_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .body(Map.of("name", "Rivaldi", "age", 10));
        var rootLinks = restOperations.exchange(request, new TypeReferences.EntityModelType<>() {
        }).getBody();

        selfHref = Optional.ofNullable(rootLinks).map(em -> em.getRequiredLink("self"))
            .map(Link::getHref)
            .orElse("");

        assertThat(selfHref.isEmpty()).isFalse();
    }

    @Test
    @Order(2)
    void getStudentById() {
        var request = RequestEntity.get(URI.create(selfHref))
            .accept(MediaTypes.HAL_JSON)
            .build();
        var rootLinks = restOperations.exchange(request, new TypeReferences.EntityModelType<Student>() {
        }).getBody();

        var actual = Optional.ofNullable(rootLinks).map(EntityModel::getContent).orElseGet(Student::new);

        assertThat(actual).matches(student -> "Rivaldi".equals(student.getName()))
            .matches(student -> 10 == student.getAge());
    }

    @SpringBootApplication
    static class Config {
        @Bean
        RestTemplate restTemplate() {
            return new RestTemplate();
        }
    }
}
