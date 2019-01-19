fetch("./Syntaxes.txt")
	.then(function(response) {
		return response.text();
	})
	.then(function(syntaxes) {
		const lines = syntaxes.split("\n")
		const type = lines[0];
		const id = null;
		const name = null;
		const description = null
		const pattern = null;
		const example = null;
		for (line in lines) {
			if (line.includes("name: ")) {
				fetch("./card.html")
					.then(function(response) {
						return response.text();
					})
					.then(function(card) {
						card = card
							.replace("%id%", id)
							.replace("%name%", name)
							.replace("%description%", description)
							.replace("%pattern%", pattern)
							.replace("%example%", example)
						document.getElementsByClassName(type)[0].innerHTML += card;
					})
				
				if (
					line === "Conditions"
					|| line === "Effects" 
					|| line === "Expressions"
					|| line === "Events"
				) {
					type = line;
				}

				id = line.split("name: ")[1].toLowerCase().replace(" ", "_")
				name = line.split("name: ")[1]
			
			} else if (line.includes("description:")) {
				description = line.split("description: ")[1]
			} else if (line.includes("pattern:")) {
				pattern = line.split("pattern: ")[1]
			} else if (line.includes("example:")) {
				example = line.split("example: ")[1]
			}
			
		}
		
	});

function copyLink() {
	document.getElementById("myInput").select();
	document.execCommand("copy");
	console.log(document.getElementById("myInput").select())
}

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