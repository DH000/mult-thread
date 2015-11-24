package com.lin.thread;
public class SynTest {
	
	public static synchronized void method1(){
		System.out.println("method1");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static synchronized void method2(){
		System.out.println("method2");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				method1();
			}
		});
		Thread t2 = new Thread(new Runnable() {
			public void run() {
				method2();
			}
		});
		
		t1.start();
		t2.start();
	}
}
