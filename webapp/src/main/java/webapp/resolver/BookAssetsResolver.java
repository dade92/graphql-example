package webapp.resolver;

import model.Asset;
import model.Book;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import repository.AssetsRepository;

import java.util.List;

@Controller
public class BookAssetsResolver {

    private static final String FIELD_NAME = "assets";

    private final AssetsRepository assetsRepository;

    public BookAssetsResolver(AssetsRepository assetsRepository) {
        this.assetsRepository = assetsRepository;
    }

    @SchemaMapping(typeName = "Book", field = FIELD_NAME)
    public List<Asset> assets(Book book) {
        return assetsRepository.retrieveAssets(book);
    }
}


