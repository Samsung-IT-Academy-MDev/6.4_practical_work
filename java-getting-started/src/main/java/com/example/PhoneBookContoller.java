package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PhoneBookContoller {
	@Value("${spring.datasource.url}")
	private String dbUrl;

	@Autowired
	private DataSource dataSource;
	  
	@RequestMapping("/hi")
	public String hi() {
		return "Hi";    
	}

	@RequestMapping(method = RequestMethod.GET,value = "/read")
	public List<PhonebookEntry> read(){
		ArrayList<PhonebookEntry> ls=new ArrayList<PhonebookEntry>();
		 try (Connection connection = dataSource.getConnection()) {
		      Statement stmt = connection.createStatement();
		      ResultSet rs = stmt.executeQuery("SELECT * FROM phonebook");
		      while (rs.next()) {
		        ls.add(new PhonebookEntry(rs.getLong("id"),rs.getString("name"), rs.getString("phone")));
		      }
		    } catch (Exception e) {
		    }
		return ls;
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/create")
	public Boolean create(@RequestBody PhonebookEntry pb) {
		try (Connection connection = dataSource.getConnection()) {
		      PreparedStatement stmt = connection.prepareStatement("insert into phonebook (name,phone) values(?,?)");
		      stmt.setString(1, pb.name);
		      stmt.setString(2, pb.phone);
		      int num = stmt.executeUpdate();
				return num > 0;
		    } catch (Exception e) { }
		return false;
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/delete")
	public Boolean delete(@RequestBody PhonebookEntry pb) {
		try (Connection connection = dataSource.getConnection()) {
		      PreparedStatement stmt = connection.prepareStatement("delete from phonebook where id=?");
		      stmt.setLong(1, pb.id);
		      int num = stmt.executeUpdate();
				return num > 0;
		    } catch (Exception e) { }
		return false;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/update")
	public Boolean update(@RequestBody PhonebookEntry pb) {
		try (Connection connection = dataSource.getConnection()) {
		      PreparedStatement stmt = connection.prepareStatement("UPDATE phonebook SET name = ?,phone = ? WHERE name=? where id=?");
		      stmt.setString(1, pb.name);
		      stmt.setString(2, pb.phone);
		      stmt.setLong(2, pb.id);
		      int num = stmt.executeUpdate();
				return num > 0;
		    } catch (Exception e) { }
		return false;
	}
	
}
