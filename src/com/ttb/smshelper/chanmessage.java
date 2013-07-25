package com.ttb.smshelper;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class chanmessage extends Activity {
	EditText editName, editPhone;
	Button btnAdd;
	ArrayList<list_chan> arrLich = new ArrayList<list_chan>();
	ArrayAdapter<list_chan> adapter = null;
	ListView lvblocklist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manhinh_chan);
		doFormWidgets();
		getDefaultInfor();
		addEventFormWidgets();
	}

	private void getDefaultInfor() {
		editName.requestFocus();

	}

	private void doFormWidgets() {

		editName = (EditText) findViewById(R.id.editName);
		editPhone = (EditText) findViewById(R.id.editPhone);
		btnAdd = (Button) findViewById(R.id.btnAdd);
		lvblocklist = (ListView) findViewById(R.id.lvblocklist);
		adapter = new ArrayAdapter<list_chan>(this,
				android.R.layout.simple_list_item_1, arrLich);
		lvblocklist.setAdapter(adapter);

	}

	private void addEventFormWidgets() {

		btnAdd.setOnClickListener(new MyButtonEvent());
		lvblocklist.setOnItemLongClickListener(new MyListViewEvent());

	}

	private class MyButtonEvent implements OnClickListener {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {

			case R.id.btnAdd:
				processAddJob();
				break;
			}
		}

		private void processAddJob() {

			String name = editName.getText() + "";
			String phone = editPhone.getText() + "";
			list_chan chan = new list_chan(name, phone);
			arrLich.add(chan);
			adapter.notifyDataSetChanged();

			editName.setText("");
			editPhone.setText("");
			editName.requestFocus();
		}

	}

	private class MyListViewEvent implements OnItemLongClickListener {

		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {

			arrLich.remove(arg2);
			adapter.notifyDataSetChanged();
			return false;
		}

	}
}
