package fact.it.utopia_edgeservice.controller;

import fact.it.utopia_edgeservice.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
public class EdgeServiceController {

    String http = "http://";

    @Autowired
    private RestTemplate restTemplate;
    @Value("${userservice.baseurl}")
    private String userServiceBaseUrl;
    @Value("${gameservice.baseurl}")
    private String gameServiceBaseUrl;
    @Value("${analytic-service.baseurl}")
    private String analyticserviceBaseUrl;

    /* --- USER REQUESTS --- */
        @CrossOrigin
    @GetMapping("/users")
    public List<User> getAllUsers(){
        ResponseEntity<List<User>> responseEntityUsers =
                restTemplate.exchange(http + userServiceBaseUrl + "/users",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
                        });
        return responseEntityUsers.getBody();
    }
    
        @CrossOrigin
    @GetMapping("users/{userID}")
    public User getUserById(@PathVariable int userID){
        return restTemplate.getForObject(http + userServiceBaseUrl + "/users/{userID}",
                User.class, userID);
    }

    // CREATE NEW USER
         @CrossOrigin
    @PostMapping("user")
    public User createNewUser(@RequestBody User u) {
        int userID = getAllUsers().toArray().length + 1;

        User user = new User(userID, u.getName(), u.getInterestID(), u.getBirthyear());

        return restTemplate.postForObject(http + userServiceBaseUrl + "/user", user, User.class);
    }

    // UPDATE USER
         @CrossOrigin
    @PutMapping("user")
    public ResponseEntity<Void> updateScore(@RequestBody User u){

        restTemplate.exchange(http + userServiceBaseUrl + "/user" ,
                HttpMethod.PUT, new HttpEntity<>(u), User.class);

        return ResponseEntity.ok().build();
    }

    /* --- HIGHSCORES --- */
    @CrossOrigin
    @GetMapping("/highscores")
    public List<User> getHighscores(){
        ResponseEntity<List<User>> responseEntityUsers =
                restTemplate.exchange(http + userServiceBaseUrl + "/highscores",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
                        });
        return responseEntityUsers.getBody();
    }

    /* --- GAME DATA --- */
        @CrossOrigin
    @GetMapping("/stations")
    public List<Station> getAllStation(){
        ResponseEntity<List<Station>> responseEntityStations =
                restTemplate.exchange(http + gameServiceBaseUrl + "/stations",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Station>>() {
                        });
        return responseEntityStations.getBody();
    }
    @CrossOrigin
    @GetMapping("station/{stationID}")
    public Station getStationById(@PathVariable int stationID){
        return restTemplate.getForObject(http + gameServiceBaseUrl + "/station/{stationID}",
                Station.class, stationID);
    }
    @CrossOrigin
    @GetMapping("/questions/{stationID}")
    public List<Question> getAllQuestions(@PathVariable int stationID){
        ResponseEntity<List<Question>> responseEntityQuestions =
                restTemplate.exchange(http + gameServiceBaseUrl + "/questions/{stationID}",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Question>>() {
                        }, stationID);
       return responseEntityQuestions.getBody();
    }
    @CrossOrigin
    @GetMapping("/question/{questionID}")
    public Question getQuestion(@PathVariable int questionID){
        return restTemplate.getForObject(http + gameServiceBaseUrl + "/question/{questionID}",
                Question.class, questionID);
    }
    @CrossOrigin
    @GetMapping("/interests")
    public List<Interest> getAllInterests(){
        ResponseEntity<List<Interest>> responseEntityStations =
                restTemplate.exchange(http + gameServiceBaseUrl + "/interests",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Interest>>() {
                        });
        return responseEntityStations.getBody();
    }
    @CrossOrigin
    @GetMapping("/interests/{interestID}")
    public Interest getInterestById(@PathVariable int interestID){
        return restTemplate.getForObject(http + gameServiceBaseUrl + "/interests/{interestID}",
                Interest.class, interestID);
    }
    @CrossOrigin
    @GetMapping("/startstation/{interestID}")
    public Station getStartStation(@PathVariable int interestID){
        Interest interestArea = getInterestById(interestID);
        List<Station> stations = interestArea.getStations();
        Random rnd = new Random();
        return stations.get(rnd.nextInt(stations.size()));
    }
    @CrossOrigin
    @GetMapping("/visits")
    public List<Visit> getAllVisits() {
        ResponseEntity<List<Visit>> responseEntityVisits = restTemplate.exchange(http + analyticserviceBaseUrl + "/visits",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Visit>>(){});
        return responseEntityVisits.getBody();
    }
    @CrossOrigin
    @GetMapping("/visits/{stationID}")
    public List<Visit> getVisitsForStation(@PathVariable int stationID) {
        ResponseEntity<List<Visit>> responseEntityVisits =
                restTemplate.exchange(http + analyticserviceBaseUrl + "/visits/{stationID}",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Visit>>() {
                        }, stationID);
        return responseEntityVisits.getBody();
    }
    @CrossOrigin
    @GetMapping("/visits/date/{date}")
    public List<Visit> getVisitForDate(@PathVariable String date) {
        ResponseEntity<List<Visit>> responseEntityVisits =
                restTemplate.exchange(http + analyticserviceBaseUrl + "/visits/date/{date}",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Visit>>() {
                        }, date);
        return responseEntityVisits.getBody();
    }
    @CrossOrigin
    @GetMapping("/visits/date/{date}/station/{stationID}")
    public List<Visit> getVisitForDateAndStation(@PathVariable String date, @PathVariable int stationID) {
        ResponseEntity<List<Visit>> responseEntityVisits =
                restTemplate.exchange(http + analyticserviceBaseUrl + "/visits/date/{date}/station/{stationID}",
                        HttpMethod.GET, null, new ParameterizedTypeReference<List<Visit>>() {
                        },date, stationID);
        return responseEntityVisits.getBody();
    }
    @CrossOrigin
    @GetMapping("/visits/totalPerStation")
    public List<Visit> getTotalPerStation() {
        ResponseEntity<List<Visit>> responseEntityVisits = restTemplate.exchange(http + analyticserviceBaseUrl + "/visits/totalPerStation",
                HttpMethod.GET, null, new ParameterizedTypeReference<List<Visit>>(){});
        return responseEntityVisits.getBody();
    }
     @CrossOrigin
    @PutMapping("/visit")
    public ResponseEntity<Void> updateScore(@RequestParam Integer stationID, @RequestParam Integer interestID) {
        VisitDTO v = new VisitDTO(stationID, interestID);
        restTemplate.exchange(http + analyticserviceBaseUrl + "/visit",
                HttpMethod.PUT, new HttpEntity<>(v), VisitDTO.class);

        return ResponseEntity.ok().build();
    }
}
