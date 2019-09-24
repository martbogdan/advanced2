var start = Date.now(); // запомнить время начала

var timer = setInterval(function() {
    // сколько времени прошло с начала анимации?
    var timePassed = Date.now() - start;

    if (timePassed >= 2000) {
        clearInterval(timer); // закончить анимацию через 2 секунды
        return;
    }

    // отрисовать анимацию на момент timePassed, прошедший с начала анимации
    draw(timePassed);

}, 20);

// в то время как timePassed идёт от 0 до 2000
// left изменяет значение от 0px до 400px
function draw(timePassed) {
   tarU.style.left = timePassed / 5 + 'px';
}