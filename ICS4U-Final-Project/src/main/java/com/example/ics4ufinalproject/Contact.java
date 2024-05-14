package com.example.ics4ufinalproject;

import javafx.scene.layout.Pane;

import java.io.*;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Contact {
    private String firstName;
    private String lastName;
    private String email;
    private String number;

    private String postalCode;

    private String company;

    public static ArrayList<Contact> contactList;

    public Contact(String firstName, String lastName, String email, String number, String postalCode, String company) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.number = number;
        this.postalCode = postalCode;
        this.company = company;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public static void updateContacts(){

    }

    public static void addContact(Contact contact){

    }

    public static void deleteContact(Contact contact){

    }

    public static void updateTaskList() throws IOException {

        //get the file based on local path and create the required variables for writing into json
        File info = new File("./src/main/java/com/example/motivationaltasktracker/info.json");

        FileWriter file = new FileWriter(info);

        JSONArray contactListJson = new JSONArray();

        //loops through all tasks and stores their key info like name, date, and difficulty
        for(int i = 0; i< contactList.size(); i++){

            JSONObject tempInfo = new JSONObject();
            tempInfo.put("firstName", contactList.get(i).getFirstName());
            tempInfo.put("lastName", contactList.get(i).getLastName());
            tempInfo.put("email", contactList.get(i).getEmail());
            tempInfo.put("number", contactList.get(i).getNumber());
            tempInfo.put("postalCode", contactList.get(i).getPostalCode());
            tempInfo.put("company", contactList.get(i).getCompany());

            contactListJson.add(tempInfo);
        }

        try{
            //We can write any JSONArray or JSONObject instance to the file
            file.write(contactListJson.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * method used to read the information from the json file, used in the very start of code
     * @param pane
     */
    public static void readTasks(Pane pane){
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        File info = new File("ICS4U-Final-Project/src/main/java/com/example/ics4ufinalproject/contacts.json");

        try (FileReader reader = new FileReader(info))
        {

            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray contactList = (JSONArray) obj;

            if (contactList instanceof JSONArray) {
                //loops through the json file to get all of the jsonObjects and uses the lamda expression to call on the parseTaskObject method which will add the tasks into the actual task List
                contactList.forEach( emp -> parseContactObject( (JSONObject) emp, pane ) );
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
     * records down the information of the task passed in as parameters and put it into arrayList
     * @param thisTask
     * @param pane
     */
    private static void parseContactObject(JSONObject thisContact, Pane pane)
    {
        //JSONObject thisTask = (JSONObject) task.get("Task");

        String tFirstName =  (String) thisContact.get("firstName");

        String tLastName = (String) thisContact.get("lastName");

        String tEmail = (String) thisContact.get("email");

        String tNumber = (String) thisContact.get("number");

        String tPostalCode = (String) thisContact.get("postalCode");

        String tCompany = (String) thisContact.get("company");

        Contact tempContact = new Contact( tFirstName, tLastName, tEmail, tNumber, tPostalCode, tCompany);

        contactList.add(tempContact);
    }
}
