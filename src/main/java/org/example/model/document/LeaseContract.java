package org.example.model.document;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Entity
public class LeaseContract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer lessorId;
    private Integer lesseeId;
    private Integer roomId;

    private Instant createTime;
    private Instant startTime;
    private Instant endTime;

    private BigDecimal rent;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
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

    public BigDecimal getRent() {
        return rent;
    }

    public void setRent(BigDecimal rent) {
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
