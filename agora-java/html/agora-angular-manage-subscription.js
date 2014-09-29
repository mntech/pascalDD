angular.module('agora').directive(
            "repeatComplete",
            function( $rootScope ) {
 
                // Because we can have multiple ng-repeat directives in
                // the same container, we need a way to differentiate
                // the different sets of elements. We'll add a unique ID
                // to each set.
                var uuid = 0;
 
 
                // I compile the DOM node before it is linked by the
                // ng-repeat directive.
                function compile( tElement, tAttributes ) {
 
                    // Get the unique ID that we'll be using for this
                    // particular instance of the directive.
                    var id = ++uuid;
 
                    // Add the unique ID so we know how to query for
                    // DOM elements during the digests.
                    tElement.attr( "repeat-complete-id", id );
 
                    // Since this directive doesn't have a linking phase,
                    // remove it from the DOM node.
                    tElement.removeAttr( "repeat-complete" );
 
                    // Keep track of the expression we're going to
                    // invoke once the ng-repeat has finished
                    // rendering.
                    var completeExpression = tAttributes.repeatComplete;
 
                    // Get the element that contains the list. We'll
                    // use this element as the launch point for our
                    // DOM search query.
                    var parent = tElement.parent();
 
                    // Get the scope associated with the parent - we
                    // want to get as close to the ngRepeat so that our
                    // watcher will automatically unbind as soon as the
                    // parent scope is destroyed.
                    var parentScope = ( parent.scope() || $rootScope );
 
                    // Since we are outside of the ng-repeat directive,
                    // we'll have to check the state of the DOM during
                    // each $digest phase; BUT, we only need to do this
                    // once, so save a referene to the un-watcher.
                    var unbindWatcher = parentScope.$watch(
                        function() {
 
                            console.info( "Digest running." );
 
                            // Now that we're in a digest, check to see
                            // if there are any ngRepeat items being
                            // rendered. Since we want to know when the
                            // list has completed, we only need the last
                            // one we can find.
                            var lastItem = parent.children( "*[ repeat-complete-id = '" + id + "' ]:last" );
 
                            // If no items have been rendered yet, stop.
                            if ( ! lastItem.length ) {
 
                                return;
 
                            }
 
                            // Get the local ng-repeat scope for the item.
                            var itemScope = lastItem.scope();
 
                            // If the item is the "last" item as defined
                            // by the ng-repeat directive, then we know
                            // that the ng-repeat directive has finished
                            // rendering its list (for the first time).
                            if ( itemScope.$last ) {
 
                                // Stop watching for changes - we only
                                // care about the first complete rendering.
                                unbindWatcher();
 
                                // Invoke the callback.
                                itemScope.$eval( completeExpression );
 
                            }
 
                        }
                    );
 
                }
 
                // Return the directive configuration. It's important
                // that this compiles before the ngRepeat directive
                // compiles the DOM node.
                return({
                    compile: compile,
                    priority: 1001,
                    restrict: "A"
                });
 
            }
        );

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
				}else{
					return null;
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