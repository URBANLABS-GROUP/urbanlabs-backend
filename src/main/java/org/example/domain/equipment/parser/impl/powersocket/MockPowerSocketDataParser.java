package org.example.domain.equipment.parser.impl.powersocket;

import org.example.domain.equipment.parser.BaseIotDataParser;
import org.example.model.telemetry.impl.IotTelemetry;

import java.util.List;

public class MockPowerSocketDataParser extends BaseIotDataParser {

    @Override
    public List<IotTelemetry> parseDate(byte[] data) {
        return null;
    }
}
