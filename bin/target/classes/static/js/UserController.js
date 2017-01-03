myApp.controller("UserController",function($scope,$rootScope, UserService,AuthService) {
          var self = this;
          
          //----------------------------------------------------------
          
  		

		self.login = function() {
			UserService.login(self.username, self.password).then(
					function(success) {
						alert('STATUT :' + success.status + '  TOKEN : '
								+ success.data);
						AuthService.saveToken(success.data);

					},
					function(error) {
						alert('STATUT :' + error.status + '  TOKEN : '
								+ error.data);

					});
		};

		self.getUser = function() {
			UserService.getUser(self.idUser).then(function(success) {

				alert(JSON.stringify(success.data));

			}, function(error) {
				alert('STATUT :' + error.status + '  ERROR : ' + error.data);

			});
		};

		self.logout = function() {
			AuthService.logout && AuthService.logout()
		}
		self.isAuthed = function() {
			return AuthService.isAuthed ? AuthService.isAuthed() : false
		}
		
		
		//----------------------------------------------------------------------------------------------
          
               
 
      });