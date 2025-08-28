// ===== ТЕКУЩЕЕ ВРЕМЯ =====
function updateClock() {
    const now = new Date();
    
    const hours = String(now.getHours()).padStart(2, '0');
    const minutes = String(now.getMinutes()).padStart(2, '0');
    const seconds = String(now.getSeconds()).padStart(2, '0');
    
    const timeString = `${hours}:${minutes}:${seconds}`;
    
    const options = { 
        weekday: 'long', 
        year: 'numeric', 
        month: 'long', 
        day: 'numeric' 
    };
    const dateString = now.toLocaleDateString('ru-RU', options);
    
    document.getElementById('clock').textContent = timeString;
    document.getElementById('date').textContent = dateString;
}

// ===== СЕКУНДОМЕР =====
let stopwatchInterval = null;
let stopwatchStartTime = 0;
let stopwatchElapsed = 0;
let stopwatchRunning = false;
let lapCount = 1;

function updateStopwatchDisplay() {
    const totalMilliseconds = stopwatchElapsed + (stopwatchRunning ? Date.now() - stopwatchStartTime : 0);
    const totalSeconds = Math.floor(totalMilliseconds / 1000);
    const hours = Math.floor(totalSeconds / 3600);
    const minutes = Math.floor((totalSeconds % 3600) / 60);
    const seconds = totalSeconds % 60;
    const milliseconds = Math.floor((totalMilliseconds % 1000) / 10);
    
    document.getElementById('stopwatch-display').textContent = 
        `${String(hours).padStart(2, '0')}:${String(minutes).padStart(2, '0')}:${String(seconds).padStart(2, '0')}.${String(milliseconds).padStart(2, '0')}`;
}

function startStopwatch() {
    if (!stopwatchRunning) {
        stopwatchStartTime = Date.now() - stopwatchElapsed;
        stopwatchRunning = true;
        stopwatchInterval = setInterval(updateStopwatchDisplay, 10);
        document.getElementById('start-stopwatch').textContent = 'Пауза';
        document.getElementById('start-stopwatch').classList.remove('btn-success');
        document.getElementById('start-stopwatch').classList.add('btn-warning');
    }
}

function pauseStopwatch() {
    if (stopwatchRunning) {
        stopwatchElapsed = Date.now() - stopwatchStartTime;
        stopwatchRunning = false;
        clearInterval(stopwatchInterval);
        document.getElementById('start-stopwatch').textContent = 'Старт';
        document.getElementById('start-stopwatch').classList.remove('btn-warning');
        document.getElementById('start-stopwatch').classList.add('btn-success');
    }
}

function resetStopwatch() {
    stopwatchElapsed = 0;
    stopwatchRunning = false;
    clearInterval(stopwatchInterval);
    updateStopwatchDisplay();
    document.getElementById('start-stopwatch').textContent = 'Старт';
    document.getElementById('start-stopwatch').classList.remove('btn-warning');
    document.getElementById('start-stopwatch').classList.add('btn-success');
    
    // Очищаем список кругов
    document.getElementById('laps').innerHTML = '';
    lapCount = 1;
}

function lapStopwatch() {
    if (stopwatchRunning) {
        const lapsContainer = document.getElementById('laps');
        const lapTime = document.getElementById('stopwatch-display').textContent;
        
        const lapItem = document.createElement('div');
        lapItem.className = 'lap-item';
        lapItem.textContent = `Круг ${lapCount++}: ${lapTime}`;
        
        lapsContainer.prepend(lapItem);
    }
}

// ===== ТАЙМЕР НА 4 ЧАСА =====
let timerInterval = null;
let timerTotalSeconds = 4 * 3600; // 4 часа в секундах
let timerRemaining = timerTotalSeconds;
let timerRunning = false;

function updateTimerDisplay() {
    const hours = Math.floor(timerRemaining / 3600);
    const minutes = Math.floor((timerRemaining % 3600) / 60);
    const seconds = timerRemaining % 60;
    
    const display = document.getElementById('timer-display');
    display.textContent = `${String(hours).padStart(2, '0')}:${String(minutes).padStart(2, '0')}:${String(seconds).padStart(2, '0')}`;
    
    // Обновляем прогресс-бар
    const progressPercent = (timerRemaining / timerTotalSeconds) * 100;
    const progressBar = document.getElementById('timer-progress');
    progressBar.style.width = `${progressPercent}%`;
    progressBar.textContent = `${Math.round(progressPercent)}%`;
    
    // Меняем цвет при приближении к нулю
    if (timerRemaining <= 60) { // Меньше минуты
        progressBar.classList.add('bg-danger');
        display.classList.add('warning');
    } else if (timerRemaining <= 300) { // Меньше 5 минут
        progressBar.classList.add('bg-warning');
        display.classList.remove('warning');
    } else {
        progressBar.classList.remove('bg-warning', 'bg-danger');
        display.classList.remove('warning');
    }
}

function startTimer() {
    if (!timerRunning && timerRemaining > 0) {
        timerRunning = true;
        timerInterval = setInterval(() => {
            if (timerRemaining > 0) {
                timerRemaining--;
                updateTimerDisplay();
            } else {
                clearInterval(timerInterval);
                timerRunning = false;
                // Можно добавить звуковое оповещение
                alert('Таймер завершил работу!');
            }
        }, 1000);
    }
}

function pauseTimer() {
    if (timerRunning) {
        clearInterval(timerInterval);
        timerRunning = false;
    }
}

function resetTimer() {
    clearInterval(timerInterval);
    timerRunning = false;
    timerRemaining = timerTotalSeconds;
    updateTimerDisplay();
    document.getElementById('timer-display').classList.remove('warning');
}

// ===== ИНИЦИАЛИЗАЦИЯ =====
document.addEventListener('DOMContentLoaded', function() {
    // Инициализация текущего времени
    updateClock();
    setInterval(updateClock, 1000);
    
    // Инициализация секундомера
    updateStopwatchDisplay();
    document.getElementById('start-stopwatch').addEventListener('click', function() {
        if (stopwatchRunning) {
            pauseStopwatch();
        } else {
            startStopwatch();
        }
    });
    document.getElementById('lap-stopwatch').addEventListener('click', lapStopwatch);
    document.getElementById('reset-stopwatch').addEventListener('click', resetStopwatch);
    
    // Инициализация таймера
    updateTimerDisplay();
    document.getElementById('start-timer').addEventListener('click', startTimer);
    document.getElementById('pause-timer').addEventListener('click', pauseTimer);
    document.getElementById('reset-timer').addEventListener('click', resetTimer);
});