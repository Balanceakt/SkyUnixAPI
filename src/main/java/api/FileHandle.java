package api;

import utils.FilePath;
import utils.FolderHandle;

public abstract class FileHandle {

    public FileHandle() {
        FolderHandle.folderCheck(FilePath.folderPath);
    }

}
