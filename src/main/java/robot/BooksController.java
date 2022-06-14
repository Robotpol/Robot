package robot;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/v1/books")
public class BooksController {

    private final Bookstores bookstores;

    public BooksController(Bookstores bookstores) {
        this.bookstores = bookstores;
    }

    @GetMapping("{bookstore}")
    ResponseEntity<Books> getBooks(
            @PathVariable String bookstore,
            @RequestParam Map<String, String> filters
            ) {
        filters.forEach((f, l) -> System.out.println(f + ":" + l));
        Books books = bookstores.getBooks(bookstore);
        return ResponseEntity.ok(books);
    }

}
