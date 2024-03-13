package api;

import utils.FilePath;
import utils.FolderHandle;

/**
 * An abstract class for handling files.
 */
public abstract class FileHandle {

    /**
     * Constructs a new FileHandle object.
     * <p>
     * This constructor checks if the folder specified by {@link FilePath#folderPath} exists.
     */
    public FileHandle() {
        FolderHandle.folderCheck(FilePath.folderPath);
    }

}
