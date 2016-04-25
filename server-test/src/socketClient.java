import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

class socketClient {
	
	public Socket socket;
	public PrintWriter out;
	public Scanner sc;
	
	public static void main(String args[]) throws Exception {
		
	//Create socket connection
    socketClient sc=new socketClient();
    sc.execAdb();
    sc.initializeConnection();
    
	}
	
	
	private  void execAdb() {
		// run the adb bridge
		try {
		  Process p=Runtime.getRuntime().exec("C:\\adt-bundle-windows-x86_64-20140321\\sdk\\platform-tools\\adb.exe forward tcp:38300 tcp:38300");
		  Scanner sc = new Scanner(p.getErrorStream());
		  if (sc.hasNext()) {
		    while (sc.hasNext()) System.out.println(sc.next());
		    System.err.println("Cannot start the Android debug bridge");
		  }
		} catch (Exception e) {
			System.err.println(e.toString());
		}
		
	}
	
	private void connection(){
		try{
			socket = new Socket("localhost", 38300);
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			out.write("hello wifitestpro");
			out.write("eof\n");
			out.flush();
			
			Scanner sc=new Scanner(socket.getInputStream());

			socket.setSoTimeout(10*1000);
				
			
			// add a shutdown hook to close the socket if system crashes or exists unexpectedly
			Thread closeSocketOnShutdown = new Thread() {
		     public void run() {
		       try {
		    	 	socket.close();
		       } catch (IOException e) {
		    	 	e.printStackTrace();
		      }
		     }
		 };Runtime.getRuntime().addShutdownHook(closeSocketOnShutdown);
		} catch (UnknownHostException e) {
			
			System.err.println("Socket connection problem (Unknown host)" + e.getStackTrace());
			
		} catch (IOException e) {
			 
			System.err.println("Could not initialize I/O on socket" + e.getStackTrace());
			
		} 
		
		
	}
	
	
	
	public void initializeConnection(){
		//Create socket connection
		try{
		socket = new Socket("localhost", 38300);
		out = new PrintWriter(socket.getOutputStream(), true);
		sc=new Scanner(socket.getInputStream());

		out.write("hello wifitestpro");
		out.write("eof\n");
		out.flush();
		
		// add a shutdown hook to close the socket if system crashes or exists unexpectedly
		Thread closeSocketOnShutdown = new Thread() {
		     public void run() {
		         try {
		            socket.close();
		         } catch (IOException e) {
		            e.printStackTrace();
		         }
		     }
		};
		    Runtime.getRuntime().addShutdownHook(closeSocketOnShutdown);
		} catch (UnknownHostException e) {
		    System.err.print("Socket connection problem (Unknown host)"+e.getStackTrace());
		} catch (IOException e) {
			System.err.print("Could not initialize I/O on socket" +e.getStackTrace());
		}
	}
	
	
	
	
	
}