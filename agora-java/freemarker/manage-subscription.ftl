<!DOCTYPE html>
<html>
<head>
	<title>Agora - Welcome</title>
	 
	<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
	<link rel="shortcut icon" href="agora_icon.ico"/>
	<script type="text/javascript" src="jq.js"></script>
	<script type="text/javascript" src="js-lib/angular.min.js"></script>
	<script type="text/javascript" src="js-lib/moment.js"></script>
	<script type="text/javascript" src="js-lib/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="js-lib/bootstrap/js/bootstrap-datepicker.js"></script>
	<script type="text/javascript" src="ng-table.js"></script>
	<script type="text/javascript" src="validation-rule.js"></script>
	<script type="text/javascript" src="js-lib/angular.treeview.js"></script>
	<script type="text/javascript" src="js-lib/ng-context-menu.js"></script>
	<script type="text/javascript" src="js-lib/ng-dialog/js/ngDialog.min.js"></script>
	<script type="text/javascript" src="js-lib/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="js-lib/dataTables.fixedColumns.js"></script>
	<script type="text/javascript" src="js-lib/dataTables.fixedHeader.js"></script>
	<script type="text/javascript" src="js-lib/angular-datatables.js"></script>
	<script type="text/javascript" src="js-lib/firebugx.js"></script>
	<script type="text/javascript" src="js-lib/jquery-ui-1.8.16.custom.min.js"></script>
	<script type="text/javascript" src="js-lib/jquery.event.drag-2.2.js"></script>
	
	
	<script type="text/javascript" scr = "js-lib/angular-file-upload-shim.min.js"></script>
	<script type="text/javascript" src="js-lib/angular-file-upload.js"></script>
	

	
	<link href="css-lib/excelTable2007.css" rel="stylesheet" type="text/css" />

	
	<link href="js-lib/ng-dialog/css/ngDialog.min.css" rel="stylesheet" type="text/css" />
	<link href="js-lib/ng-dialog/css/ngDialog-theme-default.min.css" rel="stylesheet" type="text/css" />
	<link data-require="ng-table@*" data-semver="0.3.0" rel="stylesheet" href="http://bazalt-cms.com/assets/ng-table/0.3.0/ng-table.css" />
    <link data-require="bootstrap-css@*" data-semver="3.0.0" rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css" />
	<link type="text/css" rel="stylesheet" href="skins/skin1/top.css"/>
	<script type="text/javascript" src="skinable_tabs.min.js"></script>
	<link href="js-lib/bootstrap/css/datepicker.css" rel="stylesheet" type="text/css" />
	<link href="new.css" rel="stylesheet" type="text/css" />
	
	
	<style>
		body { font-family: sans-serif; }
  
    	.column-grid  >  td { 
    		border: 1px solid #EEE;
    		
		}
  		.column-label  > td, .row-label {
    		text-align: center;
    		background: #EEE;
  		}
 		
 		.colapse {
 			width:74%;
 			margin-left: 25%;
 		}
 		.expand {
 			width:99%;
 		}
  		.row-label { width: 2em; height:20px; }
		.cell-title {
	    	font-weight: bold;
	    }
		.table-input > td > input  {margin-bottom: 10px !important; width:75px; border: none;height:20px; font: 300 11px helvetica neue, helvetica, arial, sans-serif;}
	    .cell-effort-driven {
	    	text-align: center;
	    }
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
		
		#loading-id {
			position: absolute;
			z-index: 100;
			top: 50%;
			left: 50%;
		}
		.content_holder_inner {
			background:white;
		}
		.custom-nov {
			border: none !important;
		}
	</style>
	
</head>
<body id="template" ng-app='agora' ng-controller="SubscriptionController">
<span id="loading-id"><img src="images/loading.gif"/></span>
<a href="adminReportList.m" class="logo" title="Essentia: AGORA"><img src="logo_agora.png"></a>

<div class="tabs_holder">
	<ul>
		<li><a href="#subscription-tab" >Manage Subscription</a></li>
		<li><a href="#company-tab" >Manage Company</a></li>
	</ul>
	<div class="content_holder">
		<div id="subscription-tab ">
			<div ng-init="loadmedia()">
				<div style = "float:left; background:white;" >
				<button style = "float: right !important; background: #E4ECF7;" type="button" class=" glyphicon glyphicon-list pull-left" ng-model="collapsed" ng-click="collapsed=!collapsed"> </button>
					<div ng-hide="collapsed">	
				 		<div context-menu="onParentAdd();"  class=" position-fixed" data-target="myParentMenu" ng-class="{ 'highlight': highlight, 'expanded' : expanded }"  data-ng-class="node.selected" style = "background: white; padding-left: 2%;" >
					 		<ul>
				      			<li>
									<i class="collapsed" > Root</i>
								</li>
				      		</ul>
			      		</div>
					 	<div  style="background-color: white; padding-top: 2%; width:250px;float: left;border-right: 2px solid black; height:500px;"
		 		      			data-angular-treeview="true"
				      			data-tree-id="mytree"
				      			data-node-id="id"
				      			data-tree-model="roleList">
					    </div>
				    </div>
			    </div>
			     	<div ng-class="collapsed==true ?'expand' :'colapse' " style="background-color: white; padding-top: 1%; padding-left: 10px;" >
			     	<div>
					<div style= "height: 50px">
						<ul class="nav nav-tabs custom-nov" role="tablist" id="myTab">
						  <li class="active"><a ng-click = "inableMediaInfo()" href="#home" role="tab" data-toggle="tab"  style = "background: #E4ECF7;">Media Info</a></li>
						  <li><a ng-click = "inableOrates()" href="#oRates" role="tab" data-toggle="tab" style = "background: #E4ECF7;">O' Rates</a></li>
						  <li><a ng-click = "inablePrates()" href="#pRates" role="tab" data-toggle="tab" style = "background: #E4ECF7;">P' Rates</a></li>
						  <li><a ng-click = "disabledOrates()" href="#addSpec" role="tab" data-toggle="tab" style = "background: #E4ECF7;">Ad' Spec</a></li>
						</ul>
					</div>
					<div class="tab-content">
	  					<div class="tab-pane active" id="home">
	  						<table  ng-hide="editIdImage===subscription.id" ng-show="isSubscription" style="width: 99%;" class="table Contextual  table-striped table-bordered table-condensed table-hover">
								<tr>
					    			<th style="width:16%;">Image</th>
					    			<td>
					    			<img ng-click = "showImagePopup(subscription.id)" data-ng-src="{{img}}" alt="">
					    			<a class="glyphicon glyphicon-edit" href="" ng-click="setEditIdImage(subscription)" id="editCatBtn{{subscription.id}}" style="text-decoration: none;color : purple; float:right;"/></a>	
					    			</td>
					    		</tr>	  						
	  						</table>
	  						<table  ng-show="editIdImage === subscription.id" ng-if="editIdImage === subscription.id">
					    		<tr>
			 						<td  data-title="'Name'" style="border:1pt solid black;width: 10%;">
			 							<div ng-include src="'add-edit-Image.html'">"editIdImage"
			 							</div>
			 						</td>
				 				</tr>
			 				</table>
	  						<table ng-hide="editIdCat===subscription.id" ng-show="isSubscription" style="width: 99%;" class="table Contextual  table-striped table-bordered table-condensed table-hover">
					    		<tr>
					    			<th style="width:16%;">Title</th>
					    			<td>
					    				{{subscription.title}}
					    				<a  class="glyphicon glyphicon-edit" href="" ng-click="setEditIdCat(subscription)" id="editCatBtn{{subscription.id}}" style="text-decoration: none;color : purple; float:right;"/></a>
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
						<div  style ="float: left; width: 99%;" class="dwrapper tab-pane" id="oRates">
							<div ng-controller="sheet">
								<table   datatable="ng" dt-options="dtOptions" class="ExcelTable2007">
									<thead>
									<tr class="column-label">
										<td></td>	
								      <td  class="heading" ng-repeat="column in columns">{{column}}</td>
								    </tr>
								    </thead>
								    <tbody>
								    <tr style="height: 35px !important"  class="column-grid table-input" ng-repeat="row in rows" >
								      <td style = "overflow-x: auto; overflow-y: hidden;" class="row-label heading"">{{row.Y_names}}</td>
								      <td><input ng-blur="save('LeaderBoard', row, row.LeaderBoard)" ng-model="row.LeaderBoard"></input></td>
								      <td><input ng-blur="save('Banner', row, row.Banner)" ng-model="row.Banner"></input></td>
								      <td><input ng-blur="save('Skyscraper', row, row.Skyscraper)" ng-model="row.Skyscraper"></input></td>
								      <td><input ng-blur="save('Sponsored_Links_ROS', row, row.Sponsored_Links_ROS)" ng-model="row.Sponsored_Links_ROS"></input></td>
								      <td><input ng-blur="save('Interstitial_Pop_Up', row, row.Interstitial_Pop_Up)" ng-model="row.Interstitial_Pop_Up"></input></td>
								      <td><input ng-blur="save('Page_Peel', row, row.Page_Peel)" ng-model="row.Page_Peel"></input></td>
								      <td><input ng-blur="save('Page_Push', row, row.Page_Push)" ng-model="row.Page_Push"></input></td>
								      
								      <td><input ng-blur="save('Video_Ad', row, row.Video_Ad)" ng-model="row.Video_Ad"></input></td>
								      <td><input ng-blur="save('Logo', row, row.Logo)" ng-model="row.Logo"></input></td>
								      <td><input ng-blur="save('Box_Ad', row, row.Box_Ad)" ng-model="row.Box_Ad"></input></td>
								      <td><input ng-blur="save('Sponsor_Posts_per_post', row, row.Sponsor_Posts_per_post)" ng-model="row.Sponsor_Posts_per_post"></input></td>
								      <td><input ng-blur="save('Small_Box', row, row.Small_Box)" ng-model="row.Small_Box"></input></td>
								      <td><input ng-blur="save('Marketplace', row, row.Marketplace)" ng-model="row.Marketplace"></input></td>
								      <td><input ng-blur="save('Custom', row, row.Custom)" ng-model="row.Custom"></input></td>
								      
								      <td><input ng-blur="save('Text_Ads', row, row.Text_Ads)" ng-model="row.Text_Ads"></input></td>
								      <td><input ng-blur="save('Featured_Products', row, row.Featured_Products)" ng-model="row.Featured_Products"></input></td>
								      <td><input ng-blur="save('Text_65_Words', row, row.Text_65_Words)" ng-model="row.Text_65_Words"></input></td>
								      <td><input ng-blur="save('Button', row, row.Button)" ng-model="row.Button"></input></td>
								      <td><input ng-blur="save('Box', row, row.Box)" ng-model="row.Box"></input></td>
								      <td><input ng-blur="save('Rectangle', row, row.Rectangle)" ng-model="row.Rectangle"></input></td>
								      <td><input ng-blur="save('e_solution_Broadcast', row, row.e_solution_Broadcast)" ng-model="row.e_solution_Broadcast"></input></td>
								      <td><input ng-blur="save('Footer', row, row.Footer)" ng-model="row.Footer"></input></td>
								     
								      <td><input ng-blur="save('Top_position', row, row.Top_position)" ng-model="row.Top_position"></input></td>
								      <td><input ng-blur="save('Button_footer', row, row.Button_footer)" ng-model="row.Button_footer"></input></td>
								      <td><input ng-blur="save('Showcase', row, row.Showcase)" ng-model="row.Showcase"></input></td>
								      <td><input ng-blur="save('Banner_Footer', row, row.Banner_Footer)" ng-model="row.Banner_Footer"></input></td>
								      <td><input ng-blur="save('Featured_Profile_Pdt', row, row.Featured_Profile_Pdt)" ng-model="row.Featured_Profile_Pdt"></input></td>
								      <td><input ng-blur="save('large_rectangle', row, row.large_rectangle)" ng-model="row.large_rectangle"></input></td>
								      <td><input ng-blur="save('e_solution_Broadcast_Footer', row, row.e_solution_Broadcast_Footer)" ng-model="row.e_solution_Broadcast_Footer"></input></td>
								      
								      <td><input ng-blur="save('Hosting', row, row.Hosting)" ng-model="row.Hosting"></input></td>
								      <td><input ng-blur="save('Exhibit_Hall', row, row.Exhibit_Hall)" ng-model="row.Exhibit_Hall"></input></td>
								      <td><input ng-blur="save('Pdt_List', row, row.Pdt_List)" ng-model="row.Pdt_List"></input></td>
								      <td><input ng-blur="save('Insert_Footer', row, row.Insert_Footer)" ng-model="row.Insert_Footer"></input></td> 
								    </tr>
								    </tbody>
							  	</table>
						  	</div>	
						</div>
	  					<div  style ="float: left; width: 99%;" class="dwrapper tab-pane"  id="pRates">
	  						<div ng-controller="pratessheet">
								<table  datatable="ng" dt-options="dtOptions" class="ExcelTable2007">
									<thead>
									<tr class="column-label">
								      <td></td>
								      <td  class="heading" ng-repeat="pcolumn in pratesColumns" style="">{{pcolumn}}</td>
								    </tr>
								    </thead>
								    <tbody>
								    <tr  style="height: 35px !important"  class="column-grid table-input" ng-repeat="row in pratesRows" >
								      <td style = " overflow-x: auto; overflow-y: hidden;" class="row-label heading"">{{row.prates_Yname}}</td>
								      <td><input ng-blur="savePrates('W', row, row.W)" ng-model="row.W"></input></td>
								      <td><input ng-blur="savePrates('H', row, row.H)" ng-model="row.H"></input></td>
								      <td><input ng-blur="savePrates('oneX', row, row.oneX)" ng-model="row.oneX"></input></td>
								      <td><input ng-blur="savePrates('threeX', row, row.threeX)" ng-model="row.threeX"></input></td>
								      <td><input ng-blur="savePrates('sixX', row, row.sixX)" ng-model="row.sixX"></input></td>
								      <td><input ng-blur="savePrates('twelveX', row, row.twelveX)" ng-model="row.twelveX"></input></td>
								      <td><input ng-blur="savePrates('eighteenX', row, row.eighteenX)" ng-model="row.eighteenX"></input></td>
								      
								      <td><input ng-blur="savePrates('twentyFourX', row, row.twentyFourX)" ng-model="row.twentyFourX"></input></td>
								      <td><input ng-blur="savePrates('thirtySixX', row, row.thirtySixX)" ng-model="row.thirtySixX"></input></td>
								      <td><input ng-blur="savePrates('metallic', row, row.metallic)" ng-model="row.metallic"></input></td>
								      <td><input ng-blur="savePrates('standard', row, row.standard)" ng-model="row.standard"></input></td>
								      <td><input ng-blur="savePrates('matched', row, row.matched)" ng-model="row.matched"></input></td>
								      <td><input ng-blur="savePrates('four_color', row, row.four_color)" ng-model="row.four_color"></input></td>
								    </tr>
								    </tbody>
							  	</table>
						  	</div>
						</div>
	  					<div class="tab-pane" id="addSpec">
	  					</div>
	  					</div>
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
<script type="text/javascript">
function isFile() {
/* 	var ele=document.getElementById("file").value;
	if(ele!= null && ele!="") {
		 return true;
	} else {
		alert("Select file before submitting!");
		return false;
	} */
}
</script>
<script type="text/ng-template" id="show-original-image.html">
	<table style="width: 99%;">
			<tr>
			<img data-ng-src="{{originalImagePath}}" style = "height : 400px; width : 460px;" alt="" >
			</tr>
	</table>
</script>

<script type="text/ng-template" id="add-edit-Image.html">
		<table style="width: 99%; " class="table Contextual  table-striped table-bordered table-condensed table-hover">
			<tr>
				<th  style="width:16%;">Image Logo</th>
				<td>
					<form onsubmit="return isFile();" method="POST" id="upload-csv-data" enctype="multipart/form-data">
					<input type="file" style = "width: 50%; width: 50%; float: left;" ng-file-select="onFileSelect($files)" >
					<button type="submit" ng-click="fileUpload(subscription);closeThisDialog()" style = "margin-left: 2%;" class="btn btn-mini btn-success">Upload</button>
					<button class="btn btn-dafault" ng-click="resetEditImageId();closeThisDialog()" >Cancel</button></form>
				</td>
				
				
			</tr>
		</table>
</script>
<script type="text/ng-template" id="add-edit-user.html">
		<table style="width: 99%; " class="table Contextual  table-striped table-bordered table-condensed table-hover">
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
  angular.module('agora', ['angularTreeview', 'ng-context-menu' ,'ngDialog', 'ngTable', 'angularFileUpload','datatables']).factory('MyHttpInterceptor', function ($q) {
                                    return {
                                      request: function (config) {
                                                  $('#loading-id').show();
                                                  return config || $q.when(config);           
                                      },
                                 
                                      requestError: function (rejection) {
                                                  $('#loading-id').hide();
                                          return $q.reject(rejection);
                                      },
                                 
                                      // On response success
                                      response: function (response) {
                                                  $('#loading-id').hide();
                                          return response || $q.when(response);
                                      },
                                 
                                      // On response failture
                                      responseError: function (rejection) {
                                                  $('#loading-id').hide();
                                          return $q.reject(rejection);
                                      }
                                    };
                  })
                   .config(function ($httpProvider) {
                                   $httpProvider.interceptors.push('MyHttpInterceptor');  
                   });
</script>

<script type="text/javascript" src="agora-angular-manage-subscription.js"></script>
