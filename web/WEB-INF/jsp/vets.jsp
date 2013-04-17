<%@ include file="/WEB-INF/jsp/includes.jsp" %>
<%@ include file="/WEB-INF/jsp/header.jsp" %>

<!--
	Example using dhtmlx tag library
-->

<div id="layout" style="height:300px;"></div>

<dhtmlx:body name='initializeDHTMLX' imagePath='/imgs/' skin="dhx_skyblue">
    <dhtmlx:layout name='layout' id='layout'  pattern='1C' >
        <dhtmlx:layoutcell name='a' text='Veterinarians' i18n='false'>
            <dhtmlx:grid   name='dataGrid'>
                <dhtmlx:column  name='firstName' header='First Name' type='ro' align='left' sort='str' width='*' i18n='false' />
                <dhtmlx:column  name='lastName' header='Last Name' type='ro' align='left' sort='str' width='*' i18n='false' />
                <dhtmlx:column  name='specialtiesList' header='Specialities' type='ro' align='left' sort='str' width='*' i18n='false' />
            </dhtmlx:grid>
        </dhtmlx:layoutcell>
    </dhtmlx:layout>
</dhtmlx:body>


<script type="text/javascript">
    /**
     * Returns the list of the columns in the grid
     * @param grid
     */
    function getColumns(grid) {
        var columns = "", num = grid.getColumnsNum();
        for( i = 0; i < num; i++ ) {
            columns+=grid.getColumnId( i ) + (i < (num-1) ? "," : "");
        }
        return columns;
    }

    function initialize() {
        dhtmlx.skin = "dhx_skyblue";
        window.dhx_globalImgPath = '/imgs/';
        initializeDHTMLX();
        dataGrid.attachEvent("onXLS", function(obj,count){layout.cells('a').progressOn();});
        dataGrid.attachEvent("onXLE", function(obj,count){layout.cells('a').progressOff();});
        dataGrid.clearAll();
        dataGrid.loadXML( "/petclinic/vets/grid/tag?columns=" + getColumns( dataGrid ));
    }

    /* Below the initialization function using the DHTMLX API
    function initialize() {
        dhtmlx.skin = "dhx_skyblue";
        window.dhx_globalImgPath = '/imgs/';

        layout.cells('a').setText('Veterinarians');
        dataGrid = layout.cells('a').attachGrid( );

        dataGrid.setSkin('dhx_skyblue');
        dataGrid.setImagePath('/imgs/');
        dataGrid.setColumnIds('firstName,lastName,specialtiesList');
        dataGrid.setHeader('First Name,Last Name,Specialities');
        dataGrid.setColTypes('ro,ro,ro');
        dataGrid.setColAlign('left,left,left');
        dataGrid.setColSorting('str,str,str');
        dataGrid.setInitWidths('*,*,*');
        dataGrid.init();

        dataGrid.attachEvent("onXLS", function(obj,count){layout.cells('a').progressOn();});
        dataGrid.attachEvent("onXLE", function(obj,count){layout.cells('a').progressOff();});
        dataGrid.clearAll();
        dataGrid.loadXML( "/petclinic/vets/grid/tag?columns=" + getColumns( dataGrid ));
    }
    */

    /* Below the initialization function for docklet
        function initialize() {
            dhtmlx.skin = "dhx_skyblue";
            window.dhx_globalImgPath = '/imgs/';

            layout = new dhtmlXLayoutObject("dataGrid", "1C");
            layout.cells('a').setText("Veterinarians");

            dataGrid = new docklet( {
                container : layout.cells("a") ,
                widgetType : "grid",
                url  : "/petclinic/vets/grid",
                onXLS: function(){ layout.cells('a').progressOn();  },
                onXLE: function(){ layout.cells('a').progressOff(); }
            });
        }
    */
    dhtmlxEvent(window, 'load', initialize);
</script>
<%@ include file="/WEB-INF/jsp/footer.jsp" %>
