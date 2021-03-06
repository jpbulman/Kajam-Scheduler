package db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Random;
import java.util.UUID;

import org.junit.Test;

import model.Schedule;

public class ScheduleDAOTest {

	@Test
	public void testFind() {
	    ScheduleDAO sd = new ScheduleDAO();
	    try {
	    	Schedule s = sd.getSchedule(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"));
	    	System.out.println("Schedule " + s.name + " with id: " + s.id);
	    } catch (Exception e) {
	    	fail ("didn't work:" + e.getMessage());
	    }
	}
	
	@Test
	public void testCreate() {
	    ScheduleDAO sd = new ScheduleDAO();
	    try {
	    	// can add it
	    	String id = UUID.randomUUID().toString();
	    	Schedule schedule = new Schedule(UUID.fromString(id), "testName",125,
	    			30,LocalTime.of(0, 0), LocalTime.of(3, 0), 
	    			LocalDate.of(2000, 1, 1), LocalDate.of(2000, 1, 10),
	    			LocalDateTime.now().toString());
	    	boolean b = sd.addSchedule(schedule);
	    	System.out.println("add schedule: " + b);
	    	
	    	// can retrieve it
	    	Schedule s2 = sd.getSchedule(UUID.fromString(id));
	    	System.out.println("S2:" + s2.id);
	    	
	    	// can delete it
	    	assertTrue (sd.deleteSchedule(s2));
	    } catch (Exception e) {
	    	fail ("didn't work:" + e.getMessage());
	    }
	}
	
	@Test
	public void testUpdate() {
		ScheduleDAO sd = new ScheduleDAO();
		try {
			Schedule s = sd.getSchedule(UUID.fromString("38400000-8cf0-11bd-b23e-10b96e4ef00d"));
			Random random = new Random();
			int newCode = random.nextInt(100)+1;
			s.secretCode = newCode;
			assertTrue(sd.updateSchedule(s));
			assertEquals(sd.getSchedule(s.id).secretCode,newCode);
			System.out.println("Changed test schedule secret code to "+newCode);
		}catch (Exception e) {
	    	fail ("didn't work:" + e.getMessage());
	    }
	}
}
