var resultsTable = document.querySelector("#results-table");
var templateRow = document.querySelector("#results-template");

var templateRowValue = templateRow.querySelector(".value");
var templateRowType = templateRow.querySelector(".type");

templateRowType.oninput = newRow;
templateRowValue.oninput = newRow;

function newRow() {
	if (templateRowType.value == "" || templateRowValue.value == "")
		return;
	
	var templateClone = templateRow.cloneNode(true);
	templateClone.id = templateClone.className = "";
	
	var index = resultsTable.querySelectorAll("tbody tr").length + 1;
	templateClone.querySelector(".index").innerHTML = index;
	
	var clonePrefix = "result-" + index;
	
	templateCloneType = templateClone.querySelector(".type");
	templateCloneType.value = templateRowType.value;
	templateCloneType.name = clonePrefix + "-type";
	
	templateCloneValue = templateClone.querySelector(".value");
	templateCloneValue.name = clonePrefix + "-value";
	
	resultsTable.querySelector("tbody").append(templateClone);
	
	templateRowType.value = "";
	templateRowValue.value = "";
	
	templateCloneValue.focus();
	templateCloneValue.scrollIntoView();
}