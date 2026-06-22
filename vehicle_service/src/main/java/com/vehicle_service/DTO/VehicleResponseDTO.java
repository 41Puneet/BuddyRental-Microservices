package com.vehicle_service.DTO;
import java.util.UUID;
import com.vehicle_service.Enums.FuelType;
import com.vehicle_service.Enums.TransmissionType;
import com.vehicle_service.Enums.VehicleType;

public class VehicleResponseDTO {

    private UUID vehicleId;
    private String vehicleNumber;
    private UUID ownerId;
    private String brand;
    private String model;
    private Double pricePerDay;
    private Double securityPrice;
    private Double AdvancePayment;
    private Integer manufacturingYear;
    private String city;
    private VehicleType vehicleType;
    private FuelType fuelType;
    private TransmissionType transmissionType;
    private Boolean isAvailable;

    public VehicleResponseDTO(){

    }
    public VehicleResponseDTO(UUID vehicleId,String vehicleNumber,UUID ownerId,String brand,String model,VehicleType vehicleType,FuelType fuelType,TransmissionType transmissionType,Double pricePerDay,Double securityPrice,Double AdvancePayment,Integer manufacturingYear,String city,Boolean isAvailable){
        this.vehicleId=vehicleId;
        this.vehicleNumber=vehicleNumber;
        this.ownerId=ownerId;
        this.brand=brand;
        this.model=model;
        this.vehicleType=vehicleType;
        this.fuelType=fuelType;
        this.transmissionType=transmissionType;
        this.pricePerDay=pricePerDay;
        this.securityPrice=securityPrice;
        this.AdvancePayment=AdvancePayment;
        this.manufacturingYear=manufacturingYear;
        this.city=city;
        this.isAvailable=isAvailable;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public UUID getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(UUID vehicleId) {
        this.vehicleId = vehicleId;
    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(UUID ownerId) {
        this.ownerId = ownerId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public TransmissionType getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(TransmissionType transmissionType) {
        this.transmissionType = transmissionType;
    }

    public Double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public Double getSecurityPrice() {
        return securityPrice;
    }

    public void setSecurityPrice(Double securityPrice) {
        this.securityPrice = securityPrice;
    }

    public Double getAdvancePayment() {
        return AdvancePayment;
    }

    public void setAdvancePayment(Double AdvancePayment) {
        this.AdvancePayment = AdvancePayment;
    }

    public Integer getManufacturingYear() {
        return manufacturingYear;
    }

    public void setManufacturingYear(Integer manufacturingYear) {
        this.manufacturingYear = manufacturingYear;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public Boolean getIsAvailable() {
        return isAvailable;
    }
    public void setIsAvailable(Boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    
}
