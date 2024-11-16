
import java.util.Scanner;
import config.AppConfiguration;
import domain.Cake;
import ui.ClientUI;
import ui.EmployeeUI;

public class Main {
    public static void main(String[] args) {
        AppConfiguration config = new AppConfiguration();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("Type 'client' if you are a client, 'employee' otherwise");
            String userType = sc.nextLine();

            if (userType.equalsIgnoreCase("client")) {
                ClientUI.handleClientOptions(config, sc);
            } else if (userType.equalsIgnoreCase("employee")){
                EmployeeUI.handleEmployeeOptions(config, sc);
            }

            System.out.println("Do you want to enter another option? yes/no");
            String option = sc.nextLine();
            if (option.equalsIgnoreCase("no")) {
                break;
            }
        }
    }
}
