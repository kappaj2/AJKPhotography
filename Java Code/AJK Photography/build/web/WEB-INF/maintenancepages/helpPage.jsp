<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%--
The taglib directive below imports the JSTL library. If you uncomment it,
you must also add the JSTL library to the project. The Add Library... action
on Libraries node in Projects view can be used to add the JSTL 1.1 library.
--%>
<%--
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> 
--%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>

    <h1>JSP Page</h1>
    
    <table border=1">
        <tr>
            <th>
                Help page
            </th> 
        </tr>
        <td>
           Setup and configuration.<br>
           The system has some configuration that needs to be done before you load images. These are:
            <ul>
                <li>Configure database to hold information</li>
                <li>Deploy application to application server</li>
                <li>Configure administration username/password</li>
                <li>Configure directories for holding images</li>
                <li>Configure images sizes</li>
            </ul>
            The database must first be created. This is MySQL with no real dependancy upon version. I have tested with version 4.xx right<br>
            up to version 5.xx and experienced no problems.
        </td>
    </table>
    
    </body>
</html>
