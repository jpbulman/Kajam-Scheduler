package db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.UUID;

import org.junit.Test;

import model.TimeSlot;

public class TimeSlotDAOTest {

	@Test
	public void testFind() {
	    TimeSlotDAO td = new TimeSlotDAO();
	    try {
	    	TimeSlot t = td.getTimeSlot(UUID.fromString("9be6e94a-b57d-4cf6-939c-289328895598"));
	    	System.out.println("TimeSlot with id: " + t.id);
	    	assertEquals("9be6e94a-b57d-4cf6-939c-289328895598", t.id.toString());
	    } catch (Exception e) {
	    	fail ("didn't work:" + e.getMessage());
	    }
	}
	
	@Test
	public void testCreate() {
	    TimeSlotDAO td = new TimeSlotDAO();
	    try {
	    	// can add it
	    	String id = UUID.randomUUID().toString();
	    	TimeSlot timeSlot = new TimeSlot(UUID.fromString(id), UUID.fromString(id),
	    			LocalTime.of(12, 0), LocalTime.of(12, 30), LocalDate.of(2018, 12, 3), true);
	    	boolean b = td.addTimeSlot(timeSlot);
	    	System.out.println("add timeSlot: " + b);
	    	
	    	// can retrieve it
	    	TimeSlot s2 = td.getTimeSlot(UUID.fromString(id));
	    	System.out.println("S2:" + s2.id);
	    	
	    	// can delete it
	    	assertTrue (td.deleteTimeSlot(s2));
	    } catch (Exception e) {
	    	fail ("didn't work:" + e.getMessage());
	    }
	}
	
	@Test
	public void testFindAll() {
		TimeSlotDAO td = new TimeSlotDAO();
		
		try {
			ArrayList<TimeSlot> timeSlots = td.getAllTimeSlots(UUID.fromString(
					"9de8607b-c846-4207-92e6-b0d4a110fbad"));
			assertEquals(timeSlots.size(), 20);
			
		} catch(Exception e) {
			fail ("didn't work:" + e.getMessage());
		}
	}
	
	@Test
	public void testFindByDateTime() {
		TimeSlotDAO td = new TimeSlotDAO();
		
		try {
			TimeSlot t = td.getTimeSlotByDateTime(UUID.fromString(
					"9de8607b-c846-4207-92e6-b0d4a110fbad"), LocalDate.of(2018, 12, 6),
					LocalTime.of(14, 0));
			assertEquals(t.scheduleID.toString(), "9de8607b-c846-4207-92e6-b0d4a110fbad");
			assertEquals(t.date,LocalDate.of(2018, 12, 6));
			assertEquals(t.startTime,LocalTime.of(14, 0));
		} catch(Exception e) {
			fail ("didn't work:" + e.getMessage());
		}
	}
	
	// Test updating isFree field
	@Test
	public void testUpdate() {
		TimeSlotDAO td = new TimeSlotDAO();
		
		try {
			TimeSlot t = td.getTimeSlot(UUID.fromString("9be6e94a-b57d-4cf6-939c-289328895598"));
			System.out.println("isFree before update: "+ t.isFree);
			boolean initialIsFree = t.isFree;
			t.isFree = !t.isFree;
			
			assertTrue(td.updateTimeSlot(t));
			t = td.getTimeSlot(UUID.fromString("9be6e94a-b57d-4cf6-939c-289328895598"));
			System.out.println("isFree after update: "+ t.isFree);
			assertEquals(t.isFree, !initialIsFree);
			
		} catch(Exception e) {
			fail ("didn't work:" + e.getMessage());
		}
	}
	
}
