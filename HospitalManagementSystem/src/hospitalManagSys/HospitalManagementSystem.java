package hospitalManagSys;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem {

	private static final String url ="jdbc:mysql://127.0.0.1:3306/hospital";
	
	private static final String username="root";
	
	private static final String password="root";
	
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");			
			
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
		Scanner scanner=new Scanner(System.in);
		try {
			Connection connection =DriverManager.getConnection(url,username,password);
			patient patients = new patient(connection, scanner);
			Doctor doctor =new Doctor(connection);
			
			while(true) {
				System.out.println("\n **** HOSPITAL MANAGEMENT SYSTEM **** \n");
				System.out.println(" 1:Add Patient");
				System.out.println(" 2:View Patient");
				System.out.println(" 3:view Doctor");
				System.out.println(" 4:Book Appointment");
				System.out.println(" 5:Exit  \n");
				
				System.out.print(" Enter Your Choice : \n");
			
				int choice=scanner.nextInt();
				
				switch (choice) {
				case 1: 
					patients.addPatient();
					break;
				case 2:
					patients.viewPatient();
					break;
				case 3:
					doctor.viewDoctor();
					break;
				case 4:
					bookAppointment(patients, doctor, connection, scanner);
					break;
				case 5:
					System.out.println("Thank You For Visiting Hospital Management System !!!");
					return;
				default:
					System.out.println("Enter valid choice");
					break;
				
				}
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void bookAppointment(patient patients, Doctor doctor,Connection connection,Scanner scanner) {
		System.out.print("Enter Patient id: ");
		int patientId=scanner.nextInt();
		System.out.print("Enter Doctor  id: ");
		int doctorId=scanner.nextInt();
		System.out.print("Enter Appointment date (YYYY-MM-DD): \n");
		String appointmentDate=scanner.next();
		
		if(patients.getPaitentById(patientId) && doctor.getDoctorById(doctorId)) {
			if(checkDoctorAvialibility( doctorId,appointmentDate ,connection)) {
				String appointmentquery="INSERT INTO appointments(patient_id,doctor_id,appointment_date)values(?,?,?)";
				try {
					PreparedStatement preparedstatement =connection.prepareStatement(appointmentquery);
					preparedstatement.setInt(1,patientId);
					preparedstatement.setInt(2, doctorId);
					preparedstatement.setString(3, appointmentDate);
					int rowaffect=preparedstatement.executeUpdate();
					if(rowaffect>0) {
						System.out.println("Appointment Booked \n");
					}
					else {
						System.out.println("Failed to book Appointment");
					}
					
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}else {
				System.out.println("Doctor not available");
			}
		} else {
			System.out.println("Either doctor or patient doesn't exit!");
		}
		
	}


	public static boolean checkDoctorAvialibility(int doctorid,String appointmentDate ,Connection connection) {
		String query="Select count(*) from appointments where doctor_id=? AND appointment_date =?";
		try {
			PreparedStatement preparedstatement=connection.prepareStatement(query);
			preparedstatement.setInt(1,doctorid);
			preparedstatement.setString(2,appointmentDate);
			ResultSet resultset = preparedstatement.executeQuery();
			if(resultset.next()) {
				int count =resultset.getInt(1);
				if(count == 0) {
					return true;
				}else {
					return false;
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
}
