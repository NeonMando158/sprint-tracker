Problem Statement: Build an app which provides a Sprint Board for Agile Team Development and Management. The Board can have multiple Sprint Iterations and each Iteration can have many cards which perform as Tasks. Each tasks can be associated to one of the stages or columns such as Start, In Progress, Stop etc. Based on the status of the Card within a Iteration, The velocity can be calculated based on the Sprint Points associated with each points. 

Velocity = total story points of cards in DONE Stage/ no. of sprints(iteration)
No of Sprints = 1 (for case of excercise)

Solution & Approach

1. For the following task I have chosen OOPs Javascript & Maven Jetty Test Driven Deployment to build the Solution. Refer .java files for the Card Class and sample outline of how i began solving the problem via javascript as well.s

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
			...,
		},
		this.list = function(){
			.....

			}
		}
     	 };

2. provision to create multiple Interation (Sprints within a board)
	var iteration1 = new Iteration('iteration','three',['start', 'end'], '2 weeks');

3. Ability to create new cards
	function cards(id, title, description, estimate, iteration, column){
		this.id = id;
		this.title = title;
		this.description = description;
		this.estimate = estimate;
		this.iteration = iteration;
		this.column = column;
	}
 
4. Cards can be created and stored as Document (Mongo) or as Data Rows in MySQL
	

5. Add a new card can be performed 
	iteration1.add();

6. List all the cards in End Column 
	iteration1.list();

7. Velocity can be Calculated for a Iteration as follows
	iteration1.velocity()

The Add New Card feature is done by performing a POST Request via a Form and I chose a Simple Java Servelet, doPost, doGet to store the data later into MySQL or Mongo using very simple JDBC adapters

Reasoning for the Solution
1. I chose Object Oriented Javascript on the Client Side which allows me to control the Instantiation of the Interation Class and also check on the status and creation of cards. This avoids a heavyweight such as JAVA to perform basic methods of calculating the velocity or CRUD features. 
2. OOPs Javascript allows use to spawn Jasmine or Karma Test Frameworks which are very lightweight and Unit tests can be performed as we build the solution. 
3. With Javascript Event Handlers, We should be able to create events such as Work In progress limit and state changes from one column to another.

The solution could be enhanced with the Usage of Frameworks such as Sprint Boot, Angular which would provide far more UX and controls and some of which adhere to Test Driven Development. 

