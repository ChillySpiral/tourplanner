package fhtw.at.tourplanner.DAL.model.fileSystem;

import fhtw.at.tourplanner.DAL.FileSystem.FileSystem;
import fhtw.at.tourplanner.DAL.FileSystem.implementation.ImageProperties;
import fhtw.at.tourplanner.DAL.helper.ConfigurationLoader;
import fhtw.at.tourplanner.DAL.model.TourModel;
import lombok.Getter;
import lombok.Setter;
import okhttp3.ResponseBody;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class FileSystemTourImage implements FileSystem {

    public final String path;

    public FileSystemTourImage(){
        this.path = ConfigurationLoader.getConfig("ImageFolder");
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
    public Pair<String, ImageProperties> findFile(TourModel tourModel) {
        File directory = new File(path);

        String[] fileNameList = directory.list();

        for (var image : fileNameList) {
            var tmpOutImage = getImageProperties(image);
            if(tourModel.getTourId() == tmpOutImage.getTourId()){
                var result = new Pair<>(image, tmpOutImage);
                return result;
            }
        }
        return null;
    }

    private ImageProperties getImageProperties(String fileName){
        var props = fileName.split("_");
        var result = new ImageProperties();
        result.setTourId(-1);
        try{
            result.setTourId(Integer.parseInt(props[1]));
            result.setFrom(props[3]);
            result.setTo(props[5]);
        } catch(Exception e){
            e.printStackTrace();
        }

        return result;
    }
}

