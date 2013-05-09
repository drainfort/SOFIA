function openInformation(problemID) {
		document.getElementById('information_' + problemID).style.display = "block";
		document.getElementById('title_' + problemID).innerHTML="<a href=\"javascript:closeInformation('" + problemID + "');\">Close " + problemID + "</a>";
}
function closeInformation(problemID) {
	document.getElementById('information_' + problemID).style.display = "none";
	document.getElementById('title_' + problemID).innerHTML="<a href=\"javascript:openInformation('" + problemID + "');\">Open " + problemID + "</a>";
}