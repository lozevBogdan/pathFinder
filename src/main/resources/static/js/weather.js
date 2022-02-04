const boxImgA = document.getElementById('box-a-img');
const boxImgB = document.getElementById('box-b-img');
const boxTempA = document.getElementById('box-a-temp');
const boxTempB = document.getElementById('box-b-temp');

fetch("something").then(data=> data.json()).then(info=>{
    console.log(info)

    boxImgA.innerText = Math.round(info.main.temp - 273.15);
    boxImgA.src = '/images/weather-icons/' + info.weather[0].icon + '.png';

});