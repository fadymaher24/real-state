import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Scanner;

public class test {

        public static void main(String[] args) {
            //basic interface of program
            Scanner input = new Scanner(System.in);
            System.out.println("WHAT would you like to do?");
            System.out.println("enter number to specify what you need from 1 to 5\n");
            System.out.println("1.Find apartments with a specific price");
            System.out.println("2.available apartments");
            System.out.println("3.numbers of installments haven't been paid for yet");
            System.out.println("4.Apartments that were recently sold");
            System.out.println("5.Apartments that each apartment has at least one installment smaller than 60,000 and the count of its installments is greater than 5");
            //put him choice and if ture continue else print that it was wrong
            int k = input.nextInt();
            if (k > 5 || k < 1) {
                System.out.println("WRONG INPUT!! try again..");
                //if the entry  from 1 to 5 fo to else statement
            } else {
                // try and exception to handle any error happen
                try {
                    //to connect the database
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/real_state", "root", "");
                    Statement stmt = con.createStatement();
                        switch (k) {
                            case (1): {
                                System.out.println("Available prices(600000,650000,700000,750000,850000,900000,950000,1200000,1700000,2M)");
                                System.out.print("maximum price = ");
                                int f = input.nextInt();
                                if (f < 600000) {
                                    System.out.println("Sorry , no available apartments in this price range");
                                } else {
						/* check if program enter else statement:
						System.out.println("hello"); */
                                    ResultSet A = stmt.executeQuery("SELECT * from apartment WHERE price<="+f+" order by apr_id asc" );
                                    System.out.println("Available apartments....(Apartment price :: apartment ID :: building number :: floor :: area :: rooms_num :: dimension of rooms :: description) ");
                                    // to show what do I want and do it in loop to get all values im all columns
                                    while (A.next()) {
                                        System.out.print("\t"+"\t"+"\t"+"\t"+"\t"+"\t"+"\t"+A.getInt("price") + " EGP"+"\t" + "\t");
                                        System.out.print("\t"+A.getInt("apr_id") + "\t" + "\t");
                                        System.out.print("\t"+A.getInt("building_number") + "\t" + "\t");
                                        System.out.print("\t"+"\t"+A.getInt("floor") + "\t");
                                        System.out.print("\t"+A.getInt("area") + "\t" + "\t");
                                        System.out.print("\t"+A.getInt("rooms_num") + "\t" + "\t");
                                        System.out.print("\t"+"\t"+A.getString("dim_of_rooms") + "\t" + "\t");
                                        System.out.println("\t"+"\t"+A.getString("description"));

                                                    }
                                }
                                break;
                            }

                            case (2): {
                                System.out.println("Apartments that are currently available :");
                                ResultSet A = stmt.executeQuery("SELECT * from apartment WHERE sold=0");
                                //get available apartments
                                System.out.println("Available apartments....(Apartment price :: apartment ID :: building number :: floor :: area :: rooms_num :: dimension of rooms :: description) ");
                                // to show what do I want and do it in loop to get all values im all columns
                                while (A.next()) {
                                    System.out.print("\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + A.getInt("price") + " EGP" + "\t" + "\t");
                                    System.out.print("\t" + A.getInt("apr_id") + "\t" + "\t");
                                    System.out.print("\t" + A.getInt("building_number") + "\t" + "\t");
                                    System.out.print("\t" + "\t" + A.getInt("floor") + "\t");
                                    System.out.print("\t" + A.getInt("area") + "\t" + "\t");
                                    System.out.print("\t" + A.getInt("rooms_num") + "\t" + "\t");
                                    System.out.print("\t" + "\t" + A.getString("dim_of_rooms") + "\t" + "\t");
                                    System.out.println("\t" + "\t" + A.getString("description"));

                                }
                            }
                            break;
                            case (3): {
                                System.out.print("Enter your ssn : ");
                                String l = input.next();
                                Statement statement = con.createStatement();
                                ResultSet A = statement.executeQuery("SELECT * FROM payment WHERE ssn=" + l);
                                while (A.next()) {
                                    System.out.println("Remaining Price is:"+A.getInt("remaining_price") + " EGP");
                                    System.out.println(A.getInt("inst_count") + " installments left");

                                }
                            }
                            break;
                            case (4): {
                                ResultSet A = stmt.executeQuery("SELECT apr_id FROM payment WHERE p_date LIKE '2021%12%'");
                                while (A.next()) {
                                    System.out.println("apartment number: " + A.getInt("apr_id") + " was/were recently sold.");
                                    ResultSet B = stmt.executeQuery("SELECT * FROM apartment WHERE apr_id="+ A.getInt("apr_id") );
                                     //....apartment description....
                                    System.out.println("Available apartments....(Apartment price :: apartment ID :: building number :: floor :: area :: rooms_num :: dimension of rooms :: description) ");
                                      while (B.next()) {
                                        System.out.print("\t" + "\t" + "\t" + "\t" + "\t" + "\t" + "\t" + B.getInt("price") + " EGP" + "\t" + "\t");
                                        System.out.print("\t" + B.getInt("apr_id") + "\t" + "\t");
                                        System.out.print("\t" + B.getInt("building_number") + "\t" + "\t");
                                        System.out.print("\t" + "\t" + B.getInt("floor") + "\t");
                                        System.out.print("\t" + B.getInt("area") + "\t" + "\t");
                                        System.out.print("\t" + B.getInt("rooms_num") + "\t" + "\t");
                                        System.out.print("\t" + "\t" + B.getString("dim_of_rooms") + "\t" + "\t");
                                        System.out.println("\t" + "\t" + B.getString("description"));
                                                           }
                                }

                            }
                            break;
                            case (5): {
                                System.out.println("list of apartments with at least one installment>60K and number of installments>5");
                                ResultSet A = stmt.executeQuery("SELECT apr_id FROM payment WHERE remaining_price>60000 AND inst_count>5");
                                while (A.next()) {
                                    System.out.println(A.getInt("apr_id"));

                                                 }
                            }
                            break;

                        }
                        con.close();
                        }
              catch (Exception e) {
                    System.out.println(e);
                }
            }

        }
    }

//wanna in case 2 make it automatic without being 0 or 1
//want to enter greater than one value