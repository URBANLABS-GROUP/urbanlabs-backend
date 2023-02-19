package ru.urbanlabs.model.businesscenter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity(name = "business_center")
public class BusinessCenter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private Integer lessorId;
    private Integer wattPrice;

    @OneToMany(mappedBy = "businessCenterId")
    private List<BusinessCenterStorey> storeys;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLessorId() {
        return lessorId;
    }

    public void setLessorId(Integer lessorId) {
        this.lessorId = lessorId;
    }

    public List<BusinessCenterStorey> getStoreys() {
        return storeys;
    }

    public void setStoreys(List<BusinessCenterStorey> storeysIds) {
        this.storeys = storeysIds;
    }

    public Integer getWattPrice() {
        return wattPrice;
    }

    public void setWattPrice(Integer wattPrice) {
        this.wattPrice = wattPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BusinessCenter that = (BusinessCenter) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(lessorId, that.lessorId) && Objects.equals(wattPrice, that.wattPrice) && Objects.equals(storeys, that.storeys);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lessorId, wattPrice, storeys);
    }

    @Override
    public String toString() {
        return "BusinessCenter{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", lessorId=" + lessorId +
            ", wattPrice=" + wattPrice +
            ", storeys=" + storeys +
            '}';
    }
}
