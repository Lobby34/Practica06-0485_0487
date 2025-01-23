import java.io.FileWriter;                              //Dependency here to write in .txt files
import java.io.IOException;                             //Dependency here to handle IO Exceptions when writting and reading files.
import java.time.LocalDate;                             //Dependency here so we can create different logs depending on the date
import java.time.LocalTime;                             //Dependency here so we can show a time inside the logs.
import java.util.Base64;                                //Dependency here to be able to "Encrypt" and "Decrypt" passwords from and to Base64.
import java.io.File;                                    //Dependency here to import and read files .xml
import java.io.FileOutputStream;
import java.util.ArrayList;                             //Dependency here to be able to create Dynamic Arrays of Objects, Strings, ints...
import java.util.Scanner;                               //Dependency here to Scan files as well as user inputs.

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.*;

import classes.FSD;
import classes.Module;
import classes.ShipModel;
import classes.User;
import classes.UserShip;                                   

public class Methods {
    //JumpRange Calculator
    public static double CalculateJumpRange (UserShip userShip) {
        userShip.calculateTotalMass();
        double totalJumpRange = ((Math.pow(1000, (1/userShip.getUserShipFSD().getClassConstant()))*userShip.getUserShipFSD().getOptimalMass()*Math.pow((userShip.getUserShipFSD().getMaxFuelJump()/userShip.getUserShipFSD().getRatingConstant()),(1/userShip.getUserShipFSD().getClassConstant())))/userShip.getTotalMass());
        return totalJumpRange;
    }

    //Know User position in the array of the ship models
    public static int UserArrayPosition (ArrayList<User> users, String userProvided) {
        int userPosition = 0;
        for (int i = 0; i < users.size(); i++) {
            if (userProvided.equals(users.get(i).getName())) {
                userPosition = i;
            }
        }
        return userPosition;
    }

    //Know ShipModel position in the array of ship models
    public static int ShipModelArrayPosition (ArrayList<ShipModel> shipModels, String shipModelProvided) {
        int shipModelPosition = 0;
        for (int i = 0; i < shipModels.size(); i++) {
            if (shipModelProvided.equals(shipModels.get(i).getShipName())) {
                shipModelPosition = i;
            }
        }
        return shipModelPosition;
    }

    //Know FSD position in the array of FSD thorugh Class and Rating from the FSD.txt File
    public static int GetFSDArrayPosition (ArrayList<FSD> fsds, String identidierProvided) {
        int FSDArrayPosition = 0;
        String[] FSDArrayProvided = identidierProvided.split(" ");
        for (int i = 0; i < fsds.size(); i++) {
            if (fsds.get(i).getClassNumber() == Integer.parseInt(FSDArrayProvided[0]) && fsds.get(i).getRatingCharacter().equals(FSDArrayProvided[1].charAt(0))) {
                   FSDArrayPosition = i;
            }
        }
        return FSDArrayPosition;
    }

    //Know FSD position in the array of FSD thorugh Class and Rating  from the Imput provided by the user.   
    public static int GetFSDArrayPositionWithUserInput (ArrayList<FSD> fsds, String identidierProvided) {
        int FSDArrayPosition = 0;

        for (int i = 0; i < fsds.size(); i++) {
            if (fsds.get(i).getClassNumber() == identidierProvided.charAt(0)-'0' && fsds.get(i).getRatingCharacter().equals(identidierProvided.charAt(1))) {
                   FSDArrayPosition = i;
            }
        }
        return FSDArrayPosition;
    }

    //Know in what position a given module is in an array.
    public static int GetModuleArrayPosition (ArrayList<Module> modules, int moduleType, String identidierProvided) {
        int moduleArrayPosition = 0;
        for (int i = 0; i < modules.size(); i++) {
            if (modules.get(i).getSlot() == moduleType && modules.get(i).getClassNumber() == identidierProvided.charAt(0)-'0' && modules.get(i).getRatingCharacter().equals(identidierProvided.charAt(1))) {
                   moduleArrayPosition = i;
            }
        }
        return moduleArrayPosition;
    }

    //Know the Array positions of all the CORE modules that a provided ship has.
    public static ArrayList<Module> GetCoreModulesArrayPositions (ArrayList<Module> modules, String modulesProvided) {
        ArrayList<Module> coreModulesArrayPositions = new ArrayList<>();
        String[] modulesProvidedArray = modulesProvided.split(",");
        for (int i = 0; i < modulesProvidedArray.length; i++) {
            for (int j = 0; j < modules.size(); j++) {
                if (modules.get(j).getSlot() == (modulesProvidedArray[i].charAt(0)-'0') && modules.get(j).getClassNumber() == (modulesProvidedArray[i].charAt(2)-'0') && modules.get(j).getRatingCharacter().equals(modulesProvidedArray[i].charAt(4))) {
                    coreModulesArrayPositions.add(modules.get(j));
                }
            }
        }
        return coreModulesArrayPositions;
    }

    //Calculate the total mass of all the UserShips
    public static void calculateTotalMassOfUserShipsInArray (ArrayList<UserShip> userShips) {
        for (int i = 0; i < userShips.size(); i++) {
            userShips.get(i).calculateTotalMass();
        }
    }

    //Password validator
    public static boolean UserAutentication (ArrayList<User> userList, String userProvided, String passwordProvided) {
        boolean autenticationResult = false;
        passwordProvided =  Base64.getEncoder().encodeToString(passwordProvided.getBytes());
        for (int i = 0; i < userList.size(); i++) {
            boolean userAutentication = userProvided.equals(userList.get(i).getName());
            boolean passwordAutentication = passwordProvided.equals(userList.get(i).getPassword());
            if (userAutentication && passwordAutentication) {
                autenticationResult = true;
            } else {}
        }
        
        return autenticationResult;
    }

    //User option selection 
    public static int MenuOptionSelection (String userName, Scanner inUser) {
        int optionSelectionA = 0;
        System.out.println("What would you like to do, " + userName + "?" + '\n' + "Please type the number of the option you would like to perform.");
        System.out.println("||1. Calculate the max Jump Range of a ship." + '\n' + "||2. See the ships you currently have in your hangar." + '\n' + "||3. Add a new ship to the Hangar." + '\n' + "||4. Sell a ship from your hangar." + '\n' + "||5. Modify the modules of one of your Ships." + '\n' + "||6. Log off & Save" + '\n' +"||7. Shut down the program & Save" + '\n');
        System.out.print("Your Selection: "); 
        try {
            optionSelectionA = inUser.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid selection. Try again.");
            //this varaible is here so the Try catch doesnt get into an infinite loop if it gives an error the frist time
            inUser.next();
        }
        return optionSelectionA;
    }

    //Module menu Selection
    public static int ModuleMenuOptionSelection (ArrayList<Module> modules, Scanner inUser, ArrayList<FSD> fsds) {
        int optionSelectionB = 0;
        Boolean validSelection = false;
        // ClearConsole();
        System.out.println("Which module slot would you like to change?");
        System.out.println("||1. Power Plant." + '\n' + "||2. Thrusters." + '\n' + "||3. FSD." + '\n' + "||4. Life Support." + '\n' + "||5. Power Distributor." + '\n' + "||6. Sensors" + '\n' +"||7. Fuel Tank" + '\n');
        System.out.print("Your Selection: ");
        while (!validSelection) {
            try {
                optionSelectionB = inUser.nextInt()+1;
                validSelection = true;
            } catch (Exception e) {
                System.out.println("Invalid selection. Try again.");
                //this varaible is here so the Try catch doesnt get into an infinite loop if it gives an error the frist time
                inUser.next();
            }
        }
        if (optionSelectionB != 4) {
            for (int i = 0; i < modules.size(); i++) {
                if (optionSelectionB == modules.get(i).getSlot()) {
                    System.out.println(modules.get(i));
                }
            }
        } else {
            for (int i = 0; i < fsds.size(); i++) {
                System.out.println(fsds.get(i));
            }
        }
        System.out.print('\n' + "Your Selection: ");
        return optionSelectionB;
    }

    //Show the ships the User OWNS.
    public static void ShowUserShips (int userPosition, ArrayList<User> users, Boolean lastOption) {
        int i = 0;
        for (i = 0; i < users.get(userPosition).getUserShipsArray().size(); i++) {
            System.out.println("||" + (i+1) + ".    " + users.get(userPosition).getUserShip(i).getShipModel().getShipName() + " || " + users.get(userPosition).getUserShip(i).getTotalMass() + "[T]");
        }
        if (lastOption) {
            System.out.println("||" + (i+1) + ".    Cancel Operation.");
            System.out.print('\n' + "Your Selection: ");
        }

    }

    //Write in the file UserShip.txt all the ships provided thorugh an Array, like The current user array, or the unused ships array.
    public static void WriteUserShipFile (ArrayList<User> users, int userPosition, ArrayList<UserShip> userShipsArray, ArrayList<UserShip> shipsNotUsedArray) {
        try {  
    	    //Creating a DocumentBuilder Object 
            DocumentBuilderFactory docFactoryWriteUserShip = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilderWriteUserShip = docFactoryWriteUserShip.newDocumentBuilder();
         
            //Creating a new xml Document       
            Document docWriteUserShip = docBuilderWriteUserShip.newDocument();
            Element rootElementWriteUserShip = docWriteUserShip.createElement("UserShips");
            docWriteUserShip.appendChild(rootElementWriteUserShip);
         
            //Adding new department element
            for (int i = 0; i < userShipsArray.size(); i++) {
                Element UserShipWriteUserShip = docWriteUserShip.createElement("UserShip");
                Element OwnerWriteUserShip = docWriteUserShip.createElement("Owner");
                Element ShipModelWriteUserShip = docWriteUserShip.createElement("ShipModel");
                Element CoreInternalWriteUserShip = docWriteUserShip.createElement("CoreInternal");
                Element FSDWriteUserShip = docWriteUserShip.createElement("FSD");
                UserShipWriteUserShip.appendChild(OwnerWriteUserShip);
                UserShipWriteUserShip.appendChild(ShipModelWriteUserShip);
                UserShipWriteUserShip.appendChild(CoreInternalWriteUserShip);
                UserShipWriteUserShip.appendChild(FSDWriteUserShip);
                OwnerWriteUserShip.appendChild(docWriteUserShip.createTextNode(users.get(userPosition).getName()));
                ShipModelWriteUserShip.appendChild(docWriteUserShip.createTextNode(users.get(userPosition).getUserShip(i).getShipModel().getShipName()));
                CoreInternalWriteUserShip.appendChild(docWriteUserShip.createTextNode(users.get(userPosition).getUserShip(i).modulesToString()));
                FSDWriteUserShip.appendChild(docWriteUserShip.createTextNode(users.get(userPosition).getUserShip(i).getUserShipFSD().getClassNumber() + " " + users.get(userPosition).getUserShip(i).getUserShipFSD().getRatingCharacter()));
                rootElementWriteUserShip.appendChild(UserShipWriteUserShip);
         
            //Creating transformer object
                TransformerFactory transformerFactoryWriteUserShip = TransformerFactory.newInstance();
                Transformer transformerWriteUserShip = transformerFactoryWriteUserShip.newTransformer();
         
            //Writing updated content into the file
                DOMSource sourceWriteUserShip = new DOMSource(docWriteUserShip);
                FileOutputStream outputWriteUserShip = new FileOutputStream("data\\UserShip.xml");
                StreamResult resultWriteUserShip = new StreamResult(outputWriteUserShip);
                transformerWriteUserShip.transform(sourceWriteUserShip, resultWriteUserShip);
            }
            for (int i = 0; i < shipsNotUsedArray.size(); i++) {
                Element UserShipWriteUserShip = docWriteUserShip.createElement("UserShip");
                Element OwnerWriteUserShip = docWriteUserShip.createElement("Owner");
                Element ShipModelWriteUserShip = docWriteUserShip.createElement("ShipModel");
                Element CoreInternalWriteUserShip = docWriteUserShip.createElement("CoreInternal");
                Element FSDWriteUserShip = docWriteUserShip.createElement("FSD");
                UserShipWriteUserShip.appendChild(OwnerWriteUserShip);
                UserShipWriteUserShip.appendChild(ShipModelWriteUserShip);
                UserShipWriteUserShip.appendChild(CoreInternalWriteUserShip);
                UserShipWriteUserShip.appendChild(FSDWriteUserShip);
                OwnerWriteUserShip.appendChild(docWriteUserShip.createTextNode(shipsNotUsedArray.get(i).getOwner()));
                ShipModelWriteUserShip.appendChild(docWriteUserShip.createTextNode(shipsNotUsedArray.get(i).getShipModel().getShipName()));
                CoreInternalWriteUserShip.appendChild(docWriteUserShip.createTextNode(shipsNotUsedArray.get(i).modulesToString()));
                FSDWriteUserShip.appendChild(docWriteUserShip.createTextNode(shipsNotUsedArray.get(i).getUserShipFSD().getClassNumber() + " " + shipsNotUsedArray.get(i).getUserShipFSD().getRatingCharacter()));
                rootElementWriteUserShip.appendChild(UserShipWriteUserShip);
         
            //Creating transformer object
                TransformerFactory transformerFactoryWriteUserShip = TransformerFactory.newInstance();
                Transformer transformerWriteUserShip = transformerFactoryWriteUserShip.newTransformer();
         
            //Writing updated content into the file
                DOMSource sourceWriteUserShip = new DOMSource(docWriteUserShip);
                FileOutputStream outputWriteUserShip = new FileOutputStream("data\\UserShip.xml");
                StreamResult resultWriteUserShip = new StreamResult(outputWriteUserShip);
                transformerWriteUserShip.transform(sourceWriteUserShip, resultWriteUserShip);
            }
        } catch (Exception e) { 
            e.printStackTrace();
        }
    }

    //Funtion to write logs in the Log file.
    public static void WriteLogs (String logGiven, String userInput) {
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        try {
            File objLogFile = new File("logs\\"+ currentDate + ".log");
            FileWriter objLogFileWriter = new FileWriter(objLogFile, true);
            objLogFile.createNewFile();
            objLogFileWriter.write(currentTime + " | " + logGiven + " | " + userInput + '\n');
            objLogFileWriter.close();
        } catch (IOException e) {}
    }

    //Press Space to continue...
    public static void PressEnterKey () {
        try
        {
            System.out.println("Press Enter key to continue...");
            System.in.read();
        }  
        catch(Exception e)
        {}
        ClearConsole();
    }

    public static void ClearConsole() { //Mètode per netejar la terminal. Cortesía de l'Alejandro, Cortesia de ChatGPT.
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException | InterruptedException e) {}
    }
}
