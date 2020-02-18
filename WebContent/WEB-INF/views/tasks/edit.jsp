<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${task != null}">
		        <h2>Edit task id: ${task.id}</h2>
		
		        <form method="POST" action="${pageContext.request.contextPath}/update">
		            <c:import url="_form.jsp" />
		        </form>
		
		        <p><a href="${pageContext.request.contextPath}/index">Back</a></p>
		        <p><a href="#" onclick="confirmDestroy();">Discard this task</a></p>
		        <form method="POST" action="${pageContext.request.contextPath}/destroy">
		            <input type="hidden" name="_token" value="${_token}" />
		        </form>
		        <script>
		        function confirmDestroy() {
		            if(confirm("Do you really want to discard this task?")) {
		                document.forms[1].submit();
		            }
		        }
		        </script>
            </c:when>
            <c:otherwise>
                <h2>No Data Found</h2>
           </c:otherwise>
        </c:choose>

    </c:param>
</c:import>