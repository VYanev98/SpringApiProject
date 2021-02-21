package com.holidaysApp.holidaysApp.controller;

import com.google.gson.Gson;
import com.holidaysApp.holidaysApp.data.entity.RoHolidays;
import com.holidaysApp.holidaysApp.services.RoHolidaysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class RoHolidaysController {
    @Autowired
    private RoHolidaysService roHolidaysService;

    @GetMapping(value="/")
    public String getHolidays(Model model) {
        roHolidaysService.ApiConnection();
        model.addAttribute("holidaysList", roHolidaysService.getHolidays());
        return "index";
    }

    @GetMapping(value = "/exportCSV")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=RoHolidaysFor2021_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        List<RoHolidays> listHolidays = roHolidaysService.getHolidays();
        try {
            ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
            String[] csvHeader = {"ID", "URL", "Country name", "Holiday name", "Holiday description",
                    "Holiday date", "Holiday year", "Holiday month", "Holiday day", "Holiday Type"};
            String[] nameMapping = {"id", "url", "countryName", "nameText", "onelinerText",
                    "dateIso", "dateDateTimeYear", "dateDateTimeMonth", "dateDateTimeDay", "types"};

            csvWriter.writeHeader(csvHeader);

            for (RoHolidays holidays : listHolidays) {
                csvWriter.write(holidays, nameMapping);
            }

            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @GetMapping(value = "/exportJSON")
    public void exportToJSON() throws IOException{
        Gson gson = new Gson();
        DateFormat dateFormatter = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String currentDateTime = dateFormatter.format(new Date());
        try (FileWriter writer = new FileWriter("JSONExport\\JSONExport_" + currentDateTime + ".json")) {
            gson.toJson(roHolidaysService.getHolidays(), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
