package org.example.domain.equipment.parser.impl.powersocket;

import org.example.domain.equipment.parser.BaseIotDataParser;
import org.example.model.telemetry.BaseTelemetry;

import java.util.List;

public class MockPowerSocketDataParser extends BaseIotDataParser {

    @Override
    public List<BaseTelemetry> parseDate(byte[] data) {
        return null;
    }
}
