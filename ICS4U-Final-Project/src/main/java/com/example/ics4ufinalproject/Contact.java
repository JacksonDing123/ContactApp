package com.example.ics4ufinalproject;

import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.scene.layout.Pane;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Contact {

    //all of the object variables for each contact as simple string properties except numer, everything is private due to java conventions
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty email;
    private SimpleListProperty<String> number; //simple list property for the number because it is a dynamic element

    private SimpleStringProperty postalCode;

    private SimpleStringProperty company;

    //class variable of the contactlist that will store all of the contacts
    public static ArrayList<Contact> contactList = new ArrayList<Contact>();

    /**
     * contacts constructor, the numbers input is initially a string, then each number is sparated by "," to be put into the simpleListProperty
     * @param firstName
     * @param lastName
     * @param email
     * @param number
     * @param postalCode
     * @param company
     */
    public Contact(String firstName, String lastName, String email, String number, String postalCode, String company) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.email = new SimpleStringProperty(email);
        this.number = new SimpleListProperty<String>(FXCollections.observableArrayList(Arrays.asList(number.split(","))));
        this.postalCode = new SimpleStringProperty(postalCode);
        this.company = new SimpleStringProperty(company);
    }

    //getters and setters for the variables

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getPostalCode() {
        return postalCode.get();
    }

    public void setPostalCode(String postalCode) {
        this.postalCode.set(postalCode);
    }

    public String getCompany() {
        return company.get();
    }

    public SimpleListProperty<String> getNumber(){
        return number;
    }

    /**
     * updates a value in the number's simpleListProperty through an index and value
     * @param index
     * @param newNumber
     */
    public void updateNumber(int index, String newNumber) {
        this.number.set(index, newNumber);
    }

    /**
     * adds a new number to the number list
     * @param newNumber
     */
    public void addNumber(String newNumber){
        this.number.add(newNumber);
    }

    public void setCompany(String company) {
        this.company.set(company);
    }

    /**
     * writes the information from the contactList into the JSON file
     * @throws IOException
     */
    public static void updateContacts() throws IOException {

        //get the file based on local path and create the required variables for writing into json
        File info = new File("./src/main/java/com/example/ics4ufinalproject/contacts.json");

        //makes the file
        FileWriter file = new FileWriter(info);

        //makes a JSON array where all of the contacts will be appended to
        JSONArray contactListJson = new JSONArray();

        //loops through all contacts and stores their key info like name, date, and difficulty
        for(int i = 0; i< contactList.size(); i++){

            //because numbers is a list, it needs to be a Json array as well
            JSONArray numberListJson = new JSONArray();

            //adding all the contact info into a temporary JSON object
            JSONObject tempInfo = new JSONObject();
            tempInfo.put("firstName", contactList.get(i).getFirstName());
            tempInfo.put("lastName", contactList.get(i).getLastName());
            tempInfo.put("email", contactList.get(i).getEmail());
            tempInfo.put("postalCode", contactList.get(i).getPostalCode());
            tempInfo.put("company", contactList.get(i).getCompany());

            //adding all of the numbers into the numbers JSON array
            for (String number : contactList.get(i).getNumber()) {
                // Create a JSONObject for each number
                JSONObject numberJson = new JSONObject();
                numberJson.put("number", number);
                numberListJson.add(numberJson);
            }

            // Add the numberListJson to the tempary JSON object
            tempInfo.put("numbers", numberListJson);

            //adds the tempary json object into the overall contact list array
            contactListJson.add(tempInfo);
        }

        try{
            //writes the information from the contactListArray into the json file
            file.write(contactListJson.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * method used to read the contact information from the json file
     */
    public static void readContacts(){
        //creats JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        //creates the file
        File info = new File("./src/main/java/com/example/ics4ufinalproject/contacts.json");

        try (FileReader reader = new FileReader(info))
        {

            //make an object out of the file through the File reader
            Object obj = jsonParser.parse(reader);
            //cast the object into a JSON array
            JSONArray contactList = (JSONArray) obj;

            //if the contacts is a JSON array then it goes through the array
            if (contactList instanceof JSONArray) {
                //if the list is empty the program returns
                if(contactList.isEmpty()){
                    System.out.println("empty document");
                    return;
                }
                //loops through the json file to get all of the jsonObjects and uses the lamda expression to call on the parseContactObject method which will add the contacts from the json file into the actual contact List
                contactList.forEach( emp -> parseContactObject( (JSONObject) emp) );
            } else {
                System.out.println("The file does not contain a JSON object.");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * pareses all the contacts in the json file
     * @param thisContact
     */
    private static void parseContactObject(JSONObject thisContact)
    {
        //gets the string of each properties for a contact

        String tFirstName =  (String) thisContact.get("firstName");

        String tLastName = (String) thisContact.get("lastName");

        String tEmail = (String) thisContact.get("email");

        //when it comes to numbers because it is an array when it was written into the json file,
        JSONArray numbersArray = (JSONArray) thisContact.get("numbers");
        ArrayList<String> numbersArryList = new ArrayList<String>();
        if (numbersArray instanceof JSONArray) {

            if(numbersArray.isEmpty()){
                System.out.println("empty document");
                return;
            }
            //loops through the json file to get all of the jsonObjects and uses the lamda expression to call on the parseNumberObject method which will add the numbers into the actual number list
            numbersArray.forEach( emp -> parseNumberObject( (JSONObject) emp, numbersArryList) );
        } else {
            System.out.println("The file does not contain a JSON object.");
        }

        String aNumber = String.join(",", numbersArryList);

        System.out.println(aNumber);

        String tPostalCode = (String) thisContact.get("postalCode");

        String tCompany = (String) thisContact.get("company");

        Contact tempContact = new Contact( tFirstName, tLastName, tEmail, aNumber, tPostalCode, tCompany);

        contactList.add(tempContact);
    }

    /**
     * parses all the numbers in the json files
     * @param emp
     * @param numbers
     */
    private static void parseNumberObject(JSONObject emp, ArrayList<String> numbers) {
        String number =  (String) emp.get("number");
        numbers.add(number);
    }
}
