<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<link rel="stylesheet" href="${contextPath}/jsp-scc-styles/project-info.css">
<div class="container">
  <p class="stat__size-width">Project full id: ${projectFullId}</p>
  <p class="stat__size-height">Group id: ${groupId}</p>
  <p class="stat__orientation">Artifact id: ${artifactId}</p>
  <p class="stat__medium">Version: ${version}</p>
</div>