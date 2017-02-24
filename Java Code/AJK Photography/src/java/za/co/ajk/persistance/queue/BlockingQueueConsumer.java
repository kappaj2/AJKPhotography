/*
 * BlockingQueueConsumer.java
 *
 * Created on 21 January 2008, 10:35
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.persistance.queue;

import java.util.Date;
import java.util.concurrent.BlockingQueue;
import org.apache.log4j.Logger;
import za.co.ajk.persistance.daos.HitCounterDAO;
import za.co.ajk.persistance.transferobjects.HitCounter;

/**
 *
 * @author akapp
 */
public class BlockingQueueConsumer extends Thread {
    
    private BlockingQueue blockingQueue;
    private HitCounterDAO hitCounterDAO = new HitCounterDAO();
    private static Logger log = Logger.getLogger(BlockingQueueConsumer.class);
    
    public BlockingQueueConsumer(BlockingQueue blockingQueue) {
        
        this.blockingQueue = blockingQueue;
        
    }
    
    public void run() {
        
        log.debug("String to run queue consumer !!!!!!!!!!!!!!!!!!!!!1");
        
        while (true) {
            try {
                String dataReceived = (String)blockingQueue.take();
                
                System.out.println(blockingQueue.take());
                System.out.println("Consumer remaining cap " + blockingQueue.remainingCapacity());
                
                HitCounter hitCounter = new HitCounter();
                hitCounter.setHitAction(dataReceived);
                hitCounter.setHitLangCode("af");
                hitCounter.setHitTime(new Date());
                hitCounter.setHitTypeCode("fe");
                
                hitCounterDAO.daoSave(hitCounter);
                
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
            
        }
    }
}
