angular.module('agora')
	.controller('SubscriptionController',function($scope, $http, ngDialog, $upload){
		
		$scope.roleList ;
		$scope.success=false;
		$scope.failure=false;
		$scope.isSubscription=false;
		$scope.subscription;
		$scope.newSubscription;
		$scope.editIdCat = -1;
		$scope.editIdImage= -1;
		$scope.subId;
		$scope.originalImagePath;
		$scope.img;
		$scope.X_names;
		$scope.Y_names;
		$scope.valueMap;
		$scope.orates_id = null;
		
		// Jagbir Change for freeze row col - start
		$scope.dtOptions = {
				paging: false,
				scrollY: '600px',
				scrollX: "100%",
				searching: false,
				ordering: false,
				scrollCollapse: true,
		}
		
		$scope.$on('event:dataTableLoaded', function(event, loadedDT) {
		   new $.fn.dataTable.FixedColumns(loadedDT.dt );
		   $('.dataTables_scrollBody').find('.column-label').hide();
		});
		// Jagbir Change for freeze row col - end
		$scope.upload = {
				file:''
		};
		$scope.$watch( 'mytree.currentNode', function( newObj, oldObj ) {
		    if( $scope.mytree && angular.isObject($scope.mytree.currentNode) ) {
		        	
		        	if($scope.orates_id != null){
		        		sheet();
		        	}else{
		        		loadSubSubscription($scope.mytree.currentNode);
		        	}
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
		
		function Matrix($scope) {
			$scope.valueMap = {
			    aRow: {
			        '0': '',
			        '1': '',
			        '2': '',
			        '3': '',
			        '4': '',
			        '5': ''
			    }
			   };
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
					$scope.img = "getImagePath/"+data.id+"?d="+new Date().getTime();
					$scope.originalImagePath = "getOriginalImagePath/"+data.id+"?d="+new Date().getTime();
					//usSpinnerService.stop('loading...');
				});
			}
		};
		
		$scope.setEditIdCat = function (subscription) {
			
			$scope.mode = "edit";
			$scope.editIdCat = subscription.id;
			$scope.subscription = angular.copy(subscription);
			
			
		};
		$scope.setEditIdImage = function (subscription) {
			$scope.mode = "edit";
			$scope.editIdImage = subscription.id;
		};
		
		$scope.resetEditUserId = function () {
			$scope.editIdCat = -1;
		};
		$scope.resetEditImageId = function () {
			$scope.editIdImage = -1;
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
		$scope.inableOrates = function () {
			$scope.orates_id = 1;
		};
		
		$scope.disabledOrates = function () {
			$scope.orates_id = null;
		};
		sheet = function( $parse) {
			$http.get("getXnamelist")
			.success(function(data) {
				console.log(data);
				$scope.X_names = data;
				
				var id = 1;
				if($scope.mytree.currentNode != null){
					id = $scope.mytree.currentNode.subscriptionId;
				}
				$http.get("getMatrixDataById/"+id)
				.success(function(_data) {
					
			$scope.columns =[];
			for(var j =0; j<$scope.X_names.length;j++){
				$scope.columns.push($scope.X_names[j]);
			}
			var x =$scope.X_names[0];
		    $scope.rows = _data; 
		    $scope.cells = {};
		    /*for(var i =0; i<_data.length;i++){
		    	$scope.rows.push(_data[i].Y_names);
		    	for(var j =0; j<$scope.X_names.length;j++){
		    		$scope.cells.push(_data[i]);
				}
		    }*/
		    process = function(exp) {
		      return exp.replace(/[A-Z]\d+/, function(ref) {
		        return 'compute("' + ref + '")';
		      })
		    }
		    $scope.compute = function(cell) {
		      return $parse(process($scope.cells[cell]))($scope);
		    };
			});
			});
		  };
		
		$scope.save = function(column, row, value) {
			$http.post("saveMatrixData/"+column+"/"+row.Y_names+"/"+row.subscription.id+"/"+value).success(function(data) {
				console.log(data);
			});
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
		
		var file=null;
		
		$scope.onFileSelect = function($files) {
		    file=$files[0];
		};
		
		$scope.fileUpload = function (sub) {
			if(file){
			$scope.upload = $upload.upload({
					url: 'uploadandsave', 
					method:'POST',
					fileFormDataName:'file',
					file:file,
				}).progress(function(evt) {
					console.log('percent: ' + parseInt(100.0 * evt.loaded / evt.total));
				}).success(function(data, status, headers, config) {
					if(data == "sucess"){
						alert("image upload successfully");
						$scope.img = "getImagePath/"+sub.id+"?d="+new Date().getTime();
						$scope.originalImagePath = "getOriginalImagePath/"+sub.id+"?d="+new Date().getTime();
						$scope.resetEditImageId();
					}else{
						alert("image upload Fail");
						
					}
					
				});
			}else{
				alert("Choose file");
			}
		};
		$scope.showImagePopup = function(id){
				ngDialog.open({
					template: 'show-original-image.html',
					scope: $scope,
					className: 'ngdialog-theme-default'
				});
			
		};
	
	});