package br.com.graphql.repository;

import br.com.graphql.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRespository extends JpaRepository<Book, String> {
}
