package com.lin.thread;
import java.util.concurrent.Exchanger;

/**
 * 
 * 
 * desc:   两个线程中缺一阻塞
 * @author xuelin
 * @date   Nov 6, 2015
 */
public class ExchangeTest {
	static Exchanger<String> ex = new Exchanger<String>();
	
	public static void main(String[] args) {
		
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				try {
					String res = ex.exchange("I`m from t1");
					System.out.println("t1: " + res);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			public void run() {
				try {
					String res = ex.exchange("I`m from t2");
					System.out.println("t2: " + res);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		t1.start();
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		t2.start();
	}
}
