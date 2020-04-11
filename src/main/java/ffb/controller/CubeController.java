package ffb.controller;

import ffb.entity.cube.Cube;
import ffb.repository.CubeRepository;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "cubes")
public class CubeController {
    private CubeRepository cubeRepository;

    public CubeController(CubeRepository cubeRepository) {
        this.cubeRepository = cubeRepository;
    }

    @GetMapping
    public ResponseEntity<Iterable<Cube>> getCubes() {
        return new ResponseEntity<>(cubeRepository.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Cube> getById(
            @PathVariable(name = "id") Long id
    ) throws NotFoundException {
        Optional<Cube> repositoryResult = cubeRepository.findById(id);
        if (repositoryResult.isPresent()) {
            return new ResponseEntity<>(repositoryResult.get(), HttpStatus.OK);
        } else {
            throw new NotFoundException("a cube with id = " + id + " not exist");
        }
    }

    @PostMapping
    public void createCube(
            @RequestBody Cube cube
    ) {
        cubeRepository.save(cube);
    }

    @PutMapping(path = "/{id}")
    @Transactional
    public void updateCube(
            @PathVariable(name = "id") Long id,
            @RequestBody Cube cube
    ) throws NotFoundException {
        if (cubeRepository.existsById(id)) {
            //TODO: have to implement update
         } else {
            throw new NotFoundException("a cube with id = " + id + " not exist");
        }
    }

    @DeleteMapping(path = "/{id}")
    public void deleteCube(
            @PathVariable(name = "id") Long id
    ) throws NotFoundException {
        if (cubeRepository.existsById(id)) {
            cubeRepository.deleteById(id);
        } else {
            throw new NotFoundException("a cube with id = " + id + " not exist");
        }
    }
}
