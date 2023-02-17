package org.example.model.telemetry.impl.iot;

import org.example.model.telemetry.BaseTelemetry;
import org.example.model.iot.equipment.EquipmentType;

public abstract class BaseIotTelemetry extends BaseTelemetry {

    protected Integer equipmentId;
    protected EquipmentType equipmentType;

}
