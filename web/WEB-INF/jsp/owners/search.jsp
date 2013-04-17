<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>


<div id="searchBox" style="height:470px;"></div>
<script type="text/javascript">
    var layout,details,pets,popupWindow,dhxWins;

    /**
     * Deletes a owner
     * @param owner
     */
    function deleteOwner(owner) {
        if (confirm("Do you really want to delete this owner ?")) {
            dhtmlxAjax.post("/petclinic/owners/" + owner + "/delete", '', function(loader) {
                goTo("/petclinic/owners/search");
            });
        }
    }
    /**
     * Deletes a pet
     * @param owner
     */
    function deletePet(owner) {
        var pet = petsGrid.getWidget().getSelectedRowId();
        if( pet != null ) {
            if (confirm("Do you really want to delete this pet ?")) {
                dhtmlxAjax.post("/petclinic/owners/" + owner + "/pets/" + pet + "/delete", '', function(loader) {
                    petsGrid.load("/petclinic/owners/" + owner + "/pets/");
                    visitGrid.load("/petclinic/owners/" + owner + "/pets/" + pet + "/visits");
                });
            }
        } else {
            alert("Select a pet on the list");
        }
    }
    /**
     * Deletes a visit
     * @param owner
     */
    function deleteVisit(owner) {
        var pet = petsGrid.getWidget().getSelectedRowId();
        if( pet != null ) {
            var visit = visitGrid.getWidget().getSelectedRowId();
            if( visit != null ) {
                if (confirm("Do you really want to delete this visit ?")) {
                    dhtmlxAjax.post("/petclinic/owners/" + owner + "/pets/" + pet + "/visits/" + visit  +"/delete", '', function(loader) {
                        visitGrid.load("/petclinic/owners/" + owner + "/pets/" + pet + "/visits");
                    });
                }
            }
        }
    }
    /**
     * Open the pet dialog window
     * @param title cation of the window
     * @param owner
     * @param pet
     */
    function openPet(title,owner,pet) {
        var popupWindow = dhxWins.createWindow("pet",690,60,550,350);
        popupWindow.denyResize();
        popupWindow.button("minmax1").hide();
        popupWindow.button("park").hide();
        popupWindow.button("close").hide();
        popupWindow.setModal(true);
        popupWindow.setText(title);
        layout_form = new dhtmlXLayoutObject(popupWindow, "1C");
        layout_form.cells('a').hideHeader();
        ownerDetail = new docklet( {
            container : layout_form.cells('a') ,
            widgetType : "form",
            onXLS: function(){ layout_form.cells('a').progressOn();},
            onXLE: function(){ layout_form.cells('a').progressOff(); },
             init : function(form) { form.setItemLabel('message', '<div id="messagebar" style="height:50px">&nbsp;</div>'); },
            url : pet == undefined ? "/petclinic/owners/" + owner +"/pet" : "/petclinic/owners/" + owner +"/pet/" + pet,
            onButtonClick : function(id) {
                if (id == "button_upd") {
                     ownerDetail.getWidget().send("/petclinic/owners/" + owner + "/pet", function (loader,response) {
                         var  errorList = parseResponse(  loader , response );
                         if( !showMessage( ownerDetail.getWidget() , errorList ) ) {
                             setTimeout(function() {
                                dhxWins.window('pet').close();
                                petsGrid.load("/petclinic/owners/" + owner + "/pets/");
                             },1000);
                         }

                     });

                }
                if( "button_cancel" == id ) {
                    dhxWins.window('pet').close();
                }
            }
         });
        return popupWindow;
    }
    /**
    * Opens the visit dialog window
    * @param title
    * @param owner
    * @param pet
    * @param visit
    */
     function openVisit(title,owner,pet,visit) {
        var popupWindow = dhxWins.createWindow("visit",690,60,550,350);
        popupWindow.denyResize();
        popupWindow.button("minmax1").hide();
        popupWindow.button("park").hide();
        popupWindow.button("close").hide();
        popupWindow.setModal(true);
        popupWindow.setText(title);
        layout_form = new dhtmlXLayoutObject(popupWindow, "1C");
        layout_form.cells('a').hideHeader();
        visitDetail = new docklet( {
            container : layout_form.cells('a') ,
            widgetType : "form",
            onXLS: function(){ layout_form.cells('a').progressOn();},
            onXLE: function(){ layout_form.cells('a').progressOff(); },
             init : function(form) { form.setItemLabel('message', '<div id="messagebar" style="height:50px">&nbsp;</div>'); },
            url : visit == undefined ? "/petclinic/owners/" + owner +"/pets/" + pet + "/visit" :"/petclinic/owners/" + owner +"/pets/" + pet + "/visits/" + visit,
            onButtonClick : function(id) {
                if (id == "button_upd") {
                     visitDetail.getWidget().send("/petclinic/owners/" + owner + "/pets/" + pet + "/visit", function (loader,response) {
                         var  errorList = parseResponse(  loader , response );
                         if( !showMessage( visitDetail.getWidget() , errorList ) ) {
                             setTimeout(function() {
                                 dhxWins.window('visit').close();
                                 visitGrid.load("/petclinic/owners/" + owner + "/pets/" + pet + "/visits");
                              },1000);
                         }
                     });

                }
                if( "button_cancel" == id ) {
                    dhxWins.window('visit').close();
                }
            }
         });
        return popupWindow;
    }

    /**
     * Load the owner data grid
     */
    function loadOwnerGrid(text) {
        layout.cells('b').detachObject();
        dataGrid = new docklet( {
            container : layout.cells("b") ,
            widgetType : "grid",
            url  : "/petclinic/owner/search/grid?text=" + text,
            onXLS: function(){ layout.cells('b').progressOn();  },
            onXLE: function(){ layout.cells('b').progressOff(); },
            onRowDblClicked : function(id) {
                openOwner(id);
            }
        });
    }
    /**
     * Open the owner view
     * @param owner
     */
    function openOwner(owner) {
         /* Init the detail */
        $('<a style="padding-left: 5px" id="delete_owner" onclick=\"deleteOwner(' + owner + ')\">Delete Owner</a>').insertAfter( $('#add_owner') );
        layout.cells('a').collapse();
        layout.cells('b').detachObject();

        details = layout.cells('b').attachLayout("2E");
        details.cells('a').setText("Owner Information");
        details.cells('a').setHeight(260);
        details.cells('a').fixSize(true,true);
        pets = details.cells('b').attachLayout("2U");
        pets.cells('a').setText("Pets");
        var petbar = pets.cells('a').attachStatusBar();
        petbar.setText("<a onclick='openPet(\"Add New Pet\"," + owner + ");'>Add New Pet</a>&nbsp;<a onclick='deletePet(" + owner +")'>Delete Pet</a>");
        pets.cells('b').setText("Visits");
        var vistbar = pets.cells('b').attachStatusBar();
        vistbar.setText("<a onclick='deleteVisit(" + owner +")'>Delete Visit</a>");

        /* Create the form */
        ownerDetail = new docklet( {
            container : details.cells('a') ,
            widgetType : "form",
            onXLS: function(){ details.cells('a').progressOn();},
            onXLE: function(){ details.cells('a').progressOff(); },
            url : "/petclinic/owners/" + owner,
            onButtonClick : function(id) {
                if( "button_add" == id ) {
                    openPet("Add New Pet",owner);
                }

            }
        });

        petsGrid = new docklet( {
            container : pets.cells('a') ,
            widgetType : "grid",
            onXLS: function(){ pets.cells('a').progressOn();},
            onXLE: function(){ pets.cells('a').progressOff(); this.selectRow(0); },
            url : "/petclinic/owners/" + owner + "/pets/",
            init: function(grid) {
                grid.selectRow(0);
                var pet = grid.getSelectedRowId();
                pet = (pet == null ? "0" : pet);
                visitGrid = new docklet( {
                    container : pets.cells('b') ,
                    widgetType : "grid",
                    url : "/petclinic/owners/" + owner + "/pets/" + pet + "/visits"
                });

            },
            onRowSelect: function(id) {
                visitGrid.load("/petclinic/owners/" + owner + "/pets/" + id + "/visits");
            }
        });
    }
    /**
     * Initialization
     */
    function initialize() {
        dhxWins=new dhtmlXWindows();
        dhxWins.setSkin("dhx_skyblue");
        dhxWins.setImagePath("/imgs/");

        dhtmlx.skin = "dhx_skyblue";
        window.dhx_globalImgPath = '/imgs/';


        layout = new dhtmlXLayoutObject("searchBox", "2E");
        layout.attachEvent("onExpand",function(item) {
            $('#delete_owner').remove();
            layout.cells('a').setHeight(120);
            loadOwnerGrid( searchBox.getWidget().getItemValue("text") );
        });


        layout.cells('a').setText("Find Owners");
        layout.setCollapsedText("a", "Click on the arrow to return");
        layout.cells('a').setHeight(120);
        layout.cells('b').hideHeader();

         /* Init the search box */
        searchBox = new docklet( {
            container : layout.cells('a') ,
            widgetType : "form",
            url : "/petclinic/owner/search/form",
            onButtonClick : function(id) {
                loadOwnerGrid( searchBox.getWidget().getItemValue("text"));
            }
        });

    }
    dhtmlxEvent(window, 'load', initialize);

</script>

<br/>
<a id="add_owner" onclick="goTo('/petclinic/owners/new')">Add Owner</a>


<%@ include file="/WEB-INF/jsp/footer.jsp" %>
