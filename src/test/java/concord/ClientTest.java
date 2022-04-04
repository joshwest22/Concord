package concord;

import static org.junit.jupiter.api.Assertions.*;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClientTest
{
	
	Server server;
	
	@BeforeEach
	void setUp() throws Exception
	{
		server = new Server();
		Registry registry = LocateRegistry.createRegistry(1099);
		registry.rebind("SERVER", server);
	}

	@AfterEach
	void tearDown() throws Exception
	{
	}

	@Test
	void test()
	{
		RMIObserved observed;
		try
		{
			Client client = new Client();
			observed = (RMIObserved) Naming.lookup("rmi://127.0.0.1/SERVER");
			observed.addObserver(client);
			client.clientName = "Tony";
			
			server.makeDonuts();
		} catch (MalformedURLException | RemoteException | NotBoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("I failed");
		}
		
		
	}

}
