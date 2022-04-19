package concord;

import java.util.ArrayList;

public class ClientSubstitute
{
	private String username;
	private String password;
	private ArrayList<Integer> listOfGroupIDs;
	public ClientSubstitute(String username, String password)
	{
		this.setUsername(username);
		this.setPassword(password);
		this.setListOfGroupIDs(new ArrayList<Integer>());
	}
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public void addGroupID(int i)
	{
		this.getListOfGroupIDs().add(i);		
	}
	public ArrayList<Integer> getListOfGroupIDs()
	{
		return listOfGroupIDs;
	}
	public void setListOfGroupIDs(ArrayList<Integer> listOfGroupIDs)
	{
		this.listOfGroupIDs = listOfGroupIDs;
	}
	public boolean login(String enteredName, String enteredPassword)
	{
		if (enteredName.equals(username) && enteredPassword.equals(enteredPassword))
		{
			return true;
		}
		else
		{
			return false;
		}		
	}

}
