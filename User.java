import java.util.ArrayList;

public class User {
    // public static void main (String[] args) {
    //     String password = "1234";
    //     String encodedPassword = Base64.getEncoder().encodeToString(password.getBytes());
    //     System.out.println(encodedPassword);
    //}

    private String name;
    private String password;
    private ArrayList<UserShip> userShips;

    //Constructor Method
    public User (String uName, String uPassword, ArrayList<UserShip> uUserShips) {
        this.name = uName;
        this.password = uPassword;
        this.userShips = uUserShips;
    }

    //Setters
    public void setName (String sName) {
        this.name = sName;
    }
    public void setPassword (String sPassword) {
        this.password = sPassword;
    }
    public void setUserShipsArray (ArrayList<UserShip> sUserShips) {
        this.userShips = sUserShips;
    }
    public void setUserShip (UserShip sUserShip, int i) {
        this.userShips.set(i, sUserShip);
    }

    //Getters
    public String getName () {
        return name;
    }
    public String getPassword () {
        return password;
    }
    public ArrayList<UserShip> getUserShipsArray () {
        return userShips;
    }
    public UserShip getUserShip (int i) {
        return userShips.get(i);
    }
}
