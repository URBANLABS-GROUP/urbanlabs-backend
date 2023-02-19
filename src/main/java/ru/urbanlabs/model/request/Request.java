package ru.urbanlabs.model.request;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Entity(name = "request")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private RequestType type;
    @Enumerated(EnumType.STRING)
    private RequestStatus status;
    private Integer roomId; // ИД комнаты
    private String title; // Название заявки
    private String customer; // Заказчик заявки
    private Integer cost; // Стоимость после выполнения
    private Instant createTime;

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public RequestType getType() {
        return type;
    }

    public void setType(final RequestType type) {
        this.type = type;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(final RequestStatus status) {
        this.status = status;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(final Integer roomId) {
        this.roomId = roomId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(final String customer) {
        this.customer = customer;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(final Integer cost) {
        this.cost = cost;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Request request = (Request) o;
        return Objects.equals(id, request.id) && type == request.type && status == request.status && Objects.equals(roomId, request.roomId) && Objects.equals(title, request.title) && Objects.equals(customer, request.customer) && Objects.equals(cost, request.cost) && Objects.equals(createTime, request.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, status, roomId, title, customer, cost, createTime);
    }

    @Override
    public String toString() {
        return "Request{" +
            "id=" + id +
            ", type=" + type +
            ", status=" + status +
            ", roomId=" + roomId +
            ", title='" + title + '\'' +
            ", customer='" + customer + '\'' +
            ", cost=" + cost +
            ", createTime=" + createTime +
            '}';
    }
}
