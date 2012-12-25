package ru.interosite.openbooker;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.view.View;

import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class MainActivityTest {

	private MainActivity mActivity;

	@Before
	public void setUp() {
		mActivity = new MainActivity();
	}

	@Test
	public void checkIsStarted() {
		mActivity.onCreate(null);
		View hello = mActivity.findViewById(R.id.hello_view);
		assertThat(hello, notNullValue());
	}
}