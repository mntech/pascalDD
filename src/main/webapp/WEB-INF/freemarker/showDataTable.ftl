<#-- Generic macro to take a list of rows and display it as a table -->

<#macro showDataTable rows hasHeader=1>
  <table border="1" cellspacing="1">
    <#list rows as row>
      <tr>
	<#list row as item>
	  <#if row_index == 0 && hasHeader == 1>
	    <th>${item!"NULL"}</th>
	  <#else>
	    <td>
	      <#if item??>${item}<#else>NULL</#if>
	    </td>
	  </#if>
	</#list>
      </tr>
    </#list>
  </table>
</#macro>
