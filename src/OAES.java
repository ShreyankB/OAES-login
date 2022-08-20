import java.sql.*;
import java.util.Scanner;

public class OAES {

    public final String dbURL = "jdbc:mysql://localhost:3306/OAES";
    public final String dbUname = "root";
    public final String dbPass = "password";

    public static void main(String[] args) throws  Exception {
        int option = 0;
        Scanner sc = new Scanner(System.in);
        OAES oaes = new OAES();

        while(option != 4) {
            System.out.println("-------------------------------------------------------------");
            System.out.println("Welcome to OAES");
            System.out.println("-------------------------------------------------------------");
            System.out.print("1. Register \n2. Login \n3. Forgot password \n4.exit \nchoice: ");
            option = sc.nextInt();

            switch (option) {
                case 1: oaes.register();
                        break;
                case 2: oaes.login();
                        break;
                case 3: oaes.forgotPasword();
                        break;
                case 4: break;
                default: System.out.println("Enter a valid choice..!!\n");
            }
        }
    }

    void register() throws Exception{
        String query = "INSERT INTO studentInfo values (?, ?, ?, ?)";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Scanner sc = new Scanner(System.in);
        Connection connection = DriverManager.getConnection(this.dbURL, this.dbUname, this.dbPass);


        String email, mobile, password, aadhar;
        System.out.print("email ID :");
        email = sc.next();

        System.out.print("mobile :");
        mobile = sc.next();

        System.out.print("password :");
        password = sc.next();

        System.out.print("aadhar :");
        aadhar = sc.next();

        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, email);
        stmt.setString(2, mobile);
        stmt.setString(3, password);
        stmt.setString(4, aadhar);
        stmt.executeUpdate();

        System.out.println("User successfully registered\n");

        stmt.close();
        connection.close();
    }

    void login() throws Exception {
        String query = "SELECT password from studentInfo where email=?";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Scanner sc = new Scanner(System.in);
        Connection connection = DriverManager.getConnection(this.dbURL, this.dbUname, this.dbPass);

        String email, password;

        System.out.print("email ID :"); // handle email not found Exception
        email = sc.next();
        System.out.print("password :");
        password = sc.next();

        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, email);

        ResultSet rs = stmt.executeQuery();
        rs.next();

        String fetchedPW = rs.getString(1);

        if(password.equals(fetchedPW)) {
            System.out.println("Logged In, Successfully!!\n");
        }
        else {
            System.out.println("Invalid Info.\nPlease try again!!\n");
        }

        stmt.close();
        connection.close();
    }

    void forgotPasword() throws Exception{
        String query = "UPDATE studentInfo SET password=? WHERE email=?";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Scanner sc = new Scanner(System.in);
        Connection connection = DriverManager.getConnection(this.dbURL, this.dbUname, this.dbPass);

        String email, password, otp;

        System.out.print("email ID :"); // handle email not found Exception
        email = sc.next();
        System.out.print("OTP :");
        otp = sc.next();

        if(!otp.equals("123456")) {
            System.out.println("Invalid OTP." + "\nPlease try again!!");
        }
        else {
            System.out.println("New Password :");
            password = sc.next();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, password);
            stmt.setString(2, email);

            stmt.executeUpdate();
            System.out.println("Password updated successfully");
        }
    }
}
