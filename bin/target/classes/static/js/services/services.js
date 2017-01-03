'use strict';

// simple stub that could use a lot of work...
myApp.factory('RESTService',
    function ($http) {
        return {
            get:function (url, callback) {
                return $http({method:'GET', url:url}).
                    success(function (data, status, headers, config) {
                        callback(data);
                        //console.log(data.json);
                    }).
                    error(function (data, status, headers, config) {
                        console.log("failed to retrieve data");
                    });
            }
        };
    }
);

myApp.factory('AuthService',
    function ($cookieStore,$window,$rootScope,ngToast) {

        return {
        	parseJwt :function(token) {
    			//division du JWT pour obtenir la payload qui contient les claims
    			var base64Url = token.split('.')[1];
    			var base64 = base64Url.replace('-', '+').replace('_', '/');
    			return JSON.parse($window.atob(base64));
    		},

    		saveToken :function(token) {
    			$window.localStorage['jwtToken'] = token
    			$rootScope.token = token;
    		},
    		logout :function() {
    			$window.localStorage.removeItem('jwtToken');
    			$rootScope.token = '';
    			$cookieStore.remove('profileImagePath');
				$rootScope.profileImagePath ='';
    			$cookieStore.remove('user');
				$rootScope.user ='';
    		},
    		getToken : function() {
    			return $window.localStorage['jwtToken'];
    		},
    		// Je controle que le token est toujours valide
    		isAuthed : function() {
    			var srvc = this;
    			var token = srvc.getToken();
    			if (token) {
    				var params = srvc.parseJwt(token);
    				$rootScope.user ={username:params.username,
    						firstname:params.firstname,
    						lastname:params.lastname,
    						email:params.email,
    						image:params.image,
    						id : params.id};
    				
    				if(Math.round(new Date().getTime() / 1000) > params.exp){
    					$rootScope.user = {
    							username : '',
    							email : '',
    							firstname : '',
    							lastname : '',
    							password : '',
    							image : '',
    							id : ''
    						};
    					srvc.logout();
    				}
    				
    				return Math.round(new Date().getTime() / 1000) <= params.exp;
    			} else {
					$rootScope.user = {
							username : '',
							email : '',
							firstname : '',
							lastname : '',
							password : '',
							image : '',
							id : ''
						};
    				return false;
    			}
    		}
        };
    }
);

myApp.factory('UserService', 
	function($http,API){
	
return {
        // the $http API is based on the deferred/promise APIs exposed by the $q service
        // so it returns a promise for us by default 
            
            login : function(username, password) {
    			var req = {
    				method : 'GET',
    				url : API + '/user/authenticate?username=' + username
    						+ '&password=' + password,
    				headers : {
    					'Content-Type' : 'application/x-www-form-urlencoded'
    				}
    			};
    			return $http(req);
    		},
    		checkUserByEmail : function(email) {
    			var req = {
    				method : 'GET',
    				url : API + '/user/checkemail?email=' + email,
    				headers : {
    					'Content-Type' : 'application/x-www-form-urlencoded'
    				}
    			};
    			return $http(req);
    		},
    		checkUserByUsername : function(username) {
    			var req = {
    				method : 'GET',
    				url : API + '/user/checkusername?username=' + username,
    				headers : {
    					'Content-Type' : 'application/x-www-form-urlencoded'
    				}
    			};
    			return $http(req);
    		},
            register : function(username,password,firstname,lastname,email,image) {
    			var req = {
    				method : 'POST',
    				url : API + '/user/register?username=' + username+ '&password=' + password+ '&firstname=' + firstname+ '&lastname=' + lastname+ '&email=' + email,
    				headers : {
    					'Content-Type' : 'application/x-www-form-urlencoded'
    				}
    			};
    			return $http(req);
    		},
    		
    		getUser : function(id) {
    			var req = {
    				method : 'GET',
    				url : API + '/rest/security/finditembyid?itemid=' + id,
    				headers : {
    					'Content-Type' : 'application/x-www-form-urlencoded'
    				}
    			};
    			return $http(req);
    		},
    		upload : function(filename) {
    			var req = {
    				method : 'POST',
    				url : API + '/upload?filepath=' + filename,
    				headers : {
    					'Content-Type' : 'application/x-www-form-urlencoded'
    				}
    			};
    			return $http(req);
    		},
    		getProfileImage: function(username) {
    			var req = {
    				method : 'GET',
    				url : API + '/getProfileImage?username=' + username,
    				headers : {
    					'Content-Type' : 'application/x-www-form-urlencoded'
    				}
    			};
    			return $http(req);
    		}
         
    };
    
});

myApp.factory('CoursesService', 
		function($http,API){
		
	return {
	        // the $http API is based on the deferred/promise APIs exposed by the $q service
	        // so it returns a promise for us by default 
	            
	            courses : function() {
	    			var req = {
	    				method : 'GET',
	    				url : API + '/Cours/coursAll',
	    				headers : {
	    					'Content-Type' : 'application/x-www-form-urlencoded'
	    				}
	    			}
	    			return $http(req)
	            },
		          user : function(id) {
		    			var req = {
		    				method : 'GET',
		    				url : API + '/users/'+id,
		    				headers : {
		    					'Content-Type' : 'application/x-www-form-urlencoded'
		    				}
		    			};
		    			return $http(req);
	    		},	            
	    		saveCmt : function(cText,coursArgsId,userId) {
		    			var req = {
		    				method : 'POST',
		    				url : API + '/userCmts/cmt?cText='+ cText +'&coursArgsId='+ coursArgsId +'&userId='+ userId,
		    				headers : {
		    					'Content-Type' : 'application/x-www-form-urlencoded'
		    				}
		    			};
		    			return $http(req);
	    		},
	    		saveReplyCmt : function(cText,cId,coursArgsId,userPostId,userReplyId) {
	    			var req = {
	    				method : 'POST',
	    				url : API + '/userCmts/replycmt?cText='+ cText +'&coursArgsId='+ coursArgsId + '&cId='+ cId + '&userPostId='+ userPostId + '&userReplyId='+userReplyId,
	    				headers : {
	    					'Content-Type' : 'application/x-www-form-urlencoded'
	    				}
	    			};
	    			return $http(req);
    		}
	    		
	}
})


myApp.factory('authInterceptor', 
		function(API,AuthService,$rootScope){
	return {
		// automatically attach Authorization header
		request : function(config) {
			var token = AuthService.getToken();
			config.headers = config.headers || {};
			//config.url.indexOf(API+ '/upload/' + $rootScope.filename
			
			if (config.method === 'POST' && token && config.url!= "https://api.cloudinary.com/v1_1/dxglghm6m/upload") {
				config.headers['Authorization'] = token;
				//.defaults.common['Authorization'] = token;
				//config.headers.token = token;
			}
			return config;
		}
	}
});

//myapp.factory('httpRequestInterceptor', function () {
//	  return {
//	    request: function (config) {
//
//	      config.headers['Authorization'] = 'Basic d2VudHdvcnRobWFuOkNoYW5nZV9tZQ==';
//	      config.headers['Accept'] = 'application/json;odata=verbose';
//
//	      return config;
//	    }
//	  };
//	});


