

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
        ArrayList<String> moduleFileOutput = new ArrayList<String>();
        ArrayList<Module> modules = new ArrayList<Module>();
        ArrayList<String> FSDFileOutput = new ArrayList<String>();
        ArrayList<FSD> fsds = new ArrayList<FSD>();
        ArrayList<String> shipModelFileOutput = new ArrayList<String>();
        ArrayList<ShipModel> shipModels = new ArrayList<ShipModel>();
        ArrayList<String> usershipsFileOutput = new ArrayList<String>();
        ArrayList<UserShip> userShips = new ArrayList<UserShip>();
        ArrayList<String> userFileOutput = new ArrayList<String>();
        ArrayList<User> users = new ArrayList<>();

        //File objects
        File objModulesFile = new File("data\\Module.txt");
        File objFSDFile = new File("data\\FSD.txt");
        File objClassRatingCoeficientsFile = new File("data\\ClassRatingCoeficients.txt");
        File objShipModelFile = new File("data\\ShipModel.txt");
        File objUserFile = new File("data\\User.txt");
        File objUserShipFile = new File("data\\UserShip.txt");

        //Objects used to scrape the data from txt's
        Module moduleCreationObject;
        FSD fsdCreationObject;
        ShipModel shipModelCreationObject;
        User userCreationObject;
        UserShip userShipCreationObject;

        //Other variables used in the main program.
        int[] ratingCoeficient = new int[5];
        double[] classCoeficient = new double[7];
        String[] shipModelMaxCoreInternal;
        String[] shipModelMaxOptionalInternal;
        Scanner inUser = new Scanner(System.in);
        String userName = "";
        String userPassword;
        boolean programStatus = true;
        boolean autenticationSuccessful = false;
        int optionSelection = 0;
        int userPosition;
        int shipSelected = 0;
        int shipModelPosition = 0;

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
            modules.add(moduleCreationObject = new Module(
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
            fsds.add(fsdCreationObject = new FSD(
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
            shipModels.add(shipModelCreationObject = new ShipModel(
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
            users.add(userCreationObject = new User(
                userFileOutput.get(i), 
                userFileOutput.get(i+1), 
                userShips
                ));
            i = i+3;
        }
        //USERSHIP.TXT
            //Calling the method to scrape and load all the data contained in the txt path provided above.
            userFileOutput = getDataFromTXT(objUserShipFile);
            //Transforming all the data scraped to objects so we can work with them in an array list.
                //This snippet of code goes through the array list of scrapped data and creates objects with position packs of 5. (positions 1-4 are parameters of an object, 5-8 another...)
        for (int i = 6; i < (usershipsFileOutput.size()); i++) {
            ArrayList<ShipModel> userShipModels = new ArrayList<>();
            userShipModels.add(shipModels.get(ShipModelArrayPosition(shipModels, usershipsFileOutput.get(i+1))));
            userShips.add(userShipCreationObject = new UserShip(usershipsFileOutput.get(i), userShipModels, modules, fsdCreationObject, modules));
            i = i+3;
        }
    //USER AUTENTICATION AND PROGRAM LOOP START
        while (programStatus) {
            while (!autenticationSuccessful) {
                System.out.println("Please, provide your Username.");
                userName = inUser.next();
                System.out.println("Please, provide the account password.");
                userPassword = inUser.next();
            //Testing if the user and password provided are inside the User list provided in the .txt
                if (UserAutentication(users, userName, userPassword) == true) {
                    System.out.println('\n'+ "" + '\n' + "" + '\n' + "Autentication successful" + '\n' + "" + '\n');
                    break;
                } else {
                    System.out.println('\n'+ "" + '\n' + "" + '\n' + "User or Password Incorrect." + '\n' + "Try again.");
                }
            }
            userPosition = UserArrayPosition(users, userName);
        //Main program loop. Looping all the options so the user can do diferent ones in the same instance without having to log in again.
            while (optionSelection != 6) {
                System.out.println("What would you like to do, " + userName + "?" + '\n' + "Please type the number of the option you would like to perform.");
                System.out.println("||1. Calculate the max Jump Range of a ship." + '\n' + "||2. See the ships you currently have in your hangar." + '\n' + "||3. Add a new ship to the Hangar." + '\n' + "||4. Sell a ship from your hangar." + '\n' + "||5. Modify the modules of one of your Ships." + '\n' + "||6. Log off");
                try {
                    optionSelection = inUser.nextInt();
                } catch (Exception e) {
                    // TODO: handle exception
                    System.out.println("Invalid selection. Try again.");
                    //this varaible is here so the Try catch doesnt get into an infinite loop if it gives an error the frist time
                    inUser.next();
                }
                


                switch (optionSelection) {
                    case 1:
                        boolean validSelection = false;
                        System.out.println("Select one of your current ships");
                        while (!validSelection) {
                            try {
                                for (int i = 0; i < users.get(userPosition).getUserShipsArray().size(); i++) {
                                    System.out.println(users.get(userPosition).getUserShip(i) + "" + '\n');
                                }
                                shipSelected = inUser.nextInt();
                                validSelection = true;
                            } catch (Exception e) {
                                // TODO: handle exception
                                System.out.println("Invalid selection. Try again.");
                                //this varaible is here so the Try catch doesnt get into an infinite loop if it gives an error the frist time
                                inUser.next();
                            }
                        }

                        CalculateJumpRange(users.get(userPosition).getUserShip(shipSelected));
                        break;
                
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                }
            }
        }     

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




















        //USER SHIP TEST
        ArrayList<Module> objModulesArrayTest1 = new ArrayList<>();
        ArrayList<Module> objModulesArrayTest2 = new ArrayList<>();
        
        objModulesArrayTest1.add(modules.get(4));
        objModulesArrayTest1.add(modules.get(50));
        objModulesArrayTest1.add(modules.get(86));
        objModulesArrayTest1.add(modules.get(106));
        objModulesArrayTest1.add(modules.get(156));
        objModulesArrayTest1.add(modules.get(179));
        UserShip objUserShipTest1 = new UserShip(null, shipModels.get(0), objModulesArrayTest1, fsds.get(19), objModulesArrayTest2);
        userShips.add(objUserShipTest1);
        User objUserTest1 = new User("Lobby", "1234", userShips);
        objUserShipTest1.setOwner(objUserTest1.getName());
        System.out.println(userShips.get(0));

        System.out.println("el salt total en LY és: " + CalculateJumpRange(objUserShipTest1));

        inUser.close();
    }
    
//METHODS
    //Method to use Rating and Coeficients. Cant use the one bellow since those are with ArrayLists, this one is with normal Arrays.
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

    //Know user array position
    public static int UserArrayPosition (ArrayList<User> users, String userProvided) {
        int userPosition = 0;
        boolean userIsTheSame;
        for (int i = 0; i < users.size(); i++) {
            if (userIsTheSame = userProvided.equals(users.get(i).getName())) {
                userPosition = i;
            }
        }
        return userPosition;
    }

    //Know ShipModel array position
        public static int ShipModelArrayPosition (ArrayList<ShipModel> shipModels, String shipModelProvided) {
            int shipModelPosition = 0;
            boolean shipModelIsTheSame;
            for (int i = 0; i < shipModels.size(); i++) {
                if (shipModelIsTheSame = shipModelProvided.equals(shipModels.get(i).getShipName())) {
                    shipModelPosition = i;
                }
            }
            return shipModelPosition;
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
}