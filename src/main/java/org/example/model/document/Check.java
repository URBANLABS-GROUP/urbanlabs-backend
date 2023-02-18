package org.example.model.document;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "checks")
public class Check {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer roomId;
    private Integer cost;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Check check = (Check) o;
        return Objects.equals(id, check.id) && Objects.equals(roomId, check.roomId) && Objects.equals(cost, check.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomId, cost);
    }

    @Override
    public String toString() {
        return "Check{" +
            "id=" + id +
            ", roomId=" + roomId +
            ", cost=" + cost +
            '}';
    }
}
