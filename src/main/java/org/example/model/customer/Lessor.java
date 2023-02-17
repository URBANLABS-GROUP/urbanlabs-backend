package org.example.model.customer;

import javax.persistence.*;
import java.util.List;

@Entity(name = "lessor")
public class Lessor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ElementCollection
    private List<Integer> ownBusinessCenterIds;

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

    public List<Integer> getOwnBusinessCenterIds() {
        return ownBusinessCenterIds;
    }

    public void setOwnBusinessCenterIds(List<Integer> ownBusinessCenterIds) {
        this.ownBusinessCenterIds = ownBusinessCenterIds;
    }
}
