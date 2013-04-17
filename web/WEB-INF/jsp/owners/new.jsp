<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>


<div id="ownerForm" style="height:470px;"></div>
<script type="text/javascript">

    function initialize() {
        dhtmlx.skin = "dhx_skyblue";
        window.dhx_globalImgPath = '/imgs/';

        layout = new dhtmlXLayoutObject("ownerForm", "1C");
        layout.cells('a').setText("New Owner");
        var form_data = [
            { type: "hidden", label: "id" },
            { type: "label", name:"message" , label:"&nbsp;" ,labelWidth:200 , list:[
                { type: "input", name:"firstName", bind:"firstName", width:150, label:"First Name:" ,labelWidth:"250",position:"label-top", value:"" , validate:"NotEmpty"},
                { type:"input", name:"address", bind:"address", width:150, label:"Address:", position:"label-top", value:"", validate:"NotEmpty"},
                { type:"input", name:"telephone", bind:"telephone", width:150, label:"Telephone:", position:"label-top", value:"", validate:"NotEmpty"},
                { type:"newcolumn" },
                { type:"input", name:"lastName", bind:"lastName", width:150, label:"Last Name:", position:"label-top", value:"", validate:"NotEmpty"},
                { type:"input", name:"city", bind:"city", width:"150", label:"City:", position:"label-top", value:"", validate:"NotEmpty"}
                ]},
            { type: "label", name:"message" , label:"&nbsp;" ,labelWidth:200 , list:[
                { type:"button", name:"button_upd", value:"Create"},
                { type:"newcolumn" },
                { type:"button", name:"button_canel", value:"Cancel" }
            ]}
        ];
        ownerForm = layout.cells("a").attachForm(form_data);

        ownerForm.setItemLabel('message', '<div id="messagebar" style="height:50px">&nbsp;</div>');
        ownerForm.attachEvent("onButtonClick", function(id) {
            if (id == "button_upd") {
                 ownerForm.send("/petclinic/owners/" , function (loader,response) {
                     var  errorList = parseResponse(  loader , response );
                     if( !showMessage( ownerForm , errorList ) ) {
                        setTimeout(goTo("/petclinic/owners/search"),4000);
                     }
                 });
            }
            if(id == "button_canel") {
                goTo("/petclinic/owners/search");
            }
        });

    }
    dhtmlxEvent(window, 'load', initialize);
    /* Below the initialization function for docklet

    function initialize() {
        dhtmlx.skin = "dhx_skyblue";
        window.dhx_globalImgPath = '/imgs/';


        layout = new dhtmlXLayoutObject("ownerForm", "1C");
        layout.cells('a').setText("New Owner");

        ownerForm = new docklet( {
            container : layout.cells('a') ,
            widgetType : "form",
            onXLS: function(){ layout.cells('a').progressOn();},
            onXLE: function(){ layout.cells('a').progressOff(); },
            init : function(form) { form.setItemLabel('message', '<div id="messagebar" style="height:50px">&nbsp;</div>'); },
            url : "/petclinic/owners/form",
            onButtonClick : function(id) {
                if (id == "button_upd") {
                     ownerForm.getWidget().send("/petclinic/owners/" , function (loader,response) {
                         var  errorList = parseResponse(  loader , response );
                         if( !showMessage( ownerForm.getWidget() , errorList ) ) {
                            setTimeout(goTo("/petclinic/owners/search"),4000);
                         }
                     });
                }
                if(id == "button_canel") {
                    goTo("/petclinic/owners/search");
                }
            }
        });

     }

     */


</script>

<br/>
<a onclick="goTo('/petclinic/owners/search')">Find Owner</a>

<%@ include file="/WEB-INF/jsp/footer.jsp" %>
