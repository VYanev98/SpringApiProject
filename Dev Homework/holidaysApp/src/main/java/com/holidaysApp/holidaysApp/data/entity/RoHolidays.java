package com.holidaysApp.holidaysApp.data.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.JSONArray;

import javax.persistence.*;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

@Table(name = "holidays")
public class RoHolidays {
    @Id
    private int id;

    private String url;
    private String countryName;
    private String nameText;
    private String onelinerText;
    private String dateIso;
    private String dateDateTimeYear;
    private String dateDateTimeMonth;
    private String dateDateTimeDay;
    private String types;


    @SuppressWarnings("unchecked")
    @JsonProperty("country")
    private void unpackCountryName(Map<String,Object> country) {
        this.countryName = (String)country.get("name");
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("name")
    private void unpackNameText(JSONArray name) {
        String text = name.get(0).toString();
        text = text.replace("{lang=en, text=", "");
        text = text.replace("}", "");
        this.nameText = text;
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("oneliner")
    private void unpackOnelinerText(JSONArray oneliner) {
        String text = oneliner.get(0).toString();
        text = text.replace("{lang=en, text=", "");
        text = text.replace("}", "");
        this.onelinerText = text;
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("date")
    private void unpackDates(Map<String,Object> date) {
        this.dateIso = (String)date.get("iso");
        Map<String,Integer> datetime = (Map<String,Integer>)date.get("datetime");
        this.dateDateTimeYear = datetime.get("year").toString();
        this.dateDateTimeMonth = datetime.get("month").toString();
        this.dateDateTimeDay = datetime.get("day").toString();
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("types")
    private void unpackTypes(JSONArray types) {
        String text = "";
        for (int i = 0; i < types.size(); i++) {
            text = text + types.get(i).toString() + ", ";
        }

        text = text.substring(0, text.length() - 2);

        this.types = text;
    }
}

