var url = $("#meeting_url");
var updateURL = $(location).attr('href');
url.attr("href",updateURL);
url.text(updateURL);