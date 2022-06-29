package fhtw.at.tourplanner.DAL.FileSystem.implementation;

import fhtw.at.tourplanner.DAL.FileSystem.FileSystem;
import fhtw.at.tourplanner.DAL.helper.ConfigurationLoader;
import fhtw.at.tourplanner.DAL.model.TourModel;
import fhtw.at.tourplanner.DAL.model.fileSystem.ImageProperties;
import fhtw.at.tourplanner.DAL.model.fileSystem.Pair;
import okhttp3.ResponseBody;

import java.io.*;

public class FileSystemImpl implements FileSystem {

    public final String path;

    public FileSystemImpl(){
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

        if(fileNameList == null)
            return null;

        for (var image : fileNameList) {
            var tmpOutImage = getImageProperties(image);
            if(tourModel.getTourId() == tmpOutImage.getTourId()){
                return new Pair<>(image, tmpOutImage);
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
        } catch(Exception e){
            e.printStackTrace();
        }

        return result;
    }
}

