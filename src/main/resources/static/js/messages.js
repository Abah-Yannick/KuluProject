
	myApp.controller('commentsCtrl', function ($scope) {
        var markdown, postAuthorEmail;
        postAuthorEmail = 'jan.kanty.pawelski@gmail.com';
        
        $scope.comments = [];
        $scope.restService.get('data/messages.json', function (data) {
        	$scope.comments = data;
            }
        );
        $scope.newComment = {};
        markdown = function (string) {
            string = string.replace(/(@.+)@/g, '<span class="reply">$1</span>');
            string = string.replace(/\*\*(.+)\*\*/g, '<strong>$1</strong>');
            string = string.replace(/__(.+)__/g, '<strong>$1</strong>');
            string = string.replace(/\*(.+)\*/g, '<em>$1</em>');
            string = string.replace(/_(.+)_/g, '<em>$1</em>');
            string = string.replace(/``(.+)``/g, '<code>$1</code>');
            string = string.replace(/`(.+)`/g, '<code>$1</code>');
            return string;
        };
        $scope.parseContent = function (content) {
            return $sce.trustAsHtml(content);
        };
        $scope.isAuthor = function (email) {
            return email === postAuthorEmail;
        };
        $scope.getGravatar = function (email) {
            var hash;
            if (email === void 0) {
                email = '';
            }
            hash = email.trim();
            hash = hash.toLowerCase();
            hash = md5(hash);
            return '//gravatar.com/avatar/' + hash + '?s=104&d=identicon';
        };
        $scope.loveComment = function (commentId) {
            var comment, i, len, ref, results;
            ref = $scope.comments;
            results = [];
            for (i = 0, len = ref.length; i < len; i++) {
                if (window.CP.shouldStopExecution(1)) {
                    break;
                }
                comment = ref[i];
                if (comment.id === commentId) {
                    results.push(comment.loved = !comment.loved);
                } else {
                    results.push(void 0);
                }
            }
            window.CP.exitedLoop(1);
            return results;
        };
        $scope.addReply = function (author) {
            if ($scope.newComment.content === void 0) {
                $scope.newComment.content = '';
            }
            if ($scope.newComment.content.search('@' + author + '@') === -1) {
                if ($scope.newComment.content[0] === '@') {
                    $scope.newComment.content = ', ' + $scope.newComment.content;
                } else {
                    $scope.newComment.content = ' ' + $scope.newComment.content;
                }
                return $scope.newComment.content = '@' + author + '@' + $scope.newComment.content;
            }
        };
        $scope.addNewComment = function () {
            $scope.newComment.id = $scope.comments.length + 1;
            $scope.newComment.author.website = $scope.newComment.author.website.replace(/https?:\/\/(www.)?/g, '');
            $scope.newComment.content = markdown($scope.newComment.content);
            $scope.newComment.loved = false;
            $scope.comments.push($scope.newComment);
            return $scope.newComment = {};
        };
        return $scope.$watch('newComment.email', function (newValue, oldValue) {
            var newCommentAvatar;
            newCommentAvatar = document.getElementById('newCommentAvatar');
            return newCommentAvatar.src = $scope.getGravatar($scope.newComment.email);
        });
    });