package com.example.DemoGraphQL.resolver;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import com.example.DemoGraphQL.exception.BookNotFoundException;
import com.example.DemoGraphQL.model.Author;
import com.example.DemoGraphQL.model.Book;
import com.example.DemoGraphQL.repository.AuthorRepository;
import com.example.DemoGraphQL.repository.BookRepository;

import java.util.Optional;

public class Mutation implements GraphQLMutationResolver {
    private BookRepository bookRepository;
    private AuthorRepository authorRepository;

    public Mutation(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public Author newAuthor(String firstName, String lastName) {
        Author author = new Author();
        author.setFirstName(firstName);
        author.setLastName(lastName);

        authorRepository.save(author);

        return author;
    }

    public Book newBook(String title, String isbn, Integer pageCount, String authorId) {
        Book book = new Book();
        book.setAuthor(new Author(authorId));
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setPageCount(pageCount != null ? pageCount : 0);

        bookRepository.save(book);

        return book;
    }

    public Book newBookWithNewAuthor(String title, String isbn, Integer pageCount, Author author) {
        Author newAuthor = newAuthor(author.getFirstName(), author.getLastName());

        Book book = new Book();
        book.setAuthor(new Author(newAuthor.getId()));
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setPageCount(pageCount != null ? pageCount : 0);

        bookRepository.save(book);

        return book;
    }

    public boolean deleteBook(String id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if(!bookOptional.isPresent()) {
            throw new BookNotFoundException("The book to be deleted was not found", id);
        }
        bookRepository.delete(bookOptional.get());
        return true;
    }

    public boolean deleteAuthor(String id) {
        Optional<Author> authorOptional = authorRepository.findById(id);
        if(!authorOptional.isPresent()) {
            throw new BookNotFoundException("The author to be deleted was not found", id);
        }
        authorRepository.delete(authorOptional.get());
        return true;
    }

    public Book updateBookPageCount(Integer pageCount, String id) {
        Optional<Book> bookOptional = bookRepository.findById(id);
        if(!bookOptional.isPresent()) {
            throw new BookNotFoundException("The book to be updated was not found", id);
        }
        bookOptional.get().setPageCount(pageCount);
        Book book = bookOptional.get();
        Book updatedBook = bookRepository.save(book);
        return updatedBook;
    }
}
