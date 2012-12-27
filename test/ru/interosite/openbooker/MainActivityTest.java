package ru.interosite.openbooker;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

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
		assertThat(mActivity, notNullValue());
	}
	
	@Test
	public void checkAutobox() {
		Integer ii = 1;
		assertThat(ii, equalTo(1));
	}
}