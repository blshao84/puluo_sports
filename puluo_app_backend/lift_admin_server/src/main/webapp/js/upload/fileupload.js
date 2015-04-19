/*
 * jQuery File Upload Plugin JS Example 8.9.1
 * https://github.com/blueimp/jQuery-File-Upload
 *
 * Copyright 2010, Sebastian Tschan
 * https://blueimp.net
 *
 * Licensed under the MIT license:
 * http://www.opensource.org/licenses/MIT
 */

/* global $, window */

function doFileUpload(uploadURL) {
	$(function() {
		'use strict';

		// Initialize the jQuery File Upload widget:
		$('#fileupload').fileupload({
			// Uncomment the following to send cross-domain cookies:
			// xhrFields: {withCredentials: true},
			url : uploadURL
		});

		// Enable iframe cross-domain access via redirect option:
		$('#fileupload').fileupload(
				'option',
				'redirect',
				window.location.href.replace(/\/[^\/]*$/,
						'/cors/result.html?%s'));

		if (window.location.hostname === 'blueimp.github.io') {
			// Demo settings:
			$('#fileupload').fileupload(
					'option',
					{
						url : uploadURL,//'/uploading/image',
						// Enable image resizing, except for Android and Opera,
						// which actually support image resizing, but fail to
						// send Blob objects via XHR requests:
						disableImageResize : /Android(?!.*Chrome)|Opera/
								.test(window.navigator.userAgent),
						maxFileSize : 4000000,
						acceptFileTypes : /(\.|\/)(gif|jpe?g|png)$/i
					});
			// Upload server status check for browsers with CORS support:
			if ($.support.cors) {
				$.ajax({
					url : uploadURL,///uploading/image',
					type : 'HEAD'
				}).fail(
						function() {
							$('<div class="alert alert-danger"/>').text(
									'Upload server currently unavailable - '
											+ new Date()).appendTo(
									'#fileupload');
						});
			}
		} else {
			// Load existing files:
			$('#fileupload').addClass('fileupload-processing');
			$.ajax({
				// Uncomment the following to send cross-domain cookies:
				// xhrFields: {withCredentials: true},
				url : $('#fileupload').fileupload('option', 'url'),
				dataType : 'json',
				context : $('#fileupload')[0]
			}).always(function() {
				$(this).removeClass('fileupload-processing');
			}).done(
					function(result) {
						$(this).fileupload('option', 'done').call(this,
								$.Event('done'), {
									result : result
								});
					});
		}

	});
}

function getUploadedFiles()  {
	var files = $('.uploaded-image-links');
	var filesToUpload = new Array(files.length);
	for (var i = 0; i < files.length; i++) {
		var title = files[i].title;
		var link = files[i].href;
		filesToUpload[i] = {
			'title':title,
			'link':link
		};
	};
	return {
		files:filesToUpload
	};
}