package pl.agh.edu.kis.sp2.sim.util;

public class LocalizationDistanceUtility {
	public static Double distance(double lat1, double lon1, Double lat2, Double lon2, char unit) {
		if (lat2 == null || lon2 == null) {
			return null;
		}
		if ((lat1 == lat2) && (lon1 == lon2)) {
			return (double) 0;
		}
		else {
			double theta = lon1 - lon2;
			double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
			dist = Math.acos(dist);
			dist = Math.toDegrees(dist);
			dist = dist * 60 * 1.1515;
			if (unit == 'K') {
				dist = dist * 1.609344;
			} else if (unit == 'N') {
				dist = dist * 0.8684;
			}
			return (dist);
		}
	}
}
