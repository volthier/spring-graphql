package br.com.graphql.service.datafetcher;

import br.com.graphql.model.Book;
import br.com.graphql.repository.BookRespository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllBooksDataFetcher implements DataFetcher<List<Book>> {

    @Autowired
    BookRespository respository;

    @Override
    public List<Book> get(DataFetchingEnvironment dataFetchingEnvironment) {
        return respository.findAll();
    }
}
