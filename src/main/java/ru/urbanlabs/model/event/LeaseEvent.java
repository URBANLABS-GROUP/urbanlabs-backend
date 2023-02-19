package ru.urbanlabs.model.event;

import javax.persistence.*;
import java.time.Instant;
import java.util.Objects;

@Entity
public class LeaseEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Integer leaseContractId;
    private Instant paymentMonth;
    private Instant createTime;
    private Integer money;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLeaseContractId() {
        return leaseContractId;
    }

    public void setLeaseContractId(Integer leaseContractId) {
        this.leaseContractId = leaseContractId;
    }

    public Instant getPaymentMonth() {
        return paymentMonth;
    }

    public void setPaymentMonth(Instant month) {
        this.paymentMonth = month;
    }

    public Instant getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeaseEvent that = (LeaseEvent) o;
        return Objects.equals(id, that.id) && Objects.equals(leaseContractId, that.leaseContractId) && Objects.equals(paymentMonth, that.paymentMonth) && Objects.equals(createTime, that.createTime) && Objects.equals(money, that.money);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, leaseContractId, paymentMonth, createTime, money);
    }

    @Override
    public String toString() {
        return "LeaseEvent{" +
            "id=" + id +
            ", leaseContractId=" + leaseContractId +
            ", paymentMonth=" + paymentMonth +
            ", createTime=" + createTime +
            ", money=" + money +
            '}';
    }
}
