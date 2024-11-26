import java.util.ArrayList;

public class UserShip {
    String owner;
    ShipModel shipModel;
    ArrayList<Module> userShipCoreModules;
    FSD frameShiftDrive;
    ArrayList<Module> userShipOptionalModules;
    double totalMass;

    //Constructor method 
    public UserShip (String uOwner, ShipModel uShipModel, ArrayList<Module> uUserShipCoreModules, FSD uFrameShiftDrive, ArrayList<Module> uUserShipOptionalModules) {
        this.owner = uOwner;
        this.shipModel = uShipModel;
        this.userShipCoreModules = uUserShipCoreModules;
        this.frameShiftDrive = uFrameShiftDrive;
        this.userShipOptionalModules = uUserShipOptionalModules;
    }

    //Setters
    public void setOwner (String sOwner) {
        this.owner = sOwner;
    }
    public void setShipModel (ShipModel sShipModel) {
        this.shipModel = sShipModel;
    }
    public void setUserShipCoreModulesArray (ArrayList<Module> sUserShipCoreModules) {
        this.userShipCoreModules = sUserShipCoreModules;
    }
    public void setUserShipCoreModule (Module sModule, int i) {
        this.userShipCoreModules.set(i, sModule);
    }
    public void setFSD (FSD sFrameShiftDrive) {
        this.frameShiftDrive = sFrameShiftDrive;
    }
    public void setUserShipOptionalModulesArray (ArrayList<Module> sUserShipOptionalModules) {
        this.userShipOptionalModules = sUserShipOptionalModules;
    }
    public void setUserShipOptionalModule (Module sModule, int i) {
        this.userShipOptionalModules.set(i, sModule);
    }
    public void setTotalMass (double sTotalMass) {
        this.totalMass = sTotalMass;
    }

    //Getters
    public String getOwner () {
        return this.owner;
    }
    public ShipModel getShipModel () {
        return shipModel;
    }
    public ArrayList<Module> getUserShipCoreModulesArray () {
        return userShipCoreModules;
    }
    public Module getUserShipCoreModule (int i) {
        return userShipCoreModules.get(i);
    }
    public FSD getUserShipFSD () {
        return frameShiftDrive;
    }
    public ArrayList<Module> getUserShipOptionalModulesArray () {
        return userShipOptionalModules;
    }
    public Module getUserShipOptionalModule (int i) {
        return userShipOptionalModules.get(i);
    }
    public double getTotalMass () {
        return totalMass;
    }

    //Calculate total mass
    public void calculateTotalMass () {
        this.totalMass = userShipCoreModules.get(0).getMass() + userShipCoreModules.get(1).getMass() + userShipCoreModules.get(2).getMass() + userShipCoreModules.get(3).getMass() + userShipCoreModules.get(4).getMass() + userShipCoreModules.get(5).getMass() + shipModel.getHullMass();
    }

    //ToString
    public String toString () {
        return owner + " " + '\n' + shipModel.getShipName() + '\n' + userShipCoreModules.get(0) + '\n' + userShipCoreModules.get(1) + '\n' + frameShiftDrive + '\n' + userShipCoreModules.get(2) + '\n' + userShipCoreModules.get(3) + '\n' + userShipCoreModules.get(4) + '\n' + userShipCoreModules.get(5) + '\n' + userShipOptionalModules;
    }

    

    //Add module to X position

    //Set the array list length for optional modules.
}
