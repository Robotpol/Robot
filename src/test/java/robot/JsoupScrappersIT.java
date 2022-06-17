package robot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    public void shouldScrapFixedPage(BookstoreScrapper scrapper, String webpage) throws NoSuchMethodException,
            IOException, InvocationTargetException, IllegalAccessException {
        //g
        Method method = scrapper.getClass().getDeclaredMethod("loopPages",
                Document.class, int.class, List.class);
        method.setAccessible(true);

        URL url = Thread.currentThread().getContextClassLoader().getResource(webpage);
        File file = new File(Objects.requireNonNull(url).getPath());
        Document document = Jsoup.parse(file, "utf-8");
        List<Book> books = new ArrayList<>();
        //w
        method.invoke(scrapper, document, 1, books);
        //t
        SoftAssert sa = new SoftAssert();
        sa.assertTrue(output.toString().contains("---- Done"), "End of scrapping should be logged");
        sa.assertFalse(books.isEmpty(), "There should be some books scrapped");
        sa.assertAll();
    }

}