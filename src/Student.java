import java.sql.*;
import java.util.Scanner;

public class Student {

   public static final String url = "jdbc:mysql://localhost:3306/information";


    public static final String userName = "root";

    public static final String password = "Anand_kr@2004";

    public static void main(String[] args) {


        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");

        }catch (ClassNotFoundException e)
        {
            System.out.println(  e.getMessage());

        }

        try {
            Connection connection = DriverManager.getConnection(url,userName,password);
            while (true)
            {
                System.out.println();
                System.out.println("welcome to Simple Contact Management System");
                Scanner sc = new Scanner(System.in);
                System.out.println("1. Add new Contact");
                System.out.println("2. View All Contact");
                System.out.println("3. Edit contact by Id");
                System.out.println("4. Delete contact by id");
                System.out.println("5. Program Exit");
                System.out.println("Enter your choice: ");

                int choice =  sc.nextInt();
                switch (choice)
                {
                    case 1:
                        addNewContact(connection,sc);
                        break;
                    case 2:
                        viewContact(connection);
                        break;
                    case 3:
                        updateContact(connection,sc);
                        break;
                    case 4:
                        deleteContact(connection,sc);
                        break;
                    case 5:
                        Exit();
                        break;
                    default:
                        System.out.println("invalid choice");

                }

            }

        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addNewContact( Connection connection,Scanner sc)
    {
            System.out.println();
            System.out.println("Enter student ID");
            int studentID = sc.nextInt();
            sc.nextLine();
            System.out.print("Enter your Name : ");
            String name = sc.next();
            sc.nextLine();
            System.out.print("Enter your Phone Number: ");
            String phoneNumber = sc.next();
            sc.nextLine();
            System.out.print("Enter your Email Address: ");
            String EmailAddress = sc.next();
            sc.nextLine();

            String query = "Insert into Contact_student (Student_id,name,Phone_number ,Email_address) " + " value(" + studentID + " ,'" + name + "','" + phoneNumber + "','" + EmailAddress + "')";

            try (Statement statement = connection.createStatement()) {
                int affectedRows = statement.executeUpdate(query);


                if (affectedRows > 0) {
                    System.out.println("Add contact successfully");
                } else {
                    System.out.println("failed to contact");
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }



    }

    public static void viewContact(Connection connection)
    {
        String sql = "select Student_id,Name,Phone_number,Email_address from Contact_student";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            System.out.println("Current Contact details");
            System.out.println("+---------------+-----------------------+-----------------------------------");
            System.out.println("|  Name         |  phone number         + Email Address                     |");
            System.out.println("+---------------+-----------------------+-----------------------------------");
            

            while (resultSet.next())
            {
                int student_id = resultSet.getInt("Student_id");
                String name = resultSet.getString("Name");
                String phoneNumber = resultSet.getString("Phone_number");
                String EmailAddress = resultSet.getString("Email_address");

//                format the contact detail in table form

                System.out.printf("| %-10d  | %-15s |  %-15s | %-20s  |\n",student_id,name,phoneNumber,EmailAddress);

            }

            System.out.println("+---------------+-----------------------+-----------------------------------");
        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void updateContact(Connection connection,Scanner sc)
    {
        System.out.println("Enter student_id for update some contact Details ");
        int Id = sc.nextInt();
        sc.nextLine();


        if(!StudentExist(connection,Id))
        {
            System.out.println(" StudentId not found and Student not Exist");
        }


        System.out.println("enter new name");
        String newName = sc.next();
        sc.nextLine();
        System.out.println("enter new Phone number");
        String phoneNumber = sc.next();
        sc.nextLine();
        System.out.println("enter new email");
        String email = sc.next();
        sc.nextLine();

        String query = "update Contact_student SET name = '" + newName + "', " +
            "Phone_number = '" + phoneNumber + "', " + "Email_address = '" +email+ "' " +
            "Where Student_id = " + Id;

        try (Statement statement = connection.createStatement()){

            int rowAffected = statement.executeUpdate(query);

            if(rowAffected > 0)
            {
                System.out.println("update successfully");
            }
            else {
                System.out.println("update failed");
            }

        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteContact(Connection connection,Scanner sc)
    {
        System.out.println("Enter studentID which want to be deleted");
        int Id = sc.nextInt();

        if(!StudentExist(connection,Id))
        {
            System.out.println(" StudentId not found and Student not Exist");
        }


        String query = "Delete from Contact_student where Student_id = " + Id;

        try(Statement statement = connection.createStatement()) {
            int rowAffected = statement.executeUpdate(query);

            if(rowAffected > 0)
            {
                System.out.println("Deleted successfully");
            }
            else {
                System.out.println("delete by id failed");
            }
        }
        catch (SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }

    public static boolean StudentExist(Connection connection,int Id)
    {
        try {
            String query = "select Student_id from Contact_student where Student_id = " +Id;

            try(Statement statement = connection.createStatement()) {
                ResultSet resultSet = statement.executeQuery(query);

                return resultSet.next();
            }

        }catch (SQLException e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static void Exit() throws InterruptedException {
        System.out.print("Thank You");
        int i = 5;
        while (i!=0)
        {
            Thread.sleep(1000);
            System.out.print(('.'));
            i--;
        }

        System.out.print("Exit Your Simple Contact management System");
        System.out.println();
        System.out.println("-----------------------------------------------");
    }

}


