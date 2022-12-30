package fact.it.utopia_edgeservice.controller;

import fact.it.utopia_edgeservice.model.Question;
import fact.it.utopia_edgeservice.model.Station;
import fact.it.utopia_edgeservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class EdgeServiceController {

    String http = "http://";

    @Autowired
    private RestTemplate restTemplate;
    @Value("${userservice.baseurl}")
    private String userServiceBaseUrl;
    @Value("${gameservice.baseurl}")
    private String gameServiceBaseUrl;

    /* --- USER REQUESTS --- */
    @GetMapping("/users")
    public List<User> getAllUsers(){
        ResponseEntity<List<User>> responseEntityUsers =
                restTemplate.exchange(http + userServiceBaseUrl + "/users",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
                        });
        return responseEntityUsers.getBody();
    }
    @GetMapping("users/{userID}")
    public User getUserById(@PathVariable int userID){
        return restTemplate.getForObject(http + userServiceBaseUrl + "/users/{userID}",
                User.class, userID);
    }

    // CREATE NEW USER
    @PostMapping("user")
    public User createNewUser(@RequestParam String username, @RequestParam Integer birthyear, @RequestParam String interest){
        /*Get user count*/
        int userID = getAllUsers().toArray().length + 1;

        /* Create new user*/
        User u = new User(userID, username, interest, birthyear);

        return restTemplate.postForObject(http + userServiceBaseUrl + "/user",
                u, User.class);
    }

    // UPDATE USER
    @PutMapping("user")
    public ResponseEntity<Void> updateScore(@RequestBody User u ){

        restTemplate.exchange(http + userServiceBaseUrl + "/user" ,
                HttpMethod.PUT, new HttpEntity<>(u), User.class);

        return ResponseEntity.ok().build();
    }

    /* --- HIGHSCORES --- */
    @GetMapping("/highscores")
    public List<User> getHighscores(){
        ResponseEntity<List<User>> responseEntityUsers =
                restTemplate.exchange(http + userServiceBaseUrl + "/highscores",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
                        });
        return responseEntityUsers.getBody();
    }

    /* --- GAME DATA --- */
    @GetMapping("/stations")
    public List<Station> getAllStation(){
        ResponseEntity<List<Station>> responseEntityStations =
                restTemplate.exchange(http + gameServiceBaseUrl + "/stations",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Station>>() {
                        });
        return responseEntityStations.getBody();
    }

    @GetMapping("station/{stationID}")
    public Station getStationById(@PathVariable int stationID){
        return restTemplate.getForObject(http + gameServiceBaseUrl + "/station/{stationID}",
                Station.class, stationID);
    }

    @GetMapping("/questions/{stationID}")
    public List<Question> getAllQuestions(@PathVariable int stationID){
        ResponseEntity<List<Question>> responseEntityQuestions =
                restTemplate.exchange(http + gameServiceBaseUrl + "/questions/{stationID}",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Question>>() {
                        }, stationID);
       return responseEntityQuestions.getBody();
    }





}
