<%@ taglib prefix="logic" uri="/WEB-INF/struts-logic.tld"%>
<%@ taglib prefix="tiles" uri="/WEB-INF/struts-tiles.tld"%>
<%@ taglib prefix="bean"  uri="/WEB-INF/struts-bean.tld"%>
<%@ taglib prefix="html"  uri="/WEB-INF/struts-html.tld"%>
<%@ taglib prefix="c"     uri="/WEB-INF/c.tld" %>


<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<html:html lang="true">
<link rel="stylesheet" href="<html:rewrite page='/css/ajk.css'/>">
	
<head>
  <title><c:out value="${appName}"/></title>
  <html:base/>
  <meta NAME = 'KEYWORDS' CONTENT = ''>
  <meta NAME = 'DESCRIPTION' CONTENT = ''>
  <meta NAME = 'AUTHOR' CONTENT = 'kappaj@gmail.com'>
  <meta NAME = 'ROBOTS' CONTENT = 'INDEX, FOLLOW'>
</head>


<style type="text/css">
  /* pushes the page to the full capacity of the viewing area */
  html {height:99%;}
  body {height:99%; width:99%; margin:0; padding:0;}
  /* prepares the background image to full capacity of the viewing area */
  #bg {position:fixed; top:0%; left:0; width:99%; height:100%;}
  /* places the content ontop of the background image */
  #content {position:fixed; z-index:1;  }
</style>

<body>

    <table border="0" width="100%" height="100%" class="TableMaster">
        <tr>
            <td align="center" height="15%" colspan="3">
                <tiles:insert attribute="headerTile"/>
            </td>
        </tr>
        <tr>
            <td align="center" height="40%" colspan="3">
                <tiles:insert attribute="bodyTile"/>
            </td>
        </tr>
        
        <tr valign="top">
            <td align="center" height="35%" width="35%">
                <tiles:insert attribute="packagesTile"/>
            </td>
            <td align="center" height="35%" width="45%">
                <tiles:insert attribute="aboutMeTile"/>
            </td>
            <td align="center" height="35%" width="20%" valign="top">
                <tiles:insert attribute="linksTile"/>
            </td>
        </tr>

    </table>
</body>
</html:html>
