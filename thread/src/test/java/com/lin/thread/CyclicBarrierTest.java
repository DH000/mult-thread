package com.lin.thread;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

/**
 * 
 * desc:   所有线程到达出发，否则阻塞，若其中线程终端，则所有线程可立即通过
 * @author xuelin
 * @date   Nov 6, 2015
 */
public class CyclicBarrierTest {
	
	static CyclicBarrier barrier = new CyclicBarrier(3);
	
	public static void main(String[] args) {
		for(int i=0; i<6; i++){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Thread thread = new Thread(new Runnable() {
				public void run() {
					try {
						System.out.println("线程" + Thread.currentThread().getName() + "安全到达");
						barrier.await();
						System.out.println("起跑线程" + Thread.currentThread().getName());
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (BrokenBarrierException e) {
						e.printStackTrace();
					}
				}
			});
			thread.setName("thread-" + i);
			thread.start();
			
			if(1 == i){
				thread.interrupt();
			}
		}
	}
	
	@Test
	public void mainTest() throws InterruptedException, BrokenBarrierException{
		ExecutorService executorService = Executors.newCachedThreadPool();
		for(int i=0; i<6; i++){
			executorService.execute(new Runnable() {
				public void run() {
					try {
						barrier.await();
						System.out.println("线程....");
//						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (BrokenBarrierException e) {
						e.printStackTrace();
					}
				}
			});
		}
		executorService.shutdown();
		
		barrier.await();
		System.out.println("主线程没阻塞");
	}
}
