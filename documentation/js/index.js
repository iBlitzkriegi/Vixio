const user = "AlexLew95"
const maincard = {}
const syntaxes = ""
const filter = {
	names: document.getElementsByClassName("card-header-title"),
	patterns: document.getElementsByClassName("card-pattern")
}

//console.log(filter)

async function request(link) {
	let back = await fetch(link)
	.then(function(response) {
		return response.text();
	})
	.then(function(asText) {
		return asText;		
	});
	return back;
}

async function start() {
	maincard.card = await request("https://raw.githubusercontent.com/" + user + "/Vixio/master/documentation/card.html");
	maincard.syntaxes = await request("https://raw.githubusercontent.com/" + user + "/Vixio/master/documentation/Syntaxes.txt");
	const lines = maincard.syntaxes.split("\n")
	maincard.type = lines[0].toLowerCase().replace(":", "");
	maincard.id = lines[1].split("name: ")[1].toLowerCase().replace(" ", "_")
	maincard.name = lines[2].split("name: ")[1]
	maincard.description = lines[3].split("description: ")[1]
	maincard.pattern = lines[4].split("pattern: ")[1]
	maincard.example = lines[5].split("example: ")[1]
	maincard.patterns = []
	maincard.is_pattern = false
	for (i = 0; i < lines.length; i++) {
		line = lines[i]
		if (line.includes("name: ")) {
			console.log(maincard.type)
			card = maincard.card
				.replace("%id%", maincard.id)
				.replace("#%id%", "#" + maincard.id)
				.replace("%name%", '<span class="tag is-large" style="background-color: rgb(97, 237, 120)">' + maincard.type + '</span><p class="card-header-title" style="margin-left: 3%">' + maincard.name + '</p>')
				.replace("%description%", maincard.description)
				.replace("%pattern%", maincard.patterns.join("\n"))
				.replace("%example%", maincard.example)
			document.getElementsByClassName(maincard.type)[0].innerHTML += card;
			maincard.id = line.split("name: ")[1].toLowerCase().replace(" ", "_");
			maincard.name = line.split("name: ")[1];
			maincard.is_pattern = false;
		
		} else if (line.includes("description:")) {
			maincard.description = line.split("description: ")[1];
		} else if (line.includes("patterns:")) {
			maincard.is_pattern = true;
			maincard.patterns = [];
		} else if (line.includes("example:")) {
			maincard.example = line.split("example: ")[1];
		} else if (
			line === "Conditions:"
			|| line === "Effects:" 
			|| line === "Expressions:"
			|| line === "Events:"
		) {
			maincard.type = line.toLowerCase().replace(":", "");
		}

		if (maincard.is_pattern === true) {
			maincard.patterns.push(line.split("- ")[1])
				/*.replace("from", "<span>")
			)*/
		}
		
	}

}

function search() {
	cards = document.getElementsByClassName("syntaxes")[0].getElementsByClassName("card")
	search_value = document.getElementsByClassName("search-input")[0].value.toLowerCase();
	for (i = 0; i < cards.length; i++) {
		txtValue = cards[i].getElementsByClassName("card-header-title")[0].textContent;
		pattern = cards[i].getElementsByClassName("card-pattern")[0];
		if (txtValue.toLowerCase().indexOf(search_value) > -1) {
			cards[i].style.display = "";
		} else {
			cards[i].style.display = "none";
		}
	}
}

start()


const $navbarBurgers = Array.prototype.slice.call(document.querySelectorAll('.navbar-burger'), 0);

// Check if there are any navbar burgers
if ($navbarBurgers.length > 0) {

// Add a click event on each of them
$navbarBurgers.forEach( el => {
el.addEventListener('click', () => {

  // Get the target from the "data-target" attribute
  const target = el.dataset.target;
  const $target = document.getElementById(target);

  // Toggle the "is-active" class on both the "navbar-burger" and the "navbar-menu"
  el.classList.toggle('is-active');
  $target.classList.toggle('is-active');

});
});
};