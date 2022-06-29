package fhtw.at.tourplanner.BL.jsonGenerator;

import fhtw.at.tourplanner.DAL.model.export.exportTourModel;

import java.io.File;
import java.io.IOException;

public interface JsonGenerator {
     void writeJSON(File exportFile, exportTourModel exportTour) throws IOException;
     exportTourModel readJSON(File importFile) throws IOException;
}
