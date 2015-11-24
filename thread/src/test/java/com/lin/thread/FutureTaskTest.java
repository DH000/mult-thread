package com.lin.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.junit.Test;

public class FutureTaskTest {
	
	@Test
	public void callBackTest() throws InterruptedException, ExecutionException {
		FutureTask<String> task = new FutureTask<String>(new Callable<String>() {
			public String call() throws Exception {
				
				Thread.sleep(1000);
				
				return "I`m callback.";
			}
		});
		
		// 开启线程
		new Thread(task).start();
		
		for (; !task.isDone();) {
		}
		
		System.out.println("线程终止，返回值：" + task.get());
	}
	
	@Test
	public void runnableTest() throws InterruptedException, ExecutionException {
		String result = "I`m result.";
		FutureTask<String> task = new FutureTask<String>(new Runnable() {
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, result);
		
		// 开启线程
		new Thread(task).start();
		
		for (; !task.isDone();) {
		}
		
		System.out.println("线程终止，返回值：" + task.get());
	}
	
	@Test
	public void executorCallbackTest() throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		Future<String> future = executorService.submit(new Callable<String>() {
			public String call() throws Exception {
				
				Thread.sleep(3000);
				
				return "I`m callback.";
			}
		});
		executorService.shutdown();
		
		System.out.println("线程终止，结果：" + future.get());
	}
	
	@Test
	public void executorRunnableTest() throws InterruptedException, ExecutionException {
		ExecutorService executorService = Executors.newCachedThreadPool();
		Future<?> future = executorService.submit(new Runnable() {
			public void run() {
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		executorService.shutdown();
		
		System.out.println("线程终止，结果：" + future.get());
	}
}
