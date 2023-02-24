package vttp2022.csf.assessment.server.repositories;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.aggregation.StringOperators;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import vttp2022.csf.assessment.server.models.Comment;
import vttp2022.csf.assessment.server.models.Restaurant;

@Repository
public class RestaurantRepository {

	public static final String CSF_COLL_RES= "restaurants";
	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	AmazonS3 s3client;
	// TODO Task 2
	// Use this method to retrive a list of cuisines from the restaurant collection
	// You can add any parameters (if any) and the return type 
	// DO NOT CHNAGE THE METHOD'S NAME
	// Write the Mongo native query above for this method
	//  db.restaurants.distinct("cuisine");
	public String getCuisines() {
		// Implmementation in here
		List<String> cuisines = mongoTemplate.findDistinct(new Query(), "cuisine", CSF_COLL_RES, String.class);
		JsonArrayBuilder jab = Json.createArrayBuilder();
		for(String cuisine: cuisines){
			jab.add(cuisine.replace("/", "_"));
		}
		JsonArray arr = jab.build();
		System.out.println(arr);
		return arr.toString();
	}

	// TODO Task 3
	// Use this method to retrive a all restaurants for a particular cuisine
	// You can add any parameters (if any) and the return type 
	// DO NOT CHNAGE THE METHOD'S NAME
	// Write the Mongo native query above for this method
	//  db.restaurants.find({"cuisine":"Asian"},{_id:0,name:1})
	public List<Document> getRestaurantsByCuisine(String cuisine) {
		// Implmementation in here
		Criteria c = Criteria.where("cuisine").is(cuisine);
		Query q = Query.query(c);
		q.fields().exclude("_id").include("name");
		return mongoTemplate.find(q, Document.class, CSF_COLL_RES);
	}

	// TODO Task 4
	// Use this method to find a specific restaurant
	// You can add any parameters (if any) 
	// DO NOT CHNAGE THE METHOD'S NAME OR THE RETURN TYPE
	// Write the Mongo native query above for this method
	// db.restaurants.aggregate([
	// 	{$match:{restaurant_id:"30112340"}},
	// 	{$project:{
	// 		restaurant_id:1, 
	// 		name:1, 
	// 		cuisine:1,
	// 		address:{$concat:["$address.building",", ","$address.street",", ","$address.zipcode",", ","$borough"]},
	// 		"coordinates":"$address.coord"
	// 	}}
	// ])
	
	public Optional<Restaurant> getRestaurant(String cuisine, Integer idx) {
		// Implmementation in here
		Criteria c = Criteria.where("cuisine").is(cuisine);
		Query q = Query.query(c);
		q.fields().exclude("_id").include("restaurant_id", "name");
		String res_id = mongoTemplate.find(q, Document.class, CSF_COLL_RES).get(idx).getString("restaurant_id");
		MatchOperation match = Aggregation.match(Criteria.where("restaurant_id").is(res_id));
		ProjectionOperation proj = Aggregation
                .project("restaurant_id", "name", "cuisine")
                .and("address.coord").as("coordinates")
                .and(StringOperators.Concat.valueOf("address.building").concat(", ").concatValueOf("address.street").concat(", ").concatValueOf("address.zipcode").concat(", ").concatValueOf("borough")).as("address")
                .andExclude("_id");
		Aggregation pipeline = Aggregation.newAggregation(match, proj);
		AggregationResults<Document> res =  mongoTemplate.aggregate(pipeline, CSF_COLL_RES, Document.class);
		// {{cuisine=Asian, name=China Grill, restaurant_id=40386481, 
			//coordinates=[-73.9787406, 40.7611474], address=51, West 52 Street, 10019, Manhattan}}52 Street, coord=[-73.9787406, 40.7611474], zipcode=10019}}
		System.out.println(res.getMappedResults().get(0));
		Document restaurantdoc = res.getMappedResults().get(0);
		String key = restaurantdoc.getString("restaurant_id");
		byte [] image = new byte[1];
		ObjectMetadata metadata = new ObjectMetadata();
		Restaurant restaurant = Restaurant.createRest(restaurantdoc);
		MapCache mapCache = new MapCache();
		try {
			image = mapCache.getMap(key);
		} catch (Exception ex ){
			RestTemplate restTemplate = new RestTemplate();
			byte[] imagebytes = restTemplate.getForObject("http://map.chuklee.com/map?lat={lat}&lng={lng}", byte[].class, restaurant.getCoordinates().getLatitude(), restaurant.getCoordinates().getLongitude());
			// Metadata of the file
			Map<String, String> userData = new HashMap<>();
  			userData.put("name", restaurant.getName());
  			metadata = new ObjectMetadata();
  			metadata.setUserMetadata(userData);
			metadata.setContentType("image/jpeg");
  			metadata.setContentLength(imagebytes.length);
			// Create a put request
			PutObjectRequest putReq = new PutObjectRequest(
				"qas275", // bucket name
				"%s.jpeg".formatted(key), //key
				new ByteArrayInputStream(imagebytes), //inputstream
				metadata);
	
			// Allow public access
			putReq.withCannedAcl(CannedAccessControlList.PublicRead);
			s3client.putObject(putReq);
		}
			// If key is not found
			// For S3ObjectInputStream
		return Optional.of(restaurant);
	}	


	
	// TODO Task 5
	// Use this method to insert a comment into the restaurant database
	// DO NOT CHNAGE THE METHOD'S NAME OR THE RETURN TYPE
	// Write the Mongo native query above for this method
	//  
	public void addComment(Comment comment) {
		// Implmementation in here
		Document doc = new Document();
		doc.append("name", comment.getName());
		doc.append("rating", comment.getRating());
		doc.append("restaurantId", comment.getRestaurantId());
		doc.append("text", comment.getText());
        Document res = mongoTemplate.insert(doc, CSF_COLL_RES);
	}
	
	// You may add other methods to this class

}
