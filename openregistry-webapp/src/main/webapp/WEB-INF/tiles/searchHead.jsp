<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript" src="<c:url value="/dojo/dojo.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/spring/Spring.js" />"></script>
<script type="text/javascript" src="<c:url value="/resources/spring/Spring-Dojo.js" />"></script>
<link href="dojo/resources/dojo.css" rel="stylesheet" />
<link href="dijit/themes/nihilo/nihilo.css" rel="stylesheet" />
<link href="css/or/or.forms.css" rel="stylesheet" />
<script type="text/javascript">
    dojo.require("dojo.parser");
    dojo.require("dijit.form.DateTextBox");
    dojo.require("dijit.form.ComboBox");
    dojo.require("dijit.form.Button");

    Spring.addDecoration(new Spring.ElementDecoration({elementId: "dateOfBirth", widgetType: "dijit.form.DateTextBox", widgetAttrs: {promptMessage : "Enter the person's birth date."}}));
    Spring.addDecoration(new Spring.ElementDecoration({elementId: "givenName", widgetType: "dijit.form.TextBox", widgetAttrs: {promptMessage : "Enter all or part of the family name."}}));
    Spring.addDecoration(new Spring.ElementDecoration({elementId: "familyName", widgetType: "dijit.form.TextBox", widgetAttrs: {promptMessage : "Enter all or part of the given name."}}));
    Spring.addDecoration(new Spring.ElementDecoration({elementId: "identifierType", widgetType: "dijit.form.ComboBox", widgetAttrs: {promptMessage : "Enter all or part of the family name."}}));
    Spring.addDecoration(new Spring.ElementDecoration({elementId: "identifierValue", widgetType: "dijit.form.TextBox", widgetAttrs: {promptMessage : "Enter the value of the identifier."}}));
    Spring.addDecoration(new Spring.ElementDecoration({elementId: "submitButton", widgetType: "dijit.form.Button"}));
    Spring.addDecoration(new Spring.AjaxEventDecoration({elementId: "submitButton", event: "onclick",formId: "orForm", params: {fragments: "searchResults"}}));

    function getContainer(node) {
        var body = dojo.body();
        while(node && node != body && !dojo.hasClass(node, "container")) {
            node = node.parentNode;
        }
        if(dojo.hasClass(node, "container")) {
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

function createFadeOutEffect() {
    var fadeOut = dojo.fadeOut({node: "status",duration: 1000});
    fadeOut.play();
}
</script>


<style type="text/css">
  .container {
margin-top: 10px;
color: #292929;
width: 300px;
border: 1px solid #BABABA;
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
width: 98%;
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
