package org.example.domain.document.parser;

import org.example.model.telemetry.impl.DocumentTelemetry;
import org.example.model.telemetry.impl.IotTelemetry;

import java.util.List;

public abstract class BaseDocumentParser {

    public abstract List<DocumentTelemetry> parseDate(byte[] data);
}
