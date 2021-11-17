package Services;

import java.io.IOException;
import java.net.Socket;
import java.nio.file.Path;

public interface ReadAndWrite {

    Path getFilePath (String path);

     String guessContentType(Path filePath) throws IOException;


    void sendResponse (Socket clientSocket, String status, String contentType, byte[] content) throws IOException;
}
