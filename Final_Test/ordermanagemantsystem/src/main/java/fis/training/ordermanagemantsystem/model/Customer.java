package fis.training.ordermanagemantsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "tbl_customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 10, max = 100)
    @Column(name="name")
    private String name;

    @NotNull
    @PositiveOrZero
    @Column(name="mobile", length = 10)
    private String mobile;

    @NotNull
    @Size(min = 10, max = 100)
    @Column(name="address")
    private String address;
//
//    @ManyToOne()
//    private List<Order> orderss;
}
