$(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});

// SAVE ============================================
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();

	// Form validation-------------------
	var status = validatePatientForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}

	// If valid------------------------
	var type = ($("#hidSave").val() == "") ? "POST" : "PUT";
	$.ajax({
		url : "PatientsAPI",
		type : type,
		data : $("#formPatient").serialize(),
		dataType : "text",
		complete : function(response, status) {
			onPatientSaveComplete(response.responseText, status);
		}
	});
});
function onPatientSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divPatientsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidpatientidSave").val("");
	$("#formPatient")[0].reset();
}

$("#formPatient").submit();

// UPDATE==========================================
$(document).on(
		"click",
		".btnUpdate",
		function(event) {
			$("#hidpatientidSave").val(
					$(this).closest("tr").find('#hidpatientidUpdate').val());
			$("#name").val($(this).closest("tr").find('td:eq(0)').text());
			$("#password").val($(this).closest("tr").find('td:eq(1)').text());
			$("#email").val($(this).closest("tr").find('td:eq(2)').text());
			$("#phonenumber")
					.val($(this).closest("tr").find('td:eq(3)').text());
			$("#address").val($(this).closest("tr").find('td:eq(4)').text());
		});

// REMOVE==========================================
$(document).on("click", ".btnRemove", function(event) {
	$.ajax({
		url : "PatientsAPI",
		type : "DELETE",
		data : "patientid=" + $(this).data("patientid"),
		dataType : "text",
		complete : function(response, status) {
			onPatientDeleteComplete(response.responseText, status);
		}
	});
});

function onPatientDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divPatientsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}

// CLIENTMODEL=========================================================================
function validatePatientForm() {
	// NAME
	if ($("#name").val().trim() == "") {
		return "Insert Patient Name.";
	}
	// PASSWORD
	if ($("#password").val().trim() == "") {
		return "Insert Password.";
	}

	// EMAIL-------------------------------
	if ($("#email").val().trim() == "") {
		return "Insert email.";
	}
	// convert to decimal price
	//$("#email").val(parseFloat(tmpPrice).toFixed(2));
	
	// PNO------------------------
	if ($("#phonenumber").val().trim() == "") {
		return "Insert pno.";
	}
	// is numerical value
	var tmpPno = $("#phonenumber").val().trim();
	if (!$.isNumeric(tmpPno)) {
		return "Insert a numerical value for Phone Number.";
	}
/*	var phoneno = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;
	if (){
		return  "Please Enter Phone number as 10 digit numbers";
	}
*/
	// ADDRESS
	if ($("#address").val().trim() == "") {
		return "Insert Patient Address.";
	}
	return true;
}