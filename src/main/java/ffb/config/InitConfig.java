package ffb.config;

import ffb.entity.ProductionLocation;
import ffb.entity.cube.Cube;
import ffb.repository.CubeRepository;
import ffb.repository.ProductionLocationRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;

@Configuration
public class InitConfig {
    private CubeRepository cubeRepository;
    private ProductionLocationRepository productionLocationRepository;

    public InitConfig(CubeRepository cubeRepository,
                      ProductionLocationRepository productionLocationRepository) {
        this.cubeRepository = cubeRepository;
        this.productionLocationRepository = productionLocationRepository;
    }

    @PostConstruct
    @Transactional
    public void initIt() {
        String locationNameMoscow = "Moscow";
        productionLocationRepository.save(new ProductionLocation(locationNameMoscow));
        ProductionLocation moscow = productionLocationRepository.findByName(locationNameMoscow);

        String locationNameTver = "Tver";
        productionLocationRepository.save(new ProductionLocation(locationNameTver));
        ProductionLocation tver = productionLocationRepository.findByName(locationNameTver);

        List<Cube> list = Arrays.asList(
                new Cube("#000", 100, moscow),
                new Cube("#000", 20, moscow),
                new Cube("#fff", 30, tver),
                new Cube("#fff", 40, tver),
                new Cube("#fff", 70, tver)
        );

        cubeRepository.saveAll(list);
    }
}
