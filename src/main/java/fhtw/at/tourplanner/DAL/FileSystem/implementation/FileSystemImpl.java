package fhtw.at.tourplanner.DAL.FileSystem.implementation;

import fhtw.at.tourplanner.Configuration.AppConfiguration;
import fhtw.at.tourplanner.DAL.FileSystem.FileSystem;
import fhtw.at.tourplanner.DAL.model.TourModel;
import lombok.extern.log4j.Log4j2;
import okhttp3.ResponseBody;

import java.io.*;

@Log4j2
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
                log.error("Writing image to system failed [ error:"+e.getMessage()+ "]");
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
            log.error("Writing image to system failed [ error:"+e.getMessage()+ "]");
            return false;
        }
    }

    @Override
    public boolean deleteFile(TourModel tourModel) {
        var fileName = findFile(tourModel);
        if(fileName == null)
            return false;
        var file = new File(path + fileName);
        file.delete();
        return true;
    }

    @Override
    public String findFile(TourModel tourModel) {
        File directory = new File(path);

        String[] fileNameList = directory.list();

        if(fileNameList == null) {
            log.info("No images found on system");
            return null;
        }
        for (var image : fileNameList) {
            var tmpOutTourId = getTourIdFromImage(image);
            if(tourModel.getTourId() == tmpOutTourId){
                return image;
            }
        }
        log.info("Image for tour [ id: "+tourModel.getTourId()+"] not found");
        return null;
    }

    private int getTourIdFromImage(String fileName){
        var props = fileName.split("_");
        try{
            var tourId = Integer.parseInt(props[1]);
            return tourId;
        } catch(Exception e){
            log.error("Getting TourId from Image [ file: "+fileName+"] failed [ error: "+e.getMessage()+" ]");
            return -1;
        }
    }
}

