package core.util;

import com.google.gson.annotations.SerializedName;

/**
 * @author Arthur Kupriyanov
 */
public class PersonData {
    @SerializedName("name")
    private String name;
    @SerializedName("height")
    private int height;
    @SerializedName("class")
    private String classOf;
    @SerializedName("img")
    private String img;
    @SerializedName("video")
    private String video;

    public String getName() {
        return name;
    }

    int getHeight() {
        return height;
    }

    String getClassOf() {
        return classOf;
    }

    String getImg() {
        return img;
    }

    String getVideo() {
        return video;
    }

    @Override
    public String toString() {
        return "PersonData{" +
                "name='" + name + '\'' +
                ", width=" + height +
                ", classOf='" + classOf + '\'' +
                ", img='" + img + '\'' +
                ", video='" + video + '\'' +
                '}';
    }
}
