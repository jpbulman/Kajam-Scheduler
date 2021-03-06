package db;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.UUID;

import model.Schedule;

public class ScheduleDAO {

	java.sql.Connection conn;

    public ScheduleDAO() {
    	try  {
    		conn = DatabaseUtil.connect();
    	} catch (Exception e) {
    		System.out.println(e);
    		conn = null;
    	}
    }
    
    
    public Schedule getSchedule(UUID id) throws Exception {
        
        try {
            Schedule s = null;
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Schedules WHERE id=?;");
            ps.setString(1,  id.toString());
            ResultSet resultSet = ps.executeQuery();
            
            while (resultSet.next()) {
                s = generateSchedule(resultSet);
            }
            resultSet.close();
            ps.close();
            
            return s;

        } catch (Exception e) {
        	e.printStackTrace();
        	return null;
            //throw new Exception("Failed in getting schedule: " + e.getMessage());
        }
    }
    
    public ArrayList<Schedule> getAllSchedules(){
    	try {
    		ArrayList<Schedule> schedules = new ArrayList<Schedule>();
    		PreparedStatement ps = conn.prepareStatement("SELECT * FROM Schedules;");
    		ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                schedules.add(generateSchedule(resultSet));
            }
            resultSet.close();
            ps.close();
            
            return schedules;
    		
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }
    
    
    public boolean deleteSchedule(Schedule s) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("DELETE FROM Schedules WHERE id = ?;");
            ps.setString(1, s.id.toString());
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);

        } catch (Exception e) {
            throw new Exception("Failed to delete schedule: " + e.getMessage());
        }
    }
    
    public boolean addSchedule(Schedule s) throws Exception {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Schedules WHERE id = ?;");
            ps.setString(1, s.id.toString());
            ResultSet resultSet = ps.executeQuery();
            
            // already present?
            while (resultSet.next()) {
            	//Schedule sch = generateSchedule(resultSet);
                resultSet.close();
                return false;
            }

            ps = conn.prepareStatement("INSERT INTO Schedules (id,name,secretCode, duration,startHour,endHour,startDate,endDate,creationDateTime) values(?,?,?,?,?,?,?,?,?);");
            ps.setString(1,  s.id.toString());
            ps.setString(2,  s.name);
            ps.setInt(3, s.secretCode);
            ps.setInt(4, s.duration);
            ps.setInt(5, s.startTime.getHour());
            ps.setInt(6, s.endTime.getHour());
            ps.setDate(7, Date.valueOf(s.startDate));
            ps.setDate(8, Date.valueOf(s.endDate));
            ps.setString(9, s.timestamp);
            ps.execute();
            return true;

        } catch (Exception e) {
            throw new Exception("Failed to insert schedule: " + e.getMessage());
        }
    }
    
    public boolean updateSchedule(Schedule s) throws Exception {
        try {
        	String query = "UPDATE Schedules SET name=?, secretCode=?, duration=?, startHour=?, endHour=?, startDate=?, endDate=?, creationDateTime=? WHERE id=?;";
        	PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1,  s.name);
            ps.setInt(2, s.secretCode);
            ps.setInt(3, s.duration);
            ps.setInt(4, s.startTime.getHour());
            ps.setInt(5, s.endTime.getHour());
            ps.setDate(6, Date.valueOf(s.startDate));
            ps.setDate(7, Date.valueOf(s.endDate));
            ps.setString(8, s.timestamp);
            ps.setString(9, s.id.toString());
            int numAffected = ps.executeUpdate();
            ps.close();
            
            return (numAffected == 1);
        } catch (Exception e) {
            throw new Exception("Failed to update schedule: " + e.getMessage());
        }
    }
    
    private Schedule generateSchedule(ResultSet resultSet) throws Exception {
    	String id = resultSet.getString("id");
        String name  = resultSet.getString("name");
        int secretCode = resultSet.getInt("secretCode");
        int duration = resultSet.getInt("duration");
        int startTime = resultSet.getInt("startHour");
        int endTime = resultSet.getInt("endHour");
        Date startDate = resultSet.getDate("startDate");
        Date endDate = resultSet.getDate("endDate");
        String ts = resultSet.getString("creationDateTime");
        
        
        return new Schedule (UUID.fromString(id),name, secretCode, duration, LocalTime.of(startTime, 0),
        		LocalTime.of(endTime, 0), startDate.toLocalDate(), endDate.toLocalDate(), ts);
    }
	
}
