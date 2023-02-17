package org.example.domain.equipment.parser;

import org.example.model.telemetry.impl.iot.BaseIotTelemetry;

import java.util.List;

public abstract class BaseIotDataParser {

    public abstract List<BaseIotTelemetry> parseDate(byte[] data);
}
