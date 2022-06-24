import Attributes.AlertBox;
import Attributes.Appointment;
import Attributes.Doctor;
import Attributes.Patient;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends Application {

    @FXML
    TextField patientID;
    @FXML
    TextField patientName;
    @FXML
    TextField patientAge;
    @FXML
    TextField patientEmail;
    @FXML
    TextField patientPhNo;

    @FXML
    TableView<Patient> iPatientTable;

    @FXML
    TableColumn<Patient,Integer> iPatientID;
    @FXML
    TableColumn<Patient,String> iPatientName;
    @FXML
    TableColumn<Patient,Integer> iPatientAge;
    @FXML
    TableColumn<Patient,String> iPatientEmail;
    @FXML
    TableColumn<Patient,String> iPatientPhoneNo;

    @FXML
    ComboBox categories;

    @FXML
    TextField doctorID;
    @FXML
    TextField doctorName;
    @FXML
    TextField doctorPhoneNo;
    @FXML
    TextField doctorEmail;

    @FXML
    TableView<Doctor> iDoctorTable;

    @FXML
    TableColumn<Doctor,Integer> iDoctorID;
    @FXML
    TableColumn<Doctor,String> iDoctorName;
    @FXML
    TableColumn<Doctor,String> iDoctorCategory;
    @FXML
    TableColumn<Doctor,String> iDoctorEmail;
    @FXML
    TableColumn<Doctor,String> iDoctorPhoneNo;

    @FXML
    TextField appointmentID;
    @FXML
    TextField doctorIDApp;
    @FXML
    TextField disease;
    @FXML
    TextField patientIDApp;
    @FXML
    DatePicker appointmentDate;

    @FXML
    TableView<Appointment> iAppointmentTable;

    @FXML
    TableColumn<Appointment,Integer> iAppointmentID;
    @FXML
    TableColumn<Appointment, Integer> iDoctorNameApp;
    @FXML
    TableColumn<Appointment,Integer> iPatientNameApp;
    @FXML
    TableColumn<Appointment,String> iDisease;
    @FXML
    TableColumn<Appointment,String> iAppointmentDate;


    Pattern patternID = Pattern.compile("^\\d{4}$");
    Pattern patternNo = Pattern.compile("^((\\+92)|(0092)|(92)|(0))(3)([0-9]{9})$");
    Pattern patternEmail=Pattern.compile("^([A-Za-z0-9_.-]{5,}[@][A-Za-z]{3,}([.][a-z]{1,6}){1,3})$");
    Pattern patternage = Pattern.compile("^\\d{2}$");

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("GUI/startWindow.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Hospital Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.centerOnScreen();
    }

    public void switchScene (ActionEvent event, String s)throws Exception{
        Parent tableViewParent = FXMLLoader.load(getClass().getResource(s));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
        window.centerOnScreen();
    }
    public Object Socket(String header,Object Data) throws IOException, ClassNotFoundException {
        byte[] sendData;
        byte[] receiveData=new byte[1024];

        DatagramSocket ClientSocket=new DatagramSocket();
        InetAddress IP = InetAddress.getByName("Lemy");
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream Msg = new ObjectOutputStream(outputStream);

        Msg.writeObject(header);
        Msg.writeObject(Data);
        Msg.close();

        sendData=outputStream.toByteArray();

        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IP, 9876);

        ClientSocket.send(sendPacket);

        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

        ClientSocket.receive(receivePacket);

        ByteArrayInputStream inputStream=new ByteArrayInputStream(receiveData);
        ObjectInput ObjectInput=new ObjectInputStream(inputStream);

        return ObjectInput.readObject();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void newScene(ActionEvent e) throws Exception {

        String v =  ((Button)e.getSource()).getId();
        if(v.equalsIgnoreCase("continue")){
            switchScene(e, "GUI/mainMenu.fxml");
        }
        else if(v.equalsIgnoreCase("backPatientDashboard")){
            switchScene(e, "GUI/patientDashboard.fxml");
        }
        else if(v.equalsIgnoreCase("backDoctorDashboard")){
            switchScene(e, "GUI/doctorDashboard.fxml");
        }
        else if(v.equalsIgnoreCase("backAppointmentDashboard")){
            switchScene(e, "GUI/appointmentDashboard.fxml");
        }
        else if(v.equalsIgnoreCase("backMain")){
            switchScene(e, "GUI/mainMenu.fxml");
        }
        else if(v.equalsIgnoreCase("backStart")){
            switchScene(e, "GUI/startWindow.fxml");
        }
        else if(v.equalsIgnoreCase("patient")){
            switchScene(e, "GUI/patientDashboard.fxml");
        }
        else if(v.equalsIgnoreCase("doctor")){
            switchScene(e, "GUI/doctorDashboard.fxml");
        }
        else if(v.equalsIgnoreCase("appointments")){
            switchScene(e, "GUI/appointmentDashboard.fxml");
        }
        else if(v.equalsIgnoreCase("addPatient")){
            switchScene(e, "GUI/addPatient.fxml");
        }
        else if(v.equalsIgnoreCase("addDoctor")){
            switchScene(e, "GUI/addDoctor.fxml");
        }
        else if(v.equalsIgnoreCase("addAppointment")){
            switchScene(e, "GUI/addAppointment.fxml");
        }
        else if(v.equalsIgnoreCase("viewPatient")){
            switchScene(e, "GUI/patientTable.fxml");
        }
        else if(v.equalsIgnoreCase("viewDoctor")){
            switchScene(e, "GUI/doctorTable.fxml");
        }
        else if(v.equalsIgnoreCase("viewAppointment")){
            switchScene(e, "GUI/appointmentTable.fxml");
        }
        else if(v.equalsIgnoreCase("exit")){
            System.exit(1);
        }
    }

    public void addPatient() throws IOException, ClassNotFoundException {
        if(!patientID.getText().isEmpty()){
            Matcher matchID = patternID.matcher(patientID.getText());
            if(matchID.find()) {
                if (Server.SearchID(new Patient(),Integer.parseInt(patientID.getText()))==null) {
                    if(!patientName.getText().isEmpty()){
                        Matcher matchAge = patternage.matcher(patientAge.getText());
                        if(!patientAge.getText().isEmpty()) {
                            if (matchAge.find()) {
                                Matcher matchEmail = patternEmail.matcher(patientEmail.getText());
                            if (!patientEmail.getText().isEmpty()) {
                                if(matchEmail.find()){
                                        if (!patientPhNo.getText().isEmpty()) {
                                            Matcher matchNo = patternNo.matcher(patientPhNo.getText());
                                            if (matchNo.find()) {
                                                Patient p=new Patient(Integer.parseInt(patientID.getText()),patientName.getText(),
                                                        Integer.parseInt(patientAge.getText()),patientEmail.getText(),patientPhNo.getText());
                                                Object Reply=Socket("addPatient",p);
                                                AlertBox.informationAlert("Server Response",Reply.toString());
                                                patientID.clear();
                                                patientName.clear();
                                                patientAge.clear();
                                                patientPhNo.clear();
                                                patientEmail.clear();
                                            }
                                            else
                                                AlertBox.errorAlert("Format Error", "Enter a Valid Pakistani Number");
                                        }
                                        else
                                            AlertBox.errorAlert("Null Error","Phone number of Patient is not specified");
                                }
                                else
                                    AlertBox.errorAlert("Format Error","Enter a Valid email address");
                            }
                            else
                                AlertBox.errorAlert("Null Error","Email Address field cannot be empty");


                        }
                            else
                                AlertBox.errorAlert("Age Error","Enter an Actual Age");
                        }
                        else
                            AlertBox.errorAlert("Null Error","Age field cannot be empty");

                    }
                    else
                        AlertBox.errorAlert("Null Error","Name field cannot be empty");
                }
                else
                    AlertBox.errorAlert("Redundancy Error","Patient with same ID already exists");
            }
            else
                AlertBox.errorAlert("Format Error","Please Enter a 4 digit ID");
        }
        else
            AlertBox.warningAlert("Null Error","ID field is empty");
    }
    public void searchPatient() throws IOException, ClassNotFoundException {
        String info=AlertBox.textDialogue("Patient","Search Patient Records","Please Enter Name or ID");
        if(!info.equals("-1")) {
            Object Reply = Socket("searchPatient", info);
            if(Reply.toString().equals("-1"))
                AlertBox.informationAlert("Not Found!","No Patient with this ID or name exists");
            else
                AlertBox.informationAlert("Patient Found!",Reply.toString());
        }
        else
            AlertBox.warningAlert("Null Error","Input Field is Empty");


    }
    public void viewPatient() throws IOException, ClassNotFoundException {
        Object Reply=Socket("viewPatient","view");
        if(!Reply.toString().equals("-1")){
            ObservableList<Patient> data=FXCollections.observableArrayList();
            ArrayList<Patient> P= (ArrayList<Patient>) Reply;
            data.addAll(P);
            iPatientID.setCellValueFactory((new PropertyValueFactory<>("ID")));
            iPatientName.setCellValueFactory((new PropertyValueFactory<>("name")));
            iPatientAge.setCellValueFactory((new PropertyValueFactory<>("age")));
            iPatientEmail.setCellValueFactory((new PropertyValueFactory<>("email")));
            iPatientPhoneNo.setCellValueFactory((new PropertyValueFactory<>("phoneNo")));
            iPatientTable.setItems(data);

        }
        else
            AlertBox.warningAlert("Empty","No Patients Record have been added!");
    }

    public void deletePatient() throws IOException, ClassNotFoundException {
        if(iPatientTable.getSelectionModel().getSelectedItem()==null){
            AlertBox.warningAlert("Selection Error","Please Select A record u want to delete");
        }
        else {
            Patient patient = iPatientTable.getSelectionModel().getSelectedItem();
            Object Reply=Socket("deletePatient",patient.getID());
            if(Reply.toString().equals("-1"))
                AlertBox.warningAlert("Cannot Delete","Patient Alread has an Appointment Booked!!");
            else
                AlertBox.informationAlert("Record Deleted!",Reply.toString());
        }
    }

    public void addDoctor() throws IOException, ClassNotFoundException {
        if(!doctorID.getText().isEmpty()){
            Matcher matchID = patternID.matcher(doctorID.getText());
            if(matchID.find()) {
                if (Server.SearchID(new Doctor(),Integer.parseInt(doctorID.getText()))==null) {
                    if(!doctorName.getText().isEmpty()){
                        Matcher matchNo = patternNo.matcher(doctorPhoneNo.getText());
                        if(!doctorPhoneNo.getText().isEmpty()) {
                            if (matchNo.find()) {
                                Matcher matchEmail = patternEmail.matcher(doctorEmail.getText());
                                if (!doctorEmail.getText().isEmpty()) {
                                    if(matchEmail.find()){
                                        if (!(categories.getValue() ==null)) {
                                            Doctor d=new Doctor(Integer.parseInt(doctorID.getText()),doctorName.getText(),doctorEmail.getText(),
                                                    doctorPhoneNo.getText(),categories.getValue().toString());
                                            Object Reply=Socket("addDoctor",d);
                                            AlertBox.informationAlert("Server Response",Reply.toString());
                                            doctorID.clear();
                                            doctorName.clear();
                                            doctorPhoneNo.clear();
                                            doctorEmail.clear();

                                        }
                                        else
                                            AlertBox.errorAlert("Null Error","Please Select a Category");
                                    }
                                    else
                                        AlertBox.errorAlert("Format Error","Enter a Valid email address");
                                }
                                else
                                    AlertBox.errorAlert("Null Error","Email Address field cannot be empty");


                            }
                            else
                                AlertBox.errorAlert("Age Error","Enter a Valid Pakistani Phone no");
                        }
                        else
                            AlertBox.errorAlert("Null Error","Phone number field cannot be empty");

                    }
                    else
                        AlertBox.errorAlert("Null Error","Name field cannot be empty");
                }
                else
                    AlertBox.errorAlert("Redundancy Error","Doctor with same ID already exists");
            }
            else
                AlertBox.errorAlert("Format Error","Please Enter a 4 digit ID");
        }
        else
            AlertBox.warningAlert("Null Error","ID field is empty");
    }

    public void viewDoctor() throws IOException, ClassNotFoundException {
        Object Reply=Socket("viewDoctor","view");
        if(!Reply.toString().equals("-1")){
            ObservableList<Doctor> data=FXCollections.observableArrayList();
            ArrayList<Doctor> D= (ArrayList<Doctor>) Reply;
            data.addAll(D);
            iDoctorID.setCellValueFactory((new PropertyValueFactory<>("ID")));
            iDoctorName.setCellValueFactory((new PropertyValueFactory<>("name")));
            iDoctorCategory.setCellValueFactory((new PropertyValueFactory<>("Category")));
            iDoctorEmail.setCellValueFactory((new PropertyValueFactory<>("email")));
            iDoctorPhoneNo.setCellValueFactory((new PropertyValueFactory<>("phoneNo")));
            iDoctorTable.setItems(data);

        }
        else
            AlertBox.warningAlert("Empty","No Doctor Records have been added!");
    }
    public void searchDoctor() throws IOException, ClassNotFoundException {
        String info=AlertBox.textDialogue("Doctor","Search Doctor Records","Please Enter Name or ID");
        if(!info.equals("-1")) {
            Object Reply = Socket("searchDoctor", info);
            if(Reply.toString().equals("-1"))
                AlertBox.informationAlert("Not Found!","No Doctor with this ID or name exists");
            else
                AlertBox.informationAlert("Doctor Found!",Reply.toString());
        }
        else
            AlertBox.warningAlert("Null Error","Input Field is Empty");


    }
    public void deleteDoctor() throws IOException, ClassNotFoundException {
        if(iDoctorTable.getSelectionModel().getSelectedItem()==null){
            AlertBox.warningAlert("Selection Error","Please Select A record u want to delete");
        }
        else {
            Doctor doc = iDoctorTable.getSelectionModel().getSelectedItem();
            Object Reply=Socket("deleteDoctor",doc.getID());
            if(Reply.toString().equals("-1"))
                AlertBox.warningAlert("Cannot Delete","Doctor has an Appointment with a Patient!!");
            else
                AlertBox.informationAlert("Record Deleted!",Reply.toString());
        }
    }

    public void addAppointment() throws IOException, ClassNotFoundException {
        if(!appointmentID.getText().isEmpty()){
            Matcher matchID = patternID.matcher(appointmentID.getText());
            if(matchID.find()) {
                if (!doctorIDApp.getText().isEmpty()) {
                    int ID = -1;
                    String name = "-1";
                    try {
                        ID = Integer.parseInt(doctorIDApp.getText());
                    } catch (NumberFormatException e) {
                        name = doctorIDApp.getText();
                    }
                    if (Server.SearchID(new Doctor(), ID) != null || Server.SearchName(new Doctor(), name) != null) {
                        Doctor doc = null;
                        if(Server.SearchID(new Doctor(), ID) != null)
                            doc=((Doctor)Server.SearchID(new Doctor(), ID));
                        if(Server.SearchName(new Doctor(),name)!=null)
                            doc=((Doctor)Server.SearchName(new Doctor(), name));
                        if(!patientIDApp.getText().isEmpty()){
                            try {
                                ID = Integer.parseInt(patientIDApp.getText());
                            } catch (NumberFormatException e) {
                                name = patientIDApp.getText();
                            }
                            if (Server.SearchID(new Patient(), ID) != null || Server.SearchName(new Patient(), name) != null){
                                Patient patient = null;
                                if(Server.SearchID(new Patient(), ID) != null)
                                    patient=(Patient)Server.SearchID(new Patient(), ID);
                                if(Server.SearchName(new Patient(),name)!=null)
                                    patient=(Patient)Server.SearchName(new Patient(), name);
                                if(!disease.getText().isEmpty()){
                                    if(!(appointmentDate.getValue() ==null)){
                                        Appointment A=new Appointment(Integer.parseInt(appointmentID.getText()),doc.getID(),patient.getID()
                                        ,disease.getText(),appointmentDate.getValue().toString());
                                        Object Reply=Socket("addAppointment",A);
                                        AlertBox.informationAlert("Server Response",Reply.toString());
                                        appointmentID.clear();
                                        doctorIDApp.clear();
                                        patientIDApp.clear();
                                        disease.clear();
                                        appointmentDate.getEditor().clear();
                                    }
                                    else
                                        AlertBox.errorAlert("Null","Please Select An appointment Date");
                                }
                                else
                                    AlertBox.errorAlert("Empty","Disease Field cannot be empty");
                            }
                            else
                                AlertBox.errorAlert("Not Found!", "No Patient with that name or ID exists");
                        }
                        else
                            AlertBox.errorAlert("Empty","Patient Field cannot be empty");
                    }
                    else
                        AlertBox.errorAlert("Not Found!", "No Doctor with that name or ID exists");
                }
                else
                    AlertBox.errorAlert("Empty","Doctor Field cannot be empty");
            }
            else
                AlertBox.errorAlert("Format Error","Please Enter a 4 digit ID");
        }
        else
            AlertBox.warningAlert("Null Error","ID field is empty");
    }
    public void viewAppointments() throws IOException, ClassNotFoundException {
        Object Reply=Socket("viewAppointments","view");
        if(!Reply.toString().equals("-1")){
            ObservableList<Appointment> data=FXCollections.observableArrayList();
            ArrayList<Appointment> App= (ArrayList<Appointment>) Reply;
            data.addAll(App);
            iAppointmentID.setCellValueFactory((new PropertyValueFactory<>("appNo")));
            iDoctorNameApp.setCellValueFactory(new PropertyValueFactory<>("Doc"));
            iPatientNameApp.setCellValueFactory((new PropertyValueFactory<>("Patient")));
            iDisease.setCellValueFactory((new PropertyValueFactory<>("disease")));
            iAppointmentDate.setCellValueFactory((new PropertyValueFactory<>("appDate")));
            iAppointmentTable.setItems(data);
        }
        else
            AlertBox.warningAlert("Empty","No Appointments have been booked yet!");
    }
    public void searchAppointment() throws IOException, ClassNotFoundException {
        String info=AlertBox.textDialogue("Appointments","Search For an Appointment","Please Enter Appointment No");
        if(!info.equals("-1")) {
            Object Reply = Socket("searchAppointment", info);
            if(Reply.toString().equals("-1"))
                AlertBox.informationAlert("Not Found!","No Appointments with this ID found");
            else {
                Object[] obj=Reply.toString().split("\n");
                String p=((Patient) (Server.SearchID(new Patient(),Integer.parseInt(String.valueOf(obj[2]))))).getName();
                String d=((Doctor) (Server.SearchID(new Doctor(),Integer.parseInt(String.valueOf(obj[1]))))).getName();
                obj[2]="Patient: "+p+" with ID "+obj[2];
                obj[1]="Doctor : "+d+" with ID "+obj[1];
                StringBuilder reply= new StringBuilder();
                for(Object ob:obj){
                    reply.append(ob).append("\n");
                }
                AlertBox.informationAlert("Appointment Found!", String.valueOf(reply));

            }
        }
        else
            AlertBox.warningAlert("Null Error","Input Field is Empty");


    }
    public void deleteAppointment() throws IOException, ClassNotFoundException {
        if(iAppointmentTable.getSelectionModel().getSelectedItem()==null){
            AlertBox.warningAlert("Selection Error","Please Select A record u want to delete");
        }
        else {
            Appointment App = iAppointmentTable.getSelectionModel().getSelectedItem();
            Object Reply=Socket("deleteAppointment",App.getAppNo());
            AlertBox.informationAlert("Record Deleted!",Reply.toString());
        }
    }


}
