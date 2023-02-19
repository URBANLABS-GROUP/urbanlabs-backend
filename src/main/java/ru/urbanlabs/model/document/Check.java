package ru.urbanlabs.model.document;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "checks")
public class Check {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer roomId;
    private Instant time;
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

    public Instant getTime() {
        return time;
    }

    public void setTime(Instant time) {
        this.time = time;
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
        return Objects.equals(id, check.id) && Objects.equals(roomId, check.roomId) && Objects.equals(time, check.time) && Objects.equals(cost, check.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomId, time, cost);
    }

    @Override
    public String toString() {
        return "Check{" +
            "id=" + id +
            ", roomId=" + roomId +
            ", time=" + time +
            ", cost=" + cost +
            '}';
    }
}
