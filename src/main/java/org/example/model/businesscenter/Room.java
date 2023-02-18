package org.example.model.businesscenter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.Objects;

@Entity(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int businessCenterStoreyId;
    private String position;
    private String name;
    private Integer leaseContractId;
    private Integer requiredTemp;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return businessCenterStoreyId == room.businessCenterStoreyId && Objects.equals(id, room.id) && Objects.equals(position, room.position) && Objects.equals(name, room.name) && Objects.equals(leaseContractId, room.leaseContractId) && Objects.equals(requiredTemp, room.requiredTemp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, businessCenterStoreyId, position, name, leaseContractId, requiredTemp);
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
            '}';
    }
}
