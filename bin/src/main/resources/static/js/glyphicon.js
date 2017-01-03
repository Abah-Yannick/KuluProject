	myApp.controller('glyficonCtrl',function(CoursesService,$scope,$rootScope,$cookieStore) {
		
		$scope.hand = function (id){
			$('#collapse'+id).on('shown.bs.collapse', function () {
				   $('#collapse'+id+'gly').removeClass("glyphicon-hand-down").addClass("glyphicon-hand-up");
				});

			$('#collapse'+id).on('hidden.bs.collapse', function () {
				   $('#collapse'+id+'gly').removeClass("glyphicon-hand-up").addClass("glyphicon-hand-down");
				});
			};
		

		
		
	});
		    
