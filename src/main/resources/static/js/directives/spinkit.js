myApp.directive('routeLoadingIndicator', function($rootScope) {
  return {
    restrict: 'E',
    template: '<div ng-show="isRouteLoading" >'+
   '<div id="man">'+
   '<div id="eye-l"></div>'+
   '<div id="eye-r"></div>'+
   '<div id="nose"></div>'+
   '<div id="mouth"></div>'+
   '</div>'+
   '</div>',
  
    replace: true,
    link: function(scope, elem, attrs) {
      scope.isRouteLoading = false;
 
      $rootScope.$on('$routeChangeStart', function() {
        scope.isRouteLoading = true;
      });
      $rootScope.$on('$routeChangeSuccess', function() {
        scope.isRouteLoading = false;
      });
    }
  };
});
