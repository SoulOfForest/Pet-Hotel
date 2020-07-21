const musicContainer = document.getElementById('music-container');

const prevBtn = document.getElementById('prev');
const nextBtn = document.getElementById('next');
const playBtn = document.getElementById('play');

const audio = document.getElementById('audio');
const progress = document.getElementById('progress');
const progressContainer = document.getElementById('progress-container');
const title = document.getElementById('title');
const cover = document.getElementById('cover');  

const currentTime = document.querySelector('.time-container .current');
const durationTime = document.querySelector('.time-container .total');

const songs = ['CastleOnTheHill', 'Perfect', 'Photograph']; 

//keep track of current Song

let currentSongIndex = 1;

// Initially load song details into DOM

loadSong(songs[currentSongIndex]);


// update song details

function loadSong(song) {
    title.innerText = song;

    audio.src = `../music/${song}.mp3`;

    cover.src = `../images/${song}.jpg`;
}

// Play Song

function playSong() {
    musicContainer.classList.add('play');
    
    playBtn.querySelector('i.fas').classList.remove('fa-play');
    playBtn.querySelector('i.fas').classList.add('fa-pause');

    audio.play();
}

// Pause Song

function pauseSong() {
    musicContainer.classList.remove('play');
    
    playBtn.querySelector('i.fas').classList.remove('fa-pause');
    playBtn.querySelector('i.fas').classList.add('fa-play');

    audio.pause();
}

// Play next song

function nextSong() {
    if (currentSongIndex === songs.length - 1) {
        currentSongIndex = 0;
    } else {
        currentSongIndex += 1;
    }

    loadSong(songs[currentSongIndex]);
    playSong();
}

// Update Progress Bar
function updateProgress(e) {
    const { currentTime, duration } = audio;

    const progressPercent = currentTime / duration * 100;

    progress.style.width = progressPercent + "%";
    // Set Duration Time
    setDurationTime();
    // Update current time
    updateCurrentTime();
} 

// set Progress Bar

function setProgress(e) {
    const progressPercent = e.offsetX / this.clientWidth;

    audio.currentTime = audio.duration * progressPercent;
}

// set Audio TimeStamp

function setDurationTime() {
    if (audio.duration) {
        const minute = Math.floor(audio.duration / 60);
        let second = Math.floor(audio.duration % 60);

        if (second < 0) {
            second = `0${second}`
        }

        durationTime.innerText = `${minute}:${second}`;
    } else {
        durationTime.innerText = `0:00`;
    }

}

function updateCurrentTime() {
    const minute = Math.floor(audio.currentTime / 60);
    let second = Math.floor(audio.currentTime % 60);

    if (second < 10) {
        second = `0${second}`
    }

    currentTime.innerText = `${minute}:${second}`;
}

// Event Listeners

playBtn.addEventListener('click', function(e) {
    const isPlaying = audio.currentTime > 0 && !audio.paused && !audio.ended; 

    if (isPlaying) {
        pauseSong();
    } else {
        playSong();
    }
});

nextBtn.addEventListener('click', nextSong);

prevBtn.addEventListener('click', function(e) {
    if (currentSongIndex === 0) {
        currentSongIndex = songs.length - 1;
    } else {
        currentSongIndex -= 1;
    }

    loadSong(songs[currentSongIndex]);
    playSong();
})

// Handle Time/Song Update

audio.addEventListener('timeupdate', updateProgress);

// handle progress bar

progressContainer.addEventListener('click', setProgress);

// Auto Play Song

audio.addEventListener('ended', nextSong);