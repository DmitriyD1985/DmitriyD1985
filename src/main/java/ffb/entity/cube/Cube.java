package ffb.entity.cube;

import ffb.entity.ProductionLocation;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Cube {
    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column(name = "hex_color")
    private String color;

    @Column
    private Integer size;

    @ManyToOne
    @JoinColumn
    private ProductionLocation productionLocation;

    public Cube() {
    }

    public Cube(String color, Integer size, ProductionLocation productionLocation) {
        this.color = color;
        this.size = size;
        this.productionLocation = productionLocation;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public ProductionLocation getProductionLocation() {
        return productionLocation;
    }

    public void setProductionLocation(ProductionLocation productionLocation) {
        this.productionLocation = productionLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cube cube = (Cube) o;
        return Objects.equals(id, cube.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
