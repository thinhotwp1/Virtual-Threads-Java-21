package ldt.example;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class VirtualThreadLoggingDemo {
    public static void main(String[] args) {
        int taskCount = 200; // Đặt số lượng tác vụ nhỏ để dễ xem log

        System.out.println("=== Running with Virtual Threads ===");
        runWithVirtualThreads(taskCount);

        System.out.println("\n=== Running with Platform Threads ===");
        runWithPlatformThreads(taskCount);
    }

    private static void runWithVirtualThreads(int taskCount) {
        Instant start = Instant.now();
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < taskCount; i++) {
                int taskId = i;
                executor.submit(() -> {
                    String threadName = Thread.currentThread().toString();
//                    System.out.printf("Task %d is running on %s%n", taskId, threadName);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.printf("Task %d completed on %s%n", taskId, threadName);
                });
            }
        }
        Instant end = Instant.now();
        System.out.println("Virtual Threads Execution Time: " + Duration.between(start, end).toMillis() + " ms");
    }

    private static void runWithPlatformThreads(int taskCount) {
        Instant start = Instant.now();
        try (ExecutorService executor = Executors.newFixedThreadPool(10)) { // Giới hạn 10 threads
            for (int i = 0; i < taskCount; i++) {
                int taskId = i;
                executor.submit(() -> {
                    String threadName = Thread.currentThread().toString();
//                    System.out.printf("Task %d is running on %s%n", taskId, threadName);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.printf("Task %d completed on %s%n", taskId, threadName);
                });
            }
        }
        Instant end = Instant.now();
        System.out.println("Platform Threads Execution Time: " + Duration.between(start, end).toMillis() + " ms");
    }
}
