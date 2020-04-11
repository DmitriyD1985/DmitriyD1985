package ffb.repository;


import ffb.entity.ProductionLocation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionLocationRepository extends CrudRepository<ProductionLocation, Long> {
    ProductionLocation findByName(String name);
}
