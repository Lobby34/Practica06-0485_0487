import java.util.ArrayList;

public class UserShip {
    User owner;
    ShipModel shipModel;
    ArrayList<Module> userShipCoreModules;
    FSD frameShiftDrive;
    ArrayList<Module> userShipOptionalModules;
    double totalMass;

    //Constructor method 
    public UserShip (User uOwner, ShipModel uShipModel, ArrayList<Module> uUserShipCoreModules, FSD uFrameShiftDrive, ArrayList<Module> uUserShipOptionalModules) {
        this.owner = uOwner;
        this.shipModel = uShipModel;
        this.userShipCoreModules = uUserShipCoreModules;
        this.frameShiftDrive = uFrameShiftDrive;
        this.userShipOptionalModules = uUserShipOptionalModules;
    }

    //Setters

    //Getters
    public ShipModel getShipModel () {
        return shipModel;
    }
    public Module getUserShipCoreModules (int i) {
        return userShipCoreModules.get(i);
    }
    public FSD getUserShipFSD () {
        return frameShiftDrive;
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
