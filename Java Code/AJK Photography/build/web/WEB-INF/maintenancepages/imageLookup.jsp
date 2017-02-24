<%@ page language="java"%>
<%@ taglib prefix="html"  uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean"  uri="/WEB-INF/struts-bean.tld" %> 

<%@ taglib prefix="logic" uri="/WEB-INF/struts-logic.tld" %>

<script>

  var req;
  var which;

  function retrieveURL(url) {
    if (window.XMLHttpRequest) { // Non-IE browsers
      req = new XMLHttpRequest();
      req.onreadystatechange = processStateChange;
      try {
        req.open("GET", url, true);
      } catch (e) {
        alert(e);
      }
      req.send(null);
    } else if (window.ActiveXObject) { // IE
      req = new ActiveXObject("Microsoft.XMLHTTP");
      if (req) {
        req.onreadystatechange = processStateChange;
        req.open("GET", url, true);
        req.send();
      }
    }
  }

  function processStateChange() {
    if (req.readyState == 4) { // Complete
      if (req.status == 200) { // OK response
        document.getElementById("characters").innerHTML = req.responseText;
      } else {
        alert("Problem: " + req.statusText);
      }
    }
  }

</script>
   
<%-- create a html form --%>
<html:form action="/admin/imageMaintenanceAction">
    
    <html:hidden property="typeHeading" value="TypeHeading"/>
    
    <%-- print out the form data --%>
    <table border="0" class="TableMaintenance">
        <tr>
            <th colspan="2" class="th1"><bean:message key="title.page.gallery.images.update" /></th>
        </tr>       
        
      
        <tr>
            <th class="tr1" align="right">Category</th>
            <td class="tr1" align="left">
                <html:select property="categoryCode" onchange="retrieveURL('${contextPath}/admin/CategoryLookupAction.do?CategoryCode=' + this.value);" >	
                    <html:options collection="categoryMap" 
                                  property="key" 
                                  labelProperty="value.categoryDescription"/>;
                </html:select>
            </td>
        </tr>
        <tr>
            <th class="tr1" align="right">Gallery</th>
            <td class="tr1" align="left">
                <html:select property="galleryCode">	
                    <html:options collection="galleryMap" 
                                  property="key" 
                                  labelProperty="value.galleryDescription"/>;
                </html:select>
            </td>
        </tr>        
        <tr>
            <td colspan="2"><html:submit>Retrieve Images</html:submit></td>
        </tr>
    </table>
    
    <%-- set the parameter for the dispatch action --%>
    <html:hidden property="exec" value="imageList" />
   
  
</html:form>
