package com.expensetracker;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class ExpenseTracker {
    private ArrayList<Expense> expenses;
    private Scanner scanner;

    public ExpenseTracker() {
        expenses = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    // Method to add an expense
    public void addExpense() {
        System.out.println("Enter the expense category: ");
        String category = scanner.nextLine();
        double amount = -1;

        // Validation of the amount entered
        while (amount <= 0) {
            System.out.println("Enter the amount (decimals with comma): ");
            if (scanner.hasNextDouble()) {
                amount = scanner.nextDouble();
                if (amount <= 0) {
                    System.out.println("Invalid amount. Please enter a valid positive number.");
                }
            } else {
                System.out.println("Invalid amount. Please enter a valid number.");
                scanner.next(); // Consuming invalid input
            }
        }

        scanner.nextLine(); // Consumes the line break
        Date date = new Date(); // Current date
        Expense expense = new Expense(category, amount, date);
        expenses.add(expense);
        System.out.println("\n-------Expense added successfully!-------\n");
    }


    // Method of showing expenses
    public void displayExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("\nNo expenses recorded.\n");
        } else {
            System.out.println("\n=========== Recorded Expenses ===========\n");
            for (Expense expense : expenses) {
                System.out.printf("Category: %s | Amount: %.2f | Date: %s\n",
                        expense.getCategory(), expense.getAmount(), expense.getDate().toString());
            }
            System.out.println("\n=========================================\n");
        }
    }

    // Method for calculating and displaying total expense
    public void showTotalExpenses() {
        double total = 0;
        for (Expense expense : expenses) {
            total += expense.getAmount();
        }
        System.out.println("\nTotal expenses: " + String.format("%.2f", total));
    }


    //Main menu method

    public void menu() {
        int choice;
        do {
            System.out.println("\n======= Welcome to Expense Tracker =======");
            System.out.println();
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Save Expenses to File");
            System.out.println("4. Load Expenses from File");
            System.out.println("5. Show Total Expenses");
            System.out.println("6. Exit");
            System.out.println("\n==========================================");
            System.out.print("Enter your choice: ");
            System.out.println();
            choice = scanner.nextInt();
            scanner.nextLine(); // Consuming the line break after nextInt()

            switch (choice) {
                case 1:
                    addExpense();
                    break;
                case 2:
                    displayExpenses();
                    break;
                case 3:
                    System.out.println("Enter the file name to save to (example.txt): ");
                    String saveFileName = scanner.nextLine();
                    FileManager.saveExpensesToFile(saveFileName, expenses);
                    break;
                case 4:
                    System.out.println("Enter the file name to load from: ");
                    String loadFileName = scanner.nextLine();
                    expenses = FileManager.loadExpensesFromFile(loadFileName);
                    break;
                case 5: 
                    showTotalExpenses();
                    break;
                case 6:
                    System.out.println("Exiting... Thank you for using Expense Tracker.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    public static void main(String[] args) {
        ExpenseTracker tracker = new ExpenseTracker();
        tracker.menu();
    }
}