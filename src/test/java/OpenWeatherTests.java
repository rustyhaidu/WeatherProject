import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import ro.mta.se.lab.model.weather.WeatherBody;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class OpenWeatherTests {

    @Test
    public void testBasicRequest(){
        ObjectMapper mapper = new ObjectMapper();
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://api.openweathermap.org/data/2.5/weather?q=Cluj-Napoca&appid=91b14ab8d3eb4985193f39010f433451")
                .build(); // defaults to GET

        Response response;
        try {
            response = client.newCall(request).execute();
            WeatherBody weatherBody = mapper.readValue(response.body().byteStream(), WeatherBody.class);


            assertEquals("Cluj-Napoca", weatherBody.getName());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
