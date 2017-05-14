var addCard = function (event) {
    if (event && event.keyCode == 13) {
        $('#add-card').click();
    }
}
function urlify(card) {
    var cardUrlified = jQuery.extend({}, card);
    var urlRegex = /(https?:\/\/[^\s]+)/g;
    cardUrlified.labelUrlified = card.label.replace(urlRegex, '<a href="$1" target="blank">$1</a>');
    return cardUrlified;
}
var put_card_on_board = function (card) {
    var cardElement = $('.card#' + card.id);
    if (cardElement.length == 0) {
        var cardUrlified = urlify(card);
        cardElement = $(Mustache.to_html($('#card-template').html(), cardUrlified));
        cardElement.show('drop');
        var closeElement = cardElement.children('.card-text').children('.delete');
        closeElement.click(function () {
            $.ajax({
                url:'api/card/' + card.id,
                type:'DELETE',
                dataType:'json',
                error:function (data) {
                    $('#error-message').html('<p>' + data.responseText + '</p>');
                }
            });
            return false;
        });
    } else {
        cardElement.detach();
    }
    $('#' + card.state).append(cardElement);
};
var fetch_and_create_cards = function () {
    $.getJSON('api/cards.json', function (data) {
        for (var i in data.cards) {
            put_card_on_board(data.cards[i]);
        }
    });
};
var add_card_button = function () {
    var addCard = $('#add-card');
    addCard.button();
    addCard.click(function () {
        $.ajax({
            url:'api/card/' + encodeURIComponent($('#add-card-text').val()),
            type:'PUT',
            dataType:'json',
            error:function (data) {
                $('#error-message').html('<p>' + data.responseText + '</p>');
            }
        })
    });
}
var make_states_columns_sortable = function () {
    $('.state').sortable({
		placeholder:"ui-state-highlight",
        connectWith:".state"
    }).disableSelection();
    $('.state').droppable({
        drop:function (event, ui) {
            $.ajax({
                url:'api/card/' + $(ui.draggable).attr('id') + '/' + this.id,
                type:'POST',
                error:function (data) {
                    $('#error-message').html('<p>' + data.responseText + '</p>');
                }
            });
        }
    });
};
var verify_web_socket = function () {
    if (!window.WebSocket) {
        window.WebSocket = window.MozWebSocket;
        if (!window.WebSocket)
            alert("WebSocket not supported by this browser, please refresh");
    }
}
var activate_web_socket = function () {
    var onerror = function () {
    };
    var onopen = function () {
        $('#network-status').toggleClass('connected').html('connected');
    };
    var onmessage = function (m) {
        var action = $.parseJSON(m.data);
        $.each(action, function (index, card) {
            put_card_on_board(card);
        });
    };
    var onclose = function () {
        $('network-status').toggleClass('connected').html('disconnected');
        this.ws = null;
    };
    var location = document.location.toString().replace('http://', 'ws://') + 'ws';
    this.ws = new WebSocket(location, 'vrandesh');
    this.ws.onerror = onerror;
    this.ws.onopen = onopen;
    this.ws.onmessage = onmessage;
    this.ws.onclose = onclose;
};
$(function () {
    add_card_button();
    make_states_columns_sortable();
    fetch_and_create_cards();
    verify_web_socket();
    activate_web_socket();
});
