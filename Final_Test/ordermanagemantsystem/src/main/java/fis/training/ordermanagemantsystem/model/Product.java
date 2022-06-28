package fis.training.ordermanagemantsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Entity
@Table(name = "tbl_product")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 100, min = 10)
    @Column(name="name")
    private String name;

    @NotNull
    @Min(1)
    @Column(name="price")
    private Double price;

    @NotNull
    @PositiveOrZero
    @Column(name="avaiable", nullable = true)
    private Long avaiable;
}
