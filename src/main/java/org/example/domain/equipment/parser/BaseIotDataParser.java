package org.example.domain.equipment.parser;

import org.example.model.telemetry.impl.IotTelemetry;

import java.util.List;

public abstract class BaseIotDataParser {

    public abstract List<IotTelemetry> parseDate(byte[] data);
}
