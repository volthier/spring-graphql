package br.com.graphql.service.datafetcher;

import br.com.graphql.model.Book;
import br.com.graphql.repository.BookRespository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BookDataFetcher implements DataFetcher<Optional<Book>> {

    @Autowired
    BookRespository respository;

    @Override
    public Optional<Book> get(DataFetchingEnvironment dataFetchingEnvironment) {
        String isn = dataFetchingEnvironment.getArgument("id");
        return respository.findById(isn);
    }
}
