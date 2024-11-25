public class FSD extends Module {
    private double optimalMass;
    private double maxFuelJump;
    private double classConstant;
    private double ratingConstant;

    //Metode constructor
    public FSD (int slot, int classNumber, Character ratingCharacter, double mass, double mOptimalMass, double mMaxFuelJump, double mClassConstant, double mRatingConstant) {
        super(slot, classNumber, ratingCharacter, mass);
        this.optimalMass = mOptimalMass;
        this.maxFuelJump = mMaxFuelJump;
        this.classConstant = mClassConstant;
        this.ratingConstant = mRatingConstant;
    }
    //Secondary Constructor to be able to insert "null" in rating constant and class constant
    public FSD (int slot, int classNumber, Character ratingCharacter, double mass, double mOptimalMass, double mMaxFuelJump) {
        super(slot, classNumber, ratingCharacter, mass);
        this.optimalMass = mOptimalMass;
        this.maxFuelJump = mMaxFuelJump;
    }

    //Setters
    public void setOptimalMass (double sOptimalMass) {
        this.optimalMass = sOptimalMass;
    }
    public void setMaxFuelJump (double sMaxFuelJump) {
        this.maxFuelJump = sMaxFuelJump;
    }
    public void setClassConstant (double sClassConstant) {
        this.classConstant = sClassConstant;
    }
    public void setRatingConstant (double sRatingConstant) {
        this.ratingConstant = sRatingConstant;
    }

    //Getters
    public double getOptimalMass () {
        return optimalMass;
    }
    public double getMaxFuelJump () {
        return maxFuelJump;
    }
    public double getClassConstant () {
        return classConstant;
    }
    public double getRatingConstant () {
        return ratingConstant;
    }

    //Calculate the Class and Rating coefficients for every. Doing it here instead of in the .txt this way I dont have to copy every single time the same numbers + its easier to change them.
    public void setClassRating (int[] ratingCoeficient, double[] classCoeficient) {
        this.ratingConstant = ratingCoeficient[getRatingCharacter()-'A'];
        this.classConstant = classCoeficient[getClassNumber()-1];
    }

    //To string
    public String toString () {
        return "Module: ||" + getClassNumber() + getRatingCharacter() + " FSD||"+ '\n' + "Mass: " + getMass() + "(t)" + '\n';
    }
}
