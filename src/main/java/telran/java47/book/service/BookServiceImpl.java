package telran.java47.book.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.java47.book.dao.AuthorRepository;
import telran.java47.book.dao.BookReposirtory;
import telran.java47.book.dao.PublisherRepository;
import telran.java47.book.dto.AuthorDto;
import telran.java47.book.dto.BookDto;
import telran.java47.book.dto.exceptions.EntityNotFoundException;
import telran.java47.book.model.Author;
import telran.java47.book.model.Book;
import telran.java47.book.model.Publisher;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
	
	final BookReposirtory bookReposirtory;
	final AuthorRepository authorRepository;
	final PublisherRepository publisherRepository;
	final ModelMapper modelMapper;

	@Override
	@Transactional
	public boolean addBook(BookDto bookDto) {
		if(bookReposirtory.existsById(bookDto.getIsbn())) {
			return false;
		}
		//Publisher
		Publisher publisher = publisherRepository.findById(bookDto.getPublisher())
				.orElse(publisherRepository.save(new Publisher(bookDto.getPublisher())));
		//Authors
		Set<Author> authors = bookDto.getAuthors().stream()
				.map(a -> authorRepository.findById(a.getName())
						.orElse(authorRepository.save(new Author(a.getName(), a.getBirthDate()))))
				.collect(Collectors.toSet());
		Book book = new Book(bookDto.getIsbn(), bookDto.getTitle(), authors, publisher);
		bookReposirtory.save(book);
		return true;
	}

	@Override
	public BookDto findBookByIsbn(String isbn) {
		Book book = bookReposirtory.findById(isbn).orElseThrow(EntityNotFoundException::new);
		return modelMapper.map(book, BookDto.class);
	}

	@Override
	@Transactional
	public BookDto removeBook(String isbn) {
		Book book = bookReposirtory.findById(isbn).orElseThrow(EntityNotFoundException::new);
		bookReposirtory.delete(book);
		return modelMapper.map(book, BookDto.class);
	}

	@Override
	@Transactional
	public AuthorDto removeAuthor(String author) {
		Author auth = authorRepository.findById(author).orElseThrow(EntityNotFoundException::new);
		bookReposirtory
		.findByAuthorsName(author)
		.stream()
		.forEach(bookReposirtory::delete);
		authorRepository.delete(auth);
		return modelMapper.map(auth, AuthorDto.class);
	}

	@Override
	public BookDto updateBookTitle(String isbn, String title) {
		Book book = bookReposirtory.findById(isbn).orElseThrow(EntityNotFoundException::new);
		book.setTitle(title);
		bookReposirtory.save(book);
		return modelMapper.map(book, BookDto.class);
	}

	@Override
	public List<BookDto> findBooksByAuthor(String author) {
		return bookReposirtory
				.findByAuthorsName(author)
				.stream()
				.map(b -> modelMapper.map(b, BookDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<BookDto> findBooksByPublisher(String publisher) {
		return bookReposirtory
				.findPublisherByPublisherName(publisher)
				.stream()
				.map(b -> modelMapper.map(b, BookDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<AuthorDto> findBookAuthors(String isbn) {
		Book book = bookReposirtory.findById(isbn).orElseThrow(EntityNotFoundException::new);
		return book
				.getAuthors()
				.stream()
				.map(a -> modelMapper.map(a, AuthorDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<String> findPublishersByAuthor(String author) {
		return authorRepository.findPublishersByAuthor(author);
	}

}
