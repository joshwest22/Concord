package concord;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class User
{
	private static final int MAX = 999999;
	private static final int MIN = 0;
	private String username;
	private String realname;
	private String password;
	private Integer userID;
	private URL userPic;
	private String userBio;
	private Boolean onlineStatus;
	private ArrayList<Integer> blockedUserIDs; 
	private ArrayList<Invitation> pendingInvites;
	
	public User(String username, String realname, String password, Integer userID, URL userPic, String userBio,
			Boolean onlineStatus)
	{
		this.username = username;
		this.realname = realname;
		this.password = password;
		this.userID = userID;
		this.userPic = userPic;
		this.userBio = userBio;
		this.onlineStatus = onlineStatus;
		this.blockedUserIDs = new ArrayList<Integer>();
		this.pendingInvites = new ArrayList<Invitation>();
	}
	
	public User(String username, String realname, String password)
	{
		
		this.username = username;
		this.realname = realname;
		this.password = password;
		Random rn = new Random();
		int range = MAX - MIN + 1;
		Integer generatedID = rn.nextInt(range) + MIN; 
		this.userID = generatedID;
		URL defualtUserPic = null;
		try
		{
			defualtUserPic = new URL("http://concordLogo.png"); //url might be wrong
		} 
			catch (MalformedURLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		this.userPic = defualtUserPic ;
		this.userBio = "No bio";
		this.onlineStatus = false;
		this.blockedUserIDs = new ArrayList<Integer>();
		this.pendingInvites = new ArrayList<Invitation>();
	}
	
	public User()
	{
		this("username", "realname", "password");
	}

	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getRealname()
	{
		return realname;
	}
	public void setRealname(String realname)
	{
		this.realname = realname;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	public Integer getUserID()
	{
		return userID;
	}
	public void setUserID(Integer userID)
	{
		this.userID = userID;
	}
	public URL getUserPic()
	{
		return userPic;
	}
	public void setUserPic(URL userPic)
	{
		this.userPic = userPic;
	}
	public String getUserBio()
	{
		return userBio;
	}
	public void setUserBio(String userBio)
	{
		this.userBio = userBio;
	}
	public Boolean getOnlineStatus()
	{
		return onlineStatus;
	}
	public void setOnlineStatus(Boolean onlineStatus)
	{
		this.onlineStatus = onlineStatus;
	}
	public ArrayList<Integer> getBlockedUserIDs()
	{
		return blockedUserIDs;
	}
	public void setBlockedUsers(ArrayList<Integer> blockedUsers)
	{
		this.blockedUserIDs = blockedUsers;
	}
	public ArrayList<Invitation> getPendingInvites()
	{
		return pendingInvites;
	}

	public void setPendingInvites(ArrayList<Invitation> pendingInvites)
	{
		this.pendingInvites = pendingInvites;
	}

	public void blockUser(Integer blockeeID)
	{
		blockedUserIDs.add(blockeeID);
	}
	public void unblockUser(Integer unblockeeID)
	{
		blockedUserIDs.remove(unblockeeID);
	}
	public Message sendMessage(String m)
	{
		Message newM = new Message(m, userID);
		return newM;
	}
	
}
