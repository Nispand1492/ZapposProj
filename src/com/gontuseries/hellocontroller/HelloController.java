package com.gontuseries.hellocontroller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;

import jdk.nashorn.internal.parser.JSONParser;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import sun.net.www.protocol.http.HttpURLConnection;
@Controller
public class HelloController {
 
	@RequestMapping(value = "/get_data.html" , method = RequestMethod.GET)
	protected ModelAndView get_data(HttpServletRequest request,HttpServletResponse response) throws Exception {
		ModelAndView modelandview = new ModelAndView("getdata");
		modelandview.addObject("welcomeMessage","Enter Location Zip Code of any US city" );
		return modelandview;
	}
	@RequestMapping(value = "/welcome.html" , method = RequestMethod.POST)
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
		HttpServletResponse response ,@RequestParam Map<String,String> reqPar) throws Exception {
		String zip = reqPar.get("zipcode");
		String urlStr = "http://api.openweathermap.org/data/2.5/weather?zip="+zip+",us";
		URL url = new URL(urlStr);
		System.out.println("Connected");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		if (conn.getResponseCode() != 200) {
		    throw new IOException(conn.getResponseMessage());
		  }
		BufferedReader rd = new BufferedReader(
			      new InputStreamReader(conn.getInputStream()));
			  StringBuilder sb = new StringBuilder();
			  String line;
			  while ((line = rd.readLine()) != null) {
			    sb.append(line);
			  }
			  rd.close();
			  System.out.println(sb);
			  conn.disconnect();
			 JSONObject obj = new JSONObject(sb.toString());
			 System.out.println(obj.get("main"));
			 String output = "" ; 
			 output = "<br>Temperature Average= " + obj.getJSONObject("main").get("temp");
			 output = output + "<br>Temperature Minimum = " + obj.getJSONObject("main").get("temp_min");
			 output = output + "<br>Temperature Maximum = " + obj.getJSONObject("main").get("temp_max");
			 output = output + "<br>Humidity = " + obj.getJSONObject("main").get("humidity");
			 output = output + "<br>Pressure = " + obj.getJSONObject("main").get("pressure");
			 output = output + "<br>Wind Speed = " + obj.getJSONObject("wind").get("speed");
			 output = output + "<br>Wind Degree = " + obj.getJSONObject("wind").get("deg");
			 //String data1 = (String)obj.get("main");
		ModelAndView modelandview = new ModelAndView("HelloPage");
		modelandview.addObject("welcomeMessage",output );
		
		return modelandview;
	}
}