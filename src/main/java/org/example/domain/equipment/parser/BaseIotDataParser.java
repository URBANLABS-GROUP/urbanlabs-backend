package org.example.domain.equipment.parser;

import org.example.model.telemetry.BaseTelemetry;

import java.util.List;

public abstract class BaseIotDataParser {

    public abstract List<BaseTelemetry> parseDate(byte[] data);
}
