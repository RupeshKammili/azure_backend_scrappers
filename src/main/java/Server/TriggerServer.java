package Server;

import static spark.Spark.*;

import java.io.*;

public class TriggerServer {
    public static void main(String[] args) {

        // ✅ Set server port
        port(8080);

        // ✅ Serve static files under /static/ path
        staticFiles.externalLocation("C:\\Users\\MKTtools\\git\\azure_backend_scrappers\\static");
        
        // ✅ Explicitly handle GET request for the root route
        get("/", (req, res) -> {
            return "Server is up and running!";
        });

        // ✅ Serve static files only under `/static/` route
        // Access static files with http://localhost:8080/static/index.html

        // ✅ CORS support
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
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "GET,POST,OPTIONS");
            response.header("Access-Control-Allow-Headers", "Content-Type,Authorization");
        });

        // ✅ POST route: Excel file upload
        post("/upload-excel", (req, res) -> {
            req.attribute("org.eclipse.jetty.multipartConfig", new javax.servlet.MultipartConfigElement("/temp"));

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
                System.out.println("✅ Excel uploaded: " + uploadedFile.getAbsolutePath());
                return "Excel file uploaded successfully.";
            } catch (Exception e) {
                res.status(500);
                return "❌ Failed to upload Excel file: " + e.getMessage();
            }
        });

        // ✅ POST route: Trigger tests
        post("/run-tests", (req, res) -> {
            try {
                File testProject = new File("C:\\Users\\MKTtools\\git\\azure_backend_scrappers");

                ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "mvn test");
                pb.directory(testProject);
                pb.inheritIO();

                Process p = pb.start();
                int exitCode = p.waitFor();

                String message = "Test execution completed.<br><a href='http://4.240.88.62:8080/' target='_blank'>📄 View Extent Report</a>";
                System.out.println("Returning message: " + message);
                return message;

            } catch (Exception e) {
                res.status(500);
                return "❌ Error: " + e.getMessage();
            }
        });

        System.out.println("✅ Spark server started on http://4.240.88.62:8080/");
    }
}
