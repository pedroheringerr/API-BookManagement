package com.pedro.bookManagement.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pedro.bookManagement.models.Book;
import com.pedro.bookManagement.services.BookService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;


@RestController
@RequestMapping("/api")
@Validated
public class BookController {
		
	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping("/books")
		public ResponseEntity<Page<Book>> getBooksByFilter(
			@RequestParam(required = false) String genre,
			@RequestParam(required = false) String author,
			@RequestParam(required = false)	Integer yearPublished,

			@RequestParam(value = "page", required = false, defaultValue = "0")
			@Min(value = 0, message = "Page must be 0 or greater")
			@PositiveOrZero(message = "Page must be 0 or greater")
			Integer page,

			@RequestParam(value = "size", required = false, defaultValue = "5")
			@Min(value = 1, message = "Size must be at least 1")
			@Max(value = 50, message = "Size must be at most 50")
			Integer size
			) {
			return ResponseEntity
				.status(HttpStatus.OK)
				.body(bookService.findByFilter(genre, author, yearPublished, page, size));
			}

	@GetMapping("/books/{isbn}")
	public ResponseEntity<Book> getBookByIsbn(@PathVariable String isbn) {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(bookService.findByIsbn(isbn));
	}

	@GetMapping("/books/{title}")
	public ResponseEntity<Book> getBookByTitle(@PathVariable String title) {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(bookService.findByTitle(title));
	}
	
	@PostMapping("/books")
	public ResponseEntity<Book> saveBook(@Valid @RequestBody Book book) {
		return ResponseEntity
			.status(HttpStatus.CREATED)
			.body(bookService.saveBook(book));
	}

	@PutMapping("/books/{isbn}")
	public ResponseEntity<Book> updateBook(@PathVariable String isbn, @Valid @RequestBody Book book) {
		return ResponseEntity
			.status(HttpStatus.OK)
			.body(bookService.updateBook(isbn, book));
	}

	@DeleteMapping("/books/{isbn}")
	public ResponseEntity<Book> deleteBook(@PathVariable String isbn) {
		bookService.deleteBook(isbn);
		return ResponseEntity.noContent().build();
	}
}
