<%@page import="java.util.List"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="entities.Item"%>
<%@page import="action.ActionCategory"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.Const"%>
<%
	//Format a deux decimal
	DecimalFormat df = new DecimalFormat("####0.00");

	List<Item> items = (List<Item>)request.getAttribute("productList");
%>

<jsp:include page="<%=Const.PATH_HEAD_JSP%>"/>
<jsp:include page="<%=Const.PATH_MENU_JSP%>"/>
	<!-- /.container -->
    <!-- Page Content -->
    <div class="container">
<%
if(items != null) {
%>
		
<%
}
else {
%>
		
<%
}
%>
   

<jsp:include page="<%=Const.PATH_FOOTER_JSP%>"/>