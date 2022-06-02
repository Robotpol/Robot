package robot;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/scrappers")
public class ScrapperController {
    private final ScrapperService scrapperService;

    public ScrapperController(ScrapperService scrapperService) {
        this.scrapperService = scrapperService;
    }

    @PostMapping
    ResponseEntity<ScrapperJobDto> create(
            @RequestBody ScrapperPostDto postDto
    ) {
        ScrapperJobDto scrapperPostDto = scrapperService.startExecutionAt(postDto, 0);
        return new ResponseEntity<>(scrapperPostDto, HttpStatus.CREATED);
    }

    @GetMapping
    ResponseEntity<List<ScrapperJobDto>> getScrappers() {
        List<ScrapperJobDto> scrapperJobs = scrapperService.getScrapperJobs();
        return ResponseEntity.ok(scrapperJobs);
    }

}
