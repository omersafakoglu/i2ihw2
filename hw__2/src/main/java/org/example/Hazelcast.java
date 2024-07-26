package org.example;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

import java.util.concurrent.TimeUnit;

public class Hazelcast {
    public static void main(String[] args) {
        int firstCount =20000;
        int secondCount = 100000;

        InsertSelect(firstCount);
        InsertSelect(secondCount);
    }
    public static void  InsertSelect(int count){
        int countValue = count;

        HazelcastInstance hzInstance = com.hazelcast.core.Hazelcast.newHazelcastInstance();

        IMap<String, Integer> map = hzInstance.getMap("randomNumbers2");
        long startTime = System.nanoTime();
        for (int i = 1; i <= countValue; i++) {
            map.put("Number" + i, (int) (Math.random() * 1000));
        }
        long endTime = System.nanoTime();
        long insertionTime = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        System.out.println("Hazelcast "+ countValue+ " sayı eklemesi süresi: " + insertionTime + " ms");

        startTime = System.nanoTime();
        for (int i = 0; i < countValue; i++) {
            map.get("Number" + (int) (Math.random() * 1000));
        }
        endTime = System.nanoTime();
        long retrievalTime = TimeUnit.NANOSECONDS.toMillis(endTime - startTime);
        System.out.println("Hazelcast "+ countValue +" sayı seçme süresi: " + retrievalTime + " ms");

        hzInstance.shutdown();
    }
}