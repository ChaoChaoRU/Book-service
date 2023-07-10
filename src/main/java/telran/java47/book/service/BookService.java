package telran.java47.book.service;

import java.util.List;
import java.util.stream.Stream;

import telran.java47.book.dto.AuthorDto;
import telran.java47.book.dto.BookDto;
import telran.java47.book.model.Author;

public interface BookService {
	
	boolean addBook(BookDto bookDto);
	
	BookDto findBookByIsbn(String isbn);
	
	BookDto removeBook(String isbn);
	
	AuthorDto removeAuthor(String author);
	
	BookDto updateBookTitle(String isbn, String title);
	
	List<BookDto> findBooksByAuthor(String author);
	
	List<BookDto> findBooksByPublisher(String publisher);
	
	List<AuthorDto> findBookAuthors(String isbn);
	
	List<String> findPublishersByAuthor(String author);
	
	

}
