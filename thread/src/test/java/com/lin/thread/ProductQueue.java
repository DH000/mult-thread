package com.lin.thread;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProductQueue<T> {
	
	private final T[] items;
	private final Lock lock = new ReentrantLock();
	private Condition notFull = lock.newCondition();
	private Condition notEmpty = lock.newCondition();
	
	//
	private int head, tail, count;
	
	@SuppressWarnings("unchecked")
	public ProductQueue(int maxSize) {
		items = (T[]) new Object[maxSize];
	}
	
	public ProductQueue() {
		this(10);
	}
	
	public void put(T t) throws InterruptedException {
		lock.lock();
		System.out.println("开始填充数据");
		try {
			while (count == getCapacity()) {
				notFull.await();
			}
			items[tail] = t;
			if (++tail == getCapacity()) {
				tail = 0;
			}
			++count;
			notEmpty.signalAll();
		} finally {
			lock.unlock();
		}
	}
	
	public T take() throws InterruptedException {
		lock.lock();
		System.out.println("开始获取数据");
		try {
			while (count == 0) {
				notEmpty.await();
			}
			T ret = items[head];
			items[head] = null;// GC
			//
			if (++head == getCapacity()) {
				head = 0;
			}
			--count;
			notFull.signalAll();
			return ret;
		} finally {
			lock.unlock();
		}
	}
	
	public int getCapacity() {
		return items.length;
	}
	
	public int size() {
		lock.lock();
		try {
			return count;
		} finally {
			lock.unlock();
		}
	}
	
	public static void main(String[] args) {
		final ProductQueue<Integer> pd = new ProductQueue<Integer>();
		
		Thread t1 = new Thread(new Runnable() {
			public void run() {
				try {
					System.out.println("填充数据...");
					pd.put(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		Thread t2 = new Thread(new Runnable() {
			public void run() {
				try {
					Integer i = pd.take();
					System.out.println("获取数据：" + i);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		Thread t3 = new Thread(new Runnable() {
			public void run() {
				try {
					Integer i = pd.take();
					System.out.println("t3获取数据：" + i);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		t2.start();
		t3.start();
		
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		
//		t1.start();
	}
}





