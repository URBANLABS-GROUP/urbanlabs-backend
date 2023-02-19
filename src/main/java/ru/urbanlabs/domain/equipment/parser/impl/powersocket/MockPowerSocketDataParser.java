package ru.urbanlabs.domain.equipment.parser.impl.powersocket;

import ru.urbanlabs.domain.equipment.parser.BaseIotDataParser;
import ru.urbanlabs.model.telemetry.BaseTelemetry;

import java.util.List;

public class MockPowerSocketDataParser extends BaseIotDataParser {

    @Override
    public List<BaseTelemetry> parseDate(byte[] data) {
        return null;
    }
}
