package fhtw.at.tourplanner.BL.jsonGenerator.implementation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import fhtw.at.tourplanner.BL.jsonGenerator.JsonGenerator;
import fhtw.at.tourplanner.DAL.model.export.exportTourModel;

import java.io.File;
import java.io.IOException;

public class JsonGeneratorImpl implements JsonGenerator {
    public void writeJSON(File exportFile, exportTourModel exportTour) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.writeValue(exportFile, exportTour);
    }

    public exportTourModel readJSON(File importFile) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        return mapper.readValue(importFile, exportTourModel.class);
    }
}
