package utils;

import java.io.File;
import java.io.IOException;

/**
 * Created by My on 2016/6/17.
 */
public class FileUtils {
    public static  File createFile(String filePath) {
        File f = new File(filePath);
        if (!f.exists()) {
            f.getParentFile().mkdirs();
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  f;
    };

}
