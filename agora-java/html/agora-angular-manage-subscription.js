angular.module('agora')
	.controller('SubscriptionController',function($scope, $http, ngDialog){
		
		$scope.roleList ;
		$scope.success=false;
		$scope.failure=false;
		$scope.isSubscription=false;
		$scope.subscription;
		$scope.newSubscription;
		$scope.editIdCat = -1;
		$scope.subId;
		$scope.$watch( 'mytree.currentNode', function( newObj, oldObj ) {
		    if( $scope.mytree && angular.isObject($scope.mytree.currentNode) ) {
		        	loadSubSubscription($scope.mytree.currentNode);
		    }
		}, false);
		
		$scope.loadmedia = function () {
			//usSpinnerService.spin('loading...');
			$http.get('loadmedia')
			.success(function(data, status, headers, config) {
				if(data.length>0) {
					$scope.roleList=data;
					
				} 
				//usSpinnerService.stop('loading...');
			});
		};
		
		loadSubSubscription = function (data) {
			$scope.editIdCat = -1;
			if(data!=null) {
				$http.get("loadsubmedia/"+data.subscriptionId+"/"+data.title)
				.success(function(data, status, headers, config) {
					$scope.success=false;
					$scope.failure=false;
					$scope.isSubscription=true;
					$scope.subscription=data;
					//usSpinnerService.stop('loading...');
				});
			}
		};
		
		$scope.setEditIdCat = function (subscription) {
			
			$scope.mode = "edit";
			$scope.editIdCat = subscription.id;
			$scope.subscription = angular.copy(subscription);
			
			
		};
		$scope.resetEditUserId = function () {
			$scope.editIdCat = -1;
		};
		$scope.onEditSave = function (subscription) {
			$http.post("updateSubscription", subscription).success(function(data) {
				$scope.subscription=data;
				$scope.resetEditUserId();
			});
		};
		
		$scope.onRightClick = function (sub) {
			$scope.subId = sub.subscriptionId;
		};
		$scope.onParentAdd = function () {
			$scope.subId = null;
		};
		$scope.onCompanyAdd  = function() {
			$scope.newSubscription = {
					title: "",
			}
			$scope.mode = "add";
			ngDialog.open({
				template: 'add-edit-company.html',
				scope: $scope,
				className: 'ngdialog-theme-default'
			});
			//$http.post("saveUser".i,$scope.editUser);
		};
		$scope.onCompanySave = function() {
			if ($scope.subId != null) {
				$http.post("saveCompany/"+$scope.subId , $scope.newSubscription).success(function(data) {
					if(data != "" ){
						for(var i = 0; i<$scope.roleList.length; i++){
							if($scope.roleList[i].subscriptionId == data.subscriptionId){
								$scope.roleList.splice(i,1);
								$scope.roleList.splice(i,0, data);
								$scope.subscription = data.children[data.children.length-1];
								
							}
						}
					}else{
						alert("Invalid Operation");
					}
					
					$scope.resetEditUserId();
				});
			} else {
				$http.post("saveParentCompany" , $scope.newSubscription).success(function(data) {
					$scope.subscription = data;
					$scope.roleList.push(data);
					$scope.resetEditUserId();
				});
			}
			
		};
		$scope.onCompanyDelete = function () {
			$http.post("deleteCompanyById/"+$scope.subId).success(function(data) {
				if(data != "" ){
					for(var i = 0; i<$scope.roleList.length; i++){
						if($scope.roleList[i].subscriptionId == data.subscriptionId){
							$scope.roleList.splice(i,1);
							$scope.roleList.splice(i,0, data);
							$scope.subscription = data.children[data.children.length-1];
							
						}
					}
				}else{
					for(var i = 0; i<$scope.roleList.length; i++){
						if($scope.roleList[i].subscriptionId == $scope.subId){
							$scope.roleList.splice(i,1);
						}
					}
				}
				
			});
		};
		
	});