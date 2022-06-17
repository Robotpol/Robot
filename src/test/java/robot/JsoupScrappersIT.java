package robot;

import com.sun.net.httpserver.HttpServer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyIterable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class JsoupScrappersIT {

    private ByteArrayOutputStream output;
    private HttpServer tempServer;

    @BeforeMethod
    public void setUp() throws IOException {
        tempServer = HttpServer.create(new InetSocketAddress(10124), 0);
        tempServer.start();
        tempServer.createContext("/1", (httpExchange) -> {
            byte[] bytes = getDocument("bonito.html").html().getBytes(StandardCharsets.UTF_8);
            OutputStream responseBody = httpExchange.getResponseBody();
            httpExchange.sendResponseHeaders(200, bytes.length);
            responseBody.write(bytes);
            responseBody.flush();
            responseBody.close();
        });
        tempServer.createContext("/", (httpExchange) -> {
            byte[] bytes = getDocument("gandalf.html").html().getBytes(StandardCharsets.UTF_8);
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

    @DataProvider
    private Object[][] bookstoreProvider() {
        return new Object[][]{
                {new BonitoScrapperJsoup("http://localhost:10124/")},
                {new GandalfScrapperJsoup("http://localhost:10124/")},
        };
    }

    @Test(dataProvider = "bookstoreProvider")
    public void shouldScrapFixedPage(BookstoreScrapper scrapper) {
        //g
        //w
        Books call = scrapper.call();
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