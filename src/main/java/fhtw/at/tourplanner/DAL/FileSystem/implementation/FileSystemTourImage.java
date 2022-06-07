package fhtw.at.tourplanner.DAL.FileSystem.implementation;

import fhtw.at.tourplanner.DAL.FileSystem.FileSystem;
import fhtw.at.tourplanner.DAL.model.TourModel;
import okhttp3.ResponseBody;

import java.io.*;

public class FileSystemTourImage implements FileSystem {
    @Override
    public boolean writeResponseBody(ResponseBody body, String path) {
        try {

            File file = new File(path);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public boolean deleteFile(TourModel tourModel) {
        //ToDo: Implement
        return false;
    }
}
