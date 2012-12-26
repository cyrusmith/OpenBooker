package ru.interosite.openbooker;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Adopted from Robolectric ShadowActivity
 * @author asutyagin
 */
final class MethodInvoker {
    
	private Method mMethod;
    private final Object mObj;
    
    MethodInvoker(Object obj) {
    	if(obj==null) {
    		throw new NullPointerException();
    	}
    	mObj = obj;
    }
    
    public MethodInvoker call(final String methodName, final Class ...argumentClasses) {
        try {
            mMethod = mObj.getClass().getDeclaredMethod(methodName, argumentClasses);
            mMethod.setAccessible(true);
            return this;
        } catch(NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    public Object withNothing() {
        return with();
    }

    public Object with(final Object ...parameters) {
        try {
            return mMethod.invoke(mObj, parameters);
        } catch(IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch(IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch(InvocationTargetException e) {
            throw new RuntimeException(e.getCause());
        }
    }
}