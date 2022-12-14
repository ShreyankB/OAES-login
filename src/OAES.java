import java.sql.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class OAES {

    public static void main(String[] args) throws  Exception {
        int option = 0;
        Scanner sc = new Scanner(System.in);

        while(option != 4) {
            System.out.println("-------------------------------------------------------------");
            System.out.println("Welcome to OAES");
            System.out.println("-------------------------------------------------------------");
            System.out.print("1. Register \n2. Login \n3. Forgot password \n4. exit \nchoice: ");
            option = sc.nextInt();

            switch (option) {
                case 1: Register register = new Register();
                        register.registerUser();
                        break;
                case 2: LogIn login = new LogIn();
                        login.login();
                        break;
                case 3: ForgotPassword fp = new ForgotPassword();
                        fp.updatePassword();
                        break;
                case 4: break;
                default: System.out.println("Enter a valid choice..!!\n");
            }
        }
    }


}

class Register {
    private String emailID;
    private String mobileNumber;
    private String password;
    private String aadharCard;
    private String IDProof;
    private String emailRegex;
    private String mobileRegex;
    private String aadharRegex;
    private String IDProofRegex;
    public final String dbURL = "jdbc:mysql://localhost:3306/OAES";
    public final String dbUname = "root";
    public final String dbPass = "password";
    Scanner sc = new Scanner(System.in);

    Register() {
        emailRegex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
        mobileRegex = "[0-9]{10}";
        aadharRegex = "";
        IDProofRegex = "";
    }
    Register(String emailRegex, String mobileRegex, String aadharRegex, String IDProofRegex) {
        this.emailRegex = emailRegex;
        this.mobileRegex = mobileRegex;
        this.IDProofRegex = IDProofRegex;
        this.aadharRegex = aadharRegex;
    }
    private boolean getEmailID() {
        System.out.print("EmailID:\t");
        emailID = sc.next();
        return verifyEmail(emailID);
    }
    private boolean getMobileNumber() {
        System.out.print("Mobile No.:\t");
        mobileNumber = sc.next();
        return verifyMobile(mobileNumber);
    }
    private void getPassword() {
        System.out.print("Password:\t");
        password = sc.next();
    }
    private boolean getAadharCard() {
        System.out.print("Aadhar Card:\t");
        aadharCard = sc.next();
        return verifyAadhar(aadharCard);
    }
    private boolean getIDProof() {
        System.out.print("ID Proof:\t");
        IDProof = sc.next();
        return verifyID(IDProof);
    }
    private boolean verifyEmail(String emailID) {
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(emailID);
        return matcher.matches();
    }
    private boolean verifyMobile(String mobileNumber) {
        Pattern pattern = Pattern.compile(mobileRegex);
        Matcher matcher = pattern.matcher(mobileNumber);
        return matcher.matches();
    }
    private boolean verifyAadhar(String aadharCard) {
        return true;
    }
    private boolean verifyID(String IDProof) {
        return true;
    }
    public void askUserInfo() throws Exception {
        while(!getEmailID()) System.out.println("Invalid Email ID.\nPlease, Re enter it.");
        while(!getMobileNumber()) System.out.println("\"Invalid Mobile no.\nPlease, Re enter it.");
        getPassword();
        while(!getAadharCard()) System.out.println("Invalid Aadhar card no.\nPlease, Re enter it.");
    }

    public void registerUser() throws Exception {
        askUserInfo();

        String query = "INSERT INTO studentInfo values (?, ?, ?, ?)";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Scanner sc = new Scanner(System.in);
        Connection connection = DriverManager.getConnection(this.dbURL, this.dbUname, this.dbPass);

        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, this.emailID);
        stmt.setString(2, this.mobileNumber);
        stmt.setString(3, this.password);
        stmt.setString(4, this.aadharCard);
        stmt.executeUpdate();

        System.out.println("User successfully registered\n");

        stmt.close();
        connection.close();
    }
}

class LogIn {
    private String emailID;
    private String password;
    private String mobileNumber;
    private String otp;
    boolean emailLogin;
    private String emailRegex;
    private String mobileRegex;
    public final String dbURL = "jdbc:mysql://localhost:3306/OAES";
    public final String dbUname = "root";
    public final String dbPass = "password";
    Scanner sc = new Scanner(System.in);
    LogIn() {
        emailLogin = true;
        emailRegex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
        mobileRegex = "[0-9]{10}";
    }
    private boolean getEmailID() {
        System.out.print("EmailID:\t");
        emailID = sc.next();
        return verifyEmail(emailID);
    }
    private boolean getMobileNumber() {
        System.out.print("Mobile No.:\t");
        mobileNumber = sc.next();
        return verifyMobile(mobileNumber);
    }
    private void getPassword() {
        System.out.print("Password:\t");
        password = sc.next();
    }
    private void getOTP() {
        System.out.print("OTP:\t");
        otp = sc.next();
    }
    private boolean verifyEmail(String emailID) {
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(emailID);
        return matcher.matches();
    }
    private boolean verifyMobile(String mobileNumber) {
        Pattern pattern = Pattern.compile(mobileRegex);
        Matcher matcher = pattern.matcher(mobileNumber);
        return matcher.matches();
    }    private boolean verifyPassword() throws Exception {
        String query = "SELECT password from studentInfo where email=?";
        Class.forName("com.mysql.cj.jdbc.Driver");
        Scanner sc = new Scanner(System.in);
        Connection connection = DriverManager.getConnection(this.dbURL, this.dbUname, this.dbPass);

        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, this.emailID);

        ResultSet rs = stmt.executeQuery();
        rs.next();

        String fetchedPW = rs.getString(1);

        stmt.close();
        connection.close();

        return password.equals(fetchedPW);
    }

    private boolean verifyOTP() {
        return otp.equals("123456");
    }
    private void askUserInfo() {
        System.out.print("LogIn Options:\n");
        System.out.print("1. Email + Password\n2. Mobile No. + OTP\nchoice:");
        int choice = sc.nextInt();

        if(choice == 2)
            emailLogin = false;

        if(emailLogin) {
            while(!getEmailID()) System.out.println("Invalid Email ID.\nPlease, Re enter it.");
            getPassword();
        }
        else {
            while(!getMobileNumber()) System.out.println("\"Invalid Mobile no.\nPlease, Re enter it.");
            getPassword();
        }
    }
    public void login() throws Exception{
        askUserInfo();

        if(emailLogin) {
            boolean valid = true;
            while(!verifyPassword()) {
                System.out.print("*****\t Invalid password..!!\n");
                System.out.print("*****\t You can reset the password from main menu.\n\n");
                System.out.print("1. Re enter password\n2. Main Menu\nchoice:\t");
                int choice = sc.nextInt();

                if(choice == 2) {
                    valid = false;
                    break;
                }
                else {
                    getPassword();
                }
            }

            if(valid)
                System.out.print("*****\t Logged In, Successfully..!!\n\n");
            else
                System.out.print("*****\t Redirecting to main menu\n");
        }
        else {
            boolean valid = true;

            while(!verifyOTP()) {
                System.out.print("*****\t Invalid OTP..!!\n");
                System.out.print("1. Re enter OTP\n2. Main Menu\nchoice:\t");
                int choice = sc.nextInt();

                if(choice == 2) {
                    valid = false;
                    break;
                }
                else {
                    getOTP();
                }
            }

            if(valid)
                System.out.print("*****\t Logged In, Successfully..!!\n\n");
            else
                System.out.print("*****\t Redirecting to main menu\n");
        }
    }
}

class ForgotPassword {
    private String emailID;
    private String password;
    private String mobileNumber;
    private String otp;
    boolean emailLogin;
    private String emailRegex;
    private String mobileRegex;
    public final String dbURL = "jdbc:mysql://localhost:3306/OAES";
    public final String dbUname = "root";
    public final String dbPass = "password";
    Scanner sc = new Scanner(System.in);
    ForgotPassword() {
        emailLogin = true;
        emailRegex = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
        mobileRegex = "[0-9]{10}";
    }
    private boolean getEmailID() {
        System.out.print("EmailID:\t");
        emailID = sc.next();
        return verifyEmail(emailID);
    }
    private boolean getMobileNumber() {
        System.out.print("Mobile No.:\t");
        mobileNumber = sc.next();
        return verifyMobile(mobileNumber);
    }
    private void getPassword() {
        System.out.print("New Password:\t");
        password = sc.next();
    }
    private void getOTP() {
        System.out.print("OTP:\t");
        otp = sc.next();
    }
    private boolean verifyEmail(String emailID) {
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(emailID);
        return matcher.matches();
    }
    private boolean verifyMobile(String mobileNumber) {
        Pattern pattern = Pattern.compile(mobileRegex);
        Matcher matcher = pattern.matcher(mobileNumber);
        return matcher.matches();
    }
    private boolean verifyOTP() {
        return true;
    }
    public void updatePassword() throws Exception {
        System.out.print("Update Password Options:\n");
        System.out.print("1. Mobile No. + OTP\n2. Email + Reset\nchoice:");
        int choice = sc.nextInt();

        if(choice == 1)
            emailLogin = false;

        if(emailLogin) {
            while(!getEmailID()) System.out.println("Invalid Email ID.\nPlease, Re enter it.");
            System.out.print("**** password reset link has been sent to your email ID.\n\n");
        }
        else {
            while(!getMobileNumber()) System.out.println("\"Invalid Mobile no.\nPlease, Re enter it.");
            getOTP();
            boolean valid = true;

            while(!verifyOTP()) {
                System.out.print("*****\t Invalid OTP..!!\n");
                System.out.print("1. Re enter OTP\n2. Main Menu\nchoice:\t");
                choice = sc.nextInt();

                if(choice == 2) {
                    valid = false;
                    break;
                }
                else {
                    getOTP();
                }
            }

            if(valid) {
                getPassword();
                String query = "UPDATE studentInfo SET password=? WHERE mobile=?";
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection connection = DriverManager.getConnection(this.dbURL, this.dbUname, this.dbPass);

                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setString(1, this.password);
                stmt.setString(2, this.mobileNumber);

                stmt.executeUpdate();
                System.out.println("Password updated successfully");
            }
        }
    }
}