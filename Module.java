public class Module {
    //Variables
    private int slot;                   
            /*Slots follow the next order: 
                1-BulkHead --Not used bc every ship has its own set of bulkheads, i.e. Adder's bulk heads weight 0-4-8 Tones, while Mandalay's Weight 25-50-75 Tones. We will assume all ships will have lightweight bulkhead.
                2-Power Plant
                3-Thrusters
                4-FSD --Not in use bc they have their own class with all the info.
                5-Life Support
                6-Power distributor
                7-Sensors
                8-Fuel Tank 
                9-Optional Internal
            */
    private double mass;
    private Character ratingCharacter;
    private int classNumber;

    //Mètode constructor
    public Module (int mSlot, int mClassNumber, Character mRatingCharacter, double mMass) {
        this.slot = mSlot;
        this.classNumber = mClassNumber;
        this.ratingCharacter = mRatingCharacter;
        this.mass = mMass;
    }

    //Setters
    public void setSlot (int sSlot) {
        this.slot = sSlot;
    }
    public void setClassNumber (int sClassNumber) {
        this.classNumber = sClassNumber;
    }
    public void setRatingCharacter (Character sRatinCharacter) {
        this.ratingCharacter = sRatinCharacter;
    }
    public void setMass (double sMass) {
        this.mass = sMass;
    }

    //Getters
    public int getSlot () {
        return slot;
    }
    public int getClassNumber () {
        return classNumber;
    }
    public Character getRatingCharacter () {
        return ratingCharacter;
    }
    public double getMass () {
        return mass;
    }

    //toString to show to user with the correct format
    public String toString () {
        String slotName[] = {"","BulkHead", "Power Plant","Thrusters","Frame Shift Drive (FSD)","Life Support","Power Distributor","Sensors","Fuel Tank","Optional Internal"};
        return "||" + classNumber + ratingCharacter + " " + slotName[slot] + "||"+ '\n' + "Mass: " + mass + "(t)" + '\n';
    }
}
