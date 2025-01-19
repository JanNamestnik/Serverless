package si.um.feri.serverless.utils;

public class Geolocation {
    public double lat;
    public double lng;

    private String name;

    private String address;
    private String startTime;
    private String contact;

    private String startDate;

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

    public String getStartTime() {
        return startTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getContact() {
        return contact;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setDateStart(String dateStart) {
        this.startDate = dateStart;
    }
}
