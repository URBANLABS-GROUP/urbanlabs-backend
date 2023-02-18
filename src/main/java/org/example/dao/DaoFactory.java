package org.example.dao;

import org.example.repository.*;
import org.example.repository.equipment.MoveSensorRepository;
import org.example.repository.equipment.PowerSocketRepository;
import org.example.repository.equipment.SmokeSensorRepository;
import org.example.repository.equipment.TempSensorRepository;
import org.example.repository.telemetry.MoveSensorTelemetryRepository;
import org.example.repository.telemetry.PowerSocketTelemetryRepository;
import org.example.repository.telemetry.SmokeSensorTelemetryRepository;
import org.example.repository.telemetry.TempSensorTelemetryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DaoFactory {

    private final BusinessCenterRepository businessCenterRepository;
    private final BusinessCenterStoreyRepository businessCenterStoreyRepository;
    private final RoomRepository roomRepository;
    private final LessorRepository lessorRepository;
    private final LesseeRepository lesseeRepository;
    private final LeaseEventRepository leaseEventRepository;
    private final LeaseContractRepository leaseContractRepository;
    private final RequestRepository requestRepository;
    private final PowerSocketRepository powerSocketRepository;
    private final PowerSocketTelemetryRepository powerSocketTelemetryRepository;
    private final TempSensorRepository tempSensorRepository;
    private final TempSensorTelemetryRepository tempSensorTelemetryRepository;
    private final SmokeSensorRepository smokeSensorRepository;
    private final SmokeSensorTelemetryRepository smokeSensorTelemetryRepository;
    private final MoveSensorRepository moveSensorRepository;
    private final MoveSensorTelemetryRepository moveSensorTelemetryRepository;
    private final CheckRepository checkRepository;

    @Autowired
    public DaoFactory(final BusinessCenterRepository businessCenterRepository,
                      final BusinessCenterStoreyRepository businessCenterStoreyRepository,
                      final RoomRepository roomRepository,
                      final LessorRepository lessorRepository,
                      final LesseeRepository lesseeRepository,
                      final LeaseEventRepository leaseEventRepository,
                      final LeaseContractRepository leaseContractRepository,
                      final RequestRepository requestRepository,
                      final PowerSocketRepository powerSocketRepository,
                      final PowerSocketTelemetryRepository powerSocketTelemetryRepository,
                      final TempSensorRepository tempSensorRepository,
                      final TempSensorTelemetryRepository tempSensorTelemetryRepository,
                      final SmokeSensorRepository smokeSensorRepository,
                      final SmokeSensorTelemetryRepository smokeSensorTelemetryRepository,
                      final MoveSensorRepository moveSensorRepository,
                      final MoveSensorTelemetryRepository moveSensorTelemetryRepository,
                      final CheckRepository checkRepository) {
        this.businessCenterRepository = businessCenterRepository;
        this.businessCenterStoreyRepository = businessCenterStoreyRepository;
        this.roomRepository = roomRepository;
        this.lessorRepository = lessorRepository;
        this.lesseeRepository = lesseeRepository;
        this.leaseEventRepository = leaseEventRepository;
        this.leaseContractRepository = leaseContractRepository;
        this.requestRepository = requestRepository;
        this.powerSocketRepository = powerSocketRepository;
        this.powerSocketTelemetryRepository = powerSocketTelemetryRepository;
        this.tempSensorRepository = tempSensorRepository;
        this.tempSensorTelemetryRepository = tempSensorTelemetryRepository;
        this.smokeSensorRepository = smokeSensorRepository;
        this.smokeSensorTelemetryRepository = smokeSensorTelemetryRepository;
        this.moveSensorRepository = moveSensorRepository;
        this.moveSensorTelemetryRepository = moveSensorTelemetryRepository;
        this.checkRepository = checkRepository;
    }

    public BusinessCenterRepository getBusinessCenterRepository() {
        return businessCenterRepository;
    }

    public BusinessCenterStoreyRepository getBusinessCenterStoreyRepository() {
        return businessCenterStoreyRepository;
    }

    public RoomRepository getRoomRepository() {
        return roomRepository;
    }

    public LessorRepository getLessorRepository() {
        return lessorRepository;
    }

    public LesseeRepository getLesseeRepository() {
        return lesseeRepository;
    }

    public LeaseEventRepository getLeaseEventRepository() {
        return leaseEventRepository;
    }

    public LeaseContractRepository getLeaseContractRepository() {
        return leaseContractRepository;
    }

    public RequestRepository getRequestRepository() {
        return requestRepository;
    }

    public PowerSocketRepository getPowerSocketRepository() {
        return powerSocketRepository;
    }

    public PowerSocketTelemetryRepository getPowerSocketTelemetryRepository() {
        return powerSocketTelemetryRepository;
    }

    public TempSensorRepository getTempSensorRepository() {
        return tempSensorRepository;
    }

    public TempSensorTelemetryRepository getTempSensorTelemetryRepository() {
        return tempSensorTelemetryRepository;
    }

    public SmokeSensorRepository getSmokeSensorRepository() {
        return smokeSensorRepository;
    }

    public SmokeSensorTelemetryRepository getSmokeSensorTelemetryRepository() {
        return smokeSensorTelemetryRepository;
    }

    public MoveSensorRepository getMoveSensorRepository() {
        return moveSensorRepository;
    }

    public MoveSensorTelemetryRepository getMoveSensorTelemetryRepository() {
        return moveSensorTelemetryRepository;
    }

    public CheckRepository getCheckRepository() {
        return checkRepository;
    }
}
