package main.utils;

import main.model.City;
import main.model.weather.Coordinates;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Geography {
    private final Set<String> countries = new HashSet<>();
    private final List<City> cities = new ArrayList<>();
    private final Map<String, List<String>> geoCityMap = new HashMap<>();
    private final Map<String, List<City>> geoMap = new HashMap<>();

    public Geography() {
        createMap();
    }

    public Set<String> getCountries() {
        return countries;
    }

    public List<City> getCities() {
        return cities;
    }

    public Map<String, List<City>> getGeoMap() {
        return geoMap;
    }

    public Map<String, List<String>> getGeoCityMap() {
        return geoCityMap;
    }

    public void createMap() {
        JSONParser parser = new JSONParser();

        try {
            Object obj = parser.parse(new FileReader("src/main/resources/city.list.json"));

            JSONArray jsonArray = (JSONArray) obj;

            for (JSONObject jsonObject : (Iterable<JSONObject>) jsonArray) {
                String country = jsonObject.get("country").toString();
                countries.add(country);

                City city = new City();
                city.setId((int) Double.parseDouble(jsonObject.get("id").toString()));
                city.setName(jsonObject.get("name").toString());
                city.setState(jsonObject.get("state").toString());
                city.setCountry(country);

                JSONObject coord = (JSONObject) jsonObject.get("coord");
                Coordinates coordinates = new Coordinates();
                coordinates.setLat(Double.parseDouble(coord.get("lat").toString()));
                coordinates.setLon(Double.parseDouble(coord.get("lon").toString()));

                city.setCoord(coordinates);
                cities.add(city);

                if (!geoMap.containsKey(country)) {
                    geoMap.put(country, new ArrayList<>());
                    geoCityMap.put(country, new ArrayList<>());

                } else {
                    List<City> countryCities = geoMap.get(country);
                    countryCities.add(city);
                    List<String> cityStrings = geoCityMap.get(country);
                    cityStrings.add(city.getName());
                }

            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {


    }
}
