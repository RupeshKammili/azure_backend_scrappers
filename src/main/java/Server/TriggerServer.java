package Server;

import static spark.Spark.*;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.Logger;

import javax.servlet.MultipartConfigElement;

public class TriggerServer {

    private static final Logger LOGGER = Logger.getLogger(TriggerServer.class.getName());

    public static void main(String[] args) {

        // ✅ Set dynamic port for Azure compatibility
        String portStr = System.getenv("PORT");
        int portNumber = (portStr != null) ? Integer.parseInt(portStr) : 8080;
        port(portNumber);

        // ✅ Set static files path from environment variable
        String staticPath = System.getenv("STATIC_FILES_PATH");
        if (staticPath != null && !staticPath.isEmpty()) {
            staticFiles.externalLocation(staticPath);
        } else {
            LOGGER.warning("STATIC_FILES_PATH environment variable is not set.");
        }

        // ✅ CORS setup (allow only specific origin in prod)
        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> {
            // Use * only in dev, restrict in prod
            response.header("Access-Control-Allow-Origin", System.getenv().getOrDefault("CORS_ORIGIN", "*"));
            response.header("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
            response.header("Access-Control-Allow-Headers", "Content-Type,Authorization");
        });

        // ✅ File upload endpoint
        post("/upload-excel", (req, res) -> {
            res.type("application/json");

            req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));

            try (InputStream is = req.raw().getPart("file").getInputStream()) {
                File uploadDir = new File("Uploads");
                if (!uploadDir.exists()) uploadDir.mkdirs();

                File uploadedFile = new File(uploadDir, "Scrappers.xlsx");
                try (OutputStream os = new FileOutputStream(uploadedFile)) {
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = is.read(buffer)) != -1) {
                        os.write(buffer, 0, bytesRead);
                    }
                }

                LOGGER.info("✅ Excel uploaded to: " + uploadedFile.getAbsolutePath());

                return "{\"status\":\"success\",\"message\":\"Excel file uploaded successfully.\"}";

            } catch (Exception e) {
                LOGGER.severe("❌ Upload failed: " + e.getMessage());
                res.status(500);
                return "{\"status\":\"error\",\"message\":\"Upload failed: " + e.getMessage() + "\"}";
            }
        });

        // ✅ Test trigger endpoint
        post("/run-tests", (req, res) -> {
            res.type("application/json");

            try {
                String projectPath = System.getenv("TEST_PROJECT_DIR");
                if (projectPath == null || projectPath.isEmpty()) {
                    res.status(500);
                    return "{\"status\":\"error\",\"message\":\"TEST_PROJECT_DIR is not set.\"}";
                }

                File testProject = new File(projectPath);

                // ✅ Use platform-independent command
                List<String> command;
                if (System.getProperty("os.name").toLowerCase().contains("win")) {
                    command = Arrays.asList("cmd.exe", "/c", "mvn test");
                } else {
                    command = Arrays.asList("sh", "-c", "mvn test");
                }

                ProcessBuilder pb = new ProcessBuilder(command);
                pb.directory(testProject);
                pb.inheritIO(); // Optional: For seeing console output

                Process p = pb.start();
                int exitCode = p.waitFor();

                if (exitCode == 0) {
                    LOGGER.info("✅ Test execution successful.");
                    String reportUrl = "/index.html"; // Update if hosted elsewhere
                    return String.format("{\"status\":\"success\",\"message\":\"Test execution completed.\",\"report_url\":\"%s\"}", reportUrl);
                } else {
                    LOGGER.warning("❌ Test execution failed with exit code: " + exitCode);
                    res.status(500);
                    return "{\"status\":\"error\",\"message\":\"Test execution failed.\"}";
                }

            } catch (Exception e) {
                LOGGER.severe("❌ Error during test run: " + e.getMessage());
                res.status(500);
                return "{\"status\":\"error\",\"message\":\"" + e.getMessage() + "\"}";
            }
        });

        // ✅ Health check endpoint
        get("/", (req, res) -> {
            res.type("application/json");
            return "{\"status\":\"ok\",\"message\":\"Server is up and running!\"}";
        });

        LOGGER.info("✅ TriggerServer started on port " + portNumber);
    }
}
