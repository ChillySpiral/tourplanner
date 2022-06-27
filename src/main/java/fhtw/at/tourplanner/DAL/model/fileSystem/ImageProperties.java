package fhtw.at.tourplanner.DAL.model.fileSystem;

import lombok.Getter;
import lombok.Setter;

public class ImageProperties {
    @Getter
    @Setter
    public String path;
    @Getter
    @Setter
    public int tourId;
    @Getter
    @Setter
    public String from;
    @Getter
    @Setter
    public String to;

    public String get() {
        return path;
    }
}
