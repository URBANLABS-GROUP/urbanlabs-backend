package org.example.model.document;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Entity
public class LeaseContract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Integer lessorId;
    @Column(nullable = false)
    private Integer lesseeId;
    @Column(nullable = false)
    private Integer roomId;

    private Instant createTime;
    private Instant startTime;
    private Instant endTime;

    private Integer rent;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Integer getLessorId() {
        return lessorId;
    }

    public void setLessorId(Integer lessorId) {
        this.lessorId = lessorId;
    }

    public Integer getLesseeId() {
        return lesseeId;
    }

    public void setLesseeId(Integer lesseeId) {
        this.lesseeId = lesseeId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Integer getRent() {
        return rent;
    }

    public void setRent(Integer rent) {
        this.rent = rent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeaseContract that = (LeaseContract) o;
        return Objects.equals(id, that.id) && Objects.equals(lessorId, that.lessorId) && Objects.equals(lesseeId, that.lesseeId) && Objects.equals(roomId, that.roomId) && Objects.equals(createTime, that.createTime) && Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime) && Objects.equals(rent, that.rent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lessorId, lesseeId, roomId, createTime, startTime, endTime, rent);
    }

    @Override
    public String toString() {
        return "LeaseContract{" +
                "id=" + id +
                ", lessorId=" + lessorId +
                ", lesseeId=" + lesseeId +
                ", roomId=" + roomId +
                ", createTime=" + createTime +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", rent=" + rent +
                '}';
    }
}
