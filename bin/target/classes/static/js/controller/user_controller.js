'use strict';

myApp
		.controller(
				'UserController',
				[       '$translate',
						'$scope',
						'$rootScope',
						'UserService',
						'AuthService',
						'ngToast',
						'$window',
						'$cookieStore',
						function($translate,$scope, $rootScope, UserService, AuthService,
								ngToast, $window, $cookieStore) {
					
//					var topbr = function(){
//						$(window).scroll(
//					
//						    {
//						        previousTop: 0
//						    }, 
//						    function () {
//						    var currentTop = $(window).scrollTop();
//						    if (currentTop < this.previousTop) {
//
//						        $("#topbr").show();
//						    } else {
//						    
//						        $("#topbr").hide();
//						    }
//						    this.previousTop = currentTop;
//						});
//					};
//					topbr();
							if($cookieStore.get('profileImagePath')){
							$rootScope.profileImagePath = $cookieStore.get('profileImagePath');
							}
							if($cookieStore.get('user')){
								$rootScope.user = $cookieStore.get('user');
								}
							
							var self = this;

							self.usercheck = '';
							self.color = '';

							self.login = function(username, password) {
								UserService
										.login(username, password)
										.then(
												function(success) {
													var params = AuthService.parseJwt(success.data);

													
													$rootScope.filename = params.username;
													$cookieStore.put('profileImagePath', params.image);
													$cookieStore.put('user', self.user);
													$rootScope.profileImagePath = $cookieStore.get('profileImagePath');
													$rootScope.user =  $cookieStore.get('user');

																				
													AuthService.saveToken(success.data);

												},
												function(error) {
													ngToast.danger('  ERROR : '
															+ error.data);
												});
							};

							self.getUser = function() {
								UserService
										.getUser(self.idUser)
										.then(
												function(success) {

													alert(JSON
															.stringify(success.data));

												},
												function(error) {
													ngToast
															.danger('  ERROR : '
																	+ error.data.message);

												});
							};
							self.checkUserByEmail = function(valid) {
								if (valid) {
									UserService.checkUserByEmail(
											self.user.email).then(
											function(success) {
												// self.emailcheck =
												// success.data.message;
												// self.coloremail = {
												// "color" : "#00cc00"
												// }

											}, function(error) {
												self.emailcheck = error.data;
												self.coloremail = {
													"color" : "#ff3300"
												}

											})
								} else {
									self.emailcheck = '';
								}

							};

							self.checkUserByUsername = function(valid) {
								if (valid) {
									UserService.checkUserByUsername(
											self.user.username).then(
											function(success) {
												// self.usercheck =
												// success.data;
												// self.coloruser = {
												// "color" : "#00cc00"
												// }
											}, function(error) {
												self.usercheck = error.data;
												self.coloruser = {
													"color" : "#ff3300"
												}

											})
								} else {
									self.usercheck = '';
								}

							};

							self.register = function() {
								UserService.register(self.user.username,
										self.user.password,
										self.user.firstname,
										self.user.lastname, self.user.email).then(
										function(success) {
											ngToast.create('User Created successfuly');
											self.login(success.data.username,success.data.password);

										},
										function(error) {
											ngToast.danger('  ERROR : '
													+ error.data.message);
										});
							};

							self.logout = function() {
								AuthService.logout && AuthService.logout()
								self.user = {
									username : '',
									email : '',
									firstname : '',
									lastname : '',
									password : '',
									password2 : '',
									image : ''
								};
								
								$cookieStore.remove('profileImagePath');
								$cookieStore.remove('user');
								$rootScope.user = '';
								$rootScope.profileImagePath ='';
							}
							self.isAuthed = function() {
								return AuthService.isAuthed ? AuthService
										.isAuthed() : false
							};

							$rootScope.download = function() {
								var url = 'http://localhost:8080/download?filename='
										+ $rootScope.user.username;
								UserService.downloadFileFromUrl(url).then(
										function(success) {
											ngToast.create(success.data);

											// code to download image here
										},
										function(error) {
											ngToast.danger('  ERROR : '
													+ error.data);
										});
							}
							


							// $rootScope.download = function(file) {
							//			
							// var downloadUrl =
							// 'http://localhost:8080/download/'+file;
							// UserService.downloadFileFromUrl(downloadUrl).then(function(success)
							// {
							// ngToast.create(success.data);
							// }, function(error) {
							// ngToast.danger( ' ERROR : '+ error.data.message);
							// });
							// };
							//         

							self.changeModal = function(modal) {

								$scope.modalP = modal;
								$("#" + modal).fadeIn(1000);
							};
							
							$scope.changeLanguage = function(key) {
								$translate.use(key);
							};

							/*------------------------------ FACEBOOK AUTH ---------------------------------*/

							self.FBLogin = function() {
								FB
										.login(function(response) {
											if (response.authResponse) {
												console
														.log('Welcome!  Fetching your information.... ');
												FB
														.api(
																'/me',
																function(
																		response) {
																	console
																			.log('Good to see you, '
																					+ response.name
																					+ '.');
																	self.user = {
																		id : null,
																		username : response.name,
																		address : '',
																		email : ''
																	};
																	ngToast
																			.create('Welcome  '
																					+ self.user.username);
																	self.authorized = true;
																	console
																			.log(response);

																	var accessToken = FB
																			.getAuthResponse();

																	console
																			.log(accessToken);
																});
											} else {
												console
														.log('User cancelled login or did not fully authorize.');
											}
										});
							}

							// window.fbAsyncInit = function() {
							// FB.init({
							// appId : '1754053898168120',
							// xfbml : true,
							// version : 'v2.6'
							// });
							// };
							//
							// (function(d, s, id) {
							//				var js, fjs = d.getElementsByTagName(s)[0];
							//				if (d.getElementById(id)) {
							//					return;
							//				}
							//				js = d.createElement(s);
							//				js.id = id;
							//				js.src = "//connect.facebook.net/en_US/sdk.js";
							//				fjs.parentNode.insertBefore(js, fjs);
							//			}(document, 'script', 'facebook-jssdk'));
							//			
							//			
							//			/*---------------------------------------------------GOOGLE AUTH ------------------------------------------------*/
							//			$rootScope.signedIn = false;
							//		      $scope.loginGoogle = $cookieStore.get('cookieLoginGoogle');
							//		      if ($scope.loginGoogle != undefined && $scope.loginGoogle !='') {
							//		        $scope.loginGoogle = $cookieStore.get('cookieLoginGoogle');   
							//		        $rootScope.signedIn = true;
							//		      }else{	  
							//		      $rootScope.signedIn = false;
							//		     
							//			  
							//			  function onSignIn(googleUser) {
							//				    var profile = googleUser.getBasicProfile();
							//				    console.log('ID: ' + profile.getId());
							//				    console.log('Name: ' + profile.getName());
							//				    console.log('Image URL: ' + profile.getImageUrl());
							//				    console.log('Email: ' + profile.getEmail());			   
							//					
							//					
							//				    $rootScope.user={id:profile.getId(),username:profile.getName(),address:'',email:profile.getEmail(),image:profile.getImageUrl()};
							//				    $cookieStore.put('cookieLoginGoogle', $rootScope.user.username);
							//				    $scope.profile = profile;
							//				    console.log("signedIn :  "+$rootScope.signedIn);
							//				    $rootScope.signedIn = true;
							//				    console.log("signedIn :  "+$rootScope.signedIn);
							//				   }
							//
							//                
							//				  window.onSignIn = onSignIn;
							//				  
							//		      }

						} ]);
