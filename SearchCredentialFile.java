package com.peerlogin;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;
import java.io.*; 
import java.net.*;
public class SearchCredentialFile {
	
		private String pathtocredentials;
		public int searchFile(File rootforfile){
					
					File[] driveContents=rootforfile.listFiles();
					
					File[] filesInPeerFolder;
					
					int filefound=0;
					
					String directoryName="PeerToPeer";
					
					String fileName="credentials.csv";
					
					if(driveContents!=null){
						
								for(File foldersInCurrentDrive:driveContents){
									
									if(foldersInCurrentDrive.isDirectory() && directoryName.equalsIgnoreCase(foldersInCurrentDrive.getName())){
										
													System.out.println(foldersInCurrentDrive);
													
													filefound=1;
													
													filesInPeerFolder=foldersInCurrentDrive.listFiles();
													
													for(File file:filesInPeerFolder)
														
														if(fileName.equalsIgnoreCase(file.getName())){
															
															pathtocredentials=file.getPath();
							
														}
													
													break;
											
									 }
	
									System.out.println(foldersInCurrentDrive);

							      }
								
						}
					
					System.out.println(rootforfile+ "    FinishesForSearching!!!");
					
					return filefound;

		}
		
		public String returnPath(){
			
			return pathtocredentials;
			
		}
		
		public int checkCredentials(String pathtocredentials,String uname,String pswd){
			
			int credentialsMatch=0;
			
			File credentialFile=new File(pathtocredentials);
			
			try{
				
				Scanner inputStream=new Scanner(credentialFile);
				
				while(inputStream.hasNext()){
					
					String data=inputStream.next();
					
					String[] values=data.split(",");
					
					if(uname.equals(values[0]) || pswd.equals(values[1])){
							
							System.out.println(values[0]+"->"+values[1]);
							
							credentialsMatch=1;
					
					}
					
				}
				
				inputStream.close();
			
			}
			
			catch(Exception e){
				
				System.out.println(e);
				
			}
			
		return credentialsMatch;
		
		}
		
		
		
		public void createFile(){
			
			String directoryPath="c:/"+"PeerToPeer";

			String filePath=directoryPath+"/credentials.csv";
			
			String ipfilePath=directoryPath+"/ipfile.csv";

			File createDirectory = new File(directoryPath);

			File createFile=new File(filePath);
			
			File createIpFile=new File(ipfilePath);

			try {

			createDirectory.mkdir();

			createFile.createNewFile();
			
			createIpFile.createNewFile();

			}
			
			catch (IOException e) {

			e.printStackTrace();

			}
			
		}

		public int startSearch(String username,String password){
			
				SearchCredentialFile searchfileobject=new SearchCredentialFile();
	
				File[] roots=File.listRoots();

				int rootcounter,filefound=0,credentialsMatched=0;
				
				String pathtocredentials=null;//username="akshay",password="pqr";

				for(rootcounter=0;(rootcounter<roots.length && filefound==0);rootcounter++){

					filefound=searchfileobject.searchFile(roots[rootcounter]);
							
				}
				
				if(filefound==1){
					
					pathtocredentials=searchfileobject.returnPath();
				
					System.out.println("File found at  "+pathtocredentials);
					
					credentialsMatched=searchfileobject.checkCredentials(pathtocredentials,username,password);
					
					if(credentialsMatched==1)
						
						System.out.println("Peer Verified...");
					
					else
						
						System.out.println("Peer not found...");
				
				}
				
				else{
					
					System.out.println("Creting Folder and File for this Peer...");
					
					searchfileobject.createFile();
				
				}
				
				return credentialsMatched;
		}
			
			
			public void sendRequest(String inputCredentials ) throws Exception{
				
				System.out.println("Go for search in the network 22222222222222222");
				
				byte[] sendData = new byte[1024];
				
				byte[] receiveData = new byte[1024];
				
				sendData=inputCredentials.getBytes();
				
				DatagramSocket clientSocket=new DatagramSocket();
				
				InetAddress IPAddress = InetAddress.getByName("10.2.2.20");
				
				sendData=inputCredentials.getBytes();
				
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress ,9999);
				
				clientSocket.send(sendPacket);
				
				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

		        clientSocket.receive(receivePacket);
		         
		        String message = new String(receivePacket.getData());
		         
		       //pa message.replaceAll(null, null);
		        
		        System.out.println(message+"this is it pawan");
				
			}
			
			
public static void main(String[] args)throws Exception {
								
				int credentialsFoundLocally=0;
				
				System.out.println("Enter Username");
				 
				BufferedReader userInput =new BufferedReader(new InputStreamReader(System.in));
				 
				String username=userInput.readLine();
				 
				System.out.println("Enter Password");
				 
				String password=userInput.readLine();
					
				SearchCredentialFile navigateobject=new SearchCredentialFile();
				
				credentialsFoundLocally=navigateobject.startSearch(username,password);
				
				if(credentialsFoundLocally==1){
			
					System.out.println("Found Locally");
				
				}
				
				else{
					
					System.out.println("Go for search in the network 1111111111111");
					
					String inputCredentials=username+","+password;
					
					navigateobject.sendRequest(inputCredentials);	
				}
	
}
		
		
}


