package ru.urbanlabs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.urbanlabs.dao.DaoFactory;
import ru.urbanlabs.dto.Plot;
import ru.urbanlabs.model.businesscenter.BusinessCenter;
import ru.urbanlabs.model.businesscenter.BusinessCenterStorey;
import ru.urbanlabs.model.businesscenter.Room;
import ru.urbanlabs.model.iot.equipment.EquipmentType;
import ru.urbanlabs.model.iot.equipment.impl.powersocket.PowerSocket;
import ru.urbanlabs.model.iot.equipment.impl.temp.TempSensor;
import ru.urbanlabs.model.telemetry.impl.iot.PowerSocketTelemetry;
import ru.urbanlabs.model.telemetry.impl.iot.TempSensorTelemetry;
import ru.urbanlabs.util.DateTimeUtils;
import ru.urbanlabs.util.Interval;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlotService {

    private final DaoFactory daoFactory;

    @Autowired
    public PlotService(final DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public List<Plot<Integer>> buildTempPlot(BusinessCenter businessCenter, Instant from, Instant to) {
        List<BusinessCenterStorey> storeys = daoFactory.getBusinessCenterStoreyRepository().findAllById(businessCenter.getStoreys().stream()
            .map(BusinessCenterStorey::getId)
            .collect(Collectors.toList()));

        List<Room> rooms = storeys.stream()
            .map(BusinessCenterStorey::getRooms)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

        List<Plot<Integer>> plots = new ArrayList<>();
        for (final Room room : rooms) {
            final List<Interval> intervals = DateTimeUtils.splitByDays(Interval.of(from, to), ZoneId.of("UTC"));
            final List<TempSensor> allByRoomIdIn = daoFactory.getTempSensorRepository().findAllByRoomIdIn(Collections.singletonList(room.getId()));

            for (final TempSensor tempSensor : allByRoomIdIn) {
                final List<Plot.Point<Integer>> points = new ArrayList<>();
                for (final Interval interval : intervals) {
                    final List<TempSensorTelemetry> telemetries = daoFactory.getTempSensorTelemetryRepository()
                        .findAllByEquipmentTypeEqualsAndEquipmentIdInAndFixTimeGreaterThanEqualAndFixTimeLessThanEqualOrderByFixTimeAsc
                            (EquipmentType.TEMP, Collections.singletonList(tempSensor.getId()), interval.getFrom(), interval.getTo());

                    if (telemetries.isEmpty()) {
                        continue;
                    }

                    final Double averageTemp = telemetries.stream()
                        .map(TempSensorTelemetry::getTemp)
                        .mapToInt(Integer::intValue)
                        .average()
                        .getAsDouble();

                    points.add(Plot.Point.of(interval.getFrom(), averageTemp.intValue()));
                }

                final Plot<Integer> plot = new Plot<>();
                plot.setName(room.getName());
                plot.setPoints(points);
                plots.add(plot);
            }
        }
        return plots;
    }

    public List<Plot<Integer>> buildPowerPlot(BusinessCenter businessCenter, Instant from, Instant to) {
        List<BusinessCenterStorey> storeys = daoFactory.getBusinessCenterStoreyRepository().findAllById(businessCenter.getStoreys().stream()
            .map(BusinessCenterStorey::getId)
            .collect(Collectors.toList()));

        List<Room> rooms = storeys.stream()
            .map(BusinessCenterStorey::getRooms)
            .flatMap(Collection::stream)
            .collect(Collectors.toList());

        List<Plot<Integer>> plots = new ArrayList<>();
        for (final Room room : rooms) {
            final List<Interval> intervals = DateTimeUtils.splitByDays(Interval.of(from, to), ZoneId.of("UTC"));
            final List<PowerSocket> allByRoomIdIn = daoFactory.getPowerSocketRepository().findAllByRoomIdIn(Collections.singletonList(room.getId()));

            for (final PowerSocket powerSocket : allByRoomIdIn) {
                final List<Plot.Point<Integer>> points = new ArrayList<>();
                for (final Interval interval : intervals) {
                    final List<PowerSocketTelemetry> telemetries = daoFactory.getPowerSocketTelemetryRepository()
                        .findAllByEquipmentTypeEqualsAndEquipmentIdInAndFixTimeGreaterThanEqualAndFixTimeLessThanEqualOrderByFixTimeAsc
                            (EquipmentType.POWER_SOCKET, Collections.singletonList(powerSocket.getId()), interval.getFrom(), interval.getTo());

                    if (telemetries.isEmpty()) {
                        continue;
                    }

                    int watt = telemetries.get(telemetries.size() - 1).getWatt() - telemetries.get(0).getWatt();

                    points.add(Plot.Point.of(interval.getFrom(), watt));
                }

                final Plot<Integer> plot = new Plot<>();
                plot.setName(room.getName());
                plot.setPoints(points);
                plots.add(plot);
            }
        }
        return plots;
    }
}
