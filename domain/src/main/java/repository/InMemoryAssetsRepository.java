package repository;

import model.Asset;
import model.Book;

import java.util.List;

public class InMemoryAssetsRepository implements AssetsRepository {
    @Override
    public List<Asset> retrieveAssets(Book book) {
        return List.of(
            new Asset("IMAGE", "http://example.com"),
            new Asset("VIDEO", "http://example2.com")
        );
    }
}
