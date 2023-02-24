package vttp2022.csf.assessment.server.models;

import java.util.List;

import org.bson.Document;

// Do not modify this class
public class Restaurant {
	
	private String restaurantId;
	private String name;
	private String cuisine;
	private String address;
	private LatLng coordinates;
	private String mapUrl;

	public void setRestaurantId(String restaurantId) {
		this.restaurantId = restaurantId;
	}
	public String getRestaurantId() {
		return this.restaurantId;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name;
	}

	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}
	public String getCuisine() {
		return this.cuisine;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	public String getAddress() {
		return this.address;
	}

	public void setCoordinates(LatLng coordinates) {
		this.coordinates = coordinates;
	}
	public LatLng getCoordinates() {
		return this.coordinates;
	}

	public void setMapURL(String mapUrl) {
		this.mapUrl = mapUrl;
	}
	public String getMapURL() {
		return this.mapUrl;
	}

	// {{cuisine=Asian, name=China Grill, restaurant_id=40386481, 
		//coordinates=[-73.9787406, 40.7611474], address=51, West 52 Street, 10019, Manhattan}}52 Street, coord=[-73.9787406, 40.7611474], zipcode=10019}}
	public static Restaurant createRest(Document doc){
		System.out.println(doc);
		Restaurant res = new Restaurant();
		res.restaurantId = doc.getString("restaurant_id");
		List<Double> coord = (List<Double>) doc.get("coordinates");
		res.coordinates = LatLng.createCoord(coord);
		res.cuisine = doc.getString("cuisine");
		res.name = doc.getString("name");
		res.address = doc.getString("building") + doc.getString("street") + doc.getString("zipcode") + doc.getString("borough");
		res.mapUrl = "https://qas275.sgp1.digitaloceanspaces.com/todo%2F"+res.restaurantId;
		return res;
	}
}
