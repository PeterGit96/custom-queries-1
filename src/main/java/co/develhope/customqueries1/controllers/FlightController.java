package co.develhope.customqueries1.controllers;

import co.develhope.customqueries1.entities.Flight;
import co.develhope.customqueries1.entities.FlightStatusEnum;
import co.develhope.customqueries1.repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/flights")
public class FlightController {

    @Autowired
    private FlightRepository flightRepository;

    @PostMapping("/create")
    public ResponseEntity<String> createFlights() {

        List<Flight> flights = new ArrayList<>();

        for(int i = 0; i < 50; i++) {
            String description = generateRandomString(20, 50);
            String fromAirport = generateRandomString(8, 20);
            String toAirport = generateRandomString(8, 20);
            flights.add(new Flight(null, description, fromAirport, toAirport, FlightStatusEnum.ONTIME));
        }

        flightRepository.saveAll(flights);
        return ResponseEntity.status(HttpStatus.CREATED).body("Flights created successfully!");
    }

    @GetMapping
    public List<Flight> getFlights() {
        return flightRepository.findAll();
    }

    private String generateRandomString(int min, int max) {

        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        //int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(random.ints(min, (max + 1)).limit(1).findFirst().getAsInt())
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}