package br.com.graphql.service;

import br.com.graphql.model.Book;
import br.com.graphql.repository.BookRespository;
import br.com.graphql.service.datafetcher.AllBooksDataFetcher;
import br.com.graphql.service.datafetcher.BookDataFetcher;
import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.stream.Stream;

@Service
public class GraphQLService {

    @Value("classpath:graphql/books.graphql")
    Resource resource;

    private GraphQL graphQL;

    @Autowired
    private BookRespository bookRespository;
    @Autowired
    private AllBooksDataFetcher allBooksDataFetcher;
    @Autowired
    private BookDataFetcher bookDataFetcher;


    @PostConstruct
    private void loadSchema() throws IOException {
        loadDataInHSQL();

        //get schema
        File schemaFile = resource.getFile();
        //parse schema
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
        RuntimeWiring wiring = buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
        graphQL = GraphQL.newGraphQL(schema).build();
    }

    private void loadDataInHSQL() {

        Stream.of(

                new Book("123", "Book of Clouds", "Kindle Edition",
                        new String[] {
                                "Chloe Aridjis"
                        }, "Nov 2017"),
                new Book("124", "Cloud Arch & Engineering", "Orielly",
                        new String[] {
                                "Peter", "Sam"
                        }, "Jan 2015"),
                new Book("125", "Java 9 Programming", "Orielly",
                        new String[] {
                                "Venkat", "Ram"
                        }, "Dec 2016")
        ).forEach(book -> bookRespository.save(book));
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring ->
                    typeWiring.dataFetcher("allBooks", allBooksDataFetcher)
                            .dataFetcher("book", bookDataFetcher))
                .build();
    }

    public GraphQL getGraphQL() {
        return graphQL;
    }
}
