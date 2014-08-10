<!DOCTYPE html>
<html>
<head>
	<title>Agora - Welcome</title>
	
	<link rel="shortcut icon" href="agora_icon.ico"/>
	<script type="text/javascript" src="jq.js"></script>
	<script type="text/javascript" src="angular.min.js"></script>
	<link href="new.css" rel="stylesheet" type="text/css" />
	<link type="text/css" rel="stylesheet" href="skins/skin1/top.css"/>
	<script type="text/javascript" src="skinable_tabs.min.js"></script>
</head>
<body id="template" ng-app='agora' ng-controller="PermissionController">
<a href="adminReportList.m" class="logo" title="Essentia: AGORA"><img src="logo_agora.png"></a>

	<div class="tabs_holder">
		<ul>
  			<li><a href="#role-tab">By Roles</a></li>
  			<li class="tab_selected"><a href="#user-tab">By User</a></li>
 		</ul>
 		
 		<div class="content_holder">
 		
 			<div id="role-tab">
 				<table border=1 style="background-color: azure;width: 100%;">
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
 			
 			<div id="user-tab">
 			<input ng-model="searchText">
 				<table border=1 style="background-color: azure;width: 100%;">
					<tr style="width: 100%;">
						<td style="border:1pt solid black;width: 10%;">User</td>
						<td style="border:1pt solid black;width: 40%;">Role</td>
						<td style="border:1pt solid black;width: 50%;">Permission</td>
					</tr>
					<tr ng-repeat="u in users | filter:searchText">
						<td style="border:1pt solid black;width: 10%;">{{u.un}}</td>
						<td style="border:1pt solid black;width: 40%;">
							<div style="float: left;" ng-repeat="r in u.hasRoles">
								<input type="checkbox" ng-click="userRoleChange($event, r.id, u.id)" value="{{r.id}}" ng-checked="{{r.st==1}}">{{r.rn}}
							</div>
						</td>
						
						<td style="border:1pt solid black;width: 50%;">
							<div style="float: left;" ng-repeat="a in u.hasActions">
								<input type="checkbox" ng-click="userPermissionChange($event, a.id, u.id)" value="{{a.id}}" ng-checked="{{a.st==1}}">{{a.an}}
							</div>
						</td>
					</tr>
				</table>		
 			</div>
 		
 		</div>
	</div>
	
	
	
</body>

</html>

<script>
	angular.module('agora',[])
	.controller('PermissionController',function($scope,$http){
		$scope.roles = ${rolesAsJson};
		$scope.users = ${usersAsJson};
		
		$scope.roleChange = function(e, rid, aid) {
			$http.post("role-permission/" + ($(e.target).attr('checked') == 'checked') + "/" + rid + "/" + aid);
		}
		
		$scope.userRoleChange = function(e, rid, uid) {
			$http.post("user-role/" + ($(e.target).attr('checked') == 'checked') + "/" + uid + "/" + rid);
		}
		
		$scope.userPermissionChange = function(e, aid, uid) {
			$http.post("user-permission/" + ($(e.target).attr('checked') == 'checked') + "/" + uid + "/" + aid);
		}
	})
	
	$('.tabs_holder').skinableTabs({
    	effect: 'basic_display',
    	skin: 'skin1',
    	position: 'top'
  	});
</script>