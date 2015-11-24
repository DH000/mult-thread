package com.lin.thread;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockTest {
	static Thread thread1;
	static Thread thread2;
	Lock lock = new ReentrantLock();
	Condition t1Con = lock.newCondition();
	Condition t2Con = lock.newCondition();
	static boolean tag;
	
	public void methodTest(){
		lock.lock();
		
		try{
			if(thread2.equals(Thread.currentThread())){
				t2Con.await();
			}
			Thread.sleep(1000);
			if(thread1.equals(Thread.currentThread())){
				t2Con.signal();
			}
			
			System.out.println(Thread.currentThread().getName());
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} finally{
			lock.unlock();
		}
	}
	
	public static void main(String[] args) {
		final LockTest lt = new LockTest();
		thread1 = new Thread( new Runnable() {
			public void run() {
				lt.methodTest();
			}
		});
		thread2 = new Thread(new Runnable() {
			public void run() {
				lt.methodTest();
			}
		});
		
		thread2.setName("线程2");
		thread1.setName("线程1");
		
		thread2.start();
		thread1.start();
	}
}
