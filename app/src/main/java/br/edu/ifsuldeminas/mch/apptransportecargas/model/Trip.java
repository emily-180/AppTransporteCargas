package br.edu.ifsuldeminas.mch.apptransportecargas.model;

import java.io.Serializable;

public class Trip implements Serializable {
    private Integer id;
    private String driverName;
    private String date;
    private String origin;
    private String destination;
    private String serviceType;

    public Trip() {
    }

    public Trip(Integer id, String driverName, String date, String origin, String destination, String serviceType) {
        this.id = id;
        this.driverName = driverName;
        this.date = date;
        this.origin = origin;
        this.destination = destination;
        this.serviceType = serviceType;
    }

    public Integer getId() {
        return id;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getDate() {
        return date;
    }

    public String getOrigin() {
        return origin;
    }

    public String getDestination() {
        return destination;
    }

    public String getServiceType() {
        return serviceType;
    }

    @Override
    public String toString() {
        return String.format("Data: %s\nMotorista: %s\nDestino: %s\nOrigem: %s\nServiço e Custo: %s",
                date, driverName, destination, origin, serviceType);
    }

}
