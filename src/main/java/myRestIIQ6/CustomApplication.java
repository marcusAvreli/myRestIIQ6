package myRestIIQ6;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "CustomApplication")
public class CustomApplication {
	private String name;
	private String displayName;
	private String description;
	private int id;
	
	public CustomApplication() {
		
	}
	public CustomApplication(int id,String name,String displayName,String description) {
		this.id= id;
		this.name = name;
		this.displayName = displayName;
		this.description = description;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
