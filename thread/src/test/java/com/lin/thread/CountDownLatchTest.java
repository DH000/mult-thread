package com.lin.thread;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * desc:   若计数器减到0，则线程可以通过，否则你如阻塞
 * @author xuelin
 * @date   Nov 6, 2015
 */
public class CountDownLatchTest {
	
	final static int TIMES = 5;
	
	public static void main(String[] args) {
		
		final CountDownLatch begin = new CountDownLatch(1);
		final CountDownLatch end = new CountDownLatch(TIMES);
		
		ExecutorService executorService = Executors.newFixedThreadPool(TIMES);
		for(int i=0; i<TIMES; i++){
			Thread thread = new UserThread(begin, end);
			thread.setName("thread-" + i);
			executorService.execute(thread);
		}
		
		System.out.println("sleep...3s");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		// 线程开始
		begin.countDown();
		
		try {
			end.await();
			System.out.println("所有线程结束");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	static class UserThread extends Thread{
		final CountDownLatch begin;
		final CountDownLatch end; 
		
		public UserThread(CountDownLatch begin, CountDownLatch end){
			this.begin = begin;
			this.end = end;
		}

		@Override
		public void run() {
			try {
				// 先挂起线程
				begin.await();
				System.out.println(Thread.currentThread().getName());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}finally{
				end.countDown();
			}
		}
		
	}
}
