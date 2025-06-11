package webapp.resolver;

import model.Asset;
import model.Book;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BookAssetsResolver {

    public BookAssetsResolver() {
    }

    @SchemaMapping(typeName = "Book", field = "assets")
    public List<Asset> assets(Book book) {
        //TODO extract a proper collaborator or service to fetch assets
        return List.of(new Asset("image", "http://example.com"), new Asset("video", "http://example2.com"));
    }
}


