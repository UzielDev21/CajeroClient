$(document).ready(function () {

    // Animación de entrada de los elementos
    $('.options-grid').hide().fadeIn(1000);

    // Lógica del contador de Saldo
    const $balanceElement = $('.counter-anim');

    // Obtenemos el valor raw (ej: 1500.50)
    let finalValue = parseFloat($balanceElement.attr('data-target'));

    // Si el valor no es válido (null o undefined), ponemos 0
    if (isNaN(finalValue)) {
        finalValue = 0;
        $balanceElement.text("0.00");
    }

    // Animación personalizada
    $({countNum: 0}).animate({
        countNum: finalValue
    },
            {
                duration: 2500, // Duración en milisegundos
                easing: 'swing',
                step: function () {
                    // En cada paso formateamos el número
                    $balanceElement.text(formatMoney(this.countNum));
                },
                complete: function () {
                    // Al terminar, aseguramos el valor exacto
                    $balanceElement.text(formatMoney(finalValue));
                }
            });

    // Función para dar formato de moneda (Ej: 1,250.00)
    function formatMoney(amount) {
        return amount.toFixed(2).replace(/\d(?=(\d{3})+\.)/g, '$&,');
    }

});
