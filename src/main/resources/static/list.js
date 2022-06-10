var BASE_URL = "http://67.207.76.109:8080/api/v1/";

var BOOKSTORE = {
    BONITO: "bonito",
    GANDALF: "gandalf"
};

function requestBooks(library) {
	var req = new XMLHttpRequest();
	req.open('GET', BASE_URL +'books/' + library, true);
	req.onreadystatechange = function (aEvt) {
	  if (req.readyState == 4) {
	     if(req.status == 200)
	      fillTable(JSON.parse(req.responseText).books);
	     else
	      console.error("An error occurred while fetching the books");
	  }
	};
	req.send(null);
	document.getElementById('books').innerHTML = "";
}

function fillTable(books) {
	var table = document.getElementById('books');
	for (var i = 0; i < books.length; i++) {
		let tr = document.createElement("tr");
		tr.appendChild(createTableCell(books[i].title, 'title'));
		tr.appendChild(createTableCell(((books[i].author.trim() == '') ? '-----' : books[i].author), 'author'));
		tr.appendChild(createTableCell(parseFloat(books[i].oldPrice).toFixed(2), 'oldPrice'));
		tr.appendChild(createTableCell(parseFloat(books[i].price).toFixed(2), 'newPrice'));
		let discount = 100 - (parseFloat(books[i].price) / parseFloat(books[i].oldPrice)) * 100;
		tr.appendChild(createTableCell(discount.toFixed(0), 'discount'));
		tr.appendChild(createLinkCell(createTableCell('', 'link'), books[i].link));
		table.appendChild(tr);
	}
}

function createTableCell(content, className) {
	let td = document.createElement("td");
	td.innerText = content;
	td.className = className;
	return td;
}

function createLinkCell(tableCell, link) {
	tableCell.innerHTML = '<a target="_blank" href="' + link + '"><span class="material-icons">open_in_new</span></a>';
	return tableCell;
}

function bookstoreClicked(bookstore) {
	if (bookstore == BOOKSTORE.BONITO) {
		document.getElementById('bonito').className = 'btn btn-primary';
		document.getElementById('gandalf').className = 'btn btn-outline-primary';
		requestBooks(BOOKSTORE.BONITO);
	} else if (bookstore == BOOKSTORE.GANDALF) {
		document.getElementById('gandalf').className = 'btn btn-primary';
		document.getElementById('bonito').className = 'btn btn-outline-primary';
		requestBooks(BOOKSTORE.GANDALF);
	}
}

bookstoreClicked(BOOKSTORE.GANDALF);
