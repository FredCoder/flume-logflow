$(function() {
	init();
});

/* init */
var loadingFlag = false;
function init() {
	isotopeLayout();
	
	getServiceNames();
	getLogtypes();

	refreshTable();
    scrollLoad();
    
    connectWebSocket();
}


function isotopeLayout(){
	$('.vertical-list').isotope({
		itemSelector : '.vertical-list__item',
		layoutMode : 'vertical',
		vertical : {
			horizontalAlignment : 0.3
		}
	});
}

function scrollLoad(){
	$(window).scroll(function(){
		console.log(loadingFlag);
		if ($(document).height() -$(window).height() -20  <=  $(window).scrollTop() && !loadingFlag){
			var page = $('#hide-page').val();
			$('#hide-page').val(parseInt(page) + 1);
			loadingFlag = true;
			appendTable();
		}
	});
}

function getServiceNames() {
	var dom = $('.logflow-services');
	$.post("/logflow/getServiceNames", function(data) {
		$(data).each(function(i, item) {
			var html = makeServiceNames(item);
			dom.append(html);
		})
		dom.find("button").each(function(x, btnItem) {
			$(this).on('click', function() {
				$("#hide-page").val(1);
				dom.find("button").removeClass("is-checked");
				$(this).addClass("is-checked");
				$(".hide-services").val($(this).val());
				refreshTable();
				closeWebSocket();
				connectWebSocket();
			})
		});

	});
}

function getLogtypes() {
	var dom = $('.logflow-types');
	$.post("/logflow/getLogTypes", function(data) {
		$(data).each(function(i, item) {
			var html = makeLogTypes(item);
			dom.append(html);
		})
		dom.find("button").each(function(x, btnItem) {
			$(this).on('click', function() {
				$("#hide-page").val(1);
				dom.find("button").removeClass("is-checked");
				$(this).addClass("is-checked");
				$(".hide-types").val($(this).val());
				refreshTable();
			})
		});
	});
}

/* controller */

function refreshTable() {
	var param = $("#queryParam").serializeArray();
	$(param).each(function(i, item) {
		if (isEmpty(item.value)) {
			removeArrayItem(param, item)
		}
	});
	queryPage(param);
}

function appendTable() {
	var param = $("#queryParam").serializeArray();
	$(param).each(function(i, item) {
		if (isEmpty(item.value)) {
			removeArrayItem(param, item)
		}
	});
	queryPageAppend(param);
}


/* service */

function loadContentEvent(item) {
	$(item).on('click', function() {
        $(this).toggleClass('gigante_item');
        $('.vertical-list').isotope('layout');
        var id = $(this).children('.id').html();
        queryLogContent(item, id);
        //$(this).children('.text_content_hide').toggleClass('text_content_show');
    });
}

function queryLogContent(item, idNumber) {
	if ($(item).children('div').hasClass('text_content_hide')){
		if (parseInt(idNumber) == 0) {
			$(item).children('.text_content_hide').toggleClass('text_content_show');
		} else {
			$.post("/logflow/queryLogContent", {
				id : idNumber
			}, function(data) {
				console.log(data.content);
				$(item).find('.log-content').html(data.content);
				$(item).children('.text_content_hide').toggleClass('text_content_show');
			});
		}
	}
}

function queryPage(param) {
	$.post("/logflow/queryPage", param, function(data) {
		$('.vertical-list').isotope("remove",$('.vertical-list__item')).isotope("layout");
		$(data.rows).each(function(i, row){
			var $item = $(makeRow(row));
			$('.vertical-list').prepend($item).isotope('prepended',$item);
			$('.vertical-list').isotope('layout');
			loadContentEvent($item);
		});
	});
}

function queryPageAppend(param) {
	$.post("/logflow/queryPage", param, function(data) {
		$(data.rows).each(function(i, row){
			var $item = $(makeRow(row));
			$('.vertical-list').append($item).isotope('appended',$item);
			$('.vertical-list').isotope('layout');
			loadContentEvent($item);
		});
		loadingFlag = false;
	});
}

/* template */

function makeServiceNames(item) {
	return '<button class="button" data-filter=".' + item.toLowerCase() + '" value="'+item+'">'
			+ item + '</button>';
}

function makeLogTypes(item) {
	return '<button class="button" data-filter=".' + item.toLowerCase() + '" value="'+item+'">'
			+ item + '</button>';
}

function makeRow(row) {
	var html = '<li class="vertical-list__item logflow-vertical gigante_hight logflow-vertical-'+row.logType.toLowerCase()+'">'
			+ '<div class="id" style="display:none;">' + row.id + '</div>'
			+ '<div class="log-type">'+row.logType+'</div>'
			+ '<div class="service-name">'+row.serviceName+'</div>'
			+ '<div class="pack">'+row.pack+'</div>'
			+ '<div class="log-date">'+row.logDateStr+'</div>'
			+ '<div class="collect-time">'+row.collectTimeStr+'</div>'
			+ '<div class="text_content_hide" style="width: 100%;height: 85%">'
			+ '<pre class="code-display" style="height: 100%;overflow-x: auto;overflow-y: auto;">'
			+ '<code class="js log-content">'
			+ '</code>'
			+ '</pre>'
			+ '</div>'
			+ '</li>';
	return html;
}

function makeWsRow(row) {
	row = JSON.parse(row);
	var html = '<li class="vertical-list__item logflow-vertical gigante_hight logflow-vertical-'+row.logType.toLowerCase()+'">'
			+ '<div class="id" style="display:none;">' + row.id + '</div>'
			+ '<div class="log-type">'+row.logType+'</div>'
			+ '<div class="service-name">'+row.serviceName+'</div>'
			+ '<div class="pack">'+row.pack+'</div>'
			+ '<div class="log-date">'+row.logDateStr+'</div>'
			+ '<div class="collect-time">'+row.collectTimeStr+'</div>'
			+ '<div class="text_content_hide" style="width: 100%;height: 85%">'
			+ '<pre class="code-display" style="height: 100%;overflow-x: auto;overflow-y: auto;">'
			+ '<code class="js log-content">'
			+ row.content
			+ '</code>'
			+ '</pre>'
			+ '</div>'
			+ '</li>';
	return html;
}

/* anime event*/

/* utils */

function isEmpty(value) {
	if (value !== null && value !== undefined && value.length != 0) {
		return false;
	}
	return true;
}

function removeArrayItem(arr, item) {
	return arr.splice($.inArray(item, arr), 1);
}
