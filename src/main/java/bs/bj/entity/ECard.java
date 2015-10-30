package bs.bj.entity;

import javax.persistence.*;

/**
 * Created by boubdyk on 30.10.2015.
 */

@Entity
@Table(name = "CARD")
public class ECard {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    public ECard(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
