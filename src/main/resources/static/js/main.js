(function($) {

	"use strict";
	
	var checkboxFunction = function() {
		// Activate tooltip
		$('[data-toggle="tooltip"]').tooltip();
		
		// Select/Deselect checkboxes
		var checkbox = $('table tbody input[type="checkbox"]');
		$("#selectAll").click(function(){
			if(this.checked){
				checkbox.each(function(){
					this.checked = true;                        
				});
			} else{
				checkbox.each(function(){
					this.checked = false;                        
				});
			} 
		});
		checkbox.click(function(){
			if(!this.checked){
				$("#selectAll").prop("checked", false);
			}
		});
	};
	checkboxFunction();
	
	var patientData = function () {
		var responseData = $.get("getAllPatients")
			  .done(function(response) {
				  $('#patientTable').bootstrapTable({
					  data: response
				  })
			  })
			  .fail(function() {
			    alert("error");
			  });
		return responseData;
	}

	patientData();
	
	    $('#patientTable').on('check.bs.table uncheck.bs.table check-all.bs.table uncheck-all.bs.table', function () {
	    	$('#remove').prop('disabled', !$('#patientTable').bootstrapTable('getSelections').length)
	    	$('#update').prop('disabled', !($('#patientTable').bootstrapTable('getSelections').length === 1))
	    })
	    $('#remove').click(function () {
	      var ids = $.map($('#patientTable').bootstrapTable('getSelections'), function (row) {
	    	  $.ajax({
	 	 	        url: '/deletePatientById/'+ row.id,
	 	 	        type: 'DELETE',
	 	 	        success: function(result) {
	 	 	        	$('#deleteEmployeeModal').modal('show');
	 	 	        }
	 	 	    });
	        return row.id
	      })
	    $('#patientTable').bootstrapTable('remove', {
	  	        field: 'id',
	  	        values: ids
	  	      })
	  	$('#remove').prop('disabled', true)
	  	$('#update').prop('disabled', true)
	    })
	     
	    
	  $("#add-patient-form").on('submit', function(event) {
	  $('#addPatientModal').modal('hide');
	  event.preventDefault();
	  var $form = $(this),
	    url = $form.attr('action');
	  $.post(url, {
  		  name: $('#name').val(),
  		  gender: $('#gender').val(),
  		  phoneNumber: $('#phoneNumber').val(),
  	  }).done(function(data) {
  		$("#addPatientDialog .modal-title").text('Creation Successful');
  		$("#addPatientDialog .modal-body").text('Patient record has been created successfully.');
  	    $('#addPatientDialog').modal('show');
  	  }).fail(function() {
  		  $("#addPatientDialog .modal-title").text('Creation Failure');
  		  $("#addPatientDialog .modal-body").text('Patient record creation failed. Please try again.');
  		  $('#addPatientDialog').modal('show');
  	  });
	});
	    
	 $('#addPatientOk').on('click', function(event) {
		 $('#patientTable').bootstrapTable('refresh')
    });
	 
	 $('#search-input').on('keyup keypress change', function(event) {
		$('#patientTable').bootstrapTable('showLoading');
		$.get("searchPatients", {'name': $('#search-input').val()})
		  .done(function(response) {
			  $('#patientTable').bootstrapTable('hideLoading');
			  $('#patientTable').bootstrapTable('load', response);
		  })
		  .fail(function() {
		    alert("error");
		  });
	 });
    
	 $('#update').on('click', function(event) {
 		 var ids = $.map($('#patientTable').bootstrapTable('getSelections'), function (row) {
 			$('#updateName').val(row.name);
 			if(row.gender === 'MALE') {
 				$('#updateGender').val(1);
 			} else if(row.gender === 'FEMALE') {
 				$('#updateGender').val(2);
 			} else if(row.gender === 'OTHERS') {
 				$('#updateGender').val(3);
 			}
 	 		$('#updatePhoneNumber').val(row.phoneNumber);
 	 		$('#updateId').val(row.id);
	        return row.id
	      })
	      $('#editPatientModal').modal('show');
    });
	 
	 $('#update-patient-form').on('submit', function(event) {
		 event.preventDefault();
		 var updateData = {
			'id': $('#updateId').val(),
			'name': $('#updateName').val(),
			'gender': $('#updateGender').val(),
			'phoneNumber': $('#updatePhoneNumber').val()
		 }
		 $.ajax({
			  url: 'updatePatient',
			  type: 'PUT',
			  data: updateData,
			  success: function(data) {
				  $('#editPatientModal').modal('hide');
				  $('#patientTable').bootstrapTable('refresh');
			  }
			});
	 });
    
})(jQuery);

