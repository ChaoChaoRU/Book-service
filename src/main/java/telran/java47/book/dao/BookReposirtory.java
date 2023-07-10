package telran.java47.book.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import telran.java47.book.model.Book;

public interface BookReposirtory extends PagingAndSortingRepository<Book, String> {
	
	List<Book> findByAuthorsName(String author);

	List<Book> findPublisherByPublisherName(String publisher);

}
