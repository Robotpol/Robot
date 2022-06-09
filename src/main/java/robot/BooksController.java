package robot;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/books")
public class BooksController {

    private final Bookstores bookstores;

    public BooksController(Bookstores bookstores) {
        this.bookstores = bookstores;
    }

    @GetMapping("{bookstore}")
    ResponseEntity<Books> getBooks(
            @PathVariable String bookstore
    ) {
        Books books = bookstores.getBooks(bookstore);
        return ResponseEntity.ok(books);
    }

}
