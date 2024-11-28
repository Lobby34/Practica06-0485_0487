

import java.io.File; //Dependency here to import and read files .txt
import java.io.FileNotFoundException;   //Dependency here to handle exeptions and enable reading files .txt
// import java.io.IOException;
// import java.io.FileWriter;
import java.util.Scanner;   //Dependency here to Scan files as well as user inputs.
import java.util.ArrayList; //Dependency here to be able to create Dynamic Arrays of Objects, Strings, ints...
import java.util.Base64; //Dependency here to be able to "Encrypt" and "Decrypt" passwords from and to Base64.

public class Main {
    public static void main(String[] args) {
//VARIABLES
        //File Array list to read files and store the objects made.
            //Variables used to store in an array all the lines as string in the Modules.txt and store the Module Objects.
        ArrayList<String> moduleFileOutput = new ArrayList<String>();
        ArrayList<Module> modules = new ArrayList<Module>();
            //Variables used to store in an array all the lines as string in the FSD.txt and store the FSD Objects.
        ArrayList<String> FSDFileOutput = new ArrayList<String>();
        ArrayList<FSD> fsds = new ArrayList<FSD>();
            //Variables used to store in an array all the lines as string in the ShipModel.txt and store the shipModel Objects.
        ArrayList<String> shipModelFileOutput = new ArrayList<String>();
        ArrayList<ShipModel> shipModels = new ArrayList<ShipModel>();
            //Variables used to store in an array all the lines as string in the userShips.txt and store the userShip Objects.
        ArrayList<String> usershipsFileOutput = new ArrayList<String>();
        ArrayList<UserShip> userShips = new ArrayList<UserShip>();
            //Variables used to store in an array all the lines as string in the User.txt and store the User Objects.
        ArrayList<String> userFileOutput = new ArrayList<String>();
        ArrayList<User> users = new ArrayList<>();

        //File objects
        File objModulesFile = new File("data\\Module.txt");
        File objFSDFile = new File("data\\FSD.txt");
        File objClassRatingCoeficientsFile = new File("data\\ClassRatingCoeficients.txt");
        File objShipModelFile = new File("data\\ShipModel.txt");
        File objUserFile = new File("data\\User.txt");
        File objUserShipFile = new File("data\\UserShip.txt");

        //Variables used to create the class objects.
        int[] ratingCoeficient = new int[5];
        double[] classCoeficient = new double[7];
        String[] shipModelMaxCoreInternal;
        String[] shipModelMaxOptionalInternal;

        //Other variables used in the main program. 
            //Scanner for all the user inputs
        Scanner inUser = new Scanner(System.in);
            //Boolean to know if the program has to continue runnning or stop its execution
        boolean programStatus = true;
            //Boolean to get out of the autentication process loop.
        boolean autenticationSuccessful = false;
            //Boolean to know if there is a logged user.
        boolean userLoggedOn = false;
            //integer used to know what option of the main user menu has been selected.
        int optionSelection = 0;
            //integer used to store the user position on the Users array. 
        int userPosition = 0;
            //integer used to store the ship selected in the menus that need a ship to be selected. (ex 1)
        int shipSelected = 0;
            //String used to store the username input from the user.
        String userName = "";
            //String used to store the password input from the user.
        String userPassword;
            //Here to know if the selection made is valid for the submenues of the user menu.
        boolean validSelection = false;
            //Option to confirm the action that the user is about to do.
        String userConfirmation = "";
  
//MAIN PROGRAM
    //Import data from all .txt
        //CLASSRATINGCOEFICIENTS.TXT
        getClassRatingCoeficients(objClassRatingCoeficientsFile, ratingCoeficient, classCoeficient);
        //MODULE.TXT
            //Calling the method to scrape and load all the data contained in the txt path provided above.
        moduleFileOutput = getDataFromTXT(objModulesFile);
            //Transforming all the data scraped to objects so we can work with them in an array list.
                //This snippet of code goes through the array list of scrapped data and creates objects with position packs of 5. (positions 1-5 are parameters of an object, 6-10 another...)
        for (int i = 9; i < (moduleFileOutput.size()); i++) {
            modules.add(new Module(
                Integer.parseInt(moduleFileOutput.get(i+1)), 
                Integer.parseInt(moduleFileOutput.get(i+2)), 
                moduleFileOutput.get(i+3).charAt(0), 
                Double.parseDouble(moduleFileOutput.get(i+4))
                ));
            i = i+4;
        }
        //FSD.TXT
            //Calling the method to scrape and load all the data contained in the txt path provided above.
        FSDFileOutput = getDataFromTXT(objFSDFile);
            //Transforming all the data scraped to objects so we can work with them in an array list.
                //This snippet of code goes through the array list of scrapped data and creates objects with position packs of 7. (positions 1-7 are parameters of an object, 8-15 another...)
        for (int i = 9; i < (FSDFileOutput.size()); i++) {
            fsds.add(new FSD(
                Integer.parseInt(FSDFileOutput.get(i+1)), 
                Integer.parseInt(FSDFileOutput.get(i+2)), 
                FSDFileOutput.get(i+3).charAt(0), 
                Double.parseDouble(FSDFileOutput.get(i+4)), 
                Double.parseDouble(FSDFileOutput.get(i+5)), 
                Double.parseDouble(FSDFileOutput.get(i+6))
                ));
            i = i+6;
        }
            //This part of the code calls every FSD object and gives to each their RatingCoeficient and their ClassCoeficient.
        for (int i = 0; i < fsds.size(); i++) {
        fsds.get(i).setClassRating(ratingCoeficient, classCoeficient);
        }
        //SHIPMODEL.TXT
            //Calling the method to scrape and load all the data contained in the txt path provided above.
        shipModelFileOutput = getDataFromTXT(objShipModelFile);
            //Transforming all the data scraped to objects so we can work with them in an array list.
                //This snippet of code goes through the array list of scrapped data and creates objects with position packs of 5. (positions 1-5 are parameters of an object, 6-10 another...)
        for (int i = 6; i < (shipModelFileOutput.size()); i++) {
            //This 2 lines are here to transform a String in format X,X,X,X,X,X,X to a array, where every X is a position in the array. There are 2 variables as to divide Optinal and Core Internal modules.
            shipModelMaxCoreInternal = shipModelFileOutput.get(i+2).split(",");
            shipModelMaxOptionalInternal = shipModelFileOutput.get(i+2).split(",");
                //Constructor method of the shipModel without the Optional Internals. ATM Doing it without them.
            shipModels.add(new ShipModel(
                shipModelFileOutput.get(i), 
                Double.parseDouble(shipModelFileOutput.get(i+1)), 
                Integer.parseInt(shipModelMaxCoreInternal[0]), 
                Integer.parseInt(shipModelMaxCoreInternal[1]), 
                Integer.parseInt(shipModelMaxCoreInternal[2]), 
                Integer.parseInt(shipModelMaxCoreInternal[3]), 
                Integer.parseInt(shipModelMaxCoreInternal[4]), 
                Integer.parseInt(shipModelMaxCoreInternal[5]), 
                Integer.parseInt(shipModelMaxCoreInternal[6])
                ));
            i = i+4;
        }
        //USER.TXT
            //Calling the method to scrape and load all the data contained in the txt path provided above.
        userFileOutput = getDataFromTXT(objUserFile);
            //Transforming all the data scraped to objects so we can work with them in an array list.
                //This snippet of code goes through the array list of scrapped data and creates objects with position packs of 5. (positions 1-4 are parameters of an object, 5-8 another...)
        for (int i = 0; i < (userFileOutput.size()); i++) {
            users.add(new User(
                userFileOutput.get(i), 
                userFileOutput.get(i+1), 
                userShips
                ));
            i = i+3;
        }
        //USERSHIP.TXT
            //Calling the method to scrape and load all the data contained in the txt path provided above.
        usershipsFileOutput = getDataFromTXT(objUserShipFile);
            //Transforming all the data scraped to objects so we can work with them in an array list.
                //This snippet of code goes through the array list of scrapped data and creates objects with position packs of 5. (positions 1-4 are parameters of an object, 5-8 another...)
        for (int i = 6; i < (usershipsFileOutput.size()); i++) {
            userShips.add(new UserShip(
                usershipsFileOutput.get(i), 
                shipModels.get(ShipModelArrayPosition(shipModels, usershipsFileOutput.get(i+1))), 
                GetCoreModulesArrayPositions(modules, usershipsFileOutput.get(i+2)), 
                fsds.get(GetFSDArrayPosition(fsds, usershipsFileOutput.get(i+3)))
                ));
            i = i+5;
        }
        //This method is here to calculate the total mass of the ships since it isnt saved in the TXT file.
        calculateTotalMassOfUserShipsInArray(userShips);

    //USER AUTENTICATION AND PROGRAM LOOP START
        while (programStatus) {
        //Here so if the user leaves and logins again the menu shows (and doesnt keep the last selected option) (Since logging off leaved last selection to log off, it would log off instantly)
            optionSelection = 0;
            while (!autenticationSuccessful) {
                System.out.println("Please, provide your Username.");
                userName = inUser.next();
                System.out.println("Please, provide the account password.");
                userPassword = inUser.next();
            //Testing if the user and password provided are inside the User list provided in the .txt
                if (UserAutentication(users, userName, userPassword) == true) {
                    System.out.println('\n'+ "" + '\n' + "" + '\n' + "Autentication successful" + '\n' + "" + '\n');
                    userLoggedOn = true;
                    break;
                } else {
                    System.out.println('\n'+ "" + '\n' + "" + '\n' + "User or Password Incorrect." + '\n' + "Try again.");
                }
            }
            userPosition = UserArrayPosition(users, userName);
        //Main program loop. Looping all the options so the user can do diferent ones in the same instance without having to log in again.
            while (userLoggedOn) {
                optionSelection = MenuOptionSelection(userName, inUser);
            //Option menu for the normal User. (Center of the app.)
                switch (optionSelection) {
                //CASE 1: Calculate the Ship JumpRange Capabilities.
                    case 1:
                        System.out.println('\n'+ "" + '\n' + "" + '\n' + "Select one of your current ships" + '\n');
                        validSelection = false;
                        while (!validSelection) {
                            try {
                                ShowUserShips(userPosition, users, true);
                                System.out.print('\n' + "Your Selection: ");
                                shipSelected = inUser.nextInt()-1;
                                validSelection = true;
                            } catch (Exception e) {
                                // TODO: handle exception
                                System.out.println("Invalid selection. Try again.");
                                //this varaible is here so the Try catch doesnt get into an infinite loop if it gives an error the frist time
                                inUser.next();
                            }
                            System.out.println('\n'+ "" + '\n' + "" + '\n');
                        }
                        if (shipSelected >= users.get(userPosition).getUserShipsArray().size()) {
                        } else {
                        System.out.println("The MAX distance you can cover in a Jump is: " + CalculateJumpRange(users.get(userPosition).getUserShip(shipSelected)) + '\n');
                        }
                        PressEnterKey();
                        break;

                //CASE 2: Show the User all their ships
                    case 2:
                        System.out.println('\n'+ "" + '\n' + "" + '\n' + "Displaying all the ships you have stored in your Hangar.");
                        ShowUserShips(userPosition, users, false);
                        System.out.println("");
                        PressEnterKey();
                        break;

                //CASE 3: Add a new ship to the User's hangar
                    case 3:
                        System.out.println('\n'+ "" + '\n' + "" + '\n' + "What model of ship would you like to add to your hangar?" + '\n');
                        validSelection = false;
                        while (!validSelection) {
                            try {
                                int i = 0;
                                    for (i = 0; i < shipModels.size(); i++) {
                                        System.out.println("||" + (i+1) + ": " + shipModels.get(i).getShipName());
                                    }
                                System.out.println("||" + (i+1) + ": Cancel operation.");
                                System.out.print('\n' + "Your Selection: ");
                                shipSelected = inUser.nextInt()-1;
                                if(shipSelected < shipModels.size()) {
                                    validSelection = true;
                                    System.out.println("Are you sure that you want to add the ship model: || " + shipModels.get(shipSelected).getShipName() + "?" + '\n' + "(Y/N)");
                                    System.out.print('\n' + "Your Selection: ");
                                    userConfirmation = inUser.next();
                                    if (userConfirmation.charAt(0) == 'Y' || userConfirmation.charAt(0) == 'y') {
                                        userShips.add(new UserShip(userName, shipModels.get(shipSelected), GetCoreModulesArrayPositions(modules, "2 2 E,3 2 E,5 2 E,6 2 E,7 2 E,8 1 C"), fsds.get(GetFSDArrayPosition(fsds, "2 E"))));
                                        userShips.getLast().calculateTotalMass();
                                        System.out.println("Ship added Successfully." + '\n');
                                    } else {
                                        validSelection = true;
                                    }
                                } else if (shipSelected == (shipModels.size())) {
                                    validSelection = true;
                                } else {
                                    System.out.println("-Invalid selection. Try again.");
                                }
                            } catch (Exception e) {
                                // TODO: handle exception
                                System.out.println("Invalid selection. Try again.");
                                //this varaible is here so the Try catch doesnt get into an infinite loop if it gives an error the frist time
                                inUser.next();
                            }
                            PressEnterKey();
                            System.out.println('\n'+ "" + '\n' + "" + '\n');
                        }
                        break;
                        
                //CASE 4: Remove an existent ship from the User's hangar
                    case 4:
                        try {
                            System.out.println('\n'+ "" + '\n' + "" + '\n' + "What ship would you like to remove from your hangar?" + '\n');                        
                            ShowUserShips(userPosition, users, true);
                            shipSelected = inUser.nextInt()-1;
                            if (shipSelected < users.get(userPosition).getUserShipsArray().size()) {
                                System.out.println("Are you sure that you want to REMOVE PERMANENTLY your SHIP: || " + users.get(userPosition).getUserShip(shipSelected) + "?" + '\n' + "(Y/N)");
                                System.out.print('\n' + "Your Selection: ");
                                userConfirmation = inUser.next();
                                if (userConfirmation.charAt(0) == 'Y' || userConfirmation.charAt(0) == 'y') {
                                    users.get(userPosition).getUserShipsArray().remove(shipSelected);
                                }
                            }
                        } catch (Exception e) {
                            // TODO: handle exception
                            System.out.println("Invalid selection. Try again.");
                            //this varaible is here so the Try catch doesnt get into an infinite loop if it gives an error the frist time
                            inUser.next();
                        }
                        PressEnterKey();
                        break;

                //CASE 5: Swap one of the users ship modules.
                    case 5:
                        System.out.println("Option " + optionSelection + " Selected");
                        break;
                        
                //CASE 6: Log off but keep the app running.
                    case 6:
                        System.out.println('\n'+ "" + '\n' + "" + '\n' + "You just logged off!!" + '\n'+ "" + '\n' + "" + '\n');
                        autenticationSuccessful = false;
                        userLoggedOn = false;
                        break;

                //CASE 7: Log off, save progress and shut down the app.        
                    case 7:
                        System.out.println("Closing the program... ");
                        userLoggedOn = false;
                        autenticationSuccessful = false;
                        programStatus = false;
                        break;

                //CASE 8: Other options not mentioned above. All of them will give an error.
                    default:
                        System.out.println('\n'+ "" + '\n' + "" + '\n' + "Invalid option selected." + '\n'+ "" + '\n' + "" + '\n');
                        //userLoggedOn = false;
                        break;
                }
            }
        }
        inUser.close();

/*
//TESTS TO SHOW OBJECTS
    //Show all the data through CMD
        //Showing all the transfered data from module.txt as objects
        for (int i = 0; i < modules.size(); i++) {
            if (modules.get(i).getSlot() != 0) {
                System.out.println(modules.get(i));
            }
        }   
        //Showing all the transfered data from FSD.txt as objects.
        for (int i = 0; i < fsds.size(); i++) {
            System.out.println(fsds.get(i));
        }
*/
    }
    
//METHODS
    //Method to read, Process and Store Rating and Coeficients. Cant use the one bellow since those are with ArrayLists, this one is with normal Arrays.
    public static void getClassRatingCoeficients(File objFile, int[] ratingCoeficient, double[] classCoeficient) {
        try {
            int i = 0;
            Scanner fileScanner = new Scanner(objFile);
            String OutputString;
            String[] OutputStringArray;
            while (fileScanner.hasNextLine()) {
                i++;
            //Since one array is an INT and the other a DOUBLE, i've done it this way with ifs as to not create another method and do both in only one pass.
                if (i == 1) {
                    OutputString = fileScanner.nextLine();
                    OutputStringArray = OutputString.split(",");
                    for (int j = 0; j < 5; j++) {
                        ratingCoeficient[j] = Integer.parseInt(OutputStringArray[j]);
                    }
                } else if (i == 2) {
                    OutputString = fileScanner.nextLine();
                    OutputStringArray = OutputString.split(",");
                    for (int j = 0; j < 7; j++) {
                        classCoeficient[j] = Double.parseDouble(OutputStringArray[j]);
                    }
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            //Here to 1st see if the file is not found, 2nd to actually let the code rune without errors.
            System.out.println("File " + objFile + " not found.");
        }
    }

    //Method used to Read files. Here we look at the file path provided and load the information to the program through an ArrayList<String> 
    public static ArrayList<String> getDataFromTXT (File objFile) {
        ArrayList<String> fileOutput = new ArrayList<String>();
        try {
            Scanner fileScanner = new Scanner(objFile);
            while (fileScanner.hasNextLine()) {
                String fileDataOutput = fileScanner.nextLine();
                fileOutput.add(fileDataOutput);
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            //Here to 1st see if the file is not found, 2nd to actually let the code rune without errors.
            System.out.println("File " + objFile + " not found.");
        }
  
        return fileOutput;
    }

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

    //Know FSD position in the array of FSD thorugh Class and Rating
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
        int optionSelection = 0;
        System.out.println("What would you like to do, " + userName + "?" + '\n' + "Please type the number of the option you would like to perform.");
        System.out.println("||1. Calculate the max Jump Range of a ship." + '\n' + "||2. See the ships you currently have in your hangar." + '\n' + "||3. Add a new ship to the Hangar." + '\n' + "||4. Sell a ship from your hangar." + '\n' + "||5. Modify the modules of one of your Ships." + '\n' + "||6. Log off & Save" + '\n' +"||7. Shut down the program & Save" + '\n');
        System.out.print("Your Selection: "); 
        try {
            optionSelection = inUser.nextInt();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Invalid selection. Try again.");
            //this varaible is here so the Try catch doesnt get into an infinite loop if it gives an error the frist time
            inUser.next();
        }
        return optionSelection;
    }

    //Show the ships the User OWNS.
    public static void ShowUserShips (int userPosition, ArrayList<User> users, Boolean lastOption) {
        int i = 0;
        for (i = 0; i < users.get(userPosition).getUserShipsArray().size(); i++) {
            System.out.println("||" + (i+1) + ".    " + users.get(userPosition).getUserShip(i).getShipModel().getShipName() + " || " + users.get(userPosition).getUserShip(i).getTotalMass() + "[T]");
        }
        if (lastOption) {
            System.out.println("||" + (i+1) + ".    Cancel Operation.");
        }
    }

    public static void PressEnterKey () {
        //Press Space to continue...
        try
        {
            System.out.println("Press Enter key to continue...");
            System.in.read();
        }  
        catch(Exception e)
        {}  
    }
}