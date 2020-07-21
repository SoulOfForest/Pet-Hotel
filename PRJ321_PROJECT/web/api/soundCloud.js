/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


fetch('https://deezerdevs-deezer.p.rapidapi.com/search?q=eminem', {
    mode: 'cors',
    credentials: 'same-origin',
    headers: {
        "x-rapidapi-host": "deezerdevs-deezer.p.rapidapi.com",
        "x-rapidapi-key": "e77e702af6mshae6037bf240fae5p1a3c9ejsn8f5c69c03fde",
	"useQueryString": true
    },
})
.then(response => response.json())
.then(data => console.log(data))
.catch(err => console.log(err));