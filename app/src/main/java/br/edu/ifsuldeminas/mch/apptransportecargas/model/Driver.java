package br.edu.ifsuldeminas.mch.apptransportecargas.model;

import com.google.android.gms.maps.model.LatLng;

public class Driver {
    private String id;
    private String name;
    private LatLng location; //localização
    private String truckModel; //modelo do caminhao
    private String licensePlate; //placa

    public Driver(String id, String name, LatLng location, String truckModel, String licensePlate) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.truckModel = truckModel;
        this.licensePlate = licensePlate;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LatLng getLocation() {
        return location;
    }

    public String getTruckModel() {
        return truckModel;
    }

    public String getLicensePlate() {
        return licensePlate;
    }
}
