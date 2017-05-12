<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Object-oriented JavaScript class example</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style>
.start {
	border-bottom: 2px solid green;
	background: #f5f5f5;
	margin: 1px;
}

.end {
	border-bottom: 2px solid red;
	background: #f5f5f5;
	margin: 1px;
}

#start {
	list-style: none;
}

#start li {
	padding: 2px;
	border-bottom: 2px solid lightgrey;
	margin: 1px;
	background: white;
}

#end {
	list-style: none;
}

#end li {
	padding: 2px;
	border-bottom: 2px solid lightgrey;
	margin: 1px;
	background: white;
}
</style>
</head>
<body>
	<div class="container">
		<h1>Tracking System for Agile Cards</h1>
	</div>
	<div class="container">
		<button type="button" data-toggle="modal" data-target=".new-card"
			id="newCard" class="btn btn-primary">Add Card</button>
	</div>
	<div class="container listofCards">
		<div class="col-sm-5 start">
			<h4>Start</h4>
			<ul id="start"></ul>
		</div>
		<div class="col-sm-2" style="width: 20px;"></div>
		<div class="col-sm-5 end">
			<h4>End</h4>
			<ul id="end"></ul>
		</div>
	</div>
	<!-- modal container -->
	<div class="container modal-container">
		<div class="modal fade new-card" tabindex="-1" role="dialog"
			aria-labelledby="newCardLabel">
			<div class="modal-dialog modal-sm" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title" id="newCardLabel2">Create new Card</h4>
					</div>
					<div class="modal-body">
						<form>
							<div class="form-group">
								<label for="card-title" class="control-label">Title: </label> <input
									type="text" class="form-control" id="card-title">
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
						<button type="submit" class="btn btn-primary">Save Card</button>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

<script>
      function Iteration(sprintname, sprintnumber, columns, duration, velocity ){
		this.name = {sprintname, sprintnumber};
		this.columns = columns;
		this.duration = duration;
		this.velocity = function() {
		var totalSum = 0;
		for(var i=0; i<card.length; i++){
			 if(card[i].column === "end"){	
			  totalSum += parseInt(card[i].estimate);
			 }
		}
		return totalSum;
	},
	this.add = function(){
		addCard(card.length);
	},
	this.moveCard = function (card, column){
		updateCard(card.id, column);
	},
	this.undoLastMove = function(card){
		console.log("Undo Last move");
	},
	this.list = function(){
		$("#start").empty();
		$("#end").empty();
		for(var j=0; j<card.length; j++){
			console.log(card[j].title);
			console.log(card[j].column);
		 if(card[j].column === "start"){
		  var node = document.createElement("li");
		  var textnode = document.createTextNode(card[j].title);
		  node.appendChild(textnode);
		  document.getElementById("start").appendChild(node);	
		 }else if(card[j].column === "end"){
		  var node = document.createElement("li");
		  var textnode = document.createTextNode(card[j].title);
		  node.appendChild(textnode);
		  document.getElementById("end").appendChild(node);	
	 	 }	
		}
	}
      };
	
      
	var iteration1 = new Iteration('iteration','three',['start', 'end'], '2 weeks');
	
	function cards(id, title, description, estimate, iteration, column){
		this.id = id;
		this.title = title;
		this.description = description;
		this.estimate = estimate;
		this.iteration = iteration;
		this.column = column;
	}

	var card = [];	
	function addCard(d){
	  card.push(new cards(d,'new card '+d+' summary', 'some description for the card '+d, '3', iteration1.name, iteration1.columns[1]));
		
		return true;
	};
	function updateCard(a, b){
	  card[a-1].column = b; 
	}
	card.push(new cards(1,'this is card 1', 'some description for the card 1', '3', iteration1.name, iteration1.columns[0]));
	card.push(new cards(2,'this is card 2', 'some description for the card 2', '5', iteration1.name, iteration1.columns[0]));
	card.push(new cards(3,'this is card 3', 'some description for the card 3', '4', iteration1.name, iteration1.columns[1]));

	
	(function() {
		iteration1.list();
		document.getElementById("newCard").addEventListener("click", function(){
			iteration1.add();
			iteration1.list();
		});
		var form = document.querySelector("form");
		form.addEventListener("submit", function(event){
			console.log("saving the new card", form.elements.value.value);
			event.preventDefault();
		});

	})();
    </script>
</html>
