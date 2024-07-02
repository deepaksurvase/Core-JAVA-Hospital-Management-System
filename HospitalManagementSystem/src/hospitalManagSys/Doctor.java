package hospitalManagSys;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Doctor {
	private Connection connection;
	
	public Doctor(Connection connection) {
		super();
		this.connection = connection;
		
	}
	
	
	
	
	public void viewDoctor() {
		String query="Select * from doctors";
		try {
			PreparedStatement preparedstatement = connection.prepareStatement(query);
			ResultSet resultset=preparedstatement.executeQuery();
			System.out.println("Doctors");
			System.out.println("+------------+----------------------+---------------------------+ ");
			
			System.out.println("| Doctor Id  | Name                 | specialization            | ");
			
			System.out.println("+------------+----------------------+---------------------------+ ");
			
			while(resultset.next()) {
				int id=resultset.getInt("id");
				String name=resultset.getString("name");
				String specialization=resultset.getString("specialization");
				System.out.printf("| %-11s| %-21s| %-26s|\n",id,name,specialization);
				System.out.println("+------------+----------------------+---------------------------+ ");
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean getDoctorById(int id) {
		String query="Select * from doctors where id =?";
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
