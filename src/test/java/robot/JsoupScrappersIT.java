package robot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@Listeners(MockitoTestNGListener.class)
public class JsoupScrappersIT {

    private ByteArrayOutputStream output;

    @BeforeMethod
    public void setUp() {
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @AfterMethod
    public void cleanUp() {
        System.setOut(System.out);
    }

    @DataProvider
    private Object[][] bookstoreProvider() {
        return new Object[][]{
                {new BonitoScrapperJsoup(), "bonito.html"},
                {new GandalfScrapperJsoup(), "gandalf.html"},
        };
    }

    @Test(dataProvider = "bookstoreProvider")
    public void shouldScrapFixedPage(BookstoreScrapper scrapper, String webpage) throws IOException {
        //g
        BookstoreScrapper mock = spy(scrapper);
        Document document = getDocument(webpage);
        List<Book> books = new ArrayList<>();
        //w
        when(mock.nextPage(0)).thenReturn(document);
        mock.loopPages(document, 1, books);
        //t
        SoftAssert sa = new SoftAssert();
        sa.assertTrue(output.toString().contains("---- Done"), "End of scrapping should be logged");
        sa.assertFalse(books.isEmpty(), "There should be some books scrapped");
        sa.assertAll();
    }

    private Document getDocument(String webpage) throws IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource(webpage);
        File file = new File(Objects.requireNonNull(url).getPath());
        return Jsoup.parse(file, "utf-8");
    }
}