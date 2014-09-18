angular.module('agora')
	.controller('PermissionController',function($scope, $http, ngTableParams,$filter,beData,ngDialog){
		$scope.roles = beData.role();
		//$scope.compsData = beData.company();
		
		
		
		$scope.editUser = {};
		$scope.getColor = {};
		
		$scope.oldu = "";
		$scope.roleChange = function(e, rid, aid) {
			$http.post("role-permission/" + ($(e.target).attr('checked') == 'checked') + "/" + rid + "/" + aid);
		}
		
		$scope.compRoleChange = function(e, rid, cid) {
			$http.post("company-role/" + ($(e.target).attr('checked') == 'checked') + "/" + cid + "/" + rid);
		}
		
		$scope.userRoleChangeForNewUser = function(e, r) {
			if($(e.target).attr('checked') == 'checked') {
				$scope.editUser.roles.push({
					i : r,
					c : 0
				});
			
			} else {
				//todo
			}
		};
		
		$scope.userRoleChange = function(e, r) {
			if($(e.target).attr('checked') == 'checked') {
				r.c=1;
			
			} else {
				r.c=0;
			}
			
		};
		
	
		
		$scope.manageUserForCompany = function(c, cu){
			$scope.oldu = cu;
			$scope.editUser = {
					ca:c.a,
					a:cu.a,
					i : cu.i,
					c: c.n,
					e: cu.e,
					fn: cu.fn,
					ln: cu.ln,
					n: cu.n,
					pw: cu.pw,
					sd: cu.sd,
					un: cu.un,
					roles:cu.roles
			}
			
			$scope.mode = "add";
			ngDialog.open({
				template: 'manage-edit-user.html',
				scope: $scope,
				className: 'ngdialog-theme-default'
			});
			
		};
		
		$scope.users = [];
		$scope.showPanelId = -1;
		$scope.getUserForCompany = function(cid) {
			$scope.showPanelId = cid ; 
			$http.get('get-users/'+cid).success(function(data){
				$scope.users = data;
            });
		}
		
		
		$scope.editCompany = {};
		$scope.getCompany = function(c){
			$scope.onManageCompany(c);
		};
		
		
		
		$scope.showUserPanel = false;
		$scope.showRolePanel = true;
		$scope.showCompanyPanel = false;
		
		$scope.userWait = true;
		var usersData = {};
		var companyData = {};
		forCompanyTab = function() {
			$http.get('get-company').sucess(function(data){
				companyData = data;
				alert("1");
				//$scope.tableCompany = new ngTableParams();
				$scope.tableCompany = new ngTableParams(
						{page: 1, count: 20},
						{
							getData: function($defer, params) {
								 var orderedData = params.filter() ? $filter('filter')(companyData, params.filter()) : companyData;
				                 $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
				                 params.total(orderedData.length); // set total for recalc pagination
								
							}
						});
				$scope.companyWait = false;
			});
		};
		
		//forCompanyTab();
		forUserTab  = function() {
			$http.get('get-users').success(function(data){
				usersData = data;
				
				$scope.tableUsers = new ngTableParams(
					{page: 1, count: 20},
					{
						getData: function($defer, params) {
							 var orderedData = params.filter() ? $filter('filter')(usersData, params.filter()) : usersData;
			                 $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
			                 params.total(orderedData.length); // set total for recalc pagination
							
						}
					});
				$scope.userWait = false;
			});
			
		};
		
		forUserTab();
		var compsData = {};
		$scope.companyWait = true;
		forCustomer  = function() {
			$http.get('get-companies').success(function(data){
				compsData = data;
				$scope.tableCompany = new ngTableParams(
						{
			        		page: 1, count: 50, filter:{a:1}          
			    		},
			    		{
			    			getData: function($defer, params) {
			    			   var orderedData = params.filter() ? $filter('filter')(compsData, params.filter()) : compsData;
			                   $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
			                   params.total(orderedData.length); // set total for recalc pagination
			    			}
			    		});
				$scope.companyWait = false;
			});
			
		};
		
		forCustomer();
		
		CopyUserProp   = function(oldc,newc) {
			oldc.c=newc.c;
			oldc.e=newc.e;
			oldc.fn=newc.fn;
			oldc.ln=newc.ln;
			oldc.n= newc.fn + " " + newc.ln; 
			oldc.pw=newc.pw;
			oldc.pw=newc.pw;
			oldc.un=newc.un;
			oldc.sd=newc.sd;
		}
		
		CopyCompnayProp   = function(oldc,newc) {
			oldc.ci=newc.ci;
			oldc.cn=newc.cn;
			oldc.cr=newc.cr;
			oldc.em=newc.em;
			oldc.ph=newc.ph;
			oldc.cp=newc.cp;
		}
		
		
	
		$scope.userUpdate  = function() {
			$http.post("saveUser/"+$scope.editUser.i , $scope.editUser).success(function(data){
				
				CopyUserProp($scope.oldu,$scope.editUser);
				$scope.resetEditUserId();
			});
		}
		$scope.onUserUpdate  = function(c) {
			$http.post("saveUser/"+$scope.editUser.i , $scope.editUser).success(function(data){
				CopyUserProp(c,$scope.editUser);
				$scope.resetEditUserId();
			});
		}
		
		$scope.mode = "edit";
		$scope.addUserForCompany  = function(c) {
			$scope.editUser = {
					c: c.n,
					e: "",
					fn: "",
					i: -1,
					ln: "",
					n: "",
					pw: "",
					a:1,
					sd: "",
					un: "",
					roles:[],
			}
			$scope.mode = "add";
			ngDialog.open({
				template: 'add-edit-user.html',
				scope: $scope,
				className: 'ngdialog-theme-default'
			});
			//$http.post("saveUser".i,$scope.editUser);
		};
		
		$scope.mode = "edit";
		$scope.onUserAdd  = function() {
			$scope.editUser = {
					c: "",
					e: "",
					fn: "",
					i: -1,
					ln: "",
					n: "",
					pw: "",
					a:1,
					sd: "",
					un: "",
			}
			$scope.mode = "add";
			ngDialog.open({
				template: 'add-new-user.html',
				scope: $scope,
				className: 'ngdialog-theme-default'
			});
			//$http.post("saveUser".i,$scope.editUser);
		};
		
		$scope.mode = "edit";
		$scope.oldc="";
		$scope.onManageCompany  = function(c) {
			$scope.oldc = c;
			$scope.editCompany = {
					a:c.a,
					cn:c.n,
					ci:c.i,
					cr: c.cr,
					em: c.em,
					ph:c.ph,
					nt:c.nt,
					cp:c.cp,
			}
			$scope.mode = "add";
			ngDialog.open({
				template: 'manage-edit-company.html',
				scope: $scope,
				className: 'ngdialog-theme-default'
			});
			//$http.post("saveUser".i,$scope.editUser);
		}
		
		$scope.mode = "edit";
		$scope.onManageUser  = function(c, cu) {
			
			//$http.post("saveUser".i,$scope.editUser);
		}
		
		$scope.mode = "edit";
		$scope.onCompanyAdd  = function(c) {
			$scope.editCompany = {
					n: "",
					cp:"",
					cr: "",
					em: "",
					ci: -1,
					ph:"",
					nt:"",
					a:1
			}
			$scope.mode = "add";
			ngDialog.open({
				template: 'add-edit-company.html',
				scope: $scope,
				className: 'ngdialog-theme-default'
			});
			//$http.post("saveUser".i,$scope.editUser);
		};
		
		$scope.onUserSave = function() {
			//usersData.unshift($scope.editUser);
			$http.post("saveUser",$scope.editUser).success(function(data) {
				usersData.unshift(data);
				$scope.users.push(data);
			});
		};
		
		$scope.onCompanySave = function() {
			//compsData.unshift($scope.editCompany);
			$http.post("saveCompany",$scope.editCompany).success(function(data) {
				//$scope.tableCompany.data.push($scope.editCompany);
				compsData.unshift(data);
				//forCustomer();
				$scope.tableCompany.reload();
			});
		};

		$scope.onCompanyUpdate = function() {
			$http.post("updateCompany/"+$scope.editCompany.cn , $scope.editCompany).success(function(data) {
				CopyCompnayProp($scope.oldc, $scope.editCompany);
				//$scope.resetEditUserId();
			});
		}
		
		
		$scope.activeCompany = function(ci) {
			$http.post("activeCompany/"+ci ).success(function(data) {
				/*for(var j = 0; j<data.length;j++) {
					$scope.users[j].a = 1;
				}*/
				
				for(var j = 0; j<compsData.length;j++) {
					if(compsData[j].i == ci){
						compsData[j].a = 1;
						break;
					}
				}
			});
		};
		$scope.deleteCompany = function(ci) {
		
			$http.post("deleteCompany/"+ci ).success(function(data) {

				for(var j = 0; j<compsData.length;j++) {
					if(compsData[j].i == ci){
						compsData[j].a = 0;
						break;
					}
				}
				//Jagbir: this will not work
				/*for(var j = 0; j < data.length;j++) {
					$scope.users[j].a = 0;
				}*/
			});
		}
		
		$scope.activeUser = function(uid) {
			$http.post("activeUserOfCompany/"+uid ).success(function(data) {
				for(var j = 0; j<usersData.length;j++) {
					if(usersData[j].i == uid){
						usersData[j].a = 1;
						break;
					}
				}
			});
		};
		$scope.activeUserOfCompany = function(cu) {
			$http.post("activeUserOfCompany/"+cu.i ).success(function(data) {
				for(var j = 0; j<$scope.users.length;j++) {
					if($scope.users[j].i == cu.i){
						$scope.users[j].a = 1;
						break;
					}
				}
			});
		};
		$scope.deleteUser = function(uid){
			$http.post("deleteUserOfCompany/"+uid ).success(function(data) {
				for(var j = 0; j<usersData.length;j++) {
					if(usersData[j].i == uid){
						usersData[j].a = 0;
						break;
					}
				}
			});
		};
		$scope.deleteUserOfCompany = function(cu) {
			$http.post("deleteUserOfCompany/"+cu.i ).success(function(data) {
				for(var j = 0; j<$scope.users.length;j++) {
					if($scope.users[j].i == cu.i){
						$scope.users[j].a = 0;
						break;
					}
				}
			});
		};
		
		$scope.editUserId = -1;
		$scope.resetEditUserId = function() {
			$scope.editUserId = -1;
			$scope.mode = "edit";
		}
		
		$scope.setEditUserCtx   = function(c) {
			$scope.mode = "edit";
			$scope.editUserId = c.i;
			$scope.editUser = angular.copy(c);
		}
		
	});