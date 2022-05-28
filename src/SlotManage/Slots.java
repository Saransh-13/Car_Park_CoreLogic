package SlotManage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Slots {
    Scanner sc = new Scanner(System.in);
    public int size;
    boolean[] SlotList;
    String driver;
    String num;


    public void createSlots() {
        System.out.println("Enter number of slots in parking ");
        size = sc.nextInt();
        SlotList = new boolean[size];
        for (int s = 0; s < SlotList.length; s++) {
            SlotList[s] = false;
        }
    }
    synchronized public void addSlot()
    {
        System.out.println("Enter the name of Driver ");
        driver= sc.next();
        System.out.println("Enter Vehicle number ");
        num=sc.next();
        int i;
        System.out.println("Enter the slot no.");
        i= sc.nextInt();
        try{
        if(!SlotList[i])
        {
            System.out.println("Slot "+i+" is alloted ");
            SlotList[i]=true;
        }
        else {
            System.out.println("Not alloted ");
        }
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:C://sqlite//Car_Parking.db");
            String insert="Insert into Car_Details values(?,?,?)";

            PreparedStatement p=con.prepareStatement(insert);

            p.setInt(1,i);
            p.setString(2,driver);
            p.setString(3,num);

            p.executeUpdate();
            con.close();
        }
        catch(Exception a)
        {
            System.out.println(a);
        }
    }
    public void remove()
    {
        int slotRemove;
        System.out.println("Enter the slot number to be empty: ");
        slotRemove= sc.nextInt();

        try{
        if (slotRemove<SlotList.length)
        {
            SlotList[slotRemove] = false;
        }
        else {
            System.out.println("Invalid slot no. ");
        }

            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:C://sqlite//Car_Parking.db");
            String del="delete from Car_Details where slotNo=?";

            PreparedStatement p1=con.prepareStatement(del);
            p1.setInt(1,slotRemove);
            p1.executeUpdate();
            con.close();
        }

        catch(Exception b)
        {
            System.out.println(b.getMessage());
        }
    }
    public void displaySlots()
    {
        for( int i=0;i<SlotList.length;i++)
            System.out.println("Slot "+i+" "+SlotList[i]);
    }
}

