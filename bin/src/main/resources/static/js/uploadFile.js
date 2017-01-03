
myApp
	.controller(
			'uploadFileCtrl',
			[
					'$scope',
					'$rootScope',
					'UserService',
					'AuthService',
					'ngToast',
					'$window',
					'$cookieStore',
					function($scope, $rootScope, UserService, AuthService,
							ngToast, $window, $cookieStore) {

	jQuery(document).ready(function($) {
	    $('form input[type=file]').each(function(){
	 
	        $(this).change(function(evt){
	            var input = $(this);
	            var file = input.prop('files')[0];
	            var regex = /^(image\/)(gif|(x-)?png|p?jpeg)$/i;
	 
	            if( file && file.size < 2 * 1048576 && file.type.search(regex) != -1 ) { // 2 MB (this size is in bytes)
	 
	            	ngToast.create( 'Success!' );
	 
	            }else{
	 
	         //       alert( 'File non ammesso - Tipo: ' + file.type + '; dimensioni: ' + file.size );
	                ngToast.danger('File non ammesso - Tipo: ' + file.type + '; dimensioni: ' + file.size);
	 
	                input.replaceWith( input.val('').clone(true) );
	 
	                evt.preventDefault();
	            }   
	        })
	    });
	});
	
	$rootScope.upload = function() {
		UserService
				.upload($rootScope.filename,$rootScope.token)
				.then(
						function(success) {

							ngToast.create(success.data);

						},
						function(error) {
							ngToast
									.danger('  ERROR : '
											+ error.data.message);

						});
	};
}]);
