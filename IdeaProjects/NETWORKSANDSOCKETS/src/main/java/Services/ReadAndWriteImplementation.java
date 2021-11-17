package Services;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ReadAndWriteImplementation implements ReadAndWrite {

    public void sendResponse(Socket clientSocket, String status, String contentType, byte[] content) throws IOException {

        OutputStream displayOnScreen = clientSocket.getOutputStream();
        displayOnScreen.write(("HTTP/1.1 \r\n" + status).getBytes());
        displayOnScreen.write(("ContentType: " + contentType + "\r\n").getBytes());
        displayOnScreen.write("\r\n".getBytes());
        displayOnScreen.write(content);
        displayOnScreen.write("\r\n\r\n".getBytes());
        displayOnScreen.flush();
        clientSocket.close();
    }


    @Override
    public Path getFilePath(String path) {
        if ("/".equals(path)) {
            path = "src/main/resources/index.html";
        } else if ("/json".equals(path)) {
            path = "src/main/resources/info.json";
        } else {
            path = "src/main/resources/ErrorFile.html";
        }

        return Paths.get(path);
    }


    public String guessContentType(Path filePath) throws IOException {

            return Files.probeContentType(filePath);
    }
}
