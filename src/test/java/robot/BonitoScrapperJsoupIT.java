package robot;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
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

/**
 * @author Mariusz Bal
 */
@Test
public class BonitoScrapperJsoupIT {

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

    public void shouldScrapFixedPage() throws NoSuchMethodException, IOException, InvocationTargetException, IllegalAccessException {
        //g
        BookstoreScrapper bonito = new BonitoScrapperJsoup();
        Method method = bonito.getClass().getDeclaredMethod("loopPages",
                Document.class, int.class, List.class);
        method.setAccessible(true);

        URL url = Thread.currentThread().getContextClassLoader().getResource("bonito.html");
        File file = new File(Objects.requireNonNull(url).getPath());
        Document document = Jsoup.parse(file, "utf-8");
        List<Book> books = new ArrayList<>();
        //w
        method.invoke(bonito, document, 1, books);
        //t
        SoftAssert sa = new SoftAssert();
        sa.assertTrue(output.toString().contains("---- Done"), "End of scrapping should be logged");
        sa.assertFalse(books.isEmpty(), "There should be some books scrapped");
        sa.assertAll();
    }

}