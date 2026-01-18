
//Coral Morales Cortes, HW2
package org.example;
import java.util.ArrayList;
import java.util.Scanner;

class BankAccount {
    private int _accountNumber;
    private double _balance;

    public BankAccount(int accountNum, double balance) {
        _accountNumber = accountNum;
        _balance = balance;
    }

    public int getAccountNumber() {
        return _accountNumber;
    }

    public double getBalance() {
        return _balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            _balance += amount;
        }
    }

    public void withdraw(double amount) {
        if (amount <= _balance) {
            _balance -= amount;
        }
    }

    public String toString() {
        String s = " Your account number: " + _accountNumber + ", Balance: " + _balance;
        return s;
    }

    public boolean equals(Object obj) {
        return _accountNumber == ((BankAccount) obj)._accountNumber;
    }
}

class CheckingAccount extends BankAccount {
    private int _checkNumber;

    public CheckingAccount(int accountNum, double balance, int checkNum) {
        super(accountNum, balance);
        _checkNumber = checkNum;
    }

    public void deposit(double amount, int newCheckNum) {
        if (newCheckNum > _checkNumber) {
            _checkNumber = newCheckNum;
            super.deposit(amount);
        }
    }

    public String toString() {
        String s = super.toString() + ", Check number: " + _checkNumber;
        return s;
    }

    public boolean equals(Object obj) {
        return super.equals(obj) && _checkNumber
                == ((CheckingAccount) obj)._checkNumber;
    }
}

class InterestAccount extends BankAccount {
    private double _rate;

    public InterestAccount(int accountNum, double balance, double interestRate) {
        super(accountNum, balance);
        _rate = interestRate;
    }

    public void applyInterest() {
        double Interest = getBalance() * _rate;
        deposit(Interest);
    }

    public String toString() {
        String s = super.toString() + ", Rate: " + _rate + "%";
        return s;
    }

    public boolean equals(Object obj) {
        return super.equals(obj) && _rate
                == ((InterestAccount) obj)._rate;
    }
}

class FixedDepositAccount extends InterestAccount {
    private boolean _minTime;

    public FixedDepositAccount(int accountNum, double balance, double interestRate, boolean timeStatus) {
        super(accountNum, balance, interestRate);
        _minTime = timeStatus;
    }

    public void setMinTime(boolean newStatus) {
        if (!_minTime && newStatus) {
            _minTime = newStatus;
        }
    }

    public boolean isMinTime() {
        return _minTime;
    }

    public void applyInterest() {
        if (_minTime) {
            super.applyInterest();
        }
    }

    public String toString() {
        String s = super.toString() + ", Has minimum required been met:  " + _minTime;
        return s;
    }

    public boolean equals(Object obj) {
        return super.equals(obj) && _minTime
                == ((FixedDepositAccount) obj)._minTime;
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ArrayList<BankAccount> bankAccounts = new ArrayList<>();
        Menu(scan, bankAccounts);
    }

    public static void AccountCreation(Scanner scan, ArrayList<BankAccount> accounts) {
        System.out.println("What kind of account would you like to create:  " +
                "\n a. Regular account" +
                "\n b. Checking " +
                "\n c. Interest " +
                "\n d. Fixed Deposit");
        String accountType = scan.nextLine();
        switch (accountType) {
            case "a": {
                System.out.println("Enter an account number: ");
                int acctNum = scan.nextInt();
                scan.nextLine();
                System.out.println("Enter an initial balance: ");
                double bal = scan.nextDouble();
                scan.nextLine();
                BankAccount regularAccount = new BankAccount(acctNum, bal);
                accounts.add(regularAccount);
                System.out.println("Your regular account has been created successfully! ");
                break;
            }
            case "b": {
                System.out.println("Enter an account number: ");
                int acctNum = scan.nextInt();
                scan.nextLine();
                System.out.println("Enter an initial balance: ");
                double bal = scan.nextDouble();
                scan.nextLine();
                System.out.println("enter a check number: ");
                int checkNum = scan.nextInt();
                scan.nextLine();
                CheckingAccount acct = new CheckingAccount(acctNum, bal, checkNum);
                accounts.add(acct);
                System.out.println("Checking account has been created successfully! ");
                break;
            }
            case "c": {
                System.out.println("Enter an account number: ");
                int acctNum = scan.nextInt();
                scan.nextLine();
                System.out.println("Enter an initial balance: ");
                double bal = scan.nextDouble();
                scan.nextLine();
                System.out.println("Enter initial interest: ");
                double interest = scan.nextDouble();
                scan.nextLine();
                InterestAccount rateAccount = new InterestAccount(acctNum, bal, interest);
                accounts.add(rateAccount);
                System.out.println("Interest account has been created successfully! ");
                break;
            }
            case "d": {
                System.out.println("Enter an account number: ");
                int acctNum = scan.nextInt();
                scan.nextLine();
                System.out.println("Enter an initial balance: ");
                double bal = scan.nextDouble();
                scan.nextLine();
                System.out.println("Enter initial interest: ");
                double interest = scan.nextDouble();
                scan.nextLine();
                System.out.println(" Has the minimum time period been met? enter 'true' for yes, enter 'false' for not yet ");
                boolean timeStatus = scan.nextBoolean();
                scan.nextLine();
                FixedDepositAccount fixedAccount = new FixedDepositAccount(acctNum, bal, interest, timeStatus);
                accounts.add(fixedAccount);
                System.out.println("Fixed Deposit Account has been created successfully! ");
                break;
            }
            default: {
                System.out.println("Invalid choice, try again!");
                break;
            }
        }
    }

    public static void depositOrWithdraw(Scanner scan, ArrayList<BankAccount> accounts) {
        System.out.println("Enter the account number: ");
        int accountNum = scan.nextInt();
        scan.nextLine();
        BankAccount accountFound = null;
        for (BankAccount account : accounts) {
            if (account.getAccountNumber() == accountNum) {
                accountFound = account;
            }
        }
        if (accountFound == null) {
            System.out.println("Account not found");
            return;
        }
        System.out.println("Enter 1 to deposit, enter 2 to withdraw : ");
        int num = scan.nextInt();
        scan.nextLine();
        System.out.println("Enter the amount: ");
        double amount = scan.nextDouble();
        scan.nextLine();
        if (num == 1) {
            accountFound.deposit(amount);
        } else if (num == 2) {
            accountFound.withdraw(amount);
        } else {
            System.out.println("invalid choice");
        }
    }


    public static void applyingInterest(Scanner scan, ArrayList<BankAccount> accounts) {
        System.out.println("Enter account number to apply interest: ");
        int accountNum = scan.nextInt();
        scan.nextLine();

        BankAccount accountFound = null;
        for (BankAccount acct : accounts) {
            if (acct.getAccountNumber() == accountNum) {
                accountFound = acct;
            }
        }
        if (accountFound == null) {
            System.out.println("Account not found");
            return;
        }
        if (accountFound instanceof InterestAccount) {
            InterestAccount interestAcct = (InterestAccount) accountFound;
            interestAcct.applyInterest();
            System.out.println("Interest added successfully to Interest Account");

        }
        if (accountFound instanceof FixedDepositAccount) {
            FixedDepositAccount fixedAcct = (FixedDepositAccount) accountFound;
            fixedAcct.applyInterest();
            System.out.println("Interest added successfully to Fixed Interest Account");
        } else {
            System.out.println("This account does not include interest");
        }


    }

    public static void toggleTimeRequirement(Scanner scan, ArrayList<BankAccount> accounts) {
        System.out.println("Enter account number to update time requirement : ");
        int accountNum = scan.nextInt();
        scan.nextLine();
        BankAccount accountFound = null;
        for (BankAccount acct : accounts) {
            if (acct.getAccountNumber() == accountNum) {
                accountFound = acct;
            }
        }
        if (accountFound == null) {
            System.out.println("Account not found");
            return;
        }
        if (accountFound instanceof FixedDepositAccount) {
            FixedDepositAccount fixedAccount = (FixedDepositAccount) accountFound;
            boolean timeStatus = fixedAccount.isMinTime();
            fixedAccount.setMinTime(!timeStatus);
            System.out.println("Time requirement status has been toggled");
        } else {
            System.out.println("Account is not a Fixed Deposit Account ");
        }
    }

    public static void displayInformation(Scanner scan, ArrayList<BankAccount> accounts) {
        System.out.println("1. Display information for all accounts" +
                "\n2. Display information by a certain account");
        int choice = scan.nextInt();
        scan.nextLine();
        if (choice == 1) {
            for (BankAccount account : accounts) {
                System.out.println(account);
            }
        } else if (choice == 2) {
            System.out.println("What type of account would you like to display information for: " +
                    "\n 1. Regular account" +
                    "\n 2. Checking " +
                    "\n 3. Interest " +
                    "\n 4. Fixed Deposit");
            int type = scan.nextInt();
            scan.nextLine();
            for (BankAccount account : accounts) {
                switch (type) {
                    case 1: {
                        if (!(account instanceof CheckingAccount) &&
                                !(account instanceof InterestAccount) &&
                                !(account instanceof FixedDepositAccount)) {
                            System.out.println(account);
                        }
                        break;
                    }
                    case 2: {
                        if (account instanceof CheckingAccount) {
                            System.out.println(account);
                        }
                        break;
                    }
                    case 3: {
                        if (account instanceof InterestAccount) {
                            System.out.println(account);
                        }
                        break;
                    }
                    case 4: {
                        if (account instanceof FixedDepositAccount) {
                            System.out.println(account);
                        }
                        break;
                    }
                    default: {
                        System.out.println("Not a valid input");
                    }
                }
            }
        }
    }

    public static void Menu(Scanner scan, ArrayList<BankAccount> accounts) {
        boolean active = true;
        while (active) {
            System.out.println("Menu Options: " +
                    "\n a. Create a new account" +
                    "\n b. Make a deposit or withdraw from account" +
                    "\n c. Apply interest to an account " +
                    "\n d. Switch time requirement status on an account " +
                    "\n e. Display information for all accounts " +
                    "\n f. Exit the program");
            String choice = scan.nextLine();
            switch (choice) {
                case "a": {
                    AccountCreation(scan, accounts);
                    break;
                }
                case "b": {
                    depositOrWithdraw(scan, accounts);
                    break;
                }
                case "c": {
                    applyingInterest(scan, accounts);
                    break;
                }
                case "d": {
                    toggleTimeRequirement(scan, accounts);
                    break;
                }
                case "e": {
                    displayInformation(scan, accounts);
                    break;
                }
                case "f": {
                    System.out.println("Exiting...");
                    active = false;
                    break;
                }
                default: {
                    System.out.println("Invalid input");
                    break;
                }
            }
        }
    }
}