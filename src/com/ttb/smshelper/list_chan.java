package com.ttb.smshelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;



public class list_chan {
	 private String name;
	 private String phone;
	 
	 public String getName() {
	 return name;
	 }
	 public void setName(String name) {
	 this.name = name;
	 }
	 public String getPhone() {
	 return phone;
	 }
	 public void setPhone(String phone) {
	 this.phone = phone;
	 }
	
	 public list_chan(String name, String phone) {
	 super();
	 this.name = name;
	 this.phone= phone;
	 }
	 public list_chan() {
	 super();
	 }
	 
	 
	 @Override
	 public String toString() {
	 return this.name+"\n"+this.phone;
	 }
	}
