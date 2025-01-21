import java.io.File;                                    //Dependency here to import and read files .xml
import java.io.FileNotFoundException;                   //Dependency here to handle exeptions and enable reading files .txt
import java.util.Scanner;                               //Dependency here to Scan files as well as user inputs.
import java.util.ArrayList;                             //Dependency here to be able to create Dynamic Arrays of Objects, Strings, ints...
import org.w3c.dom.*;                            //Dependency here to be able to use the Document variable type to be able to read and write xml files.
import javax.xml.parsers.DocumentBuilder;               //Dependency here to be able to create XML files.
import javax.xml.parsers.DocumentBuilderFactory;        //Dependency here to be able to create XML files.
import javax.xml.transform.Transformer;                 //Dependency here to write into an XML file once changes have been made.
import javax.xml.transform.TransformerFactory;          //Dependency here to write into an XML file once changes have been made.
import javax.xml.transform.dom.DOMSource;               //Dependency here to write into an XML file once changes have been made.
import javax.xml.transform.stream.StreamResult;         //Dependency here to write into an XML file once changes have been made.
import javax.xml.xpath.*;

public class Main {
    @SuppressWarnings("unused")
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
//TESTS
        File objModuleFile = new File("data\\Module.xml");
//TESTS
        //File objModulesFile = new File("data\\Module.txt");
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
            //This variable set is to split the ships that the user has from all the others, so when we save the ships in the file, we dont get fucked, ships dont get doubled, and ships save modified.
        ArrayList<UserShip> currentUserShipsArray = new ArrayList<UserShip>();
        ArrayList<UserShip> shipsNotBeingUsed = new ArrayList<>();

        //Other variables used in the main program. 
            //Scanner for all the user inputs
        Scanner inUser = new Scanner(System.in);
            //Scanner here BC USING THE OTHER ONE IN THE 5th CASE FOR SOME REASON MAKES EVERYTHING JUST CRASH
        Scanner inUserCase5 = new Scanner(System.in);
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
        Methods.WriteLogs("RATING COEFICIENTS LOADED", null);
        //MODULE.TXT
            //Calling the method to scrape and load all the data contained in the txt path provided above.
            Methods.WriteLogs("MODULES LOADED IN THE PROGRAM ARRAY", null);
            //Transforming all the data scraped to objects so we can work with them in an array list.
                //This snippet of code goes through the array list of scrapped data and creates objects with position packs of 5. (positions 1-5 are parameters of an object, 6-10 another...)
        try {
            DocumentBuilderFactory dbFactoryModule = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilderModule = dbFactoryModule.newDocumentBuilder();
            Document docModule = dBuilderModule.parse(objModuleFile);
            XPath xPathModule =  XPathFactory.newInstance().newXPath();
            String expressionModule = "//Module";

            NodeList nodeListModule = (NodeList) xPathModule.compile(expressionModule).evaluate(docModule, XPathConstants.NODESET);

            for (int i = 0; i < nodeListModule.getLength(); i++) {
                Node nNodeModule = nodeListModule.item(i);

                if (nNodeModule.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElementModule = (Element) nNodeModule;
                    modules.add(new Module(
                        Integer.parseInt(eElementModule.getAttribute("Type")),
                        Integer.parseInt(eElementModule.getElementsByTagName("Class").item(0).getTextContent()),
                        eElementModule.getElementsByTagName("Rating").item(0).getTextContent().charAt(0),
                        Double.parseDouble(eElementModule.getElementsByTagName("Mass").item(0).getTextContent())
                    ));
                }
            }
            Methods.WriteLogs("MODULE OBJECT CREATED", ("NAME: " + modules.getLast().getClassNumber()+modules.getLast().getRatingCharacter()));
        } catch (Exception e) {
                e.printStackTrace();
        }  


        //FSD.TXT
            //Calling the method to scrape and load all the data contained in the txt path provided above.
        FSDFileOutput = Methods.getDataFromTXT(objFSDFile);
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
                Methods.WriteLogs("FSD OBJECT CREATED", "NAME: " + fsds.getLast().getClassNumber() + fsds.getLast().getRatingCharacter());
            i = i+6;
        }
            //This part of the code calls every FSD object and gives to each their RatingCoeficient and their ClassCoeficient.
        for (int i = 0; i < fsds.size(); i++) {
        fsds.get(i).setClassRating(ratingCoeficient, classCoeficient);
        }
        //SHIPMODEL.TXT
            //Calling the method to scrape and load all the data contained in the txt path provided above.
        shipModelFileOutput = Methods.getDataFromTXT(objShipModelFile);
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
                Methods.WriteLogs("SHIPMODEL OBJECT CREATED", "Name: " + shipModels.getLast().getShipName());
            i = i+4;
        }
        //USER.TXT
            //Calling the method to scrape and load all the data contained in the txt path provided above.
        userFileOutput = Methods.getDataFromTXT(objUserFile);
            //Transforming all the data scraped to objects so we can work with them in an array list.
                //This snippet of code goes through the array list of scrapped data and creates objects with position packs of 5. (positions 1-4 are parameters of an object, 5-8 another...)
        for (int i = 0; i < (userFileOutput.size()); i++) {
            users.add(new User(
                userFileOutput.get(i), 
                userFileOutput.get(i+1), 
                currentUserShipsArray
                ));
                Methods.WriteLogs("USER OBJECT CREATED", "Name: " + users.getLast().getName());
            i = i+3;
        }
        //USERSHIP.TXT
            //Calling the method to scrape and load all the data contained in the txt path provided above.
        usershipsFileOutput = Methods.getDataFromTXT(objUserShipFile);
            //Transforming all the data scraped to objects so we can work with them in an array list.
                //This snippet of code goes through the array list of scrapped data and creates objects with position packs of 5. (positions 1-4 are parameters of an object, 5-8 another...)
        for (int i = 6; i < (usershipsFileOutput.size()-1); i++) {
            userShips.add(new UserShip(
                usershipsFileOutput.get(i), 
                shipModels.get(Methods.ShipModelArrayPosition(shipModels, usershipsFileOutput.get(i+1))), 
                Methods.GetCoreModulesArrayPositions(modules, usershipsFileOutput.get(i+2)), 
                fsds.get(Methods.GetFSDArrayPosition(fsds, usershipsFileOutput.get(i+3)))
                ));
                Methods.WriteLogs("USERSHIPS OBJECT CREATED", "Name: " + users.getLast().getName());
            i = i+5;
        }
            //This method is here to calculate the total mass of the ships since it isnt saved in the TXT file.
            Methods.calculateTotalMassOfUserShipsInArray(userShips);
            Methods.WriteLogs("MASS OF ALL THE SHIPS CALCULATED", null);

    //USER AUTENTICATION AND PROGRAM LOOP START
        //Here to clear the console only when the program is executed.
        Methods.ClearConsole();
        while (programStatus) {
            //Here so if the user leaves and logins again the menu shows (and doesnt keep the last selected option) (Since logging off leaved last selection to log off, it would log off instantly)
            optionSelection = 0;
            while (!autenticationSuccessful) {
                System.out.println("Please, provide your Username.");
                userName = inUser.next();
                System.out.println("Please, provide the account password.");
                userPassword = inUser.next();
                Methods.ClearConsole();
                //Testing if the user and password provided are inside the User list provided in the .txt
                if (Methods.UserAutentication(users, userName, userPassword) == true) {
                    System.out.println("Autentication successful" + '\n' + "" + '\n');
                    userLoggedOn = true;
                    Methods.PressEnterKey();
                    Methods.WriteLogs("USER AUTENTICATION WAS SUCESSFUL", "USER LOGGED ON: " + userName);
                    break;
                } else {
                    Methods.ClearConsole();
                    System.out.println("User or Password Incorrect." + '\n' + "Try again.");
                    Methods.WriteLogs("USER AUTENTICATION WAS NOT SUCESSFUL", "USER LOGGED ON: " + userName);
                }
            }
            //Here to know the position of the users inside the array of users.
            userPosition = Methods.UserArrayPosition(users, userName);
            Methods.WriteLogs("USER POSITION IN ARRAY CALCULATED", String.valueOf(userPosition));

            //Clearing the arrays just in case for subsequent uses.
            currentUserShipsArray.clear();
            shipsNotBeingUsed.clear();
            //This snippet of code checks if the ship is from the user logged in, or from another one. If its from another user, it stores the ship in the "ShipsNotBeingUsed". This makes the process of saving the ships later on easier.
            for(int i = 0; i < userShips.size(); i++) {
                if (userName.equals(userShips.get(i).getOwner())) {
                    currentUserShipsArray.add(userShips.get(i));
                    Methods.WriteLogs("USERSHIP MOVED TO currentUserShipsArray", currentUserShipsArray.getLast().getShipModel().getShipName());
                } else {
                    shipsNotBeingUsed.add(userShips.get(i));
                    Methods.WriteLogs("USERSHIP MOVED TO shipsNotBeingUsed", shipsNotBeingUsed.getLast().getShipModel().getShipName());
                }

            }
        //Main program loop. Looping all the options so the user can do diferent ones in the same instance without having to log in again.
            while (userLoggedOn) {
                optionSelection = Methods.MenuOptionSelection(userName, inUser);
                Methods.WriteLogs("MENU SELECTION", String.valueOf(optionSelection));
                Methods.ClearConsole();
            //Option menu for the normal User. (Center of the app.)
                switch (optionSelection) {
                //CASE 1: Calculate the Ship JumpRange Capabilities.
                    case 1:
                        System.out.println("Select one of your current ships" + '\n');
                        validSelection = false;
                        while (!validSelection) {
                            try {
                                Methods.ShowUserShips(userPosition, users, true);
                                shipSelected = inUser.nextInt()-1;
                                Methods.WriteLogs("SHIP SELECTED", String.valueOf(shipSelected));
                                
                                validSelection = true;
                            } catch (Exception e) {
                                System.out.println("Invalid selection. Try again.");
                    //this varaible is here so the Try catch doesnt get into an infinite loop if it gives an error the frist time
                                inUser.next();
                            }
                            Methods.ClearConsole();
                        }
                        if (shipSelected >= users.get(userPosition).getUserShipsArray().size()) {
                            System.out.println("Process canceled.");
                            Methods.PressEnterKey();
                        } else {
                        System.out.println("The MAX distance you can cover in a Jump is: " + Methods.CalculateJumpRange(users.get(userPosition).getUserShip(shipSelected)) + '\n');
                        Methods.WriteLogs("JUMP RANGE CALCULATED FOR THE SHIP", users.get(userPosition).getUserShip(shipSelected).getShipModel().getShipName());
                        Methods.PressEnterKey();
                        }

                        break;

                //CASE 2: Show the User all their ships
                    case 2:
                        System.out.println("Displaying all the ships you have stored in your Hangar.");
                        Methods.ShowUserShips(userPosition, users, false);
                        System.out.println("");
                        Methods.PressEnterKey();
                        Methods.WriteLogs("SHIPS SHOWN TO THE USER","");
                        break;

                //CASE 3: Add a new ship to the User's hangar
                    case 3:
                    //Asking what ship the user would like to add.
                        System.out.println("What model of ship would you like to add to your hangar?" + '\n');
                        validSelection = false;
                        while (!validSelection) {
                            try {
                                int i = 0;
                    //Shows the user all the ship models one by one.
                                    for (i = 0; i < shipModels.size(); i++) {
                                        System.out.println("||" + (i+1) + ": " + shipModels.get(i).getShipName());
                                    }
                                System.out.println("||" + (i+1) + ": Cancel operation.");
                                System.out.print('\n' + "Your Selection: ");
                                shipSelected = inUser.nextInt()-1;
                                Methods.WriteLogs("USER INPUT",String.valueOf(shipSelected));
                    //If the selection prompted to the user above is correct, we make a confirmation so that the user doesen't add a ship by error. 
                                if(shipSelected < shipModels.size()) {
                                    validSelection = true;
                                    System.out.println("Are you sure that you want to add the ship model: || " + shipModels.get(shipSelected).getShipName() + "?" + '\n' + "(Y/N)");
                                    System.out.print('\n' + "Your Selection: ");
                                    userConfirmation = inUser.next();
                                    Methods.WriteLogs("USER INPUT",String.valueOf(userConfirmation));
                        //Adding the ship with the model selected to the user hangar if the confirmation is correct.
                                    if (userConfirmation.charAt(0) == 'Y' || userConfirmation.charAt(0) == 'y') {
                                        users.get(userPosition).getUserShipsArray().add(new UserShip(userName, shipModels.get(shipSelected), Methods.GetCoreModulesArrayPositions(modules, "2 2 E,3 2 E,5 2 E,6 2 E,7 2 E,8 1 C"), fsds.get(Methods.GetFSDArrayPosition(fsds, "2 E"))));
                                        currentUserShipsArray.getLast().calculateTotalMass();
                                        Methods.WriteLogs("SHIP ADDED","TO USER: "+ userName + users.get(userPosition).getUserShipsArray().getLast().getShipModel().getShipName());
                                        System.out.println("Ship added Successfully." + '\n');
                        //If anything goes wrong, the ship wont be added and the process canceled.
                                    } else {
                                        System.out.println("Process Canceled." + '\n');                                        
                                        validSelection = true;
                                        Methods.WriteLogs("SHIP NOT ADDED","");
                                    }
                    //Here we see if the option selected is the last one, made so the user can cancel the process.
                                } else if (shipSelected == (shipModels.size())) {
                                    validSelection = true;
                                    Methods.WriteLogs("SHIP NOT ADDED","");
                                } else {
                                    System.out.println("Invalid selection. Try again.");
                                    Methods.WriteLogs("SHIP NOT ADDED","INVALID SELECTION");
                                }
                                Methods.PressEnterKey();
                            } catch (Exception e) {
                                System.out.println('\n'+ "" + '\n' + "" + '\n' + "Invalid selection. Try again.");
                    //this varaible is here so the Try catch doesnt get into an infinite loop if it gives an error the frist time
                                inUser.next();
                            }

                            System.out.println('\n'+ "" + '\n' + "" + '\n');
                        }
                        break;
                        
                //CASE 4: Remove an existent ship from the User's hangar
                    case 4:
                        validSelection = false;
                        //Loop made so if the selection is invalid, and does not find a module, or crashes, it restarts and asks again the questions.
                        while (!validSelection) {
                            try {
                                System.out.println("What ship would you like to remove from your hangar?" + '\n');
                        //Shows the ships owned by the current user, and asks the user to select one                        
                                Methods.ShowUserShips(userPosition, users, true);
                                shipSelected = inUser.nextInt()-1;
                                Methods.WriteLogs("SHIP SELECTED",String.valueOf(shipSelected));
                        //Here to know if the selected ship exists, or the last option has been selected (Cancel process)
                                if (shipSelected < users.get(userPosition).getUserShipsArray().size()) {
                                    System.out.print('\n' + "Are you sure that you want to PERMANENTLY REMOVE your: || " + users.get(userPosition).getUserShip(shipSelected).getShipModel().getShipName() + "?" + '\n' + "(Y/N)" + '\n' +"Your Selection: ");
                                    userConfirmation = inUser.next();
                                    Methods.WriteLogs("USER INPUT","CONFIRMATION GIVEN: " + userConfirmation);
                                    if (userConfirmation.charAt(0) == 'Y' || userConfirmation.charAt(0) == 'y') {
                                        System.out.println('\n'+ "" + '\n' + "" + '\n' + "The ship has been removed PERMANENTLY" + '\n');
                                        users.get(userPosition).getUserShipsArray().remove(shipSelected);
                                        Methods.WriteLogs("SHIP REMOVED PERMANENTLY","");
                                    } else {
                        //Here, as said above, to cancel the process if the user selects the last option.
                                        System.out.println("Process Canceled." + '\n');
                                        Methods.WriteLogs("PROCESS CANCELED","");
                                    }
                                    validSelection = true;
                                } else if (shipSelected == (shipModels.size()-1)) {
                                    validSelection = true;
                                    Methods.WriteLogs("PROCESS CANCELED","");
                                }
                            } catch (Exception e) {
                                System.out.println("An error has ocurred. Try again.");
                                //this varaible is here so the Try catch doesnt get into an infinite loop if it gives an error the frist time
                                inUser.next();
                                Methods.WriteLogs("PROCESS CANCELED","");
                            }
                        }
                            Methods.PressEnterKey();
                        break;

                //CASE 5: Swap one of the users ship modules.
                    case 5:
                        int slotProvided = 0;
                        validSelection = false;
                        while (!validSelection) {
                            System.out.println("Select what ship would you like to modify" + '\n');
                        //Here to show the user all the ships they have.
                            Methods.ShowUserShips(userPosition, users, true);
                                try {
                            //Makes the user select a ship
                                    shipSelected = inUser.nextInt()-1;
                                    Methods.WriteLogs("SHIP SELECTED",String.valueOf(shipSelected));
                                    Methods.ClearConsole();
                                    //ClearConsole();
                            //Shows the ship with all its modules.
                                    if (shipSelected < users.get(userPosition).getUserShipsArray().size()) {
                                        System.out.println("THE SELECTED SHIP IS THE FOLLOWING ONE. " + '\n' + users.get(userPosition).getUserShip(shipSelected) +'\n');
                                        boolean validSlotProvided = false;
                                        Methods.PressEnterKey();
                            //This loop is here to ensure that the slot selected is always lower than 8.
                                        while (!validSlotProvided){
                                            slotProvided = Methods.ModuleMenuOptionSelection(modules, inUser, fsds);
                                            Methods.WriteLogs("SLOT PROVIDED",String.valueOf(slotProvided));
                                            
                                            if (slotProvided < 8) {
                                                validSlotProvided = true;
                                            } else {
                                                System.out.println("Slot provided is invalid. Try Again.");
                                                Methods.PressEnterKey();
                                                Methods.WriteLogs("INVALID SLOT","SLOT PROVIDED INVALID" + String.valueOf(slotProvided));
                                            }
                                        }
                            //THe modules are shown above in ModuleMenuOptionSelection, here we make the user select a module and if the module is FSD, we do something, if it is NOT an FSD we do the other path
                                        String moduleSelected = inUserCase5.next();
                                        Methods.WriteLogs("MODULE SELECTED",moduleSelected);
                                //FSD Selected
                                        if (slotProvided == 4 && users.get(userPosition).getUserShip(shipSelected).getShipModel().getMaxFSD() >= moduleSelected.charAt(0)-'0' && moduleSelected.charAt(1)-'A' <= 4) {
                                            users.get(userPosition).getUserShip(shipSelected).setFSD(fsds.get(Methods.GetFSDArrayPositionWithUserInput(fsds, moduleSelected)));
                                            System.out.println("FSD changed correctly.");
                                            Methods.PressEnterKey();
                                            validSelection = true;
                                            Methods.WriteLogs("FSD CHANGED", "FSD FROM SHIP " + users.get(userPosition).getUserShip(shipSelected).getShipModel().getShipName() + "HAS BEEN CHANGED TO:" + fsds.get(Methods.GetFSDArrayPositionWithUserInput(fsds, moduleSelected)));
                                //Any other Module that isn't the FSD selected
                                                                                                                                                            //-1 To account the array size                                                          //This 4 is here Because there can only be 5 ratings, and we count from 0. A,B,C,D,E
                                        } else if (users.get(userPosition).getUserShip(shipSelected).getShipModel().getMaxModuleClassByArrayPosition(slotProvided-1) >= moduleSelected.charAt(0)-'0' && moduleSelected.charAt(1)-'A' <= 4) {                                                                                                                                                                                            //This -2 is here, bc the modules are stored in an array list (it starts with 0 and has no blank spaces) And since we dont have the FSD in this array, and the array starts at 0, we need to remove 2 positions
                                    //Since the FSD is in the middle of the list, but not in the module array, we need to remove a slot position after the FSD so the modules are changed in the correct slot. That's why we are doing this if here. 
                                        //Slots after FSD
                                            if(slotProvided > 3) {
                                                users.get(userPosition).getUserShip(shipSelected).setUserShipCoreModule(modules.get(Methods.GetModuleArrayPosition(modules, slotProvided, moduleSelected)), slotProvided-3);
                                                Methods.WriteLogs("MODULE CHANGED", "MODULE POSITION NUM: " + (slotProvided-3) +  " FROM SHIP: " + users.get(userPosition).getUserShip(shipSelected).getShipModel().getShipName() + "HAS BEEN CHANGED TO:" + modules.get(Methods.GetModuleArrayPosition(modules, slotProvided, moduleSelected)));
                                        //Slots before FSD
                                            } else {
                                                users.get(userPosition).getUserShip(shipSelected).setUserShipCoreModule(modules.get(Methods.GetModuleArrayPosition(modules, slotProvided, moduleSelected)), slotProvided-2);
                                                Methods.WriteLogs("MODULE CHANGED", "MODULE POSITION NUM: " + (slotProvided-2) +  " FROM SHIP: " + users.get(userPosition).getUserShip(shipSelected).getShipModel().getShipName() + "HAS BEEN CHANGED TO:" + modules.get(Methods.GetModuleArrayPosition(modules, slotProvided, moduleSelected)));
                                            }
                                            System.out.println("Module changed correctly.");
                                            Methods.PressEnterKey();
                                            validSelection = true;
                                        } else {
                                            System.out.println("The selected module cannot fit in the space, or the Grade is non existant. Please select a module that can fit in the space provided.");
                                            Methods.WriteLogs("MODULE SELECTION INCORRECT", "Slot: " + slotProvided + " Module: " + moduleSelected);
                                            Methods.PressEnterKey();
                                        }
                                        System.out.println(users.get(userPosition).getUserShip(shipSelected));
                                        Methods.PressEnterKey();
                                        Methods.WriteLogs("SHOWING SHIP TO USER", "");

                                    } else if (shipSelected == users.get(userPosition).getUserShipsArray().size()) {
                                        System.out.println("Process Canceled.");
                                        validSelection = true;
                                        Methods.PressEnterKey();
                                        Methods.WriteLogs("MODULE NOT CHANGED", "");
                                    }

                                } catch (Exception e) {
                                    System.out.println("Invalid selection. Try again.");
                                    inUser.next();
                                }
                        }
                        break;
                        
                //CASE 6: Log off but keep the app running.
                    case 6:
                        System.out.println("You just logged off!!" + '\n'+ "" + '\n' + "" + '\n');
                        autenticationSuccessful = false;
                        userLoggedOn = false;
                        //Here to save all the modifications made to the ships in the file UserShip.txt
                        Methods.clearUserShipFile();
                        Methods.WriteUserShipFile(users, userPosition, currentUserShipsArray, shipsNotBeingUsed);
                        Methods.PressEnterKey();
                        Methods.WriteLogs("INFORMATION STORED", "");
                        break;

                //CASE 7: Log off, save progress and shut down the app.        
                    case 7:
                        System.out.println("Closing the program... ");
                        userLoggedOn = false;
                        autenticationSuccessful = false;
                        programStatus = false;
                        //Here to save all the modifications made to the ships in the file UserShip.txt
                        Methods.clearUserShipFile();
                        Methods.WriteUserShipFile(users, userPosition, currentUserShipsArray, shipsNotBeingUsed);
                        Methods.WriteLogs("INFORMATION STORED", "");
                        break;

                //CASE 8: Other options not mentioned above. All of them will give an error.
                    default:
                        System.out.println('\n'+ "" + '\n' + "" + '\n' + "Invalid option selected." + '\n'+ "" + '\n' + "" + '\n');
                        break;
                }
            }
        }
        inUser.close();
        inUserCase5.close();
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


}