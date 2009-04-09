<%@ page session="false" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
    <head>
        <title><spring:message code="delete.person.title" /></title>
        <script type="text/javascript" src="dojo/dojo.js"></script>
        <link href="dijit/themes/nihilo/nihilo.css" rel="stylesheet" />
        <link href="dojo/resources/dojo.css" rel="stylesheet" />
        <link href="css/or/or.forms.css" rel="stylesheet" />

        <script type="text/javascript">
  //Note that you do not have to dojo.require anything
  //in order to use the dojo.anum function, as it is included
  //in the base.

  dojo.require("dojo.parser");
  dojo.require("dijit.form.DateTextBox");
  dojo.require("dijit.form.ComboBox");
  dojo.require("dijit.form.Button");


  function addElementToDojo(id, value) {
      var obj = dojo.byId(id);
      obj.setAttribute("dojoType", value);
      dojo.parser.parse(obj.parent);
  }

    dojo.addOnLoad( function() {
        addElementToDojo("dateOfBirth","dijit.form.DateTextBox");
        addElementToDojo("givenName", "dijit.form.TextBox");
        addElementToDojo("familyName", "dijit.form.TextBox");
        addElementToDojo("identifierType", "dijit.form.ComboBox");
        addElementToDojo("identifierValue", "dijit.form.TextBox");
//        addElementToDojo("submitButton", "dijit.form.Button");
    });


  function getContainer(node) {
    var body = dojo.body();
    while(node && node != body
          && !dojo.hasClass(node, "container")) {
      node = node.parentNode;
    }
    if(dojo.hasClass(node, "container")){
      return node;
    }
    return null;
  }

  dojo.addOnLoad(function() {
    //Do a query for the input nodes
    dojo.query(".container input[type=text]",
      dojo.byId("orForm"))
    .onfocus(function(evt){
      //Make the background grey when an input gets focus
        dojo.anim(getContainer(evt.target),{backgroundColor: "#ddd"});
      })
    .onblur(function(evt){
      //Make the background white when an input loses focus
        dojo.anim(getContainer(evt.target), {backgroundColor: "#fff"});
      })
    .forEach(function(input){
      //Record the initial value for the input
      input._initialValue = input.value;
    })
    .onkeyup(function(evt){
    //When the user presses a key, check the input
    //value against its initial value. If they are
    //different, add the class 'changed' to the input.
      var input = evt.target;
      if(input.value == input._initialValue) {
        dojo.removeClass(input, "changed");
      } else {
        dojo.addClass(input, "changed");
      }
    });
  });

    var fadeOut = dojo.fadeOut({node: "status",duration: 1000});

    function fadeOutAnimation() {
      fadeOut.play();
  }

    setTimeout("fadeOutAnimation()", 5000);

</script>

        <style type="text/css">
  .container {
    margin-top: 10px;
    color: #292929;
    width: 300px;
    border: 1px solid #BABABA;
  /*  background-color: #ddd;*/
    padding: 10px;
    margin-left: 10px;
    margin-bottom: 1em;
    -o-border-radius: 10px;
    -moz-border-radius: 12px;
    -webkit-border-radius: 10px;
    -webkit-box-shadow: 0px 3px 7px #adadad;
    border-radius: 10px;
    -moz-box-sizing: border-box;
    -opera-sizing: border-box;
    -webkit-box-sizing: border-box;
    -khtml-box-sizing: border-box;
    box-sizing: border-box;
    overflow: hidden;
  }

  .box {
    margin-top: 10px;
    color: #292929;
    width: 300px;
    border: 1px solid #BABABA;
    background-color: #ddd;
    padding-left: 10px;
    padding-right: 10px;
    margin-left: 10px;
    margin-bottom: 1em;
    -o-border-radius: 10px;
    -moz-border-radius: 12px;
    -webkit-border-radius: 10px;
    -webkit-box-shadow: 0px 3px 7px #adadad;
    border-radius: 10px;
    -moz-box-sizing: border-box;
    -opera-sizing: border-box;
    -webkit-box-sizing: border-box;
    -khtml-box-sizing: border-box;
    box-sizing: border-box;
    overflow: hidden;
  }

  .changed {
    background-color: yellow;
  }
        </style>
    </head>
    <body class="nihilo">
        <div id="header"><h1>OpenRegistry</h1><h2><spring:message code="delete.person.title" /></h2></div>
        <div id="content">
            <c:if test="${errorMessage != null}"><p id="status" class="box">${errorMessage}</p><script type="text/javascript">window.setTimeout("fadeOutAnimation()", 5000);</script> </c:if>
            <c:if test="${success != null}"><div id="status" class="box"><p><strong><spring:message code="deleteSuccess" /></strong></p></div></c:if>

            <div>
               <h2><spring:message code="delete.person.headers.find" /></h2>
               <form:form commandName="searchCriteria" method="POST" id="orForm">
                   <form:errors path="*" element="p" id="globalErrors" />
                   <ul>
                       <li class="container leftHalf">
                           <form:label path="givenName"><spring:message code="person.biodem.names.label" /></form:label>
                           <span>
                           <form:input path="givenName" cssErrorClass="formerror" />
                           <form:label path="givenName"><spring:message code="person.biodem.names.given.label" /></form:label>
                           </span>

                           <span>
                           <form:input path="familyName" cssErrorClass="formerror" />
                            <form:label path="familyName"><spring:message code="person.diodem.names.family.label" /></form:label>
                           </span>
                       </li>
                       <li class="container rightHalf">
                           <form:label path="dateOfBirth"><spring:message code="person.biodem.dateOfBirth.label" /></form:label>
                          <span>
                           <form:input path="dateOfBirth" cssErrorClass="formerror" />
                          </span>
                       </li>
                       <li class="container">
                           <form:label path="identifierType" for="identifiertype">Identifier</form:label>
                           <span>
                           <form:select path="identifierType" items="${identifierTypes}" itemLabel="name" itemValue="name" />
                           <form:input path="identifierValue" cssErrorClass="formerror" />
                          </span>
                       </li>
                       <li><input type="submit" value="Search -&gt;" name="_eventId_submit" id="submitButton" /></li>
                   </ul>
               </form:form>
            </div>
        </div>
        <div class="footer">
            <p><spring:message code="footer.copyright.text" /></p>
        </div>
    </body>
</html>