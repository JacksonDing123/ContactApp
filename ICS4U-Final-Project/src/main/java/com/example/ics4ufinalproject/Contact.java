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
    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty email;
    private SimpleListProperty<String> number;

    private SimpleStringProperty postalCode;

    private SimpleStringProperty company;

    public static ArrayList<Contact> contactList = new ArrayList<Contact>();

    public Contact(String firstName, String lastName, String email, String number, String postalCode, String company) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.email = new SimpleStringProperty(email);
        this.number = new SimpleListProperty<String>(FXCollections.observableArrayList(Arrays.asList(number.split(","))));
        this.postalCode = new SimpleStringProperty(postalCode);
        this.company = new SimpleStringProperty(company);
    }

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

    public void updateNumber(int index, String newNumber) {
        this.number.set(index, newNumber);
    }

    public void setCompany(String company) {
        this.company.set(company);
    }

    public static void updateContacts() throws IOException {

        //get the file based on local path and create the required variables for writing into json
        File info = new File("./src/main/java/com/example/ics4ufinalproject/contacts.json");

        FileWriter file = new FileWriter(info);

        JSONArray contactListJson = new JSONArray();

        //loops through all tasks and stores their key info like name, date, and difficulty
        for(int i = 0; i< contactList.size(); i++){

            JSONArray numberListJson = new JSONArray();

            JSONObject tempInfo = new JSONObject();
            tempInfo.put("firstName", contactList.get(i).getFirstName());
            tempInfo.put("lastName", contactList.get(i).getLastName());
            tempInfo.put("email", contactList.get(i).getEmail());
            tempInfo.put("postalCode", contactList.get(i).getPostalCode());
            tempInfo.put("company", contactList.get(i).getCompany());

            for (String number : contactList.get(i).getNumber()) {
                // Create a JSONObject for each number
                JSONObject numberJson = new JSONObject();
                numberJson.put("number", number);
                numberListJson.add(numberJson);
            }

            // Add the numberListJson to the contactJson
            tempInfo.put("numbers", numberListJson);

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
     */
    public static void readContacts(){
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        File info = new File("./src/main/java/com/example/ics4ufinalproject/contacts.json");

        try (FileReader reader = new FileReader(info))
        {

            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray contactList = (JSONArray) obj;

            if (contactList instanceof JSONArray) {

                if(contactList.isEmpty()){
                    System.out.println("empty document");
                    return;
                }
                //loops through the json file to get all of the jsonObjects and uses the lamda expression to call on the parseTaskObject method which will add the tasks into the actual task List
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
     * records down the information of the task passed in as parameters and put it into arrayList
     */
    private static void parseContactObject(JSONObject thisContact)
    {
        //JSONObject thisTask = (JSONObject) task.get("Task");

        String tFirstName =  (String) thisContact.get("firstName");

        String tLastName = (String) thisContact.get("lastName");

        String tEmail = (String) thisContact.get("email");

        JSONArray numbersArray = (JSONArray) thisContact.get("numbers");
        ArrayList<String> numbersArryList = new ArrayList<String>();
        if (numbersArray instanceof JSONArray) {

            if(numbersArray.isEmpty()){
                System.out.println("empty document");
                return;
            }
            //loops through the json file to get all of the jsonObjects and uses the lamda expression to call on the parseTaskObject method which will add the tasks into the actual task List
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

    private static void parseNumberObject(JSONObject emp, ArrayList<String> numbers) {
        String number =  (String) emp.get("number");
        numbers.add(number);
    }
}
