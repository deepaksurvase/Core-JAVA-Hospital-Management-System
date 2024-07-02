package hospitalManagSys;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class patient {
	private Connection connection;
	private Scanner scanner;
	public patient(Connection connection, Scanner scanner) {
		super();
		this.connection = connection;
		this.scanner = scanner;
	}
	
	
	public void addPatient() {
		System.out.print(" Enter Patient Name  : ");
		String name=scanner.next();
		System.out.print(" Enter Patient Age   :");
		int age=scanner.nextInt();
		System.out.print(" Enter Patient Gender:");
		String gender=scanner.next();
		
		try {
			String query="INSERT INTO patients(name ,age, gender)values(?,?,?)";
			PreparedStatement preparedstatement =connection.prepareStatement(query);
			preparedstatement.setString(1, name);
			preparedstatement.setInt(2, age);
			preparedstatement.setString(3, gender);
			
			int affectedRows =preparedstatement.executeUpdate();
			if(affectedRows>0) {
				System.out.println("  Patient Added Succesfully");
			}else {
					System.out.println("  Failed to add paitent");
				}
			}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public void viewPatient() {
		String query="Select * from patients";
		try {
			PreparedStatement preparedstatement = connection.prepareStatement(query);
			ResultSet resultset=preparedstatement.executeQuery();
			System.out.println("Patients");
			System.out.println("+------------+----------------------+-------+-------------------+ ");
			
			System.out.println("| Patient Id | Name                 | Age   | Gender            | ");
			
			System.out.println("+------------+----------------------+-------+-------------------+ ");
			
			while(resultset.next()) {
				int id=resultset.getInt("id");
				String name=resultset.getString("name");
				int age=resultset.getInt("age");
				String gender=resultset.getString("gender");
				System.out.printf("| %-11s| %-21s| %-6s| %-18s|\n",id,name,age,gender);
				System.out.println("+------------+----------------------+-------+-------------------+ ");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean getPaitentById(int id) {
		String query="Select * from Patients where id =?";
		try {
			PreparedStatement preparedstatement=connection.prepareStatement(query);
			preparedstatement.setInt(1,id);
			ResultSet resultset=preparedstatement.executeQuery();
			if(resultset.next()) {
				return true;
			}else {
				return false;
			}
			
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

}
