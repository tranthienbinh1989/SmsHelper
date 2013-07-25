package com.ttb.smshelper;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
public class lichmessage extends Activity {
	TextView txtDate,txtTime;
	 EditText editnoidung,editphone;
	 Button btnDate,btnTime,btnthemlich;
	 ArrayList<list_lich>arrLich=new ArrayList<list_lich>();
	 ArrayAdapter<list_lich>adapter=null;
	 ListView lvlichgui;
	 Calendar cal;
	 Date dateFinish;
	 Date hourFinish;
	
		@Override
		 protected void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 setContentView(R.layout.manhinh_lich);
		 loadTabs();
		 doFormWidgets();
		 getDefaultInfor();
		 addEventFormWidgets();
		 }
		 private void doFormWidgets() {
				txtDate=(TextView) findViewById(R.id.txtdate);
				 txtTime=(TextView) findViewById(R.id.txttime);
				 editnoidung=(EditText) findViewById(R.id.editnoidung);
				 editphone=(EditText) findViewById(R.id.editphone);
				 btnDate=(Button) findViewById(R.id.btndate);
				 btnTime=(Button) findViewById(R.id.btntime);
				 btnthemlich=(Button) findViewById(R.id.btnthemlich);
				 lvlichgui=(ListView) findViewById(R.id.lvlichgui);
				 adapter=new ArrayAdapter<list_lich>
				 (this,
				 android.R.layout.simple_list_item_1,
				 arrLich);
				 lvlichgui.setAdapter(adapter);
				
			}
		 private void addEventFormWidgets() {
			 btnDate.setOnClickListener(new MyButtonEvent());
			 btnTime.setOnClickListener(new MyButtonEvent());
			 btnthemlich.setOnClickListener(new MyButtonEvent());
			 lvlichgui.setOnItemClickListener(new MyListViewEvent());
			 lvlichgui.setOnItemLongClickListener(new MyListViewEvent());
			 
			
		}
		 private class MyButtonEvent implements OnClickListener
		 {
		 @Override
		 public void onClick(View v) {
		 switch(v.getId())
		 {
		 case R.id.btndate:
		 showDatePickerDialog();
		 break;
		 case R.id.btntime:
		 showTimePickerDialog();
		 break;
		 case R.id.btnthemlich:
		 processAddJob();
		 break;
		 }
		 }
		 
		}
		 private class MyListViewEvent implements
		 OnItemClickListener,
		 OnItemLongClickListener
		 {
		 
		@Override
		 public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
		 int arg2, long arg3) {
		 
		 arrLich.remove(arg2);
		 adapter.notifyDataSetChanged();
		 return false;
		 }
		 
		@Override
		 public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		 long arg3) {
		 
		 Toast.makeText(lichmessage.this,
		 arrLich.get(arg2).getDesciption(),
		 Toast.LENGTH_LONG).show();
		 }
		 
		}

		private void getDefaultInfor() {
			cal=Calendar.getInstance();
			 SimpleDateFormat dft=null;
			 dft=new SimpleDateFormat("dd/MM/yyyy",Locale.getDefault());
			 String strDate=dft.format(cal.getTime());
			 txtDate.setText(strDate);
			 dft=new SimpleDateFormat("hh:mm a",Locale.getDefault());
			 String strTime=dft.format(cal.getTime());
			 txtTime.setText(strTime);
			 dft=new SimpleDateFormat("HH:mm",Locale.getDefault());
			 txtTime.setTag(dft.format(cal.getTime()));
			editphone.requestFocus();
			 dateFinish=cal.getTime();
			 hourFinish=cal.getTime();
			
		}
		 public void showDatePickerDialog()
		 {
		 OnDateSetListener callback=new OnDateSetListener() {
		 public void onDateSet(DatePicker view, int year,
		 int monthOfYear,
		 int dayOfMonth) {
		 
		 txtDate.setText(
		 (dayOfMonth) +"/"+(monthOfYear+1)+"/"+year);
		 
		 cal.set(year, monthOfYear, dayOfMonth);
		 dateFinish=cal.getTime();
		 }
		 };
		
		 String s=txtDate.getText()+"";
		 String strArrtmp[]=s.split("/");
		 int ngay=Integer.parseInt(strArrtmp[0]);
		 int thang=Integer.parseInt(strArrtmp[1])-1;
		 int nam=Integer.parseInt(strArrtmp[2]);
		 DatePickerDialog pic=new DatePickerDialog(
		 lichmessage.this,
		 callback, nam, thang, ngay);
		 pic.setTitle("Set Date");
		 pic.show();
		 }
		 
		 public void showTimePickerDialog()
		 {
		 OnTimeSetListener callback=new OnTimeSetListener() {
		 public void onTimeSet(TimePicker view,
		 int hourOfDay, int minute) {
		
		 String s=hourOfDay +":"+minute;
		 int hourTam=hourOfDay;
		 if(hourTam>12)
		 hourTam=hourTam-12;
		 txtTime.setText
		 (hourTam +":"+minute +(hourOfDay>12?" PM":" AM"));
		 
		 txtTime.setTag(s);
		 
		 cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
		 cal.set(Calendar.MINUTE, minute);
		 hourFinish=cal.getTime();
		 }
		 };
		 
		 String s=txtTime.getTag()+"";
		 String strArr[]=s.split(":");
		 int gio=Integer.parseInt(strArr[0]);
		 int phut=Integer.parseInt(strArr[1]);
		 TimePickerDialog time=new TimePickerDialog(
		 lichmessage.this,
		 callback, gio, phut, true);
		 time.setTitle("Set Time");
		 time.show();
		 }
		 public void processAddJob()
		 {
		 String title=editphone.getText()+"";
		 String description=editnoidung.getText()+"";
		 list_lich lich=new list_lich(title, description, dateFinish, hourFinish);
		 arrLich.add(lich);
		 adapter.notifyDataSetChanged();
	
		 editphone.setText("");
		 editnoidung.setText("");
		 editphone.requestFocus();
		 }

		public void loadTabs()
		 {
		 
		 final TabHost tab=(TabHost) findViewById
		 (android.R.id.tabhost);
		 
		 tab.setup();
		 TabHost.TabSpec spec;
		
		 spec=tab.newTabSpec("t1");
		 spec.setContent(R.id.tab1);
		 spec.setIndicator("New");
		 tab.addTab(spec);
		 spec=tab.newTabSpec("t2");
		 spec.setContent(R.id.tab2);
		 spec.setIndicator("Schedule List");
		 tab.addTab(spec);
		 
		 tab.setCurrentTab(0);
		
		 tab.setOnTabChangedListener(new
		 TabHost.OnTabChangeListener() {
		 public void onTabChanged(String arg0) {
		 String s="Tab tag ="+arg0 +"; index ="+
		 tab.getCurrentTab();
		 }
		 });
		
	}

	

	
}
