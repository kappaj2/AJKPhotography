/*
 * ThumbNail.java
 *
 * Created on 08 April 2008, 08:17
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package za.co.ajk.common.util;

/**
 *
 * @author akapp
 */
import com.sun.image.codec.jpeg.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import org.apache.log4j.Logger;
import za.co.ajk.common.exception.CustomException;
import za.co.ajk.common.exception.ErrorCode;
import za.co.ajk.common.exception.Layer;

/**
 * Thumbnail.java (requires Java 1.2+)
 * Load an image, scale it down and save it as a JPEG file.
 * @author Marco Schmidt
 *
 * args[0] - source file name
 * args[1] - target file name
 * args[2] - x pixels size
 * args[3] - y pixels size
 * args[4] - resolution
 */
public class ThumbNail {
    
    private String sourceFileName;
    private String targetFileName;
    private String maxSize = "190";
    private String resolution = "72";
    private String currentImageHeightString = "";
    private String currentImageWidthString = "";
    
    private static Logger log = Logger.getLogger(ThumbNail.class);
    
    public void setMaxSize(String maxSize){
        this.maxSize = maxSize;
    }
    
    public void setResolution(String resolution){
        this.resolution = resolution;
    }
    
    public void setSourceFileName(String sourceFileName){
        this.sourceFileName = sourceFileName;
    }
    
    public void setTargetFileName(String targetFileName){
        this.targetFileName = targetFileName;
    }
    
    public void setImageWidth(String currentWidth){
        this.currentImageWidthString = currentWidth;
    }
    
    public void setImageHeight(String currentHeight){
        this.currentImageHeightString = currentHeight;
    }
    
    public void resize() throws CustomException{
        try{
            
            int maxTargetSize = Integer.parseInt(maxSize);
            
            int targetWidth = 0;
            int targetHeight = 0;
            
            log.debug("getting resize source file for fileName >"+sourceFileName+"<");
            log.debug("CurrentImageWidthString is >"+currentImageWidthString+"<");
            log.debug("CurrentImageHeightString is >"+currentImageHeightString+"<");
            
            Image image = Toolkit.getDefaultToolkit().getImage(sourceFileName);
            MediaTracker mediaTracker = new MediaTracker(new Container());
            mediaTracker.addImage(image, 0);
            mediaTracker.waitForID(0);
            
            /*
             * get current image size
             */
            int currentImageWidth = Integer.parseInt(currentImageWidthString);
            int currentImageHeight = Integer.parseInt(currentImageHeightString);
            
            /*
             *  Don't mak adjustments if image smaller than target size...
             */
            if (maxTargetSize >= currentImageWidth && maxTargetSize >= currentImageHeight ){
                return;
            }
            
            /*
             * if the width is bigger - then size the width for 190 and the height in proportion
             * if the height is bigger - then size the height for 190 and the width in proportion
             */
            if (currentImageWidth >= currentImageHeight){
                targetWidth = maxTargetSize;
                targetHeight = maxTargetSize * currentImageHeight / currentImageWidth;
            }else{
                targetHeight = maxTargetSize;
                targetWidth = maxTargetSize * currentImageWidth / currentImageHeight;
            }
            
            int thumbWidth = targetWidth;
            int thumbHeight = targetHeight;
            
            double thumbRatio = (double)thumbWidth / (double)thumbHeight;
            
            int imageWidth = image.getWidth(null);
            int imageHeight = image.getHeight(null);
            
            double imageRatio = (double)imageWidth / (double)imageHeight;
            
            if (thumbRatio < imageRatio) {
                thumbHeight = (int)(thumbWidth / imageRatio);
            } else {
                thumbWidth = (int)(thumbHeight * imageRatio);
            }
            
            // draw original image to thumbnail image object and
            // scale it to the new size on-the-fly
            BufferedImage thumbImage = new BufferedImage(thumbWidth,
                    thumbHeight, BufferedImage.TYPE_INT_RGB);
            
            Graphics2D graphics2D = thumbImage.createGraphics();
            
            graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            
            graphics2D.drawImage(image, 0, 0, thumbWidth, thumbHeight, null);
            
            // save thumbnail image to OUTFILE
            BufferedOutputStream out = new BufferedOutputStream(new
                    FileOutputStream(targetFileName));
            
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
            JPEGEncodeParam param = encoder.
                    getDefaultJPEGEncodeParam(thumbImage);
            
            int quality = Integer.parseInt(resolution);
            
            quality = Math.max(0, Math.min(quality, 100));
            
            param.setQuality((float)quality / 100.0f, false);
            encoder.setJPEGEncodeParam(param);
            encoder.encode(thumbImage);
            
            out.close();
            
        }catch (Exception ex){
            ex.printStackTrace();
            throw new CustomException(ErrorCode.THUMBNAIL_ERROR, Layer.PERSISTENCE, "Error creating a thumbnail version of the image. "+ex.getMessage(), ex);
        }
    }
    
    public static void main(String[] args) throws Exception {
        args = new String[5];
        
        args[0] = "c://BackgroundImages/0006.jpg";
        args[1] = "c://BackgroundImages/thumb.jpg";
        args[2] = "190";
        args[3] = "127";
        args[4] = "72";
        
        if (args.length != 5) {
            System.err.println("Usage: java Thumbnail INFILE " +
                    "OUTFILE WIDTH HEIGHT QUALITY");
            System.exit(1);
        }
        // load image from INFILE
        Image image = Toolkit.getDefaultToolkit().getImage(args[0]);
        MediaTracker mediaTracker = new MediaTracker(new Container());
        mediaTracker.addImage(image, 0);
        mediaTracker.waitForID(0);
        
        // determine thumbnail size from WIDTH and HEIGHT
        int thumbWidth = Integer.parseInt(args[2]);
        int thumbHeight = Integer.parseInt(args[3]);
        
        double thumbRatio = (double)thumbWidth / (double)thumbHeight;
        
        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);
        
        double imageRatio = (double)imageWidth / (double)imageHeight;
        
        if (thumbRatio < imageRatio) {
            thumbHeight = (int)(thumbWidth / imageRatio);
        } else {
            thumbWidth = (int)(thumbHeight * imageRatio);
        }
        
        // draw original image to thumbnail image object and
        // scale it to the new size on-the-fly
        BufferedImage thumbImage = new BufferedImage(thumbWidth,
                thumbHeight, BufferedImage.TYPE_INT_RGB);
        
        Graphics2D graphics2D = thumbImage.createGraphics();
        
//        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
//                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        
        graphics2D.drawImage(image, 0, 0, thumbWidth, thumbHeight, null);
        
        // save thumbnail image to OUTFILE
        BufferedOutputStream out = new BufferedOutputStream(new
                FileOutputStream(args[1]));
        
        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
        JPEGEncodeParam param = encoder.
                getDefaultJPEGEncodeParam(thumbImage);
        
        int quality = Integer.parseInt(args[4]);
        
        quality = Math.max(0, Math.min(quality, 100));
        
        param.setQuality((float)quality / 100.0f, false);
        encoder.setJPEGEncodeParam(param);
        encoder.encode(thumbImage);
        
        out.close();
        
        System.out.println("Done.");
        System.exit(0);
    }
}
