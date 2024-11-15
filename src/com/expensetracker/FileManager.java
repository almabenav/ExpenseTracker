package com.expensetracker;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class FileManager {

    // Method for saving expenses in a file

    public static void saveExpensesToFile(String fileName, ArrayList<Expense> expenses) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Expense expense : expenses) {
                // Store the category, quantity (with point as decimal separator) and date
                writer.write(expense.getCategory() + "," + expense.getAmount() + "," + expense.getDate().getTime());
                writer.newLine();
            }
            System.out.println("\nExpenses saved to file successfully.\n");
        } catch (IOException e) {
            System.out.println("Error saving expenses: " + e.getMessage());
        }
    }

    // Method for uploading expenses from a file

    public static ArrayList<Expense> loadExpensesFromFile(String fileName) {
        ArrayList<Expense> expenses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String category = parts[0];

                // Convert the quantity to double format, replacing the comma with a period.

                String amountString = parts[1].replace(',', '.');
                double amount = Double.parseDouble(amountString);

                long dateMillis = Long.parseLong(parts[2]);
                Date date = new Date(dateMillis);

                Expense expense = new Expense(category, amount, date);
                expenses.add(expense);
            }
            System.out.println("\nExpenses loaded from file successfully.\n");
        } catch (IOException e) {
            System.out.println("Error loading expenses: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error parsing number from file: " + e.getMessage());
        }
        return expenses;
    }
}