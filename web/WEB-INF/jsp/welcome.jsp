<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<img src="<spring:url value="/images/pets.png" htmlEscape="true" />" align="right" style="position:relative;right:30px;">
<h2>Welcome</h2>
<ul>
  <li><a href="<spring:url value="/petclinic/owners/search" htmlEscape="true" />">Find owner</a></li>
  <li><a href="<spring:url value="/petclinic/vets" htmlEscape="true" />">Display all veterinarians</a></li>
  <li><a href="<spring:url value="http://dhtmlx-spring-link.mylaensys.com/live-demo/pet-clinic" htmlEscape="true" />">Tutorial</a></li>
</ul>

<p>&nbsp;</p>
<p>&nbsp;</p>

<div>
    <p>The <strong>dhtmlx-petclinic</strong> is the <a href="http://code.google.com/p/dhtmlx-spring-link/">DHTMLX Spring Link</a> version of the famous <strong>Pet Clinic</strong> sample included in the
        <a href="http://www.springsource.org">Spring Framework</a> distribution bundle, running on the <a href="http://code.google.com/appengine/">Google App Engine</a>.</p>
</div>
<p>&nbsp;</p>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
