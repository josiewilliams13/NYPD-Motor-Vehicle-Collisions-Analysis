package project5;

import java.util.ArrayList;

public class Collision implements Comparable<Collision>{
	private Date date;
	private String zip;
	private String key;
	
	private int personsInjured;
	private int pedestriansInjured;
	private int cyclistsInjured;
	private int motoristsInjured;

	private int personsKilled;
	private int pedestriansKilled;
	private int cyclistsKilled;
	private int motoristsKilled;	

	//constructor will be given incorrect ArrayList
		
	public Collision(ArrayList<String> entries) throws IllegalArgumentException {
		if (entries.size() < 24) {
			throw new IllegalArgumentException();
		}
		this.date= new Date(entries.get(0));
		this.zip=entries.get(3);
		this.personsInjured=Integer.parseInt(entries.get(10));
		this.personsKilled=Integer.parseInt(entries.get(11));
		this.pedestriansInjured=Integer.parseInt(entries.get(12));
		this.pedestriansKilled=Integer.parseInt(entries.get(13));
		this.cyclistsInjured=Integer.parseInt(entries.get(14));
		this.cyclistsKilled=Integer.parseInt(entries.get(15));
		this.motoristsInjured=Integer.parseInt(entries.get(16));
		this.motoristsKilled=Integer.parseInt(entries.get(17));
		this.key=entries.get(23);
	}
	
	public String getZip() {
		return zip;
	}
	
	public Date getDate() {
		return date;
	}
	
	public String getKey() {
		return key;
	}
	
	public int getPersonsInjured() {
		return personsInjured;
	}
	
	public int getPedestriansInjured() {
		return pedestriansInjured;
	}
	
	public int getCyclistsInjured() {
		return cyclistsInjured;
	}
	
	public int getMotoristsInjured() {
		return motoristsInjured;
	}
	
	public int getPersonsKilled(){
		return personsKilled;
	}
	
	public int getPedestriansKilled() {
		return pedestriansKilled;
	}
	
	public int getCyclistsKilled() {
		return cyclistsKilled;
	}
	
	public int getMotoristsKilled() {
		return motoristsKilled;
	}
	
	@Override
	public int compareTo(Collision o) {
		if (this.zip.equals(o.zip) && this.date.equals(o.date))
			return this.key.compareTo(o.key);
		else if (this.date.equals(o.date))
			return this.date.compareTo(o.date);
		else
			return this.zip.compareTo(o.zip);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != this.getClass())
			return false;
		
		Collision object = (Collision) obj;
		
		if (this.zip.equals(object.zip) && this.date.equals(object.date) && this.key.equals(object.key))
			return true;
		
		return false;
		
//		if (this.zip.equals(obj) && this.date.equals(obj))
//			return this.key.equals(obj);
//		else if (this.date.equals(obj))
//			return this.date.equals(obj);
//		else
//			return this.zip.equals(obj);
	}
}
