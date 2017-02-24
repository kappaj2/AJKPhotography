package za.co.ajk.persistance.transferobjects;

import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;
import za.co.ajk.persistance.hibernate.dao.DTO;


/** @author Hibernate CodeGenerator */
public class VisitorComment implements Serializable, DTO {
    
    /** identifier field */
    private Long commentId;
    
    /** persistent field */
    private String commentText;
    
    /** persistent field */
    private String published;
    
    /** nullable persistent field */
    private Date dateSubmitted;
    
    /** persistent field */
    private za.co.ajk.persistance.transferobjects.Visitor visitor;
    
    /** full constructor */
    public VisitorComment(String commentText, String published, Date dateSubmitted, za.co.ajk.persistance.transferobjects.Visitor visitor) {
        this.commentText = commentText;
        this.published = published;
        this.dateSubmitted = dateSubmitted;
        this.visitor = visitor;
    }
    
    /** default constructor */
    public VisitorComment() {
    }
    
    /** minimal constructor */
    public VisitorComment(String commentText, String published, za.co.ajk.persistance.transferobjects.Visitor visitor) {
        this.commentText = commentText;
        this.published = published;
        this.visitor = visitor;
    }
    
    /**
     * 		       auto_increment
     *
     */
    public Long getCommentId() {
        return this.commentId;
    }
    
    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }
    
    public String getCommentText() {
        return this.commentText;
    }
    
    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }
    
    public String getPublished() {
        return this.published;
    }
    
    public void setPublished(String published) {
        this.published = published;
    }
    
    public Date getDateSubmitted() {
        return this.dateSubmitted;
    }
    
    public void setDateSubmitted(Date dateSubmitted) {
        this.dateSubmitted = dateSubmitted;
    }
    
    public za.co.ajk.persistance.transferobjects.Visitor getVisitor() {
        return this.visitor;
    }
    
    public void setVisitor(za.co.ajk.persistance.transferobjects.Visitor visitor) {
        this.visitor = visitor;
    }
    
    public String toString() {
        return new ToStringBuilder(this)
        .append("commentId", getCommentId())
        .toString();
    }
    
}
