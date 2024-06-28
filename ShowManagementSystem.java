package com.democar;

import java.sql.*;
import java.util.Scanner;

public class ShowManagementSystem {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		Connection connection = null;
		try
		{
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/car_show_room", "root", "root");
			int operation;
			do 
			{
				System.out.println("CAR SHOW ROOM MANAGEMENT SYSTEM");
				System.out.println("1. Add Cars");
				System.out.println("2. Add Customers");
				System.out.println("3. Add Sales");
				System.out.println("4. View Cars");
				System.out.println("5. View Customers");
				System.out.println("6. View Sales");
				System.out.println("7. Exit");
				System.out.println("Enter Operation number you want to perform:- ");
				operation=sc.nextInt();
				sc.nextLine();
				
				switch (operation) 
				{
				case 1: 
					addCars(connection, sc);
					break;
				case 2:
					addCustomers(connection, sc);
					break;
				case 3:
					addSales(connection, sc);
					break;
				case 4:
					viewCars(connection);
					break;
				case 5:
					viewCustomers(connection);
					break;
				case 6:
					viewSales(connection);
					break;
				case 7:
					System.out.println("Exiting Program.");
					break;
				default:
					System.out.println("Invalid operation. Please enter a valid operation number.");
                    break;
				}
			} 
			while (operation !=7);
		}
		catch (ClassNotFoundException e) 
        {
            System.out.println("MySQL JDBC driver not found.");
            e.printStackTrace();
        } 
        catch (SQLException e) 
        {
            System.out.println("Database error:");
            e.printStackTrace();
        }
		finally 
        {
            try 
            {
                if (connection != null) 
                {
                    connection.close();
                }
                sc.close();
            } 
            catch (SQLException e) 
            {
                System.out.println("Error closing connection:");
                e.printStackTrace();
            }
        }	
	}
	private static void addCars(Connection connection, Scanner sc) throws SQLException
	{
		System.out.println("Enter Car Model: ");
		String model=sc.nextLine();
		System.out.println("Enter Car Make: ");
		String make=sc.nextLine();
		System.out.println("Enter Car Price: ");
		int price=sc.nextInt();
		sc.nextLine();
		System.out.println("Enter Car Year: ");
		int year=sc.nextInt();
		sc.nextLine();
		
		String insertSql = "INSERT INTO cars (model, make, price, year) VALUES (?, ?, ?, ?)";
        PreparedStatement insertStatement = connection.prepareStatement(insertSql);
        insertStatement.setString(1, model);
        insertStatement.setString(2, make);
        insertStatement.setInt(3, price);
        insertStatement.setInt(4, year);
        int rowsAffected = insertStatement.executeUpdate();
        if (rowsAffected > 0) 
        {
            System.out.println("CAR ADDED SUCCESSFULLY !!");
        } 
        else 
        {
            System.out.println("FAILED TO ADD CAR.");
        }
	}
	private static void addCustomers(Connection connection, Scanner sc) throws SQLException
	{
		System.out.println("Enter Customer Name: ");
		String name=sc.nextLine();
		System.out.println("Enter Customer Email: ");
		String email=sc.nextLine();
		System.out.println("Enter Customer Phone: ");
		String phone=sc.nextLine();
		
		String insertSql = "INSERT INTO customers (name, email, phone) VALUES (?, ?, ?)";
        PreparedStatement insertStatement = connection.prepareStatement(insertSql);
        insertStatement.setString(1, name);
        insertStatement.setString(2, email);
        insertStatement.setString(3, phone);
        int rowsAffected = insertStatement.executeUpdate();
        if (rowsAffected > 0) 
        {
            System.out.println("CUSTOMER ADDED SUCCESSFULLY !!");
        } 
        else 
        {
            System.out.println("FAILED TO ADD CUSTOMER.");
        }
	}
	private static void addSales(Connection connection, Scanner sc) throws SQLException
	{
		viewCars(connection); 
        System.out.print("Enter Car ID: ");
        int car_id = sc.nextInt();
        sc.nextLine();
        viewCustomers(connection);
        System.out.print("Enter Customer ID: ");
        int cus_id = sc.nextInt();
        sc.nextLine(); 
        System.out.print("Enter Sale Date (YYYY-MM-DD): ");
        String sale_date = sc.nextLine();

        String insertSql = "INSERT INTO sales (car_id, cus_id, sale_date) VALUES (?, ?, ?)";
        PreparedStatement insertStatement = connection.prepareStatement(insertSql);
        insertStatement.setInt(1, car_id);
        insertStatement.setInt(2, cus_id);
        insertStatement.setString(3, sale_date);
        int rowsAffected = insertStatement.executeUpdate();
        if (rowsAffected > 0) 
        {
            System.out.println("SALE ADDED SUCCESSFULLY !!");
        } 
        else 
        {
            System.out.println("FAILED TO ADD SALE.");
        }
	}
	private static void viewCars(Connection connection) throws SQLException 
    {
        String retrieveSql = "SELECT * FROM cars";
        try (Statement stmt = connection.createStatement(); ResultSet resultSet = stmt.executeQuery(retrieveSql)) 
        {
            while (resultSet.next()) 
            {
                int id = resultSet.getInt("car_id");
                String model = resultSet.getString("model");
                String make = resultSet.getString("make");
                int price = resultSet.getInt("price");
                int year = resultSet.getInt("year");
                System.out.println("ID: " + id + ", Model: " + model + ", Make: " + make + ",Price: " + year);
            }
        }
    }
	private static void viewCustomers(Connection connection) throws SQLException 
    {
        String retrieveSql = "SELECT * FROM customers";
        try (Statement stmt = connection.createStatement(); ResultSet resultSet = stmt.executeQuery(retrieveSql)) 
        {
            while (resultSet.next()) 
            {
                int id = resultSet.getInt("cus_id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String phone = resultSet.getString("phone");
                System.out.println("ID: " + id + ", Name: " + name + ", Email: " + email + ", Phone: " + phone);
            }
        }
    }
	 private static void viewSales(Connection connection) throws SQLException 
	    {
	        String retrieveSql = "SELECT s.sale_id, cm.model AS car_model, cu.name AS customer_name, s.sale_date " +
                                 "FROM sales s " +
                                 "INNER JOIN cars cm ON s.car_id = cm.car_id " +
                                 "INNER JOIN customers cu ON s.cus_id = cu.cus_id";
	        try (Statement stmt = connection.createStatement(); ResultSet resultSet = stmt.executeQuery(retrieveSql)) 
	        {
	            while (resultSet.next()) 
	            {
	                int id = resultSet.getInt("sale_id");
	                String model = resultSet.getString("car_model");
	                String name = resultSet.getString("customer_name");
	                String sale_date = resultSet.getString("sale_date");
	                System.out.println("ID: " + id + ", Model: " + model + ", Name: " + name + ", Date: " + sale_date);
	            }
	        }
	    }
}
