package com.holidaysApp.holidaysApp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.holidaysApp.holidaysApp.data.entity.RoHolidays;
import com.holidaysApp.holidaysApp.data.repository.RoHolidaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class RoHolidaysService {
    @Autowired
    private RoHolidaysRepository roHolidaysRepository;
    private static String SresponseBody;

    public List<RoHolidays> getHolidays() {
        return roHolidaysRepository.findAll();
    }

    public void ApiConnection() {
        String jsonURL = "https://api.xmltime.com/holidays?accesskey=jz5uPha8BQ&" +
                "expires=2021-02-19T13%3A47%3A09%2B00%3A00&signature=y%2B2UIxcKy4t1ssUiwF3MzyKOqlA%3D&version=3&prettyprint=1&country=ro&year=2021";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(jsonURL)).build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(RoHolidaysService::GetResponseBody)
                .join();

        ParseJSON(SresponseBody);

    }

    public static void GetResponseBody(String JSONResponseBody) {
        SresponseBody = JSONResponseBody;
    }

    public void ParseJSON(String responseBody) {
        String finalJSON = responseBody.replace("{\n" +
                "\t\"version\": 3,\n" +
                "\t\"billing\": {\n" +
                "\t\t\"credits\": 1\n" +
                "\t},\n" +
                "\t\"holidays\": ", "");

        finalJSON = finalJSON.substring(0, finalJSON.length() - 2);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            List<RoHolidays> listHolidays = objectMapper.readValue(finalJSON, new TypeReference<List<RoHolidays>>(){});
            for (int i = 0; i < listHolidays.size(); i++) {
                SaveObjectToDatabase(listHolidays.get(i));
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void SaveObjectToDatabase(RoHolidays obj) {
        roHolidaysRepository.save(obj);
    }
}
