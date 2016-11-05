package org.dorkmaster.scanner.zeromq;

import org.zeromq.ZMQ;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by mjackson on 10/8/16.
 */
public class ZeroMqAdapter {

    private ExecutorService scanningService = newFixedThreadPoolWithQueueSize(5, 10);
    private ExecutorService otherServices = newFixedThreadPoolWithQueueSize(5, 10);

    private static ExecutorService newFixedThreadPoolWithQueueSize(int nThreads, int queueSize) {
        return new ThreadPoolExecutor(nThreads, nThreads,
                5000L, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(queueSize, true), new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public static void main(String[] args) {
        ZMQ.Context context = ZMQ.context(2);

        ZMQ.Socket responder = context.socket(ZMQ.REP);
        responder.bind("tcp://*:5555");

    }

    class Announce {
        public Announce() {

        }
    }
}
