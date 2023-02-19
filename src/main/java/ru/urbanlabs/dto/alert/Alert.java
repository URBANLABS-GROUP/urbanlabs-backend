package ru.urbanlabs.dto.alert;

import java.util.Objects;

public class Alert {

    protected AlertType alertType;

    public AlertType getAlertType() {
        return alertType;
    }

    public void setAlertType(AlertType alertType) {
        this.alertType = alertType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alert alert = (Alert) o;
        return alertType == alert.alertType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(alertType);
    }

    @Override
    public String toString() {
        return "Alert{" +
            "alertType=" + alertType +
            '}';
    }
}
