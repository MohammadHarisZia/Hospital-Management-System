import Attributes.Appointment;
import Attributes.Doctor;
import Attributes.Patient;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Objects;

public class Server {

    private static void writeToFile(Object b){
        String ClassName=b.getClass().getSimpleName();
        ArrayList<Object> list = ReadFile(b);
        list.add(b);
        try(ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream(ClassName,false))){
            outStream.writeObject(list);
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }
    private static ArrayList<Object> ReadFile(Object b){
        String ClassName=b.getClass().getSimpleName();
        ArrayList <Object> list= new ArrayList<>();
        try{
            ObjectInputStream in=new ObjectInputStream(new FileInputStream(ClassName));
            list=(ArrayList<Object>) in.readObject();
        }

        catch (ClassNotFoundException | ClassCastException | IOException c){
        }

        return list;

    }
    public static Object SearchID(Object b,int ID){
        ArrayList<Object> S=ReadFile(b);
        for(Object s: S){
            if(s instanceof Patient && ((Patient) s).getID()==ID)
                return s;
            else if (s instanceof  Doctor && ((Doctor) s).getID()==ID)
                return s;
            else if(s instanceof Appointment && ((Appointment) s).getAppNo()==ID)
                return s;
        }
        return null;
    }
    public static Object SearchName(Object b,String name){
        ArrayList<Object> S=ReadFile(b);
        for(Object s: S){
            if(s instanceof Patient && ((Patient) s).getName().equalsIgnoreCase(name))
                return s;
            else if(s instanceof Doctor && ((Doctor) s).getName().equalsIgnoreCase(name))
                return s;
        }
        return null;
    }
    private static void Delete(int ID,Object b){
        ArrayList<Object> S=ReadFile(b);
        for(Object s: S){
            if(s instanceof Patient) {
                if (((Patient) s).getID()==ID) {
                    S.remove(s);
                    try (ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream("Patient", false))) {
                        outStream.writeObject(S);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    return;
                }
            }
            if(s instanceof Doctor){
                if(((Doctor) s).getID()==ID){
                    S.remove(s);
                    try (ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream("Doctor", false))) {
                        outStream.writeObject(S);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    return;
                }
            }
            if(s instanceof Appointment){
                if(((Appointment) s).getAppNo()==ID){
                    S.remove(s);
                    try (ObjectOutputStream outStream = new ObjectOutputStream(new FileOutputStream("Appointment", false))) {
                        outStream.writeObject(S);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    return;
                }
            }
        }
    }
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        DatagramSocket serverSocket=new DatagramSocket(9876);
        byte[] receiveData=new byte[1024];
        byte[] sendData=new byte[1024];

        while (true){
            DatagramPacket recievePacket=new DatagramPacket(receiveData,receiveData.length);
            serverSocket.receive(recievePacket);

            //this is code to convert bytearray to string
            ByteArrayInputStream inputStream=new ByteArrayInputStream(receiveData);
            ObjectInput input=new ObjectInputStream(inputStream);

            Object header=input.readObject();
            Object ClientInfo=input.readObject();

            System.out.println(header.toString());

            InetAddress IP=recievePacket.getAddress();
            int port=recievePacket.getPort();

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ObjectOutputStream Reply = new ObjectOutputStream(outputStream);

            switch (header.toString()) {
                case "addPatient" -> {
                    Patient patient= (Patient) ClientInfo;
                    Server.writeToFile(patient);
                    Reply.writeObject("Patient Record has been Added with ID "+ patient.getID());
                    sendData = outputStream.toByteArray();
                }
                case "viewPatient" ->{
                    ArrayList<Object> P=ReadFile(new Patient());
                    if(P.size()>0)
                        Reply.writeObject(P);
                    else
                        Reply.writeObject("-1");


                    sendData = outputStream.toByteArray();
                }
                case "searchPatient" ->{
                    Object patient;
                    try{
                        int ID=Integer.parseInt(ClientInfo.toString());
                        patient= SearchID(new Patient(),ID);
                    }
                    catch (NumberFormatException e){
                        patient=SearchName(new Patient(),ClientInfo.toString());
                    }
                    Reply.writeObject(Objects.requireNonNullElse(patient, "-1"));
                    sendData=outputStream.toByteArray();
                }
                case "deletePatient" -> {
                    ArrayList<Object> App=ReadFile(new Appointment());
                    boolean flag=false;
                    for(Object ob: App){
                        if (((Appointment) ob).getPatient() == (Integer) ClientInfo) {
                            flag = true;
                            break;
                        }
                    }
                    if(flag)
                        Reply.writeObject("-1");
                    else{
                        Patient p= (Patient) SearchID(new Patient(),(Integer)ClientInfo);
                        Delete((Integer) ClientInfo,new Patient());
                        Reply.writeObject("Patient "+p.getName()+" deleted with ID "+p.getID());
                    }
                    sendData=outputStream.toByteArray();

                }
                case "addDoctor" ->{
                    Doctor doctor= (Doctor) ClientInfo;
                    Server.writeToFile(doctor);
                    Reply.writeObject("Doctor Record has been Added with ID "+ doctor.getID());
                    sendData = outputStream.toByteArray();
                }
                case "viewDoctor" ->{
                    ArrayList<Object> D=ReadFile(new Doctor());
                    if(D.size()>0)
                        Reply.writeObject(D);
                    else
                        Reply.writeObject("-1");


                    sendData = outputStream.toByteArray();
                }
                case "searchDoctor" ->{
                    Object doctor;
                    try{
                        int ID=Integer.parseInt(ClientInfo.toString());
                        doctor= SearchID(new Doctor(),ID);
                    }
                    catch (NumberFormatException e){
                        doctor=SearchName(new Doctor(),ClientInfo.toString());
                    }
                    Reply.writeObject(Objects.requireNonNullElse(doctor, "-1"));
                    sendData=outputStream.toByteArray();
                }
                case "deleteDoctor" -> {
                    ArrayList<Object> App=ReadFile(new Appointment());
                    boolean flag=false;
                    for(Object ob: App){
                        if (((Appointment) ob).getDoc() == (Integer) ClientInfo) {
                            flag = true;
                            break;
                        }
                    }
                    if(flag)
                        Reply.writeObject("-1");
                    else{
                        Doctor doc= (Doctor) SearchID(new Doctor(),(Integer)ClientInfo);
                        Delete((Integer) ClientInfo,new Doctor());
                        Reply.writeObject("Doctor "+doc.getName()+" deleted with ID "+doc.getID());
                    }
                    sendData=outputStream.toByteArray();

                }
                case "addAppointment" ->{
                    Appointment App= (Appointment) ClientInfo;
                    Server.writeToFile(App);
                    Reply.writeObject("Appointment Has been Booked with ID "+ App.getAppNo());
                    sendData = outputStream.toByteArray();
                }
                case "viewAppointments" ->{
                    ArrayList<Object> App=ReadFile(new Appointment());
                    if(App.size()>0)
                        Reply.writeObject(App);
                    else
                        Reply.writeObject("-1");


                    sendData = outputStream.toByteArray();
                }
                case "searchAppointment" ->{
                    Object App;
                    try{
                        int ID=Integer.parseInt(ClientInfo.toString());
                        App= SearchID(new Appointment(),ID);
                        if(!(App ==null))
                            Reply.writeObject(App);
                        else
                            Reply.writeObject(-1);
                    }
                    catch (NumberFormatException e){
                        Reply.writeObject(-1);
                    }
                    sendData=outputStream.toByteArray();
                }
                case "deleteAppointment" -> {
                    Appointment app= (Appointment) SearchID(new Appointment(),(Integer)ClientInfo);
                    Delete((Integer) ClientInfo,new Appointment());
                    Reply.writeObject("Appointment No "+app.getAppNo()+" Cancelled");
                    sendData=outputStream.toByteArray();

                }
            }

            DatagramPacket sendPacket=new DatagramPacket(sendData,sendData.length,IP,port);

            serverSocket.send(sendPacket);
        }
    }
}

