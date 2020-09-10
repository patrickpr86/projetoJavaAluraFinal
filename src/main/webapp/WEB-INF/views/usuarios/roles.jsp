<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<tags:pageTemplate titulo="Cadastro de Usuarios">

<jsp:body>
		<div class="container middle">

			
			
			<form:form action="${s:mvcUrl('UC#alteraRole').build()}" method="post"  commandName="usuario">
			
				<h1>Cadastro de Permissões para ${usuario.nome} </h1>		
					  
				 <form:hidden path="email"/>
				 
				 <div>
					<form:checkboxes style="padding : 5px" path="roles" items="${roles}"/>
				</div>
				
				<button type="submit" class="btn btn-primary">Atualizar</button>
				
				
				
			</form:form>
			
		</div>

</jsp:body>

</tags:pageTemplate>
