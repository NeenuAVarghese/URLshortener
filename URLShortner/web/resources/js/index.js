var uSh_handler = {
		landpage:{
			navbar:{
				logout: "#uSh_logout",
				signup: "#uSh_Signup",
				login: "#uSh_login",
				brand: "#uSh_brand",
				username: "#uSh_username"
			}
		},
		loginCard: {
			handle: "#uSh_loginCard",
			username: "#uSh_loginCard-loginId",
			password: "#uSh_loginCard-loginPassword",
			loginButton:".uSh_loginAction",
			errorMessage: "#pt_loginCard-errorMessage",
			getLoginStatus: "#getLoginStatus"
		},
		signupCard:{
			handle: "#uSh_signupCard",
			username:"#uSh_signupCard-loginId",
			password: "#uSh_signupCard-password",
			signupButton:".uSh_signupAction",
			getSignupStatus: "#getSignUpStatus",
			errorMessage:"#uSh_signupCard-errorMessage"
		},
		jumbotron_longToShort: {
			inputHandler: "#uSh_LongtoShjumbotron",
			processButton: "#us_convertlongToShort",
			inputbox: "#uSh_getLongUrl",
		},
		jumbotron_shortToLong:{
			handle: "#uSh_jumbotron",
			processButton: "#us_convertShortToLong",
			inputbox: "#uSh_getShortUrl",
			longUrlVal: "#uSh_longUrlVal"
		},
		showLongUrlCard:{
			handle: "#uSh_showlongUrlModal",
			urlSuccessDiv: "#getLongUrlSuccess",
			urlFailDiv: "#getLongUrlFail",
			setUrlVal: "#uSh_setlongVal",
			closeButton: "#uSh_showlongUrlModalClose"

		},
		userPageUrlList:{
			handle: "#uSh_userUrlList",
			longUrl: "#uSh_userLongUrl",
			shortUrl: "#uSh_userShorturl",
			copyButton: "#uSh_copy",
			deleteButton: "#uSh_Delete"
		},
		copyModal:{
			handle: "#copyModal"
		}

};



var main = function () {
	//Get Value returned for Long URL
	var longUrl = $(uSh_handler.jumbotron_shortToLong.longUrlVal).text();
	if(longUrl === "" || longUrl === "null"){

	}
	else{
		if(longUrl === "undefined"){
			$(uSh_handler.showLongUrlCard.urlSuccessDiv).hide();
			$(uSh_handler.showLongUrlCard.urlFailDiv).show();
			$(uSh_handler.showLongUrlCard.handle).modal("show");
		}
		else{
			$(uSh_handler.showLongUrlCard.urlSuccessDiv).show();
			$(uSh_handler.showLongUrlCard.urlFailDiv).hide();	
			$(uSh_handler.showLongUrlCard.setUrlVal).text(longUrl);
			$(uSh_handler.showLongUrlCard.setUrlVal).attr("href", longUrl);
			$(uSh_handler.showLongUrlCard.handle).modal("show");
		}
	}

	//Handle if Login failed
	var loginFailed = $(uSh_handler.loginCard.getLoginStatus).text();
	if(loginFailed === "true"){
		$(uSh_handler.loginCard.errorMessage).show();
		$(uSh_handler.loginCard.handle).modal("show");
	}

	//Handle if SignUp failed
	var signupFailed = $(uSh_handler.signupCard.getSignupStatus).text();
	if(signupFailed === "true"){
		$(uSh_handler.signupCard.errorMessage).show();
		$(uSh_handler.signupCard.handle).modal("show");
	}


	$(uSh_handler.showLongUrlCard.handle).on('hide.bs.modal', function () {
		$.ajax({
			url: "home",
			data:{"action":"page"},
			method: "POST",
			success: function(){
				console.log("ok");  
			}
		});
	});
	//Handle if Long to short URL button is clicked
	$(uSh_handler.jumbotron_longToShort.processButton).click(function(){

		var val = $(uSh_handler.jumbotron_longToShort.inputbox).val();
		//Check if URL consists of http:// or https://
		if (val.indexOf('http://') === -1 && val.indexOf('https://') === -1) {
			val = 'http://' + val;
		}
		//Send data to servlet
		$.ajax({
			url: "shortenURL",
			data: {"longUrl":val},
			method: "POST",
			success: function(){
				location.reload();           
			}
		});
	});

}


function copyToClipboard(element) {

	var textarea = document.createElement("textarea");
	textarea.textContent = element;
	textarea.style.position = "fixed"; // Prevent scrolling to bottom of page in MS Edge.
	document.body.appendChild(textarea);
	textarea.select();
	try {
		document.execCommand("copy"); // Security exception may be thrown by some browsers.
	} catch (ex) {
		console.warn("Copy to clipboard failed.", ex);
	} finally {
		document.body.removeChild(textarea);
		$(uSh_handler.copyModal.handle).modal("show");
	}
}

function urldelete(element){
	$.ajax({
		url: "delete",
		data: {"urlToRemove":element},
		method: "POST",
		success: function(){
			location.reload();           
		},
		error: function(){
		}
	});
}

function callUserServlet(){
	$.ajax({
		url: "home",
		data:{"action":"page"},
		method: "GET",
		success: function(){
			location.reload();           
		}
	});
}



$(document).ready(main);