package managers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by alikare4 on 30/11/16.
 */
public abstract class Manager {
  private String filePath;

  /**
   * Creates a new type of Manager whose information is stored in file filePath.
   *
   * @throws IOException if filePath cannot be opened/created.
   */
  public Manager(File file) throws IOException {
    filePath = file.getPath();
    if (file.exists()) {
      uploadInfo(filePath);
    } else {
      file.createNewFile();
    }
  }

  public abstract void uploadInfo(String path);

  /**
   * Writes our data to local file to preserve info upon relaunch.
   */
  public void writeOutToFile() {
    try {
      String content = toString();
      FileWriter writer = new FileWriter(filePath);
      BufferedWriter buffer = new BufferedWriter(writer);
      buffer.write(content);
      buffer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
