package org.example.dto.alert.impl;

import org.example.dto.alert.Alert;
import org.example.dto.alert.AlertType;

import java.util.Objects;

public class RentWithNegativeProfitAlert extends Alert {

    private int roomId;
    private int income;
    private int expense;

    public RentWithNegativeProfitAlert() {
        this.alertType = AlertType.NEGATIVE_PROFIT;
    }

    public RentWithNegativeProfitAlert(int roomId, int income, int expense) {
        this.roomId = roomId;
        this.income = income;
        this.expense = expense;
        this.alertType = AlertType.NEGATIVE_PROFIT;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
        this.income = income;
    }

    public int getExpense() {
        return expense;
    }

    public void setExpense(int expense) {
        this.expense = expense;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RentWithNegativeProfitAlert that = (RentWithNegativeProfitAlert) o;
        return roomId == that.roomId && income == that.income && expense == that.expense;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, income, expense);
    }

    @Override
    public String toString() {
        return "RentWithNegativeProfit{" +
            "roomId=" + roomId +
            ", income=" + income +
            ", expense=" + expense +
            '}';
    }
}
