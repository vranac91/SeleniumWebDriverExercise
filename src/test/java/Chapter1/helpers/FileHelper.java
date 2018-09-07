package Chapter1.helpers;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;

/**
 * @author by Dinko
 * Created at 07/09/2018
 */
public class FileHelper {
    //region HELPER METHODS


    /**
     * Method for creating new screen shot file.
     * It will check and generate missing directories.
     *
     * @param screenshot    File to be generated.
     * @return              TRUE if a file was generated, FALSE otherwise.
     */
    public static boolean createFile(@Nonnull File screenshot) {
        if (screenshot.exists()) {
            return true;
        } else {
            File parentDirectory = new File(screenshot.getParent());
            if (parentDirectory.exists() || parentDirectory.mkdirs()) {
                try {
                    return screenshot.createNewFile();
                } catch (IOException errorCreatingScreenshot) {
                    errorCreatingScreenshot.printStackTrace();
                }
            }
        }
        return false;
    }


    //endregion
}
