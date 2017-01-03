'use strict';

// declare top-level module which depends on filters,and services
var myApp = angular.module('myApp',
		    [  'ngResource',
		       'cloudinary',
		       'photoAlbumAnimations',
		       'photoAlbumControllers',
		       'photoAlbumServices',
		    'myApp.filters', 'myApp.directives', // custom directives
		    'ngGrid', // angular grid
		    'ui', // angular ui
		    'ngSanitize', // for html-bind in ckeditor
		    'ui.ace', // ace code editor
		    'ui.bootstrap', // jquery ui bootstrap
		    '$strap.directives', // angular strap
		    'ngRoute', 'pascalprecht.translate', 'ngCookies', 'angular-spinkit','ngToast','lr.upload' ]);

		myApp.constant('API', 'http://localhost:8080');
		//http://192.168.1.3:8080

var filters = angular.module('myApp.filters', []);
var directives = angular.module('myApp.directives', []);



// bootstrap angular
myApp.config([
              'cloudinaryProvider',
		'$routeProvider',
		'$locationProvider',
		'$httpProvider',
		'$translateProvider',
		'ngToastProvider',
		function(cloudinaryProvider,$routeProvider, $locationProvider, $httpProvider,
				$translateProvider, ngToastProvider) {
			$httpProvider.interceptors.push('authInterceptor');
			
			  

			var translateEN = {

				"LANG" : "en",
				"LANG-EN" :"English",
				"LANG-FR"  : "French",
				"TITLE" : "Hello",
				"FOO" : "This is a paragraph.",
				"BUTTON_LANG_EN" : "english",
				"BUTTON_LANG_FR" : "french",
				"USERPROFILE" : "User Profile",
				"CHANGEPWD" : "Change password",
				"SIGNIN" : "Sign up",
				"SIGNOUT" : "Sign out",
				"LOGIN"  : "Sign in",
				"POST"  :  "Add a comment"
			};
			var translateFR = {
				"LANG" : "fr",
				"LANG-EN" :"Anglais",
				"LANG-FR"  : "Français",
				"TITLE" : "Salut",
				"FOO" : "Ceci est un Paragraphe.",
				"BUTTON_LANG_EN" : "englais",
				"BUTTON_LANG_FR" : "français",
				"USERPROFILE" : "Profile Utilisateur",
				"CHANGEPWD" : "Changer le mot de passe",
				"SIGNIN" : "Inscription",
				"SIGNOUT" : "Sortir",
				"LOGIN"  : "Connexion",
				"POST"  :  "Commenter"
			};
			$translateProvider.translations('en', translateEN)
					.preferredLanguage('en');
			$translateProvider.translations('fr', translateFR);
			$translateProvider.useCookieStorage();
			
			cloudinaryProvider.set("cloud_name", "dxglghm6m").set("upload_preset", "zmgjp3sd");

			// Enable escaping of HTML
			// $translateProvider.useSanitizeValueStrategy('sanitize');

			// TODO use html5 *no hash) where possible
			// $locationProvider.html5Mode(true);

			$routeProvider.when('/', {
				templateUrl : 'partials/home.html'
			});

			$routeProvider.when('/fs2', {
				templateUrl : 'partials/fs2/fs2.html',
				controller : 'FS2Controller'
			});

			$routeProvider.when('/contact', {
				templateUrl : 'partials/contact.html'
			});
			$routeProvider.when('/about', {
				templateUrl : 'partials/about.html'
			});
			$routeProvider.when('/faq', {
				templateUrl : 'partials/faq.html'
			});
			$routeProvider.when('/404', {
				templateUrl : '404.html'
			});
			$routeProvider.when('/users', {
				templateUrl : 'users.html'
			});
			$routeProvider.when('/login', {
				templateUrl : 'partials/login.html'
			});
			$routeProvider.when('/register', {
				templateUrl : 'partials/register.html'
			});
			$routeProvider.when('/forgot', {
				templateUrl : 'partials/forgot.html'
			});
			$routeProvider.when('/chat', {
				templateUrl : 'partials/chat.html'
			});
		    $routeProvider.when('/photos', {
		        templateUrl: 'partials/photo-list.html',
		        resolve: {
		          photoList: function ($q, $rootScope, album) {
		            if (!$rootScope.serviceCalled) {
		              return album.photos({}, function (v) {
		                $rootScope.serviceCalled = true;
		                $rootScope.photos = v.resources;
		              });
		            } else {
		              return $q.when(true);
		            }
		          }
		        }
		      });
			$routeProvider.when('/profile', {
				templateUrl : 'partials/profile.html',
		        controller: 'photoUploadCtrl'
		      });

			// note that to minimize playground impact on app.js, we
			// are including just this simple route with a parameterized
			// partial value (see playground.js and playground.html)
			$routeProvider.when('/playground/:widgetName', {
				templateUrl : 'playground/playground.html',
				controller : 'PlaygroundCtrl'
			});

			// by default, redirect to site root
			$routeProvider.otherwise({
				redirectTo : '/',
				templateUrl : 'partials/home.html'
			});

			ngToastProvider.configure({
				animation : 'fade' // or 'slide'
			});

		} ]);

myApp.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            var model = $parse(attrs.fileModel);
            var modelSetter = model.assign;
            
            element.bind('change', function(){
                scope.$apply(function(){
                    modelSetter(scope, element[0].files[0]);
                });
            });
        }
    };
}]);

myApp.controller("MyCtrl",
		function($scope, $translate, $cookieStore, $location) {

			$scope.visib = $cookieStore.get('cookievisib');
			if ($scope.visib != undefined) {
				$scope.visib = $cookieStore.get('cookievisib');
			} else {
				$scope.visib = true;
				$cookieStore.put('cookievisib', $scope.visib)
			}

			$scope.visibilita = function(visibilita) {
				// Removing a cookie
				$cookieStore.remove('cookievisib');
				$scope.visib = visibilita;
				// Put cookie
				$cookieStore.put('cookievisib', $scope.visib);
			}
			// Get cookie
			// var favoriteCookie = $cookieStore.get('myFavorite');

			$scope.changeLanguage = function(key) {
				$translate.use(key);
			};
			
			

		});



// this is run after angular is instantiated and bootstrapped
myApp.run(function($cookieStore,CoursesService,$rootScope, $location, $http, $timeout, AuthService,
				RESTService, UserService) {

			// *****
			// Eager load some data using simple REST client
			// *****

			$rootScope.restService = RESTService;

			// async load constants
			$rootScope.constants = [];
			$rootScope.restService.get('data/constants.json', function(data) {
				$rootScope.constants = data[0];
			});

			// async load data do be used in table (playgound grid widget)
			$rootScope.listData = [];
			$rootScope.restService.get('data/generic-list.json',
					function(data) {
						$rootScope.listData = data;
					});

			$rootScope.listCours = [];
//			$rootScope.restService.get('data/listCours.json', function(data) {
//				$rootScope.listCours = data;
//			}); 
			
			if($cookieStore.get('courArg')){
				
				$rootScope.courArg = $cookieStore.get('courArg');
			}
			
			$rootScope.coursesLoad = function(){
				CoursesService.courses()
				.then(
						function(success) {
							console.log(success.data);
							$rootScope.listCours = success.data;
							$rootScope.courArgs = $rootScope.listCours.courArgs;

						},
						function(error) {

						});
			};
			
			$rootScope.coursesLoad();

			$rootScope.comments = [];
			$rootScope.restService.get('data/messages.json', function(data) {
				$rootScope.comments = data;
			});




			// TODO move this out to a more appropriate place
			$rootScope.faq = [
					{
						key : "What is Angular-Enterprise-Seed?",
						value : "A starting point for server-agnostic, REST based or static/mashup UI."
					},
					{
						key : "What are the pre-requisites for running the seed?",
						value : "Just an HTTP server.  Add your own backend."
					},
					{
						key : "How do I change styling (css)?",
						value : "Change Bootstrap LESS and rebuild with the build.sh script.  This will update the appropriate css/image/font files."
					} ];

		});
