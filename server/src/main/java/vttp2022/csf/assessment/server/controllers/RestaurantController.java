package vttp2022.csf.assessment.server.controllers;

import java.io.StringReader;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import vttp2022.csf.assessment.server.models.Comment;
import vttp2022.csf.assessment.server.models.Restaurant;
import vttp2022.csf.assessment.server.services.RestaurantService;

@Controller
@RequestMapping(path = "/api")
public class RestaurantController {
    
    @Autowired
    RestaurantService rSvc;

    @GetMapping(path = "/cuisines")
    @ResponseBody
    public ResponseEntity<String> getCuisinse(){
        System.out.println("Retrieving cuisines");
        return ResponseEntity.status(HttpStatus.OK).body(rSvc.getCuisines());
    }

    @GetMapping(path = "/{cuisine}/restaurants")
    @ResponseBody
    public ResponseEntity<String> getRestaurants(@PathVariable String cuisine){
        System.out.println(cuisine);
        String res = rSvc.getRestaurantsByCuisine(cuisine);
        System.out.println(res);
        return ResponseEntity.status(HttpStatus.OK).body(res);
    }

    @GetMapping(path = "/{cuisine}/restaurants/{idx}")
    @ResponseBody
    public ResponseEntity<String> getRestaurants(@PathVariable String cuisine, @PathVariable Integer idx){
        Optional<Restaurant> resopt = rSvc.getRestaurant(cuisine, idx);
        Restaurant res =resopt.get(); 
        JsonObjectBuilder job = Json.createObjectBuilder();
        job.add("restaurant_id", res.getRestaurantId());
        job.add("name", res.getName());
        job.add("cuisine", res.getCuisine());
        job.add("address", res.getAddress());
        JsonArrayBuilder jab = Json.createArrayBuilder();
        jab.add(res.getCoordinates().getLatitude());
        jab.add(res.getCoordinates().getLongitude());
        job.add("coordinates", jab.build().toString());
        return ResponseEntity.status(HttpStatus.OK).body(job.build().toString());
    }

    @PostMapping(path = "/comments")
    @ResponseBody
    public ResponseEntity<String> postComments(@RequestBody String body){
        JsonObject formJSON = Json.createReader(new StringReader(body)).readObject();
        System.out.println(formJSON.toString());
        Comment comment = Comment.createComment(formJSON);
        rSvc.addComment(comment);
        JsonObject jo = Json.createObjectBuilder().add("message", "Comment posted").build();
        return ResponseEntity.status(HttpStatus.OK).body(jo.toString());
    }

}
