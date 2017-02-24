<%@ page language="java"%>
<%@ taglib prefix="html"  uri="/WEB-INF/struts-html.tld" %>
<%@ taglib prefix="bean"  uri="/WEB-INF/struts-bean.tld" %> 
<%@ taglib prefix="logic" uri="/WEB-INF/struts-logic.tld" %>
<%@ taglib prefix="c"     uri="/WEB-INF/c.tld" %>



<SCRIPT LANGUAGE="JavaScript" TYPE="text/javascript">

function importXML(url)
{
	if (document.implementation && document.implementation.createDocument)
	{
		xmlDoc = document.implementation.createDocument("", "", null);
		xmlDoc.onload = populateDropDown;
	}
	else if (window.ActiveXObject)
	{
		xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
		xmlDoc.onreadystatechange = function () 
		{  if (xmlDoc.readyState == 4) 
			populateDropDown()
		};
 	}
	else
	{
		alert('Your browser can\'t handle this script');
		return;
	}

	//load the xml
	xmlDoc.load(url);
}


function populateDropDown()
{
	
	var browser = 'ie';
	var nameIndex = 0;
	var valueIndex = 1;
	if (document.implementation && document.implementation.createDocument)
	{
            //alert("FireFox !!!! in populate dropdown"  old value nameIndex = 1 and valueIndex = 3);
          browser = 'firefox';
	  var nameIndex = 0;
	  var valueIndex = 1;
	}
		
  var galleryCodeList = document.imageForm.galleryCode;

  //empty control
  for (var q=galleryCodeList.options.length; q >= 0; q--)
  {
      galleryCodeList.options[q]=null;
  } 

  //alert("Now add new list values");
  
	var x = xmlDoc.getElementsByTagName('item');
	for (j = 0; j < x[0].childNodes.length; j++)
	{
		if (x[0].childNodes[j].nodeType != 1) continue;
		var theData = document.createTextNode(x[0].childNodes[j].nodeName);
	}
	
	for (i = 0; i < x.length; i++)
	{
	  var name = '';
	  var value = '';
		for (j=0; j < x[i].childNodes.length; j++)
		{
			if (x[i].childNodes[j].nodeType != 1) continue;
			var theData = document.createTextNode(x[i].childNodes[j].firstChild.nodeValue);
			if (j==nameIndex) name = theData.nodeValue;
			if (j==valueIndex) value = theData.nodeValue;		
		}
            //alert("Adding name >"+name+"< and value >"+value+"< to the list");
            galleryCodeList.options[i] = new Option(name, value);
	}
}

</SCRIPT>

<%-- create a html form --%>
<html:form action="/admin/imageMaintenanceAction"  method="post" enctype="multipart/form-data">
    
    <%-- set the parameter for the dispatch action --%>
    <html:hidden property="exec" value="uploadAndSaveImage" />    
    <html:hidden property="typeHeading" value="TypeHeading"/>
    
    <%-- print out the form data --%>
    <table border="0"  class="TableMaintenance">
        <tr>
            <th colspan="2" class="th1"><bean:message key="title.page.image.add" /></th>
        </tr>
        
        <tr>
            <td colspan="2" class="tderror">
                <html:errors property="filesize"/>
            </td>
        </tr>
        <tr>
            <td colspan="2" class="tderror">
                <html:errors property="filetype"/>
            </td>
        </tr>
        
        <tr>
            <th class="tr1" align="right">Category</th>
            <td class="tr1" align="left">
                <html:select property="categoryCode" value="0" onchange="importXML('${contextPath}/admin/GalleryLookupAction.do?CategoryCode=' + this.value);" >	
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

                </html:select>
            </td>
        </tr>   
        <tr>
            <th class="tr1" align="right">Image Description</th>
            <td class="tr1" align="left"><html:textarea cols="80" rows="10" property="imageDescription" /></td>
        </tr> 
        <tr>
            <th class="tr1" align="right"><bean:message key="title.page.image.height" /></th>
            <td class="tr1" align="left"><c:out value="${ImagesHeight}"/></td>
        </tr>         
        <tr>
            <td class="tr1" align="right"><bean:message key="title.page.galleryimage.select"/></td>
            <td class="tr1" align="left"><html:file size="120" property="myFile" /></td>
        </tr> 
        <tr>
            <td colspan="2"><html:submit>Upload and save image</html:submit></td>
        </tr>
    </table>
    
    
</html:form>
