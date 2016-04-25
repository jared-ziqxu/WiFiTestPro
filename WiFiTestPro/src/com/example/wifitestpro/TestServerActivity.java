package com.example.wifitestpro;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.net.SocketTimeoutException;
import java.io.IOException;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class TestServerActivity extends ActionBarActivity {

	private TextView myTextView;
	public static final int TIMEOUT=100;
	private String connectionStatus=null;
	private Handler mHandler=null;
	public ServerSocket server=null;
	public Scanner socketIn=null;
	public PrintWriter socketOut=null;
	public static final String TAG="Connection";
	public boolean connected=false;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test_server);

		
		Intent intent = getIntent();
		String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
	
		Toast.makeText(TestServerActivity.this, message, message.length()).show();
		
		System.out.println("!!!TestServerActivity "+message);
		
		if (message.equalsIgnoreCase("start")) {
		    System.out.println("start server command received");
		    new Thread(initializeConnection).start();
		    String msg="Waiting for connnection from client...";
		    Toast.makeText(TestServerActivity.this, msg, msg.length()).show();
			
		} else if (message.equalsIgnoreCase("stop")) {
			System.out.println("stop server command received");
			
			
		} else {
			
			
		}
			
		setContentView(R.layout.fragment_test_server);
		myTextView = (TextView)findViewById(R.id.myTextView3);
		myTextView.setText(message);
	
	}
	


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_test_server,
					container, false);
			return rootView;
		}
	}

	
	private Runnable initializeConnection = new Thread() {
		public void run() {
			System.out.println("!!in thread, run func is called!!");

		Socket client=null;
		System.out.println("client is initialized");
		// initialize server socket
		try{
			System.out.println("1111");
		 server = new ServerSocket(38300);
		 server.setSoTimeout(TIMEOUT*1000);
		 System.out.println("2222");
		 //attempt to accept a connection
		 client = server.accept();
		 System.out.println("3333");
		 socketIn = new Scanner(client.getInputStream());
		 System.out.println("!!! after socketin");
		 if (socketIn.hasNext()) {
			 System.out.println("!!! in socket has next");
			 while (socketIn.hasNext()) System.out.println(socketIn.next());
		  }
		 //socketOut = new PrintWriter(client.getOutputStream(), true);
		} catch (SocketTimeoutException e) {
		// print out TIMEOUT
		  connectionStatus="Connection has timed out! Please try again";
		  mHandler.post(showConnectionStatus);
		} catch (IOException e) {
		  Log.e(TAG, ""+e);
		} finally {
			 System.out.println("!!! finally, close socket!");
		//close the server socket
		  try {
			if (server!=null)
				server.close();
		     } catch (IOException ec) {
				Log.e(TAG, "Cannot close server socket" + ec);
		    }
		}

		if (client!=null) {
		   connected=true;
		   // print out success
		   connectionStatus="Connection was succesful!";
		   mHandler.post(showConnectionStatus);


		   }
		 }
		};
	
	
		private Runnable showConnectionStatus = new Runnable() {
			public void run() {
			    Toast.makeText(TestServerActivity.this, connectionStatus, Toast.LENGTH_SHORT).show();
			}
		};
			
	
	
}
