<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<tags:pageTemplate titulo="Cadastro de Usuarios">

<jsp:body>
		<div class="container middle">

		<a href="${s:mvcUrl('UC#form').build()}" ><fmt:message key="lista.usuario"/> </a>
			
		<h1>Lista de Usuarios</h1>

		<p>${sucesso}</p>	

		<table class="table table-bordered table-striped table-hover">
			<tr>
				<th>Nome</th>
				<th>Email</th>
				<th>Roles</th>
				<th></th>
				
			</tr>
			<c:forEach items="${usuarios }" var="usuario">
				<tr>
					<td>${usuario.nome }</td>
					<td>${usuario.email }</td>
					<td>${usuario.roles }</td>
					<td> <a href="${s:mvcUrl('UC#Role').arg(0, usuario.email).build()}" > + </a></td>
				</tr>
			</c:forEach>
		</table>
			
		</div>

</jsp:body>

</tags:pageTemplate>
