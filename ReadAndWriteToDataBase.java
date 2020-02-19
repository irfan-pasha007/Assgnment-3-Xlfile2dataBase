package com.assignment3.employee;

import java.io.*;
import java.sql.*;
import java.util.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

public class ReadAndWriteToDataBase {

	public static void main(String[] args) {
		// read a Xl file
		try {
			readXlFile();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static FileInputStream readXlFile() throws Exception {
		FileReader reader = new FileReader("properties");
		Properties properties = new Properties();
		properties.load(reader);
		String Xl_fil_name = properties.getProperty("Xl_fil_name");
		FileInputStream inputStream = new FileInputStream(
				"C:\\Users\\Irfan Pasha\\Documents\\" + Xl_fil_name + ".xlsx");
		if (inputStream != null) {
			System.out.println("data read from xl successfully");
			writeToDataBase(inputStream);
			return inputStream;
		}

		return null;
	}
	public static void writeToDataBase(FileInputStream inputStream) {
		try {
			PreparedStatement statement = null;
			FileReader reader = new FileReader("properties");
			Properties properties = new Properties();
			properties.load(reader);

			// mysql db connection
		Connection connection = connect(properties.getProperty("user_name"), properties.getProperty("password"));
		try
		{
			if (connection != null) {
				System.out.println("database connection successful");
			}
			else
			{
				throw  new ConnectionFailedException();
			}
		}
		catch(ConnectionFailedException e)
		{
			System.out.println(e.getMessage());
		}
		
			// getting workbook and sheet
			Workbook workbook = new XSSFWorkbook(inputStream);
			Sheet firstSheet = workbook.getSheetAt(0);

			// creating a sql query
			String table_name = properties.getProperty("table_name");
			String query = properties.getProperty("INSERT INTO " + "table_name "
					+ "(business_partner_id,business_partner_name,business_partner_code,contact_name,address1,address2,city,province,country,postal_code,parent_company,toll_free_number, phone, phone_extension, fax, website_url,	payment_condition,	gl_number,	driver_min_age,	api_enabled, status,	one_way_fee_paid_by,	country_code) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
			statement = connection.prepareStatement(query);

			/*
			 * using iterator Iterator<Row> rowIterator = firstSheet.iterator();
			 * rowIterator.next(); // skip the header row while (rowIterator.hasNext()) {
			 * Row nextRow = rowIterator.next(); Iterator<Cell> cellIterator =
			 * nextRow.cellIterator(); while (cellIterator.hasNext()) { Cell nextCell =
			 * cellIterator.next();
			 */

			// to iterate row
			for (int i = 1; i <= firstSheet.getLastRowNum(); i++) {
				Row nextRow = firstSheet.getRow(i);
				// to iterate cell in a row
				for (int j = 0; j < nextRow.getLastCellNum(); j++) {
					Cell nextCell = nextRow.getCell(j);
					if (nextCell == null) {
						nextCell = nextRow.getCell(j, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					}
					int columnIndex = nextCell.getColumnIndex();
					switch (columnIndex) {
					case 0:
						int business_partner_id = (int) nextCell.getNumericCellValue();
						statement.setInt(1, business_partner_id);
						break;
					case 1:
						String business_partner_name = nextCell.getStringCellValue();
						statement.setString(2, business_partner_name);
						break;
					case 2:
						String business_partner_code = nextCell.getStringCellValue();
						statement.setString(3, business_partner_code);
						break;
					case 3:
						String contact_name = nextCell.getStringCellValue();
						statement.setString(4, contact_name);
						break;
					case 4:
						String address1 = nextCell.getStringCellValue();
						statement.setString(5, address1);
						break;
					case 5:
						String address2 = nextCell.getStringCellValue();
						statement.setString(6, address2);
						break;
					case 6:
						String city = nextCell.getStringCellValue();
						statement.setString(7, city);
						break;
					case 7:
						String province = nextCell.getStringCellValue();
						statement.setString(8, province);
						break;
					case 8:
						String country = nextCell.getStringCellValue();
						statement.setString(9, country);
						break;
					case 9:
						String postal_code = nextCell.getStringCellValue();
						statement.setString(10, postal_code);
						break;
					case 10:
						String parent_company = nextCell.getStringCellValue();
						statement.setString(11, parent_company);
						break;
					case 11:
						String toll_free_number = nextCell.getStringCellValue();
						statement.setString(12, toll_free_number);
						break;
					case 12:
						nextCell.setCellType(nextCell.CELL_TYPE_STRING);
						String phone = nextCell.getStringCellValue();

						statement.setString(13, phone);
						break;
					case 13:
						nextCell.setCellType(nextCell.CELL_TYPE_STRING);
						String phone_extension = nextCell.getStringCellValue();
						statement.setString(14, phone_extension);
						break;
					case 14:
						String fax = nextCell.getStringCellValue();
						statement.setString(15, fax);
						break;
					case 15:
						String website_url = nextCell.getStringCellValue();
						statement.setString(16, website_url);
						break;
					case 16:
						int payment_condition = (int) nextCell.getNumericCellValue();
						statement.setInt(17, payment_condition);
						break;
					case 17:
						String gl_number = nextCell.getStringCellValue();
						statement.setString(18, gl_number);
						break;
					case 18:
						int driver_min_age = (int) nextCell.getNumericCellValue();
						statement.setInt(19, driver_min_age);
						break;
					case 19:
						int api_enabled = (int) nextCell.getNumericCellValue();
						statement.setInt(20, api_enabled);
						break;
					case 20:
						int status = (int) nextCell.getNumericCellValue();
						statement.setInt(21, status);
						break;
					case 21:
						String one_way_fee_paid_by = nextCell.getStringCellValue();
						statement.setString(22, one_way_fee_paid_by);
						break;
					case 22:
						String country_code = nextCell.getStringCellValue();
						statement.setString(23, country_code);
						break;
					}
				}
				statement.addBatch();

			}
			// executing the statements
			statement.executeBatch();
			// closing all the connections
			workbook.close();
			connection.close();
		} catch (IOException ex1) {
			System.out.println("Error reading file");
			ex1.printStackTrace();
		} catch (SQLException ex2) {
			System.out.println("Database error");
			ex2.printStackTrace();
		}
	}

	public static Connection connect(String username, String password) {
		Connection connection = null;
		try {
			FileReader reader = new FileReader("properties");
			Properties properties = new Properties();
			properties.load(reader);
			Class.forName("com.mysql.jdbc.Driver");
			String host_name = properties.getProperty("host_name");
			String port_number = properties.getProperty("port_number");
			String data_base_name = properties.getProperty("data_base_name");
			String data_base_used = properties.getProperty("data_base_used");
			connection = DriverManager.getConnection(
					"jdbc:" + data_base_used + "://" + host_name + ":" + port_number + "/" + data_base_name + "",
					username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

}
