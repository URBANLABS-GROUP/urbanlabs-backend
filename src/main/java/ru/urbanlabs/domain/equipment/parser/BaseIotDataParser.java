package ru.urbanlabs.domain.equipment.parser;

import ru.urbanlabs.model.telemetry.BaseTelemetry;

import java.util.List;

public abstract class BaseIotDataParser {

    public abstract List<BaseTelemetry> parseDate(byte[] data);
}
