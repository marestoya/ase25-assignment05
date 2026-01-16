package de.unibayreuth.se.taskboard;

import de.unibayreuth.se.taskboard.api.dtos.TaskDto;
import de.unibayreuth.se.taskboard.api.mapper.TaskDtoMapper;
import de.unibayreuth.se.taskboard.business.domain.Task;
import io.restassured.http.ContentType;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;

import de.unibayreuth.se.taskboard.api.dtos.UserDto;
import de.unibayreuth.se.taskboard.business.domain.User;
import de.unibayreuth.se.taskboard.api.mapper.UserDtoMapper;
import de.unibayreuth.se.taskboard.business.ports.UserService;
import java.util.UUID;
import java.util.stream.Collectors;



public class TaskBoardSystemTests extends AbstractSystemTest {
        
    @Autowired
    private TaskDtoMapper taskDtoMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDtoMapper userDtoMapper;


    @BeforeEach
        void clean() {
                userService.clear();
                taskService.clear();
        }

    @Test
    void getAllCreatedTasks() {
        List<Task> createdTasks = TestFixtures.createTasks(taskService);

        List<Task> retrievedTasks = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/tasks")
                .then()
                .statusCode(200)
                .body(".", hasSize(createdTasks.size()))
                .and()
                .extract().jsonPath().getList("$", TaskDto.class)
                .stream()
                .map(taskDtoMapper::toBusiness)
                .toList();

        assertThat(retrievedTasks)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields("createdAt", "updatedAt") // prevent issues due to differing timestamps after conversions
                .containsExactlyInAnyOrderElementsOf(createdTasks);
    }

    @Test
    void createAndDeleteTask() {
        Task createdTask = taskService.create(
                TestFixtures.getTasks().getFirst()
        );

        when()
                .get("/api/tasks/{id}", createdTask.getId())
                .then()
                .statusCode(200);

        when()
                .delete("/api/tasks/{id}", createdTask.getId())
                .then()
                .statusCode(200);

        when()
                .get("/api/tasks/{id}", createdTask.getId())
                .then()
                .statusCode(400);

    }

    //TODO: Add at least one test for each new endpoint in the users controller (the create endpoint can be tested as part of the other endpoints).

    @Test
    void getAllCreatedUsers() {
        
        List<User> createdUsers = TestFixtures.createUsers(userService);

        List<UserDto> createdUserDtos = createdUsers.stream()
                .map(userDtoMapper::fromBusiness)
                .collect(Collectors.toList());

        List<UserDto> retrievedUsers = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/users")
                .then()
                .statusCode(200)
                .body(".", hasSize(createdUserDtos.size()))
                .extract()
                .jsonPath()
                .getList("$", UserDto.class);

        assertThat(retrievedUsers)
                .usingRecursiveFieldByFieldElementComparatorIgnoringFields( "createdAt", "updatedAt")
                .containsExactlyInAnyOrderElementsOf(createdUserDtos);
    }


    @Test
    void getUserById() {
        User createdUser = userService.createUser(
                TestFixtures.getUsers().getFirst()
        );

        UserDto retrievedUser = given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/users/{id}", createdUser.getId())
                .then()
                .statusCode(200)
                .extract()
                .as(UserDto.class);

        assertThat(retrievedUser)
                .usingRecursiveComparison()
                .ignoringFields("id", "updatedAt", "createdAt") 
                .isEqualTo(createdUser);
    }

    @Test
    void createAndDeleteUser() {

        User createdUser = userService.createUser(
                TestFixtures.getUsers().getFirst()
        );
        when()
                .get("/api/users/{id}", createdUser.getId())
                .then()
                .statusCode(200);

        when()
                .delete("/api/users/{id}", createdUser.getId())
                .then()
                .statusCode(204); 

        when()
                .get("/api/users/{id}", createdUser.getId())
                .then()
                .statusCode(404); 
    }


    @Test
    void getUserById_NotFound() {

        UUID randomId = UUID.randomUUID();

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/users/{id}", randomId)
                .then()
                .statusCode(404);
    }
}