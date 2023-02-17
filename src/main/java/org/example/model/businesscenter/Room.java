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
    private byte[] position;
    private String name;
    private Integer leaseContractId;

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

    public byte[] getPosition() {
        return position;
    }

    public void setPosition(byte[] position) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id == room.id && businessCenterStoreyId == room.businessCenterStoreyId && Arrays.equals(position, room.position) && Objects.equals(name, room.name) && Objects.equals(leaseContractId, room.leaseContractId);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, businessCenterStoreyId, name, leaseContractId);
        result = 31 * result + Arrays.hashCode(position);
        return result;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", businessCenterStoreyId=" + businessCenterStoreyId +
                ", position=" + Arrays.toString(position) +
                ", name='" + name + '\'' +
                ", leaseContractId=" + leaseContractId +
                '}';
    }
}
