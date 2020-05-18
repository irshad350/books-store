package com.example.DemoGraphQL.resolver;

import com.coxautodev.graphql.tools.GraphQLResolver;
import com.example.DemoGraphQL.exception.AuthorNotFoundException;
import com.example.DemoGraphQL.model.Author;
import com.example.DemoGraphQL.model.Book;
import com.example.DemoGraphQL.repository.AuthorRepository;

import java.util.Optional;

public class BookResolver implements GraphQLResolver<Book> {
    private AuthorRepository authorRepository;

    public BookResolver(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author getAuthor(Book book) {
        Optional<Author> authorOptional = authorRepository.findById(book.getAuthor().getId());
        if (!authorOptional.isPresent()) {
            throw new AuthorNotFoundException("The author to be get was not found", book.getAuthor().getId());

        }
        return authorOptional.get();
    }
}
