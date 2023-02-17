package org.example.model.request;

import javax.persistence.*;
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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Request that = (Request) o;
        return Objects.equals(id, that.id)
            && type == that.type
            && status == that.status
            && Objects.equals(roomId, that.roomId)
            && Objects.equals(title, that.title)
            && Objects.equals(customer, that.customer)
            && Objects.equals(cost, that.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, status, roomId, title, customer, cost);
    }
}
