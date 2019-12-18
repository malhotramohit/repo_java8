package com.gs.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ClassesTest1 {
	public static void main(String[] args) {

		Pen pen = new Pen(1, "Montex");

		Class penObjectClassObj = pen.getClass();

		checkingClassAttr(penObjectClassObj);

		checkingConstructorAttr(penObjectClassObj);

		checkingFieldAttr(pen, penObjectClassObj);

		checkingMethodAttr(pen, penObjectClassObj);

		checkingGetterAndSetters(penObjectClassObj);
		
	}

	private static void checkingGetterAndSetters(Class penObjectClassObj) {
		Method[] methods = penObjectClassObj.getMethods();

		for (Method method : methods) {
			if (isGetter(method))
				System.out.println("getter: " + method);
			if (isSetter(method))
				System.out.println("setter: " + method);
		}
	}

	public static boolean isGetter(Method method) {
		if (!method.getName().startsWith("get"))
			return false;
		if (method.getParameterTypes().length != 0)
			return false;
		if (void.class.equals(method.getReturnType()))
			return false;
		return true;
	}

	public static boolean isSetter(Method method) {
		if (!method.getName().startsWith("set"))
			return false;
		if (method.getParameterTypes().length != 1)
			return false;
		return true;
	}

	private static void checkingMethodAttr(Pen pen, Class penObjectClassObj) {
		Method[] methods = penObjectClassObj.getMethods();

		for (int i = 0; i < methods.length; i++) {
			System.out.println(methods[i]);
			System.out.println(methods[i].getReturnType());
		}

		Method method = null;
		try {
			method = penObjectClassObj.getMethod("checkPrice", null);
			System.out.println(method);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int res;
		try {
			res = (int) method.invoke(pen, null);
			System.out.println(res);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void checkingFieldAttr(Pen pen, Class penObjectClassObj) {
		Field[] fields = penObjectClassObj.getFields();

		for (int i = 0; i < fields.length; i++) {
			System.out.println(fields[i]);
			System.out.println(fields[i].getName());
			System.out.println(fields[i].getType());
		}

		Field field = fields[0];

		try {
			field.set(pen, 3434);
			System.out.println(pen);
			Field field2 = penObjectClassObj.getField("numOfColor");
			field2.set(null, 9);
			System.out.println(Pen.numOfColor);
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void checkingConstructorAttr(Class penObjectClassObj) {
		Constructor[] constructors = penObjectClassObj.getConstructors();
		for (int i = 0; i < constructors.length; i++) {
			System.out.println("constructors " + i + " :: " + constructors[i]);
		}

		try {
			Constructor constructor = penObjectClassObj.getConstructor(new Class[] { String.class });
			System.out.println("constructor with specific arg " + constructor);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Class[] parameterTypes = constructors[0].getParameterTypes();
		for (int i = 0; i < parameterTypes.length; i++) {
			System.out.println(parameterTypes[i]);
		}

		Constructor constructor2 = constructors[0];
		try {
			Pen pen2 = (Pen) constructor2.newInstance(23l, "Camel");
			System.out.println(pen2);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void checkingClassAttr(Class penObjectClassObj) {
		String className = penObjectClassObj.getName();
		System.out.println("className ::");
		System.out.println(className);

		String onlyClassName = penObjectClassObj.getSimpleName();
		System.out.println("onlyClassName :: ");
		System.out.println(onlyClassName);

		int modifiers = penObjectClassObj.getModifiers();
		System.out.println(modifiers);
		if (Modifier.isPublic(modifiers)) {
			System.out.println("public");
		} else {
			System.out.println("non - public");
		}

		Package package1 = penObjectClassObj.getPackage();
		System.out.println("package name :: " + package1.getName());

		Class superclass = penObjectClassObj.getSuperclass();
		System.out.println("super class name :: " + superclass);

		Class[] interfaces = penObjectClassObj.getInterfaces();
		for (int i = 0; i < interfaces.length; i++) {
			System.out.println("iterface " + i + " :: " + interfaces[i].getCanonicalName());
		}
	}
}
