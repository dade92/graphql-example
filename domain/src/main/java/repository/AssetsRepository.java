package repository;

import model.Asset;
import model.Book;

import java.util.List;

public interface AssetsRepository {
    List<Asset> retrieveAssets(Book book);
}
