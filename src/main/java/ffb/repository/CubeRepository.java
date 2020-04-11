package ffb.repository;

import ffb.entity.cube.Cube;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CubeRepository extends CrudRepository<Cube, Long> {
}
