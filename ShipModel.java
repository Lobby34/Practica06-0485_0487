public class ShipModel {
    private String shipName;
    private double hullMass;
    private int[] maxModuleClasses = new int[8];

    //Constructor method
    public ShipModel (String sShipName, double sHullMass, int sMaxPowerPlant, int sMaxFSD, int sMaxThrusters, int sMaxLifeSuport, int sMaxPowerDistributor, int sMaxSensors, int sMaxFuelTank) {
        this.shipName = sShipName;
        this.hullMass = sHullMass;
        this.maxModuleClasses[1] = sMaxPowerPlant;
        this.maxModuleClasses[2] = sMaxThrusters;
        this.maxModuleClasses[3] = sMaxFSD;
        this.maxModuleClasses[4] = sMaxLifeSuport;
        this.maxModuleClasses[5] = sMaxPowerDistributor;
        this.maxModuleClasses[6] = sMaxSensors;
        this.maxModuleClasses[7] = sMaxFuelTank;
    }

    //Setters
    public void setShipName (String sShipName) {
        this.shipName = sShipName;
    }
    public void setHullMass (double sHullMass) {
        this.hullMass = sHullMass;
    }
    public void setMaxModuleClasses (int[] sMaxModuleClasses) {
        this.maxModuleClasses = sMaxModuleClasses;
    }
    public void setMaxPowerPlant (int sMaxClass) {
        this.maxModuleClasses[1] = sMaxClass;
    }
    public void setMaxThrusters (int sMaxClass) {
        this.maxModuleClasses[2] = sMaxClass;
    }
    public void setMaxFSD (int sMaxClass) {
        this.maxModuleClasses[3] = sMaxClass;
    }
    public void setMaxLifeSuport (int sMaxClass) {
        this.maxModuleClasses[4] = sMaxClass;
    }
    public void setMaxPowerDistributor (int sMaxClass) {
        this.maxModuleClasses[5] = sMaxClass;
    }
    public void setMaxSensors (int sMaxClass) {
        this.maxModuleClasses[6] = sMaxClass;
    }
    public void setMaxFuelTank (int sMaxClass) {
        this.maxModuleClasses[7] = sMaxClass;
    }
    
    //Getters
    public String getShipName () {
        return shipName;
    }
    public double getHullMass () {
        return hullMass;
    }
    public int[] getMaxModuleClasses () {
        return maxModuleClasses;
    }
    public int getMaxPowerPlant () {
        return maxModuleClasses[1];
    }
    public int getMaxThrusters () {
        return maxModuleClasses[2];
    }
    public int getMaxFSD () {
        return maxModuleClasses[3];
    }
    public int getMaxLifeSuport () {
        return maxModuleClasses[4];
    }
    public int getMaxPowerDistributor () {
        return maxModuleClasses[5];
    }
    public int getMaxSensors () {
        return maxModuleClasses[6];
    }
    public int getMaxFuelTank () {
        return maxModuleClasses[7];
    }

    //Max module class searching
    public int getMaxModuleClassByArrayPosition (int i) {
        return maxModuleClasses[i];
    }

    //ToString
    public String toString () {
        return shipName + " " + hullMass + " " + maxModuleClasses[1] + " " + maxModuleClasses[2] + " " + maxModuleClasses[3] + " " + maxModuleClasses[4] + " " + maxModuleClasses[5] + " " + maxModuleClasses[6] + " " + maxModuleClasses[7];
    }
}