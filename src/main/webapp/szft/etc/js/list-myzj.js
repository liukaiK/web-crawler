// 修改列表超链接的样式问题
$(document).ready(function() {
			var trlength = $('#NewsListConter table:eq(0) tbody tr').length;
			for ( var i = 1; i <= trlength - 2; i++) {
				$('#NewsListConter table:eq(0) tbody tr:eq(' + i + ') td:eq(0) a').css({
					'width' : '700px',
					'white-space' : 'nowrap',
					"text-overflow" : "ellipsis",
					"overflow" : "hidden",
					"float" : "right"
				});
			}
			

			$('#NewsListConter2 table tbody tr td:eq(0)').css({
				'width' : '300px'
			});
			$('#NewsListConter2 table tbody tr td:eq(1)').css({
				'width' : '200px'
			});
			$('#NewsListConter2 table tbody tr td:eq(2)').css({
				'width' : '250px'
			});
			
			
			
		})