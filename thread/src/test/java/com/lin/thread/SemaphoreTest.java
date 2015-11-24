package com.lin.thread;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 
 * desc:   若容器未满时，线程可以进入，若容器已满，则线程进入阻塞
 * @author xuelin
 * @date   Nov 6, 2015
 */
public class SemaphoreTest {
	
	static Semaphore semaphore = new Semaphore(5);
	
	public static void method(){
		try {
			semaphore.acquire();
			System.out.println(Thread.currentThread().getName());
			
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			semaphore.release();
		}
	}
	
	public static void main(String[] args) {
		ExecutorService executorService = Executors.newScheduledThreadPool(10);
		for(int i=0; i<10; i++){
			Thread thread = new Thread(new Runnable() {
				public void run() {
					method();
				}
			});
			
			thread.setName("thread-" + i);
			executorService.execute(thread);
		}
	}
}









