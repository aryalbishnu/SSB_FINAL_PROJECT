const options = { weekday: 'long', year: 'numeric', month: 'long', day: 'numeric' }
setInterval(() => {
	let time = new Date();
	let hour = time.getHours();
	let timeofDay = (hour < 12) ? "AM" : "PM";
	if (hour > 12) {
		hour = hour - 12;
	}
	if (hour < 10) {
		hour = "0" + hour
	}
	if (hour === 0) {
		hour = 12;
	}
	let minute = time.getMinutes();
	if (minute < 10) {
		minute = "0" + minute;
	}
	let second = time.getSeconds();
	if (second < 10) {
		second = "0" + second
	}

	let date = time.toLocaleDateString(undefined, options);
	let setTime = hour + ":" + minute + ":" + second + "  " + timeofDay;
	//document.getElementById("timeSet").innerHTML=setTime + "  " + date;
	document.getElementById("timeSetByBody").innerHTML = setTime;
	document.getElementById("dateSetByBody").innerHTML = date;
}, 1000);



