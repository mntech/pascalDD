angular.module('agora')
	.controller('ManageCompanyController',function($scope, $http, ngTableParams,$filter,beData,ngDialog){
		$scope.roles = beData.role();
		//$scope.compsData = beData.company();
		
		
		
		$scope.unAssigned;
		$scope.dragFrom;
		$scope.dragElementId;
		$scope.subscriptionSelected=null;
		$scope.list1 = [];
		$scope.list2 = [];
		$scope.list3= [];
		$scope.draglist1 = function (e) {
			console.log(e.currentTarget.id);
			  $scope.dragFrom = "1";
			  $scope.dragElementId = e.currentTarget.id; 
		};
		 
		$scope.draglist2 = function (e) {
			console.log(e);
			$scope.dragElementId = e.currentTarget.id;
			console.log($scope.dragElementId);
			  $scope.dragFrom = "2";
		};
		  
		$scope.draglist3 = function (e) {
			//console.log(id);
			$scope.dragElementId = e.currentTarget.id;
			  $scope.dragFrom = "3";
		};
		$scope.setSelectedIndex = function (index) {
			  $scope.selectedIndex = index;
		};
		  
		$scope.unsetSelectedIndex = function () {
			  $scope.selectedIndex = null;
			  $scope.subscriptionSelected = null;
		};
		  
		//TODO here For Company Tab
		$scope.loadLists = function () {
			$http.get('loadLists').success(function(data) {
				$scope.list1 = data.unAssignedList;
				$scope.list2 = data.assignedList;
				$scope.list3 = data.assignedChildList;
				$scope.unAssigned = data.totalUnAssignedNumber;
				$scope.parent = data.totalParentNumber;
				$scope.child = data.totalChildNumber;
				$scope.dragFrom=0;
			//	usSpinnerService.stop('loading...');
			});
		};
		
		$scope.dragTo = 0;
		$scope.optionsList1 = {
				
			accept: function(dragEl) {
				if($scope.dragFrom == "2" || $scope.dragFrom == "3") {
					
				    return true;
				} else {
					return false;
				}
			}
		};
		  
		$scope.optionsList2 = {
			accept: function(dragEl) {
				if($scope.dragFrom == "1") {
					return true;
				} else {
					return false;
				}
			} 	
		};
		
		$scope.list1Drop = function() {
			
			
			if($scope.dragFrom == "2") {
				console.log($scope.dragElementId);
				$http.get('removeparentsubscription/'+$scope.dragElementId)
				.success(function(data, status, headers, config) {
					if($scope.subscriptionSelected == $scope.dragElementId) {
						$scope.subscriptionSelected = null;
						$scope.selectedIndex = -1;
					}
					$scope.loadLists();
					$scope.dragFrom=0;
				});
		     } else if($scope.dragFrom == "3") {
		    	 console.log("in list 1 drop dragfrom=3");
		    	 $http.get('removechildsubscription/'+$scope.dragElementId)
		    	 .success(function(data, status, headers, config) {
		    		 $scope.dragFrom=0;
		    		 $scope.loadLists();
		    	 });
		     }
		};
		$scope.list2Drop = function() {
			$http.get('addparentsubscription/'+$scope.dragElementId)
			.success(function(data, status, headers, config) {
				$scope.dragFrom=0;
				$scope.loadLists();
			});
		};
		
		$scope.list3Drop = function() {
			  
			  //console.log("in drop function list 3");
			$http.get('addchildsubscription/'+$scope.dragElementId+'/'+$scope.subscriptionSelected)
			.success(function(data, status, headers, config) {
				$scope.dragFrom=0;
				$scope.loadLists();
			});
		};
		 
		$scope.optionsList3 = {
			accept: function(dragEl) {
				if($scope.dragFrom == "1" && $scope.subscriptionSelected != null) {
					return true;
				} else if($scope.dragFrom == "1" && $scope.subscriptionSelected == null){
					//console.log("in list 3 accept dragfrom=0");
					//alert("Please Select Parent Before Adding Child!");
					return false;
				} else {
					return false;
				}
			}	    
		};
		
	});