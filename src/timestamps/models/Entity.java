package timestamps.models;

import java.math.BigInteger;

public class Entity {
	
	private BigInteger ID;
	private String name;
	private BigInteger group;
	private String address;
	
	
	public BigInteger getGroup() {
		return group;
	}

	public void setGroup(BigInteger group) {
		this.group = group;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	
	public BigInteger getID() {
		return ID;
	}
	
	public void setID(BigInteger ID){
		this.ID = ID;
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}
