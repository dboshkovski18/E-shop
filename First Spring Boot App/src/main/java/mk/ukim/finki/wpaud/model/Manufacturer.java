package mk.ukim.finki.wpaud.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="manufacturers")
@Data
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(name="manufacturer_address")
    private String address;

    public Manufacturer(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Manufacturer() {

    }
}
