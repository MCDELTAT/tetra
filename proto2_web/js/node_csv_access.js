var data = {};
var baseElementName = "sphere";
var counter = 1;
for (var i=1; i<=10; i++){
	var fnKey = baseElementName.concat(i.toString());
	data[fnKey] = i;
}

console.log("Contents of Data object are: ", data.sphere1);
console.log("Contents of Data object are: ", data.sphere7);