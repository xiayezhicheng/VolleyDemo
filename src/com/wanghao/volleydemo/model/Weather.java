package com.wanghao.volleydemo.model;

public class Weather {

    private WeatherInfo weatherinfo;  
  
    public WeatherInfo getWeatherinfo() {  
        return weatherinfo;  
    }  
  
    public void setWeatherinfo(WeatherInfo weatherinfo) {  
        this.weatherinfo = weatherinfo;  
    }  
    
    public class WeatherInfo {  
    	  
        private String city;  
      
        private String temp;  
      
        private String time;  
      
        public String getCity() {  
            return city;  
        }  
      
        public void setCity(String city) {  
            this.city = city;  
        }  
      
        public String getTemp() {  
            return temp;  
        }  
      
        public void setTemp(String temp) {  
            this.temp = temp;  
        }  
      
        public String getTime() {  
            return time;  
        }  
      
        public void setTime(String time) {  
            this.time = time;  
        }  
      
    }  
}
