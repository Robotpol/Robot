package robot.bonito;

import com.sun.net.httpserver.HttpServer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import robot.Books;
import robot.BookstoreScrapper;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class BonitoScrapperIT {

    private ByteArrayOutputStream output;
    private HttpServer tempServer;

    @BeforeMethod
    public void setUp() throws IOException {
        tempServer = HttpServer.create(new InetSocketAddress(10124), 0);
        tempServer.start();
        tempServer.createContext("/", (httpExchange) -> {
            byte[] bytes = getDocument("bonito.html").html().getBytes(StandardCharsets.UTF_8);
            OutputStream responseBody = httpExchange.getResponseBody();
            httpExchange.sendResponseHeaders(200, bytes.length);
            responseBody.write(bytes);
            responseBody.flush();
            responseBody.close();
        });
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @AfterMethod
    public void cleanUp() {
        System.setOut(System.out);
        tempServer.stop(0);
    }

    @Test
    public void shouldScrapFixedPage() {
        //g
        BookstoreScrapper bonitoScrapper = new BonitoScrapperJsoup("http://localhost:10124/");
        //w
        Books call = bonitoScrapper.call();
        //t
        SoftAssert sa = new SoftAssert();
        sa.assertTrue(output.toString().contains("---- Done"), "End of scrapping should be logged");
        sa.assertFalse(call.books().isEmpty(), "There should be some books scrapped");
        sa.assertAll();
    }

    private Document getDocument(String webpage) throws IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource(webpage);
        File file = new File(Objects.requireNonNull(url).getPath());
        return Jsoup.parse(file, "utf-8");
    }
}
