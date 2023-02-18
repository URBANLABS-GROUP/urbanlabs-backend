package org.example.model.businesscenter;

import javax.persistence.*;
import java.util.Objects;

@Entity(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int businessCenterStoreyId;

    @Column(columnDefinition="TEXT")
    private String position;
    private String name;
    private Integer leaseContractId;
    private Integer requiredTemp;
    private Integer allowablePowerConsumption;
    private Double area;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getBusinessCenterStoreyId() {
        return businessCenterStoreyId;
    }

    public void setBusinessCenterStoreyId(int businessCenterStoreyId) {
        this.businessCenterStoreyId = businessCenterStoreyId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLeaseContractId() {
        return leaseContractId;
    }

    public void setLeaseContractId(Integer leaseContractId) {
        this.leaseContractId = leaseContractId;
    }

    public Integer getRequiredTemp() {
        return requiredTemp;
    }

    public void setRequiredTemp(Integer requiredTemp) {
        this.requiredTemp = requiredTemp;
    }

    public Integer getAllowablePowerConsumption() {
        return allowablePowerConsumption;
    }

    public void setAllowablePowerConsumption(Integer powerConsumption) {
        this.allowablePowerConsumption = powerConsumption;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return businessCenterStoreyId == room.businessCenterStoreyId && Objects.equals(id, room.id) && Objects.equals(position, room.position) && Objects.equals(name, room.name) && Objects.equals(leaseContractId, room.leaseContractId) && Objects.equals(requiredTemp, room.requiredTemp) && Objects.equals(allowablePowerConsumption, room.allowablePowerConsumption) && Objects.equals(area, room.area);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, businessCenterStoreyId, position, name, leaseContractId, requiredTemp, allowablePowerConsumption, area);
    }

    @Override
    public String toString() {
        return "Room{" +
            "id=" + id +
            ", businessCenterStoreyId=" + businessCenterStoreyId +
            ", position='" + position + '\'' +
            ", name='" + name + '\'' +
            ", leaseContractId=" + leaseContractId +
            ", requiredTemp=" + requiredTemp +
            ", allowablePowerConsumption=" + allowablePowerConsumption +
            ", area=" + area +
            '}';
    }
}
