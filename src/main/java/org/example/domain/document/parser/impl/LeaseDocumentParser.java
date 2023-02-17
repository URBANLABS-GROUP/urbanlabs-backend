package org.example.domain.document.parser.impl;

import org.example.domain.document.parser.BaseDocumentParser;
import org.example.model.telemetry.impl.DocumentTelemetry;

import java.util.List;

public class LeaseDocumentParser extends BaseDocumentParser {

    @Override
    public List<DocumentTelemetry> parseDate(byte[] data) {
        return null;
    }
}
