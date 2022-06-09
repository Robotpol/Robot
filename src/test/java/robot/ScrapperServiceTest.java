package robot;

import org.mockito.Mock;
import org.mockito.testng.MockitoTestNGListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;
import java.util.Locale;

@Listeners(MockitoTestNGListener.class)
public class ScrapperServiceTest {

    @Mock
    private Bookstores bookstores;

    private ScrapperService underTest;

    @BeforeMethod
    public void setUp() {
        underTest = new ScrapperService(bookstores);
    }

    @Test
    public void shouldAddBonitoScrapper() {
        // given
        Bookstore bonito = Bookstore.BONITO;
        int hour = 0;
        int min = 0;
        ScrapperPostDto scrapperPostDto = new ScrapperPostDto(bonito.name(), hour, min);
        // when
        underTest.startExecutionAt(scrapperPostDto, 0);
        List<ScrapperJobDto> scrapperJobs = underTest.getScrapperJobs();
        // then
        ScrapperJobDto scrapperJob = scrapperJobs.get(0);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(scrapperJobs.size(), 1);
        softAssert.assertEquals(bonito.name().toLowerCase(Locale.ROOT), scrapperJob.bookstore());
        softAssert.assertEquals(scrapperJob.hour(), hour);
        softAssert.assertEquals(scrapperJob.min(), min);
        softAssert.assertAll();
    }

    @Test
    public void shouldAddGandalfScrapper() {
        // given
        Bookstore bonito = Bookstore.GANDALF;
        int hour = 0;
        int min = 0;
        ScrapperPostDto scrapperPostDto = new ScrapperPostDto(bonito.name(), hour, min);
        // when
        underTest.startExecutionAt(scrapperPostDto, 0);
        List<ScrapperJobDto> scrapperJobs = underTest.getScrapperJobs();
        // then
        ScrapperJobDto scrapperJob = scrapperJobs.get(0);
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(scrapperJobs.size(), 1);
        softAssert.assertEquals(bonito.name().toLowerCase(Locale.ROOT), scrapperJob.bookstore());
        softAssert.assertEquals(scrapperJob.hour(), hour);
        softAssert.assertEquals(scrapperJob.min(), min);
        softAssert.assertAll();
    }
}
