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
	<script type="text/javascript" src="validation-rule.js"></script>
	<script type="text/javascript" src="js/angular.treeview.js"></script>
	<script type="text/javascript" src="js/ng-context-menu.js"></script>
	<script type="text/javascript" src="js-lib/ng-dialog/js/ngDialog.min.js"></script>

	<link href="js-lib/ng-dialog/css/ngDialog.min.css" rel="stylesheet" type="text/css" />
	<link href="js-lib/ng-dialog/css/ngDialog-theme-default.min.css" rel="stylesheet" type="text/css" />
	<link data-require="ng-table@*" data-semver="0.3.0" rel="stylesheet" href="http://bazalt-cms.com/assets/ng-table/0.3.0/ng-table.css" />
    <link data-require="bootstrap-css@*" data-semver="3.0.0" rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" />
	<link type="text/css" rel="stylesheet" href="skins/skin1/top.css"/>
	<script type="text/javascript" src="skinable_tabs.min.js"></script>
	<link href="js-lib/bootstrap/css/datepicker.css" rel="stylesheet" type="text/css" />
	<link href="new.css" rel="stylesheet" type="text/css" />
	
	
	<style>
		.ngdialog.ngdialog-theme-default .ngdialog-content {
			min-height: 150px;
			min-width: 500px;
			
		}
		.ngdialog.ngdialog-theme-default {
			padding-top: 113px; !important
		}
		/* example */
		label {
		    display: inline-block;
		    width: 30px;
		    padding: 0 10px;
		    font-weight: bold;
		}
		
		.button_panel {
		    margin:10px 0 30px 0; padding:10px; background-color:#EEEEEE; border-radius:5px; font:12px Tahoma;
		}
		
		.input_panel {
		    padding: 10px 5px;
		}
		
		.done_button {
		    margin-top: 10px;
		}
		div[data-angular-treeview] {
		  /* prevent user selection */
		  -moz-user-select: -moz-none;
		  -khtml-user-select: none;
		  -webkit-user-select: none;
		  -ms-user-select: none;
		  user-select: none;
		
		  /* default */
		  font-family: Tahoma;
		  font-size:13px;
		  color: #555;
		  text-decoration: none;
		}
		
		div[data-tree-model] ul {
		  margin: 0;
		  padding: 0;
		  list-style: none; 
		  border: none;
		  overflow: hidden;
		}
		
		div[data-tree-model] li {
		  position: relative;
		  padding: 0 0 0 20px;
		  line-height: 20px;
		}
		
		div[data-tree-model] li .expanded {
		  padding: 1px 10px;
		  background-image: url("https://raw.githubusercontent.com/wix/angular.treecontrol/master/images/node-opened-2.png");
		  background-repeat: no-repeat;
		}
		
		div[data-tree-model] li .collapsed {
		  padding: 1px 10px;
		  background-image: url("https://raw.githubusercontent.com/wix/angular.treecontrol/master/images/node-closed-2.png");
		  background-repeat: no-repeat;
		}
		
		div[data-tree-model] li .normal {
		  padding: 1px 10px;
		  background-image: url("http://cfile23.uf.tistory.com/image/165B663A50C13F4B196CCA");
		  background-repeat: no-repeat;
		}
		
		div[data-tree-model] li i, div[data-tree-model] li span {
		  cursor: pointer;
		}
		
		div[data-tree-model] li .selected {
		  background-color: #aaddff;
		  font-weight: bold;
		  padding: 1px 5px;
		}
		
		.position-fixed {
			position: absolute !important;
			z-index:100;
		}
	</style>
</head>
<body id="template" ng-app='agora' ng-controller="SubscriptionController">
<a href="adminReportList.m" class="logo" title="Essentia: AGORA"><img src="logo_agora.png"></a>
	
<div class="tabs_holder">
	<ul>
		<li><a href="#subscription-tab" >Manage Subscription</a></li>
		<li><a href="#company-tab" >Manage Company</a></li>
	</ul>
	<div class="content_holder">
		<div id="subscription-tab">
			<div ng-init="loadmedia()">
				<div>	
			 		<div context-menu="onParentAdd();"  class=" position-fixed" data-target="myParentMenu" ng-class="{ 'highlight': highlight, 'expanded' : expanded }"  data-ng-class="node.selected" style = "background: white; padding-left: 2%;" >
			 		<ul>
		      			<li>
							<i class="collapsed" > Root</i>
						</li>
		      		</ul>
		      		</div>
			 		<div style="background-color: white; padding-top: 2%; width:25%;float: left;border-right: 2px solid black;overflow-x: scroll;height:1090px;"
		      			data-angular-treeview="true"
		      			data-tree-id="mytree"
		      			data-node-id="id"
		      			data-tree-model="roleList">
			     	</div>
			     	<div style="background-color: white; padding-top: 1%; width:75%;margin-left: 25%;padding-left: 10px;height:1090px;">
					<div style= "height: 50px">
						<ul class="nav nav-tabs" role="tablist" id="myTab">
						  <li class="active"><a href="#home" role="tab" data-toggle="tab">Media Info</a></li>
						  <li><a href="#oRates" role="tab" data-toggle="tab">O' Rates</a></li>
						  <li><a href="#pRates" role="tab" data-toggle="tab">P' Rates</a></li>
						  <li><a href="#addSpec" role="tab" data-toggle="tab">Ad' Spec</a></li>
						</ul>
					</div>
					<div class="tab-content">
	  					<div class="tab-pane active" id="home">
	  						<table ng-hide="editIdCat===subscription.id" ng-show="isSubscription" style="width: 99%;" class="table Contextual  table-striped table-bordered table-condensed table-hover">
					    		<tr>
					    			<th style="width:16%;">Title</th>
					    			<td>
					    				{{subscription.title}}
					    			</td>
					    			<td style="width:10%;"> 
					    				<input type="button" value="EDIT" ng-click="setEditIdCat(subscription)" id="editCatBtn{{subscription.id}}" class="btn btn-primary">
					    			</td>
					    		</tr>
					    		<tr>
					    			<th>Url</th>
					    			<td>{{subscription.url}}</td>
					    		</tr>
					    		<tr>
					    			<th>Category</th>
					    			<td>{{subscription.category}}</td>
					    		</tr>
					    		<tr>
					    			<th>Id</th>
					    			<td>{{subscription.subId}}</td>
					    		</tr>
					    		<tr>
					    			<th>Includes</th>
					    			<td>{{subscription.includes}}</td>
					    		</tr>
					    		<tr>
					    			<th>About</th>
					    			<td>{{subscription.about}}</td>
					    		</tr>
					    		<tr>
					    			<th>Company</th>
					    			<td>{{subscription.company}}</td>
					    		</tr>
					    		<tr>
					    			<th>Primary Language</th>
					    			<td>{{subscription.primary_language}}</td>
					    		</tr>
					    		<tr>
					    			<th>Scheduled NL frequency</th>
					    			<td>{{subscription.scheduled_NL_frequency}}</td>
					    		</tr>
					    		<tr>
					    			<th>Scheduled Sc frequency</th>
					    			<td>{{subscription.scheduled_SC_frequency}}</td>
					    		</tr>
					    		<tr>
					    			<th>Currency</th>
					    			<td>{{subscription.currency}}</td>
					    		</tr>
					    		<tr>
					    			<th>SC</th>
					    			<td>{{subscription.sc}}</td>
					    		</tr>
					    		<tr>
					    			<th>Comments</th>
					    			<td>{{subscription.comments}}</td>
					    		</tr>
					    		<tr>
					    			<th>Media Kit</th>
					    			<td>{{subscription.media_kit}}</td>
						    	</tr>
						    	<tr>
							    	<th>Access</th>
							    	<td>{{subscription.access}}</td>
						    	</tr>
						    	<tr>
							    	<th>Clientele</th>
							    	<td>{{subscription.clientele}}</td>
						    	</tr>
				    		</table>
				    		<table  ng-show="editIdCat === subscription.id" ng-if="editIdCat === subscription.id">
					    		<tr>
			 						<td  data-title="'Name'" style="border:1pt solid black;width: 10%;">
			 							<div ng-include src="'add-edit-user.html'">"editIdCat"
			 							</div>
			 						</td>
				 				</tr>
			 				</table>
			 			</div>
						<div class="tab-pane" id="oRates">.jhk..</div>
	  					<div class="tab-pane" id="pRates">...</div>
	  					<div class="tab-pane" id="addSpec">...</div>
					</div>
			  	  	</div>
				</div>
		 	</div>
		</div>
	</div>
</div>
 	
<div class="dropdown position-fixed" id="myMenu{{$index}}">
	  <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu2">
	    <li>
	      <a class="pointer" role="menuitem" tabindex="1"
	         ng-click="panel.highlight = true ">
	        <a ng-click="onCompanyAdd()">Add Sub-Subscription</a>
	        <a ng-click="onCompanyDelete()">Delete Subscription</a>
	      </a>
	    </li>
	  </ul>
</div>

<div class="dropdown position-fixed" id="myParentMenu{{$index}}">
	  <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu2">
	    <li>
	      <a class="pointer" role="menuitem" tabindex="1"
	         ng-click="panel.highlight = true ">
	        <a ng-click="onCompanyAdd()">Add Subscription</a>
	      </a>
	    </li>
	  </ul>
</div>
<script  type="text/ng-template" id="add-edit-company.html">
		<div>
			<div style = "height: 80px;">
	    		<label style = "float: left; width: 10%; padding-left: 0px;">Title:</label>
	    		<input  style = " margin-left: 2%; width: 70%; float: left;" type="input" class=" form-control" ng-model="newSubscription.title">
	    	</div>	
	 		<div>
				<button class="btn btn-success" ng-click="onCompanySave();closeThisDialog()">Save</button>
				<button class="btn btn-dafault" ng-click="resetEditUserId();closeThisDialog()" >Cancel</button>
	 		</div>
		</div>
</script>   
<script type="text/ng-template" id="add-edit-user.html">
		<table style="width: 99%;" class="table Contextual  table-striped table-bordered table-condensed table-hover">
			<tr>
				<th style="width:16%;">Title</th>
				<td><input style = "width: 50%;" type="input" disabled  class=" form-control" ng-model="subscription.title"></td>
			</tr>
			<tr>
				<th>Url</th>
				<td><input style = "width: 50%;" type="input" class=" form-control" ng-model="subscription.url"></td>
			</tr>
			<tr>
				<th>Category</th>
				<td><input style = "width: 50%;" type="input" class=" form-control" ng-model="subscription.category"></td>
			</tr>
			<tr>
				<th>Id</th>
				<td><input style = "width: 50%;" type="input" class=" form-control" ng-model="subscription.subId"></td>
			</tr>
				<tr>
				<th>Includes</th>
				<td><input style = "width: 50%;" type="input" class=" form-control" ng-model="subscription.includes"></td>
			</tr>
			<tr>
				<th>About</th>
				<td><input style = "width: 50%;" type="input" class=" form-control" ng-model="subscription.about"></td>
			</tr>
			<tr>
				<th>Company</th>
				<td><input style = "width: 50%;" type="input" class=" form-control" ng-model="subscription.company"></td>
			</tr>
			<tr>
				<th>Primary Language</th>
				<td><input style = "width: 50%;" type="input" class=" form-control" ng-model="subscription.primary_language"></td>
			</tr>
			<tr>
				<th>Scheduled NL frequency</th>
				<td><input style = "width: 50%;" type="input" class=" form-control" ng-model="subscription.scheduled_NL_frequency"></td>
			</tr>
			<tr>
				<th>Scheduled Sc frequency</th>
				<td><input style = "width: 50%;" type="input" class=" form-control" ng-model="subscription.scheduled_SC_frequency"></td>
			</tr>
			<tr>
				<th>Currency</th>
				<td><input style = "width: 50%;" type="input" class=" form-control" ng-model="subscription.currency"></td>
			</tr>
			<tr>
				<th>SC</th>
				<td><input style = "width: 50%;" type="input" class=" form-control" ng-model="subscription.sc"></td>
			</tr>
			<tr>
				<th>Comments</th>
				<td><textarea style = "width: 50%;"type="input" class=" form-control" ng-model="subscription.comments"></textarea></td>
			</tr>
			<tr>
				<th>Media Kit</th>
				<td><input style = "width: 50%;" type="input" class=" form-control" ng-model="subscription.media_kit"></td>
			</tr>
			<tr>
		    	<th>Access</th>
		    	<td><input style = "width: 50%;" type="input" class=" form-control" ng-model="subscription.access"></td>
			</tr>
			<tr>
		    	<th>Clientele</th>
		    	<td><input style = "width: 50%;" type="input" class=" form-control" ng-model="subscription.clientele"></td>
			</tr>
			
		</table>
		<table style = "float: right; margin-right: 10px; margin-bottom: 10px;">
			<tr>
				<td  style = "width: 33.33%;">
					<button class="btn btn-success" ng-click="onEditSave(subscription);closeThisDialog()">Save</button>
				    <button class="btn btn-dafault" ng-click="resetEditUserId();closeThisDialog()" >Cancel</button>
				</td>
			</tr>
		</table>
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
  angular.module('agora', ['angularTreeview', 'ng-context-menu' ,'ngDialog', 'ngTable']);
</script>

<script type="text/javascript" src="agora-angular-manage-subscription.js"></script>
