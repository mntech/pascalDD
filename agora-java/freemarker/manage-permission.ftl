<!DOCTYPE html>
<html>
<head>
	<title>Agora - Welcome</title>
	
	<link rel="shortcut icon" href="agora_icon.ico"/>
	<script type="text/javascript" src="jq.js"></script>
	<script type="text/javascript" src="js-lib/angular.min.js"></script>
	<script type="text/javascript" src="js-lib/moment.js"></script>
	<script type="text/javascript" src="js-lib/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js-lib/bootstrap/js/bootstrap-datepicker.js"></script>
	<script type="text/javascript" src="ng-table.js"></script>
	<script type="text/javascript" src="js-lib/ng-dialog/js/ngDialog.min.js"></script>
	<script type="text/javascript" src="validation-rule.js"></script>

        <script type="text/javascript" src="validation.js"></script>
	<link data-require="ng-table@*" data-semver="0.3.0" rel="stylesheet" href="http://bazalt-cms.com/assets/ng-table/0.3.0/ng-table.css" />
    
    <link data-require="bootstrap-css@*" data-semver="3.0.0" rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" />
   
	<link href="js-lib/bootstrap/css/datepicker.css" rel="stylesheet" type="text/css" />
	<link href="new.css" rel="stylesheet" type="text/css" />
	<link type="text/css" rel="stylesheet" href="skins/skin1/top.css"/>
	<link href="js-lib/ng-dialog/css/ngDialog.min.css" rel="stylesheet" type="text/css" />
	<link href="js-lib/ng-dialog/css/ngDialog-theme-default.min.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="skinable_tabs.min.js"></script>
	<style>
		.ngdialog.ngdialog-theme-default .ngdialog-content {
			min-height: 330px;
			min-width: 580px;
		}
		.redColor {
			color : red
		}
		.Color {
			color : black
		}
		.red {
			background : red
		}
		
		.purple {
			background : purple
		}
		.do	{
			disabled
		}
		.get {
			enabled
		}
	</style>
</head>
<body id="template" ng-app='agora' ng-controller="PermissionController">
<a href="adminReportList.m" class="logo" title="Essentia: AGORA"><img src="logo_agora.png"></a>

	<div class="tabs_holder">
		<ul>
  			<li><a class="tab_selected" href="#role-tab">Define Roles</a></li>
  			<li><a href="#company-tab" >Manage Subscription</a></li>
  			<li><a href="#user-tab" >Manage User</a></li>
 		</ul>
 		
 		<div class="content_holder">
 			<div id="user-tab">
 			
 				<table class="table table-striped table-hover" template-header="templates.header.user" ng-table="tableUsers" show-filter="true" style="background-color: #eee;">
 					<tbody ng-repeat="c in $data">
 					  <tr >
 						<td  ng-class="c.a == 0 ? 'redColor': 'Color' " data-title="'Name'" style="border:1pt solid black;width: 10%;" filter="{ 'n': 'text'}" >
 							{{c.n}}
 							<div style = "width = 100%;">
 							<a class="glyphicon glyphicon-edit" href="" ng-click="setEditUserCtx(c)" style="text-decoration: none;color : purple;"/> Edit</a>
 							<a ng-if="c.a == 1" class="glyphicon glyphicon-trash" href="" ng-click="deleteUser(c.i)" style="text-decoration: none;color : purple; margin-left: 10%;"/> Delete</a>
 							<a ng-if="c.a == 0" class="glyphicon glyphicon-ok" href="" ng-click="activeUser(c.i)" style="text-decoration: none;color : purple; margin-left: 10%;"/> Activate</a>
 							</div>
 						</td>
 						<td data-title="'Email'"  style="border:1pt solid black;width: 10%;">
 							{{c.e}}
 						</td>
 						<td data-title="'Company'"  style="border:1pt solid black;width: 10%;" filter="{ 'c': 'text'}">
 							{{c.c}}
 						</td>
 						<td data-title="'Credentials'"  style="border:1pt solid black;width: 10%;">
 							{{c.un}} / {{c.pw}} 
 						</td>
 					  </tr>
 					  <tr ng-show="editUserId == c.i" ng-if="editUserId == c.i">
 						<td colspan="4" data-title="'Name'" style="border:1pt solid black;width: 10%;">
 							<div ng-include src="'add-edit-user.html'">
 							</div>
 							<div style="float: left;margin-right: 5px;">
	 							<label>Actions:</label>
	 							<button class="btn btn-success" ng-click="onUserUpdate(c)" >Save</button>
 							    <button class="btn btn-dafault" ng-click="resetEditUserId()" >Cancel</button>
	 						</div> 							
 								
 						</td>
 						
 						
 					  </tr>
 					</tbody>
 				</table>
 			</div>
 			
 			<div id="role-tab">
 				<table class="table table-striped table-hover" ng-if="showRolePanel" border=1 style="background-color: #eee;width: 100%;">
					<tr style="width: 100%;">
						<td style="border:1pt solid black;width: 30%;">Roles</td>
						<td>Permissions</td>
					</tr>
					<tr ng-repeat="r in roles">
						<td style="border:1pt solid black;width: 30%;">{{r.rn}}</td>
						<td style="border:1pt solid black;">
							<div style="float: left;" ng-repeat="a in r.hasActions">
								<input  type="checkbox" ng-click="roleChange($event, r.id, a.id)" ng-checked="{{a.st==1}}">{{a.an}}
							</div>
						
						</td>
					</tr>
				</table>
 			</div>
 			
 			<div id="company-tab">
 				
 				<table template-header="templates.header.company" class="table table-striped table-hover" ng-table="tableCompany" show-filter="true" style="background-color: #eee;">
 					<tbody ng-repeat="c in $data">
 					  <tr ng-class="c.a == 0?'danger':''">
 						<td data-title="'Company'" filter="{ 'n': 'text' }" 
 						style="border:1pt solid black;width: 10%;">
 						 <a  ng-class="c.a == 0 ? 'redColor': 'Color' " 
 						 href=""  style = "font-size:16px; text-decoration: none; width: 100%;float: left;"/>
 						 {{c.n}}
 						 </a>
 							<a class="glyphicon glyphicon-eye-open" href="" ng-click="getUserForCompany(c.i)" style="text-decoration: none;color: purple; "/> Users</a>
 							<a class="glyphicon glyphicon-edit" href="" ng-click="getCompany(c)" style="text-decoration: none;color : purple; margin-left: 10%;"/> Edit</a>
 							<a ng-if="c.a == 1" class="glyphicon glyphicon-trash" href="" ng-click="deleteCompany(c.i)" style="text-decoration: none;color : purple; margin-left: 10%;"/> Delete</a>
 							<a ng-if="c.a == 0" class="glyphicon glyphicon-ok" href="" ng-click="activeCompany(c.i)" style="text-decoration: none;color : purple; margin-left: 10%;"/> Activate</a>
 						</td>
						<td data-title="'Role'" style="border:1pt solid black;width: 40%;">
							<div ng-repeat="r in c.rs" style="float: left;margin-right: 5px;">
								<input type="checkbox" 
								ng-click="compRoleChange($event, r.i, c.i)"  ng-checked="{{r.c==1}}" > {{r.n}}
							</div>
						</td>
 					  </tr>
 					  <tr ng-show="showPanelId == c.i">
 					   <td colspan="2">
 					   	<a ng-class="cu.a == 0 ? 'red': 'purple' " style="text-decoration: none;float: left;margin: 4px;font-size: 12px;font-weight: normal;" 
 					   	ng-repeat="cu in users" href="" 
 					   	ng-click="manageUserForCompany(c, cu)" class="label type-hint type-hint-expression">
 					   		{{cu.fn}} {{cu.ln}} 
 					   	</a>
 					   	<a ng-click="addUserForCompany(c)" style="text-decoration: none;background: green;float: left;margin: 4px;font-size: 12px;font-weight: normal;" href="" class="label type-hint type-hint-expression">
 					   		add user 
 					   	</a>
 					   	
 					   </td>
 					  </tr>
 					</tbody>
 				</table>
 					
 			</div>
 		
 		</div>
	</div>
	
<script type="text/ng-template" id="custom/pager">
        <ul class="pager ng-cloak">
          <li ng-repeat="page in pages"
                ng-class="{'disabled': !page.active, 'previous': page.type == 'prev', 'next': page.type == 'next'}"
                ng-show="page.type == 'prev' || page.type == 'next'" ng-switch="page.type">
            <a ng-switch-when="prev" ng-click="params.page(page.number)" href="">&laquo; Previous</a>
            <a ng-switch-when="next" ng-click="params.page(page.number)" href="">Next &raquo;</a>
          </li>
            <li> 
            <div class="btn-group">
                <button type="button" ng-class="{'active':params.count() == 10}" ng-click="params.count(10)" class="btn btn-default">10</button>
                <button type="button" ng-class="{'active':params.count() == 25}" ng-click="params.count(25)" class="btn btn-default">25</button>
                <button type="button" ng-class="{'active':params.count() == 50}" ng-click="params.count(50)" class="btn btn-default">50</button>
                <button type="button" ng-class="{'active':params.count() == 100}" ng-click="params.count(100)" class="btn btn-default">100</button>
            </div>
            </li>
        </ul>
    </script>	
  
 <script type="text/ng-template" id="templates.header.user">
 	<tr> 
 		<th ng-repeat="column in $columns" 
 		ng-click="sortBy(column, $event)" 
 		ng-show="column.show(this)" 
 		ng-init="template=column.headerTemplateURL(this)" 
 		class="header {{column.class}}"> 
 			<div ng-if="!template" ng-show="!template" ng-bind="parse(column.title)"></div> 
 			<div ng-if="template" ng-show="template">
 				<div ng-include="template"></div>
 			</div> 
 		</th> 
 	</tr> 
 	<tr ng-show="show_filter" class="ng-table-filters"> 
 		<th ng-repeat="column in $columns" ng-show="column.show(this)" class="filter"> 
 			<div ng-repeat="(name, filter) in column.filter">
 			 
 				<div ng-if="column.filterTemplateURL" ng-show="column.filterTemplateURL"> 
 					<div ng-include="column.filterTemplateURL"></div> 
 				</div> 
 				<div ng-if="!column.filterTemplateURL" ng-show="!column.filterTemplateURL"> 
 					<div>
 						<input type="text" name="{{column.filterName}}" 
 							ng-model="params.filter()[name]" 
 							ng-if="filter=='text'" 
 							class="input-filter form-control"/>
 					</div>
 				</div> 
 			</div> 
 		</th>
 	</tr>
 </script>
 
 <script type="text/ng-template" id="templates.header.company" id="templates.header.user">
 	<tr> 
 		<th ng-repeat="column in $columns" 
 		ng-click="sortBy(column, $event)" 
 		ng-show="column.show(this)" 
 		ng-init="template=column.headerTemplateURL(this)" 
 		class="header {{column.class}}"> 
 			<div ng-if="!template" ng-show="!template" ng-bind="parse(column.title)"></div> 
 			<div ng-if="template" ng-show="template">
 				<div ng-include="template"></div>
 			</div> 
 		</th> 
 	</tr> 
 	<tr ng-show="show_filter" class="ng-table-filters"> 
 		<th ng-repeat="column in $columns" ng-show="column.show(this)" class="filter"> 
 			<div ng-repeat="(name, filter) in column.filter">
 			 
 				<div ng-if="column.filterTemplateURL" ng-show="column.filterTemplateURL"> 
 					<div ng-include="column.filterTemplateURL"></div> 
 				</div> 
 				<div ng-if="!column.filterTemplateURL" ng-show="!column.filterTemplateURL"> 
 					<div>
 						<input type="text" name="{{column.filterName}}" 
 							ng-model="params.filter()[name]" 
 							ng-if="filter=='text'" 
 							class="input-filter form-control"/>
 					</div>
 				</div> 
 			</div> 
 		</th>
 		<td style="width:1%">
 			<div class="btn-group" style="margin-left: -290px;margin-bottom:5px">
			  
			  <button ng-click="params.filter()['a'] = 0" type="button" class="glyphicon glyphicon-add btn btn-default btn-sm">
			  	<span class="glyphicon glyphicon-trash"> Filter</span>
			  </button>
			  <button ng-click="params.filter()['a'] = 1" type="button" class="glyphicon glyphicon-add btn btn-default btn-sm">
			  	<span class="glyphicon glyphicon-ok"> Filter</span>
			  </button>
			</div>
			<button style="margin-left: 0px;margin-bottom:5px" ng-click="onCompanyAdd()" type="button" class="glyphicon glyphicon-add btn btn-default btn-sm">
			  	<span class="glyphicon glyphicon-plus">Add</span>
			</button>
 		</td>
 		 
 	</tr>
 </script>   

 <script type="text/ng-template" id="manage-edit-user.html">
 							<div style="float: left;margin-right: 5px;">
 								<label>First Name:</label><input type="input" ng-model="editUser.fn">
 							</div>
 							<div style="float: left;margin-right: 5px;">
	 							<label>Last Name:</label><input type="input" ng-model="editUser.ln">
 							</div>
 							<div style="float: left;margin-right: 5px;">
	 							<label>Email:</label><input type="input" ng-model="editUser.e">
 							</div>
 							
 							<div style="float: left;margin-right: 5px;">
	 							<label>Username:</label> <input type="input" ng-model="editUser.un">
 							</div>
 							<div style="float: left;margin-right: 5px;">
	 							<label>Password:</label> <input type="input" ng-model="editUser.pw">
	 						</div>
	 						<div style="float: left;margin-right: 5px;">
		    					<label>Start Date:</label>
		    					<input ng-model="editUser.sd" data-provide="datepicker" data-date-format="mm/dd/yyyy">
	 						</div>
	 						<div style="float: left;margin-left: 5px;" ng-show = "editUser.c == 'essentia'">
								<div style="float: left; font-size:11px;margin-left: 2px;" 
								ng-repeat="r in editUser.roles">
									<input type="checkbox"  ng-checked="{{r.c == 1}}"
									 ng-click="userRoleChange($event, r)"> {{r.n}}
								</div>
							</div>
	 						<div ng-if="mode=='add'" style="float: left;margin-right: 5px; width: 100%;">
	 							<label></label>
	 							<button class="btn btn-success" ng-click="userUpdate();closeThisDialog()">Update</button>
	 							<button  ng-hide="{{editUser.a == '0' }}"  class="btn btn-danger" ng-click="deleteUserOfCompany(editUser);closeThisDialog()" >Delete User</button>
 							    <button ng-hide="{{editUser.a != '0' }} || {{editUser.ca == '0' }} " class="btn btn-danger" ng-click="activeUserOfCompany(editUser);closeThisDialog()" >Active</button>
 							    <button class="btn btn-dafault" ng-click="resetEditUserId();closeThisDialog()" >Cancel</button>
	 						</div>
	 						
	 						
							
	 						
 </script>   
 
 <script type="text/ng-template" id="add-new-user.html">
 							<div style="float: left;margin-right: 5px;">
 								<label>First Name:</label><input type="input" ng-model="editUser.fn">
 							</div>
 							<div style="float: left;margin-right: 5px;">
	 							<label>Last Name:</label><input type="input" ng-model="editUser.ln">
 							</div>
 							<div style="float: left;margin-right: 5px;">
	 							<label>Email:</label><input type="input" ng-model="editUser.e">
 							</div>
 							<div style="float: left;margin-right: 5px;">
	 							<label>Company:</label> 
	 							<input type="input" ng-model="editUser.c">
 							</div>
 							<div style="float: left;margin-right: 5px;">
	 							<label>Username:</label> <input type="input" ng-model="editUser.un">
 							</div>
 							<div style="float: left;margin-right: 5px;">
	 							<label>Password:</label> <input type="input" ng-model="editUser.pw">
	 						</div>
	 						<div style="float: left;margin-right: 5px;">
		    					<label>Start Date:</label>
		    					<input ng-model="editUser.sd" data-provide="datepicker" data-date-format="mm/dd/yyyy">
	 						</div>
	 						<div ng-if="mode=='add'" style="width:100%; float: left;margin-right: 5px;">
	 							<label></label>
	 							<button class="btn btn-success" ng-click="onUserSave(); closeThisDialog()" >Save</button>
 							    <button class="btn btn-dafault" ng-click="resetEditUserId(); closeThisDialog()" >Cancel</button>
	 						</div>
 </script>   
 <script type="text/ng-template" id="add-edit-user.html">
 							<div style="float: left;margin-right: 5px;">
 								<label>First Name:</label><input type="input" ng-model="editUser.fn">
 							</div>
 							<div style="float: left;margin-right: 5px;">
	 							<label>Last Name:</label><input type="input" ng-model="editUser.ln">
 							</div>
 							<div style="float: left;margin-right: 5px;">
	 							<label>Email:</label><input type="input" ng-model="editUser.e">
 							</div>
 							<div style="float: left;margin-right: 5px;">
	 							<label>Company:</label> 
	 							 							
	 							<input type="input" disabled ng-model="editUser.c">
 							</div>
 							<div style="float: left;margin-right: 5px;">
	 							<label>Username:</label> <input type="input" ng-model="editUser.un">
 							</div>
 							<div style="float: left;margin-right: 5px;">
	 							<label>Password:</label> <input type="input" ng-model="editUser.pw">
	 						</div>
	 						<div style="float: left;margin-right: 5px;">
		    					<label>Start Date:</label>
		    					<input ng-model="editUser.sd" data-provide="datepicker" data-date-format="mm/dd/yyyy">
	 						</div>
	 						<div style="margin-left: 5px;" ng-show = "editUser.c == 'essentia'">
								
								<div style="float: left; font-size:11px;margin-left: 2px;" ng-repeat="r in roles">
									<input type="checkbox"  ng-click="userRoleChangeForNewUser($event, r.id)"> {{r.rn}}
								</div>
								
							</div>
	 						<div ng-if="mode=='add'" style="float: left;margin-right: 5px; width: 100%;">
	 							<label></label>
	 							<button class="btn btn-success" ng-click="onUserSave();closeThisDialog()">Save</button>
 							    <button class="btn btn-dafault" ng-click="resetEditUserId();closeThisDialog()" >Cancel</button>
	 						</div>
	 						
	 						
	 						
 </script>   

 <script  type="text/ng-template" id="add-edit-company.html">
 							<div style="float: left;margin-right: 5px;">
	 							<label>Company:</label> <input type="input"  ng-model="editCompany.n">
 							</div>
 							
 							<div style="float: left;margin-right: 5px;">
	 							<label>Email:</label> <input validator="[number,required]"  type="input" ng-model="editCompany.em">
 							</div>
 							<div style="float: left;margin-right: 5px;">
	 							<label>Phone: </label> <input type="input" ng-model="editCompany.ph">
	 						</div>
	 						<div style="float: left;margin-right: 5px;">
	 							<label>Contact Name:</label> <input type="input" ng-model="editCompany.cp">
	 						</div>
	 						
	 						<div style="float: left;margin-right: 5px;">
		    					<label>Start Date:</label>
		    					<input ng-model="editCompany.cr" data-provide="datepicker" data-date-format="mm/dd/yyyy">
	 						</div>
	 						<div style="float: left;margin-right: 5px;">
	 							<label>Notes</label> <textarea type="input" ng-model="editCompany.nt"></textarea>
	 						</div>
	 						<div ng-if="mode=='add'" style=" margin-top: 5%; float: left;margin-right: 5px; width: 100%;">
	 							<label></label>
	 							<button class="btn btn-success" ng-click="onCompanySave();closeThisDialog()">Save</button>
 							    <button class="btn btn-dafault" ng-click="resetEditUserId();closeThisDialog()" >Cancel</button>
	 						</div>
 </script>   

<script type="text/ng-template" id="manage-edit-company.html">
 							<div style="float: left;margin-right: 5px;">
	 							<label>Email:</label> <input type="input" ng-model="editCompany.em">
 							</div>
 							<div style="float: left;margin-right: 5px;">
	 							<label>Phone: </label> <input type="input" ng-model="editCompany.ph">
	 						</div>
	 						<div style="float: left;margin-right: 5px;">
	 							<label>Contact Name:</label> <input type="input" ng-model="editCompany.cp">
	 						</div>
	 						
	 						<div style="float: left;margin-right: 5px;">
		    					<label>Start Date:</label>
		    					<input ng-model="editCompany.cr" data-provide="datepicker" data-date-format="mm/dd/yyyy">
	 						</div>
	 						<div style="float: left;margin-right: 5px;">
	 							<label>Notes</label> <textarea type="input" ng-model="editCompany.nt"></textarea>
	 						</div>
	 						<div ng-if="mode=='add'" style="margin-top: 5%;float: left; width: 100%; margin-right: 5px;">
	 							<label></label>
	 							<button class="btn btn-success" ng-click="onCompanyUpdate();closeThisDialog()">Update</button>
	 							<button ng-hide="{{editCompany.a == '0' }}" class="btn btn-danger" ng-click="deleteCompany(editCompany.i);closeThisDialog()" >Delete</button>
	 							<button ng-hide="{{editCompany.a != '0' }}" class="btn btn-danger" ng-click="activeCompany(editCompany.i);closeThisDialog()" >Active</button>
 							    <button class="btn btn-dafault" ng-click="resetEditUserId();closeThisDialog()" >Cancel</button>
	 						</div>
 </script>   
	
</body>

</html>

<script>
	$('.tabs_holder').skinableTabs({
    	effect: 'basic_display',
    	skin: 'skin1',
    	position: 'top',
    	persist_tab: false
  	});
  	angular.module('agora',['validator', 'validator.rules']);
  	angular.module('agora',['ngTable','ngDialog'] )
  	.factory('beData', function(){
			return {
			    role: function() { return ${rolesAsJson} }
			    
			}
			
	});
	
</script>

<script type="text/javascript" src="agora-angular-permision.js"></script>