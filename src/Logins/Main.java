package Logins;

import SlotManage.Slots;


import java.sql.*;
import java.util.Scanner;

class Login {

    protected String username, password,name;
    protected long uuid, phno;
    Scanner sc = new Scanner(System.in);


    public void Add_user()throws Exception
    {
        System.out.println("Enter Name: ");
        name = sc.next();
        System.out.println("Enter Password: ");
        password = sc.next();

    }

}

class admin extends Login {

    Scanner sc = new Scanner(System.in);
    static int count =4;

    void menu(admin obj) {
        int choice=0;
        while (choice!=3) {
            System.out.println("\n1.Add User \n2.Display details of user \n3.exit");
            System.out.print("\nPlease Enter Your Choice:");
            choice = sc.nextInt();
            try {
            switch (choice) {
                case 1:
                        obj.Add_user();


                    break;

                case 2:
                    obj.Display_User();

                    break;

                case 3:break;
            }}catch(Exception e){
                System.out.println(e);
            }
        }
    }

    public void Display_User()
    {
        try{
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:C://sqlite//Car_Parking.db");
        String q="select * from user where Id=?";

        PreparedStatement st=con.prepareStatement(q);

        Scanner sc =new Scanner(System.in);
        System.out.println("Enter the Id: ");
        int id=sc.nextInt();
        st.setInt(1,id);

        ResultSet rs= st.executeQuery();

        while(rs.next())
        {
            System.out.println("Id is :"+rs.getInt("Id")+" ");
            System.out.println("Name is :"+rs.getString("name")+" ");
            System.out.println("Password is :"+rs.getString("password")+" ");
            System.out.println("Phone number is :"+rs.getInt(4)+" ");

        }
        con.close();
        }
        catch(Exception h){
            System.out.println(h);
        }
    }
    public void Add_user(){
        System.out.println("Enter Name: ");
        name = sc.next();
        System.out.println("Enter Password: ");
        password = sc.next();
        System.out.println("Enter phone number ");
        phno = sc.nextLong();
        try{
        Class.forName("org.sqlite.JDBC");
        Connection con = DriverManager.getConnection("jdbc:sqlite:C://sqlite//Car_Parking.db");
        String sql = "INSERT INTO user(Id,name,password,phno) VALUES(?,?,?,?)";

        String z ="select max(Id) as count from user";
        Statement s= con.createStatement();
        ResultSet r=s.executeQuery(z);

        int count=r.getInt("count");
        count++;
        PreparedStatement st = con.prepareStatement(sql);
        st.setInt(1,count);
        st.setString(2,name);
        st.setString(3,password);
        st.setLong(4,phno);

        st.executeUpdate();
        con.close();
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    public void update(String pw, long phno) {

        this.password = pw;
        this.phno = phno;
    }

    public void update(String username, String pw, long phno) {
        this.username = username;
        this.password = pw;
        this.phno = phno;
    }
}
class user extends Login{
    Scanner reader =new Scanner(System.in);
    Slots add=new Slots();
    void menu(admin obj){
        int choice=0;
        int choice2=0;
        while(choice!=3){
            System.out.println("\n1.Login account \n2.Update \n3.Manage Slots \n4.Exit");
            System.out.print("\nEnter Your Choice:");
            choice = sc.nextInt();

            switch(choice){
                case 1:
                    System.out.println("\nEnter Id");
                    int id= reader.nextInt();
                    System.out.print("\nEnter Username:");
                    String usnm=reader.next();
                    System.out.print("\nEnter Password:");
                    String pwd=reader.next();

                    try{
                        Class.forName("org.sqlite.JDBC");
                        Connection con = DriverManager.getConnection("jdbc:sqlite:C://sqlite//Car_Parking.db");
                        String sql2= "select * from user where Id= ? and password=?";
                        PreparedStatement st1 = con.prepareStatement(sql2);

                        st1.setInt(1,id);
                        st1.setString(2,pwd);
                        ResultSet rs= st1.executeQuery();
                        while (rs.next())
                        {
                             if ((rs.getString("name").equals(usnm))&&
                            (rs.getString("password").equals(pwd)))
                             {
                                 System.out.println("Login Successful ");
                             }
                             else {
                                 System.out.println("Invalid Login ");
                             }
                        }
                        con.close();
                    }
                    catch(Exception f)
                    {
                        System.out.println(f);
                    }

                    break;

                case 2:
                    System.out.println("\n Enter user Id: ");
                    id=reader.nextInt();
                    System.out.print("\nEnter Updated Username: ");
                    usnm=reader.next();
                    System.out.print("\nEnter Updated Password ");
                    pwd=reader.next();
                    System.out.println("Enter Updated Phone Number: ");
                    long phn = sc.nextLong();
                    try {
                        Class.forName("org.sqlite.JDBC");
                        Connection con = DriverManager.getConnection("jdbc:sqlite:C://sqlite//Car_Parking.db");
                        String sql3 = "update user set name= ? ," + "password= ? ," + "phno= ?" + "where Id =?";

                        PreparedStatement st2= con.prepareStatement(sql3);
                        st2.setString(1,usnm);
                        st2.setString(2,pwd);
                        st2.setLong(3,phn);
                        st2.setInt(4,id);
                        st2.executeUpdate();
                        con.close();
                    }
                    catch(Exception g)
                    {
                        System.out.println(g);
                    }
                    break;
                   // obj.update(usnm, pwd, phn);


                case 3:
                    add.createSlots();
                    while(choice2!=4) {
                        System.out.println("\n1.AddSlot \n2.Remove Slot \n3.Display slots \n4.Exit");
                        System.out.println("Enter your choice");
                        choice2= sc.nextInt();
                        switch (choice2){
                            case 1:
                                add.addSlot();
                                break;
                            case 2:
                                add.remove();
                                break;
                            case 3:
                                add.displaySlots();
                                break;
                            case 4:
                                break;
                        }

                    }
                    break;

                case 4:
                    System.exit(0);
            }
        }
    }
}
public class Main {

    public static void main(String[] args) {
        // TODO code application logic here2
        String username, password;
        long ph;
        admin obj = new admin();
        user us=new user();
        Slots s=new Slots();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n**** WELCOME TO CAR PARKING SYSTEM ****");
            System.out.println("\n1.Admin Login \n2.User login \n3.Exit");
            System.out.print("\nEnter Your Choice:");
            int ch = sc.nextInt();
            switch (ch) {
                case 1: obj.menu(obj);
                    break;

                case 2: us.menu(obj);
                    break;

                case 3:System.exit(0);
            }
        }
    }
}
