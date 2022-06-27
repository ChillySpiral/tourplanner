package fhtw.at.tourplanner.DAL.FileSystem;

import fhtw.at.tourplanner.DAL.model.fileSystem.ImageProperties;
import fhtw.at.tourplanner.DAL.model.TourModel;
import fhtw.at.tourplanner.DAL.model.fileSystem.Pair;
import okhttp3.ResponseBody;

public interface FileSystem {
    boolean writeResponseBody(ResponseBody body, String imageFileName);
    boolean deleteFile(TourModel tourModel);
    Pair<String, ImageProperties> findFile(TourModel tourModel);
}
