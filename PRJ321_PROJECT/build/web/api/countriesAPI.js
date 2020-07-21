const BASE_URL = 'https://restcountries.eu/rest/v2/all';

const selectElement = document.getElementById('country');

const getAllCountries = async () => {
    const response = await fetch(BASE_URL);
    const countries = await response.json();

    countries.forEach(country => {
        let option = document.createElement("option");
        option.value = country.name;
        option.text = country.name;

        selectElement.appendChild(option);
    });
    console.log(countries);
}

getAllCountries();