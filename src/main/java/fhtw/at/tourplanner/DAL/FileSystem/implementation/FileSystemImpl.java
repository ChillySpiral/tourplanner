package fhtw.at.tourplanner.DAL.FileSystem.implementation;

import fhtw.at.tourplanner.Configuration.AppConfiguration;
import fhtw.at.tourplanner.DAL.FileSystem.FileSystem;
import fhtw.at.tourplanner.DAL.model.TourModel;
import okhttp3.ResponseBody;

import java.io.*;

public class FileSystemImpl implements FileSystem {

    public final String path;

    public FileSystemImpl(AppConfiguration appConfiguration){
        this.path = appConfiguration.getImageFolder();
    }
    @Override
    public boolean writeResponseBody(ResponseBody body, String imageFileName) {
        try {

            File file = new File(path + imageFileName);

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

    @Override
    public String findFile(TourModel tourModel) {
        File directory = new File(path);

        String[] fileNameList = directory.list();

        if(fileNameList == null)
            return null;

        for (var image : fileNameList) {
            var tmpOutTourId = getTourIdFromImage(image);
            if(tourModel.getTourId() == tmpOutTourId){
                return image;
            }
        }
        return null;
    }

    private int getTourIdFromImage(String fileName){
        var props = fileName.split("_");
        try{
            var tourId = Integer.parseInt(props[1]);
            return tourId;
        } catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }
}

