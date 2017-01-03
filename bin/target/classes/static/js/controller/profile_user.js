myApp.controller("profileCtrl", function($scope) {
	
	var ospry = new Ospry('pk-test-obv85lmtsitlfasuvrt7kjb6');

	var onUpload = function(err, metadata) {
	  ospry.get({
	    url: metadata.url,
	    maxHeight: 400,
	    imageReady: function(err, domImage) {
	      $('body').append(domImage);          
	    },
	  });
	};

	$('#up-form').submit(function(e) {
	  e.preventDefault();
	  ospry.up({
	    form: this,
	    imageReady: onUpload,
	  });
	});
	
	$scope.test = function(){
		
		alert('test function upload');
	}
})