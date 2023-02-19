package ru.urbanlabs.model.telemetry.impl.iot;

import ru.urbanlabs.model.telemetry.BaseTelemetry;

import javax.persistence.Entity;

@Entity(name = "move_socket_telemetry")
public class MoveSensorTelemetry extends BaseTelemetry {

    private boolean isMove;

    public boolean isMove() {
        return isMove;
    }

    public void setMove(boolean move) {
        isMove = move;
    }

    @Override
    public String toString() {
        return "MoveSensorTelemetry{" +
            "isMove=" + isMove +
            ", id=" + id +
            ", fixTime=" + fixTime +
            ", createTime=" + createTime +
            ", equipmentId=" + equipmentId +
            ", equipmentType=" + equipmentType +
            '}';
    }
}
