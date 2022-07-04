var BASE_URL = "http://139.59.206.242:8090/api/v1/";

var BOOKSTORE = {
    BONITO: "bonito",
    GANDALF: "gandalf",
    PWN: "pwn"
};

var activeLibrary;

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
	activeLibrary = library;
}

function fillTable(books) {
	var table = document.getElementById('books');
	table.innerHTML = "";
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
		document.getElementById('pwn').className = 'btn btn-outline-primary';
		requestBooks(BOOKSTORE.BONITO);
	} else if (bookstore == BOOKSTORE.GANDALF) {
		document.getElementById('gandalf').className = 'btn btn-primary';
		document.getElementById('bonito').className = 'btn btn-outline-primary';
		document.getElementById('pwn').className = 'btn btn-outline-primary';
		requestBooks(BOOKSTORE.GANDALF);
	} else if (bookstore == BOOKSTORE.PWN) {
	    document.getElementById('pwn').className = 'btn btn-primary';
        document.getElementById('bonito').className = 'btn btn-outline-primary';
        document.getElementById('gandalf').className = 'btn btn-outline-primary';
        requestBooks(BOOKSTORE.PWN);
	}
}

bookstoreClicked(BOOKSTORE.GANDALF);

// filtering
 function checkFloat(number) {
    return !isNaN(parseFloat(number));
 }

 function validatePriceFilters(min, max) {
    let valid = true;
    if (min != "")
        valid = checkFloat(min);
    if (max != "")
        valid = checkFloat(max);
    return valid;
 }

 function filter() {
    let authorFilter = document.getElementById("filter-author").value;
    let titleFilter = document.getElementById("filter-title").value;
    let minFilter = document.getElementById("filter-min-price").value;
    let maxFilter = document.getElementById("filter-max-price").value;

    if (!validatePriceFilters(minFilter, maxFilter))
        alert("Sprawdź filtry");
    else if (authorFilter != "" || titleFilter != "" || minFilter != "" || maxFilter != "")
        sendFilterRequest(authorFilter, titleFilter, parseFloat(minFilter), parseFloat(maxFilter));
 }

 function sendFilterRequest(author, title, min, max) {
    let filterParams = new URLSearchParams({
      "author": author,
      "title": title,
      "min": min,
      "max": max
    });

    var req = new XMLHttpRequest();
    	req.open('GET', BASE_URL + 'books/' + activeLibrary + "?" + filterParams, true);
    	req.onreadystatechange = function (aEvt) {
    	  if (req.readyState == 4) {
    	     if(req.status == 200)
    	      fillTable(JSON.parse(req.responseText).books);
    	     else
    	      console.error("An error occurred while fetching the books");
    	  }
    	};
    	req.send(null);
 }
