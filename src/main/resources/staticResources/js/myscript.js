$(function(){'use strict';
	$('#ajaxButton').button().click(function(){
		$.ajax({
			url:'/list',
			success: function(data){
				var text = [];
				text.push("<table>");
				for (var i = 0; i<data.length; i++){
					var row = [];
					row.push("<tr>");
					for (var key in data[i]){
						row.push("<td>"+data[i][key]+"</td>");
					}
					row.push("</tr>");
				text.push(row.join());
				}
				text.push("</table>");
				
				$('#table').html(text.join());
			}
		});
	});
});