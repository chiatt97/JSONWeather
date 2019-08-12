import java.util.ArrayList;

import net.sf.json.JSONObject;

public class icao {
	private String stationName, temp, lat, lng, datetime,humidity,clouds;
	private double max = 0;
	private double min = 100;
	double tempTemp, sum, average;
	int count = 0;
	private Controller c;
	
	public icao(Object sN, Object temp, Object lat, Object lng, Object dt, Object hum, Object clouds, Controller c){
		this.stationName = (String)sN;
		this.temp = String.valueOf(temp);
		this.tempTemp = Double.parseDouble(this.temp);
			c.setSum(Double.parseDouble(this.temp));
		this.lat = String.valueOf(lat);
		this.lng = String.valueOf(lng);
		this.datetime = (String)dt;
		this.humidity = hum.toString();
		this.clouds = (String)clouds;
		this.c = c;
			setMaxMin();
			//calcAvg();
	}
	
	public void setMaxMin(){
		if((c.getMax() < this.tempTemp)){
			c.setMax(this.tempTemp);
		}
		else if(c.getMin() > this.tempTemp){
			c.setMin(this.tempTemp);
		}
		sum += tempTemp;
	}
	
	public void check(){
		if(stationName == null || temp == null || lat == null || lng == null || datetime == null ||
				humidity == null || clouds == null){
					if(stationName == null){
						stationName = "No observation found.";
					}
					else if(temp == null){
						temp = "No observation found.";
					}
					else if(lat == null){
						lat = "No observation found.";
					}
					else if(lng == null){
						lng = "No observation found.";
					}
					else if(datetime == null){
						datetime = "No observation found.";
					}
					else if(humidity == null){
						humidity = "No observation found.";
					}
					else if(clouds == null){
						clouds = "No observation found.";
					}
				}
	}

	public double getMin(){
		return this.min;
	}
	public double getMax(){
		return this.max;
	}

	public String toString(){
		check();
		String result = "Station Name: " + this.stationName + "\nTemperature: " + this.temp + 
		"\nLatitude: " + this.lat + "\nLongitude: " + this.lng + "\nDate/Time: " + this.datetime +
		"\nHumidity: " + this.humidity + "\nClouds: " + this.clouds + "\n\n";
		return result;
	}
}
