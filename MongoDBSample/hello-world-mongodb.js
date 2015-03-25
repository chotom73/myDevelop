/**
 * New node file
 */
var mongoose = require('mongoose');
mongoose.connect('mongodb://localhost:27017/test');

var db = mongoose.connection;

db.on('error', console.error.bind(console, 'collection error'));
db.once('open', function callback() {
	console.log('success!!!');
	
	var kittySchema = mongoose.Schema({
		name: String
	});
	
	kittySchema.methods.speak = function() {
		var greeting = this.name ? "Meow name is " + this.name : "I don't have a name";
		console.log(greeting);
	};
	
	var Kitten = mongoose.model('Kitten', kittySchema);
		
	var fluffy = new Kitten({name : 'fluffy'});
	fluffy.speak();
		
	fluffy.save(function (err, fluffy) {
		if (err) {
			throw err;
		}
	});
		
	Kitten.find(function(err, Kitten) {
		if(err) {
			throw err;
		}
		db.close();
		console.log('Kittens : ' + Kitten);
	});
});