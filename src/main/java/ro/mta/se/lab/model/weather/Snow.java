package ro.mta.se.lab.model.weather;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.org.glassfish.gmbal.ManagedObject;

public class Snow {
    @JsonProperty("1h")
    private double oneHour;

       public double getOneHour() {
        return oneHour;
    }

    public void setOneHour(double oneHour) {
        this.oneHour = oneHour;
    }

    public Snow(double oneHour) {
        this.oneHour = oneHour;
    }
}
