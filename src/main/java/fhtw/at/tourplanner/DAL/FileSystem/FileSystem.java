package fhtw.at.tourplanner.DAL.FileSystem;

import fhtw.at.tourplanner.DAL.model.TourModel;
import okhttp3.ResponseBody;

public interface FileSystem {
    boolean writeResponseBody(ResponseBody body, String path);
    boolean deleteFile(TourModel tourModel);
}
