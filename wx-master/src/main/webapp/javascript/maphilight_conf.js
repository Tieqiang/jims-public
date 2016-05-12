
	$.fn.maphilight.defaults = {
		fill : true,
		fillColor : '00afff',
		fillOpacity : 0.4,
		stroke : true,
		strokeColor : '00afff',
		strokeOpacity : 0,
		strokeWidth : 0,
		fade : true,
		alwaysOn : false,
		neverOn : false,
		groupBy : false,
		wrapClass : true,
		shadow : false,
		shadowX : 0,
		shadowY : 0,
		shadowRadius : 6,
		shadowColor : '000000',
		shadowOpacity : 0.8,
		shadowPosition : 'outside',
		shadowFrom : false
	};
	

		$('.male_fore').maphilight({
			wrapClass : 'wrap_fore'
		});
		$('.male_rear').maphilight({
			wrapClass : 'wrap_rear'
		});
		$('.female_fore').maphilight({
			wrapClass : 'wrap_fore'
		});
		$('.female_rear').maphilight({
			wrapClass : 'wrap_rear'
		});

	