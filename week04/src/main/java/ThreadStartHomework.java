import java.util.Random;
import java.util.concurrent.*;

/**
 * 本周作业：（必做）思考有多少种方式，在main函数启动一个新线程或线程池，
 * 异步运行一个方法，拿到这个方法的返回值后，退出主线程？
 * 写出你的方法，越多越好，提交到github。
 * <p>
 * 一个简单的代码参考：
 */
public class ThreadStartHomework {

    public static void main(String[] args) throws Exception {

        long start = System.currentTimeMillis();

        method01();

        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    int res;

    /**
     * 方法1： 使用Callable
     * Callalble接口支持返回执行结果，需要调用FutureTask.get()得到，
     * 此方法会阻塞主进程的继续往下执行，如果不调用不会阻塞。
     * <p>
     * 和Runnable 的区别： Runnable无返回值，Callable可以返回执行结果，是个泛型，和Future、FutureTask配合可以用来获取异步执行的结果
     * Callable接口的call()方法允许抛出异常；Runnable的run()方法异常只能在内部消化，不能往上继续抛
     *
     * @throws Exception
     */
    public static void method01() throws Exception {
        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        Callable<Integer> callable = new Callable<Integer>() {
            public Integer call() throws Exception {
                return sum();
            }
        };
        FutureTask<Integer> future = new FutureTask<>(callable);
        new Thread(future).start();
        int result = callable.call();
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    /**
     * 方法2： 使用countDownLatch
     */
    public void method02() throws InterruptedException {
        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(() -> {
            try {
                res = sum();
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        countDownLatch.await();
        System.out.println("异步计算结果为：" + res);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    /**
     * 方法3： 使用 join, join一个线程，等结果返回后 再继续主线程
     */

    public void method03() throws InterruptedException {
        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        Thread thread = new Thread(() -> {
            try {
                res = sum();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.join();
        System.out.println("异步计算结果为：" + res);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    /**
     * 方法4： 使用循环 等线程返回结果后再继续
     */
    public void method04() throws InterruptedException {
        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        new Thread(() -> {
            try {
                res = sum();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        while (res == 0) {
            Thread.sleep(500);
            System.out.println("-----------结果未返回，继续等待-----------");
        }
        System.out.println("异步计算结果为：" + res);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    /**
     * 方法5： wait 和 notify
     */
    public void method05() throws InterruptedException {
        long start = System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        Object lock = new Object();
        new Thread(() -> {
            try {
                res = sum();
                synchronized (lock) {
                    lock.notify();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        synchronized (lock) {
            lock.wait();
        }
        System.out.println("异步计算结果为：" + res);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    /**
     * 方法6： 使用 CyclicBarrier , 让一组线程等待至某个状态之后再全部同时执行。
     * 适用场景：可以用于多线程计算数据，最后合并计算结果的场景。
     * <p>
     * CyclicBarrier 和 CountDownLatch 的区别：
     * CountDownLatch 是一次性的，CyclicBarrier 是可循环利用的
     * CountDownLatch 参与的线程的职责是不一样的，有的在倒计时，有的在等待倒计时结束。
     * CyclicBarrier 参与的线程职责是一样的。
     */
    public void method06() throws BrokenBarrierException, InterruptedException {

        long start = System.currentTimeMillis();
        CyclicBarrier barrier = new CyclicBarrier(2);
        new Thread(() -> {
            try {
                res = sum();
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }).start();
        barrier.await();
        System.out.println("异步计算结果为：" + res);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }


    /**
     * 方法7： 使用 Future
     */
    public void method07() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<Integer> submit = executorService.submit(() -> sum());
        int result = submit.get();
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }


    /**
     * 方法8：使用阻塞队列
     *
     * @return
     * @throws InterruptedException
     */
    public void method08() throws InterruptedException {

        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(1);

        long start = System.currentTimeMillis();
        new Thread(() -> {
            try {
                arrayBlockingQueue.add(sum());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        //阻塞队列阻塞 等待获取到结果.
        int result = (int) arrayBlockingQueue.take();
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    private static int sum() throws InterruptedException {
        Thread.sleep(new Random().nextInt(3000));
//        Thread.sleep(3000);
        return fibo(36);
    }

    private static int fibo(int a) {
        if (a < 2) {
            return 1;
        }
        return fibo(a - 1) + fibo(a - 2);
    }
}
