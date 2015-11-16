

import java.io.Serializable;

public class Location implements Serializable
{
	private int m_x;
	private int m_y;
	private String m_message;
	
	transient private Thread myThread;
	
	Location()
	{
		m_x = 1;
		m_y = 1;
		m_message="";
		
		myThread = new Thread();
	}
	
	Location(int x, int y)
	{
		m_x = x;
		m_y = y;
		m_message="";
	}
	Location(Location copy)
	{
		m_x=copy.x();
		m_y=copy.y();
		m_message=copy.getMessage();
	}
	public void setLocation(int x, int y)
	{
		m_x = x;
		m_y = y;
	}
	public int x()
	{
		return m_x;
	}
	public int y()
	{
		return m_y;
	}
    public int gety()
    {
        return m_y;
    }
    public int getx()
    {
        return m_x;
    }

	//im using this to send messages confirming cooridates recieved. At this point mostly for testing 
	public String getMessage(){
		return m_message;
	}

	public void setMessage(String message){
		m_message=message;
	}
	
	@Override
	public String toString()
	{
		return "Location [x = " + m_x + " y = " + m_y + "] message = " + m_message;
	}
}