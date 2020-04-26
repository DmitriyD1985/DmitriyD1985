package ffb.controller;

import ffb.entity.ProductionLocation;
import ffb.repository.ProductionLocationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/production-locations")
public class ProductionLocationController {
    private ProductionLocationRepository productionLocationRepository;

    public ProductionLocationController(ProductionLocationRepository productionLocationRepository) {
        this.productionLocationRepository = productionLocationRepository;
    }

    @GetMapping
    public ResponseEntity<Iterable<ProductionLocation>> getLocations() {
        return new ResponseEntity<>(productionLocationRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{name}")
    public ResponseEntity<ProductionLocation> getLocationByName(@PathVariable(value = "name") String name) {
        return new ResponseEntity<>(productionLocationRepository.findByName(name), HttpStatus.OK);
    }
}
