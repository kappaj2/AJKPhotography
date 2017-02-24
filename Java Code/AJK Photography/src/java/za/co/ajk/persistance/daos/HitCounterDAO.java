/*
 * HitCounterDAO.java
 *
 * Created on 21 January 2008, 11:34
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.persistance.daos;

import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import za.co.ajk.common.exception.CustomException;
import za.co.ajk.persistance.hibernate.dao.hibernate.AbstractHibernateDAO;
import za.co.ajk.persistance.queue.BlockingQueueConsumer;
import za.co.ajk.persistance.transferobjects.HitCounter;

/**
 *
 * @author akapp
 * This class actually doesn't talk to the HivernateDAO for save actions.
 * It only puts the string object on the BlockingQueue for processing.
 * The Consumer Class reads the data from the queue and talks to the HibernateDAO
 * This will prevent any DB space or load problems from affecting the front-end.
 */
public class HitCounterDAO extends AbstractHibernateDAO {
    
//    private static BlockingQueue<String> blockingQueue; // = new LinkedBlockingQueue<String>();
//    
//    /*
//     *  Start a blockingqueue consumer thread when this class is loaded the first time
//     */
//    static{
//        blockingQueue = new LinkedBlockingQueue<String>();
//        BlockingQueueConsumer consumer = new BlockingQueueConsumer(blockingQueue);
//        consumer.start();
//    }
    
    private static Logger log = Logger.getLogger(HitCounterDAO.class);
    
    
    public void save(String hitTypeCode, String hitDetail) throws CustomException{
        
        log.debug("Received this and putting it on the queue - hitTypeCode >"+hitTypeCode+"< and hitDetail >"+hitDetail+"<");
        try{
            
            HitCounter hitCounter = new HitCounter();
            hitCounter.setHitAction(hitDetail);
            hitCounter.setHitLangCode("af");
            hitCounter.setHitTime(new Date());
            hitCounter.setHitTypeCode(hitTypeCode);
            
            daoSave(hitCounter);
   
            
        }catch(Exception ie){
            ie.printStackTrace();
        }
    }
    
    
    /**
     * This emthod will save the data to the DB. It will be called from the Consumer class
     */
    public void daoSave(HitCounter hitCounter) throws HibernateException{
        log.debug("==============================  Doing save of HitCounter object to DB =================================");
        super.getSession();
        super.save(hitCounter);
    }
    
}
