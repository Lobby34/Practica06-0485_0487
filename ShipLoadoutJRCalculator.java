

import java.io.File; //Dependency here to import and read files .txt
import java.io.FileNotFoundException;   //Dependency here to handle exeptions and enable reading files .txt
import java.io.IOException;             
import java.io.FileWriter;
import java.util.Scanner;   //Dependency here to Scan files as well as user inputs.
import java.util.ArrayList; //Dependency here to be able to create Dynamic Arrays of Objects, Strings, ints...

public class ShipLoadoutJRCalculator {
    public static void main(String[] args) {
//VARIABLES
        //File Array list to read files and store the objects made.
        ArrayList<String> moduleFileOutput = new ArrayList<String>();
        ArrayList<Module> modules = new ArrayList<Module>();
        ArrayList<String> FSDFileOutput = new ArrayList<String>();
        ArrayList<FSD> fsds = new ArrayList<FSD>();
        ArrayList<String> shipModelFileOutput = new ArrayList<String>();
        ArrayList<ShipModel> shipModels = new ArrayList<ShipModel>();

        //File objects
        File objModulesFile = new File("data\\Module.txt");
        File objFSDFile = new File("data\\FSD.txt");
        File objClassRatingCoeficientsFile = new File("data\\ClassRatingCoeficients.txt");
        File objShipModelFile = new File("data\\ShipModel.txt");

        //Objects used to scrape the data from txt's
        Module moduleCreationObject;
        FSD fsdCreationObject;
        ShipModel shipModelCreationObject;

        //Other variables used in the main program.
        int[] ratingCoeficient = new int[5];
        double[] classCoeficient = new double[7];
        String[] shipModelMaxCoreInternal;
        String[] shipModelMaxOptionalInternal;

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
        User objUserTest1 = new User("Lobby");
        ArrayList<Module> objModulesArrayTest1 = new ArrayList<>();
        ArrayList<Module> objModulesArrayTest2 = new ArrayList<>();
        objModulesArrayTest1.add(modules.get(4));
        objModulesArrayTest1.add(modules.get(50));
        objModulesArrayTest1.add(modules.get(86));
        objModulesArrayTest1.add(modules.get(106));
        objModulesArrayTest1.add(modules.get(156));
        objModulesArrayTest1.add(modules.get(179));
        UserShip objUserShipTest1 = new UserShip(objUserTest1, shipModels.get(0), objModulesArrayTest1, fsds.get(19), objModulesArrayTest2);
        System.out.println(objUserShipTest1);

        System.out.println("el salt total en LY és: " + CalculateJumpRange(objUserShipTest1));
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

    public static double CalculateJumpRange (UserShip userShip) {
        userShip.calculateTotalMass();
        double totalJumpRange = ((Math.pow(1000, (1/userShip.getUserShipFSD().getClassConstant()))*userShip.getUserShipFSD().getOptimalMass()*Math.pow((userShip.getUserShipFSD().getMaxFuelJump()/userShip.getUserShipFSD().getRatingConstant()),(1/userShip.getUserShipFSD().getClassConstant())))/userShip.getTotalMass());
        return totalJumpRange;
    }
}