package ru.interosite.openbooker;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		
		final PopupActionsWidget popup = new PopupActionsWidget(this);
		
		final HashMap<Integer, PopupActionItem> map = new HashMap<Integer, PopupActionItem>();
		map.put(1, new PopupActionItem(1, "Add", getResources().getDrawable(R.drawable.ic_action_income)));
		map.put(2, new PopupActionItem(2, "Remove", getResources().getDrawable(R.drawable.ic_action_expense)));
		map.put(3, new PopupActionItem(3, "Move", getResources().getDrawable(R.drawable.ic_action_move)));
		map.put(4, new PopupActionItem(4, "Cancel", getResources().getDrawable(R.drawable.ic_action_move)));
		
		for(int i=1; i < 5; i++) {
			popup.addItem(map.get(i));
		}
		
		popup.setItemSelectedListener(new PopupActionsWidget.IItemSelectListener() {
			
			@Override
			public void onItemSelected(int itemId) {
				PopupActionItem item = map.get(itemId);
				if(item!=null) {
					Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
				}
			}
		});
		
		Button button = (Button)findViewById(R.id.test_button);
		button.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View button) {
				popup.show(button);
			}
		});
	}
	
}