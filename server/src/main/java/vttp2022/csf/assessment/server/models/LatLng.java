package vttp2022.csf.assessment.server.models;

import java.util.List;

// Do not modify this class
public class LatLng {
	private float latitude;
	private float longitude;

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public float getLatitude() {
		return this.latitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public float getLongitude() {
		return this.longitude;
	}
	public static LatLng createCoord(List<Double> coords) {
		System.out.println();
		LatLng latLng = new LatLng();
		latLng.latitude = Float.parseFloat(Double.toString(coords.get(1)));
		latLng.longitude = Float.parseFloat(Double.toString(coords.get(0)));
		return latLng;
	}
}
