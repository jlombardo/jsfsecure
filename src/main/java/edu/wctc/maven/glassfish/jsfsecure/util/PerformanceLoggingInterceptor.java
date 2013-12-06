package edu.wctc.maven.glassfish.jsfsecure.util;

import java.util.concurrent.ConcurrentHashMap;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import edu.umd.cs.findbugs.annotations.SuppressWarnings;

/**
 * This class is an implementation of a Spring <code>Interceptor</code> and
 * is used to process AOP arround advice for methods configured in Spring.
 * 
 * @author  Original version by Ben Oday, ben.oday@initekconsulting.com. 
 *          Modifications by Jim Lombardo
 * @version 1.00
 */
public class PerformanceLoggingInterceptor implements MethodInterceptor {
    
    /*
     * Note that @SuppressWarnings is only used by a source code analyzer
     * that I use caled "FindBugs". You don't need this unless you do to.
     */
    @SuppressWarnings("SE_TRANSIENT_FIELD_NOT_RESTORED")
    private transient final Logger LOG = 
            LoggerFactory.getLogger(PerformanceLoggingInterceptor.class);
    private static ConcurrentHashMap<String, MethodStats> methodStats =
            new ConcurrentHashMap<String, MethodStats>();
    private static long statLogFrequency = 1;
    private static long methodWarningThreshold = 5000; // ms
    private static final String WARN_MSG =
            "WARNING: SLOW -- ";
    private static final String PERF_MSG =
            "SPEED TEST: method: ";
    

    @Override
    public Object invoke(MethodInvocation method) throws Throwable {
        StopWatch sw = new StopWatch();
        sw.start();
        
        try {
            return method.proceed();
        } finally {
            sw.stop();
            String source = 
                    method.getMethod().getDeclaringClass().getSimpleName();
            source += "#" + method.getMethod().getName();
            updateStats(source, sw.getTotalTimeMillis());
        }
        
    }

    private void updateStats(String methodName, long elapsedTime) {
        String msg = "";
        MethodStats stats = methodStats.get(methodName);
        if (stats == null) {
            stats = new MethodStats(methodName);
            methodStats.put(methodName, stats);
        }
        stats.count++;
        stats.totalTime += elapsedTime;
        if (elapsedTime > stats.maxTime) {
            stats.maxTime = elapsedTime;
        }

        if (elapsedTime > methodWarningThreshold) {
            msg = "*** " + WARN_MSG + PERF_MSG;
        } else {
            msg = "*** " + PERF_MSG;
        }

        if (stats.count % statLogFrequency == 0) {
            long avgTime = stats.totalTime / stats.count;
            long runningAvg = 
                    (stats.totalTime - stats.lastTotalTime) / statLogFrequency;
            
            System.out.println(msg + methodName + "(), cnt = " 
                    + stats.count + ", lastTime = " + elapsedTime 
                    + ", avgTime = " + avgTime + ", runningAvg = " 
                    + runningAvg + ", maxTime = " + stats.maxTime);

            //reset the last total time
            stats.lastTotalTime = stats.totalTime;
        }
    }

    // Inner class for convenience -- maintains performance history
    private class MethodStats {

        public String methodName;
        public long count;
        public long totalTime;
        public long lastTotalTime;
        public long maxTime;

        public MethodStats(String methodName) {
            this.methodName = methodName;
        }
    }
}