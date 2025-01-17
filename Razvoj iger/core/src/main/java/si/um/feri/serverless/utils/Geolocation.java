package si.um.feri.serverless.utils;

public class Geolocation {
    public double lat;
    public double lng;

    private String name;

    public Geolocation(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "Latitude: " + lat + ", Longitude: " + lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
