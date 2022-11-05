import java.io.*;
import java.nio.Buffer;
import java.util.*;

public class BankDetails {
    private Scanner scanner = new Scanner(System.in);
    private static Map<User, Account> mapOfCustomers = new HashMap<>();
    private static Map<User, Long> UserMoney = new HashMap<>();
    private File ourUsersFile = new File("src/BankUsers.data");
    private File accountsFile = new File("src/BankAccounts.data");
    private File userInfo = new File("src/UserInfo.data");


    public void create() {
        fill();
        System.out.print("Enter your name: ");
        String name = scanner.next();
        System.out.print("\nEnter your surname: ");
        String surname = scanner.next();
        System.out.print("\nEnter your passport number: ");
        String passportNO = scanner.next();
        while (!checkPassportUnique(passportNO)) {
            System.out.print("\nThe passport number is incorrect. Write again!!");
            passportNO = scanner.next();
        }
        User user = new User(name, surname, passportNO);

        System.out.print("\nEnter your id: ");
        long id = scanner.nextLong();

        while (!checkID(id) || !checkIDUnique(id)) {
            System.out.println("The id is incorrect. Write again!! ");
            id = scanner.nextLong();
        }

        System.out.print("\nEnter your pin: ");
        int pin = scanner.nextInt();
        while (!checkPin(pin)) {
            System.out.println("The digits count is incorrect!!. Please write your pin again: ");
            pin = scanner.nextInt();
        }

        Account account = new Account(id, pin);
        mapOfCustomers.put(user, account);
        UserMoney.put(user, account.getTotal());
    }

    public void logIN() {
        fill();
        System.out.println("Enter your id for entering your account: ");
        long id = scanner.nextLong();
        int countForPin = 0;
        int countForId = 0;
        while (findOwner(id) == null && countForId < 5) {
            System.out.println("Enter your id again!!: ");
            id = scanner.nextLong();
            countForId++;
        }
        if (countForId == 5) {
            System.out.println("You've run out of opportunities to try!!!");
            return;
        }
        User user = findOwner(id);
        Account account = mapOfCustomers.get(user);

        System.out.println("Please enter the password: ");
        int password = scanner.nextInt();

        while (account.getPin() != password && countForPin < 5) {
            System.out.println("The pin is wrong!!. Enter again: ");
            password = scanner.nextInt();
            countForPin++;
        }
        if (countForPin == 5) {
            System.out.println("You've run out of opportunities to try!!!");
            return;
        }
        bankingProcess(user, account);

    }

    private void bankingProcess(User user, Account account) {
        int choose;
        do {
            System.out.println("\n ***Banking System Application***");
            System.out.println("1. Display all account details \n2.Deposit the amount \n3.Withdraw the amount \n4.Delete account \n5.Exit.");
            System.out.println("Enter your choice: ");
            choose = scanner.nextInt();
            switch (choose) {
                case 1:
                    System.out.println(user);
                    System.out.println(account);
                    break;
                case 2:
                    System.out.println("Enter the amount of money that you want to deposit: ");
                    long depositAmount = scanner.nextLong();
                    account.deposit(depositAmount);
                    UserMoney.replace(user, account.getTotal());
                    break;
                case 3:
                    System.out.println("Enter the amount of money that you want to withdraw: ");
                    long withdrawAmount = scanner.nextLong();
                    account.withdraw(withdrawAmount);
                    UserMoney.replace(user, account.getTotal());
                    break;
                case 4:
                    if (findAccount(user) == null) {
                        return;
                    }
                    deleteUser(user);
                case 5:
                    writeInFile();
                    break;
            }
        }
        while (choose < 4);
    }

    private void writeInFile() {
        try {
            BufferedWriter bufferedWriter1 = new BufferedWriter(new FileWriter(ourUsersFile));
            BufferedWriter bufferedWriter2 = new BufferedWriter(new FileWriter(accountsFile));
            BufferedWriter bufferedWriter3 = new BufferedWriter(new FileWriter(userInfo));
            bufferedWriter1.write("Name---" + "Surname---" + "PassportNumber");
            bufferedWriter1.newLine();
            for (User users : mapOfCustomers.keySet()) {
                bufferedWriter1.write(users.getName() + "    " + users.getSurname() + "    " + users.getPassportNO());
                bufferedWriter1.newLine();
            }
            bufferedWriter2.write("Id---" + "Pin---" + "Balance");
            bufferedWriter2.newLine();
            for (Account accounts : mapOfCustomers.values()) {
                bufferedWriter2.write(accounts.getId() + "    " + accounts.getPin() + "    " + accounts.getTotal());
                bufferedWriter2.newLine();
            }
            bufferedWriter3.write("Name---" + "Surname---" + "PassportNumber---" + "Balance");
            bufferedWriter3.newLine();
            for (User users : mapOfCustomers.keySet()) {
                bufferedWriter3.write(users.getName() + "    " + users.getSurname() + "    " + users.getPassportNO() + "    " + mapOfCustomers.get(users).getTotal());
                bufferedWriter3.newLine();
            }
            bufferedWriter1.close();
            bufferedWriter2.close();
            bufferedWriter3.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteUser(User user) {
        if (findAccount(user) == null) {
            return;
        }
        mapOfCustomers.remove(user);
        UserMoney.remove(user);
    }

    private boolean checkPassportUnique(String passportNO) {
        for (User user1 : mapOfCustomers.keySet()) {
            if (user1.getPassportNO().equals(passportNO)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkID(long id) {
        String num = Long.toString(id);
        return num.length() == 8;
    }

    private boolean checkIDUnique(long id) {
        for (Account account1 : mapOfCustomers.values()) {
            if (account1.getId() == id) {
                return false;
            }
        }
        return true;
    }

    private boolean checkPin(int pin) {
        String num = Integer.toString(pin);
        return num.length() == 4;
    }

    private Account findAccount(User user) {
        for (User user1 : mapOfCustomers.keySet()) {
            if (user1.equals(user)) {
                return mapOfCustomers.get(user1);
            }
        }
        System.out.println("We do not have such a customer...");
        return null;
    }

    private User findOwner(long id) {
        for (User user1 : mapOfCustomers.keySet()) {
            if (mapOfCustomers.get(user1).getId() == id) {
                return user1;
            }
        }
        System.out.println("We do not have such a customer...");
        return null;
    }

    private void fill() {
        try {
            Scanner scanner1 = new Scanner(ourUsersFile);
            Scanner scanner2 = new Scanner(accountsFile);
            if (scanner1.hasNextLine() && scanner2.hasNextLine()) {
                scanner1.nextLine();
                scanner2.nextLine();
            }
            while (scanner1.hasNextLine() && scanner2.hasNextLine()) {
                String[] array = scanner1.nextLine().split("    ");
                User user = new User(array[0], array[1], array[2]);

                String[] array2 = scanner2.nextLine().split("    ");
                Account account = new Account(Long.parseLong(array2[0]), Integer.parseInt(array2[1]));
                account.setTotal(Long.parseLong(array2[2]));
                mapOfCustomers.put(user, account);
            }

            scanner1.close();
            scanner2.close();


        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
