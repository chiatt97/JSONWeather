import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import net.sf.json.JSONObject;

public class Controller {
	private String[] codes, output;
	private int count;
	private double sum;
	private double average;
	private double max = 0;
	private double min = 100;
	private ArrayList<icao> data = new ArrayList();
	
	public void read(){
		File file = new File("input.txt");
		Scanner scan;
		count = 0;
		
		codes = new String[4];

		
		try {
			scan = new Scanner(file);
			while(scan.hasNext()){
				codes[count] = scan.nextLine();
				count++;
				
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void query() throws Exception{
		output = new String[count];
		
		for(int i = 0; i < count; i++){
			String JSonString = readURL("http://api.geonames.org/weatherIcaoJSON?ICAO=" + codes[i] + "&formatted=true&username=Clxhiatt");
			JSONObject x = JSONObject.fromObject(JSonString);
			JSONObject wd =(JSONObject)(x.get("weatherObservation")); 
			String stationName = (String)wd.get("stationName");
			
			Object sn = wd.get("stationName");
			Object temp = wd.get("temperature");
			Object lat = wd.get("lat");
			Object lng = wd.get("lng");
			Object datetime = wd.get("datetime");
			Object humidity = wd.get("humidity");
			Object clouds = wd.get("clouds");
			
			icao icaoObj = new icao(sn,temp,lat,lng,datetime,humidity,clouds, this);
			
			data.add(icaoObj);
			output[i] = icaoObj.toString();
		}
	}
	
	public void print(){
			try {

				File file = new File("output.txt");
				FileWriter fw = new FileWriter(file.getAbsoluteFile());
				BufferedWriter bw = new BufferedWriter(fw);
				if (!file.exists()) {
					file.createNewFile();
				}
				
				for(int i = 0; i < output.length; i++){
					bw.write(output[i]);
				}
				bw.write(calcTemp());
				bw.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	public String calcTemp(){
		average = sum / count;
		String output = "";
		output = "Max : " + max + "\nMin : " + min + "\nAverage : " + average;
		return output;
	}
	
	public void setSum(double n){
		sum = sum + n;
	}
	
	public void setMax(double d){
		max = d;
	}
	public double getMax(){
		return this.max;
	}
	public double getMin(){
		return this.min;
	}
	public void setMin(double d){
		min = d;
	}
	
	public static String readURL(String webservice) throws Exception {
	    	URL oracle = new URL(webservice);
	    	BufferedReader in = new BufferedReader(
	        new InputStreamReader(
	        oracle.openStream()));
	
		  String inputLine;
		  String result = "";
		
		  while ((inputLine = in.readLine()) != null)
		      result = result + inputLine;
		
		  in.close();
		  return result;
	}
}
