import { Stomp } from "@stomp/stompjs";

var stompClient = null;
var subscriptionRoom = null;
var tentativas = 0;
function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    if(tentativas>5) return;
    stompClient = Stomp.over(new SockJS('http://127.0.0.1:8080/sistemasdistribuidos'));
    stompClient.connect({}, handleConnect, () => handle('erro'), (e) => handleDisconnect(e));
    console.log(stompClient);
    //stompClient.reconnect_delay = 5000;


}
function handleConnect() {
    stompClient.subscribe('/game/partidas', function (data) {
        try {
            console.log('----------> PARTIDAS: ',JSON.parse(data.body));
            showPartidas(JSON.parse(data.body));
        } catch (e) {
            console.log(e);
        }


    });
    carregarPartidas();
}
function handle(text) {
    console.log('aaaaaaaaaaaaaaaaaaaaaaaaa ' + text);
}
function handleDisconnect(e) {
    //console.log(e);
    //console.log('TENTANDO RECONECTAR');
    setInterval(() => {
        connect();
        tentativas=tentativas+1;
    }, 5000);
    
  
}
function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
} console.log("Disconnected");
function carregarPartidas() {
    console.log('carregando partidas');
    stompClient.send("/game/partidas", {}, {});
}
function criarPartida() {
    let obj = {
        nome: $("#name").val(),
    }
    //stompClient.send("/game/initGame", {}, JSON.stringify(obj));
    if(stompClient==null) return;   
    requestCriarPartida($("#name").val());
}
function entrarPartida(id) {
    let obj = {
        nomeParticipante: $("#name").val(),
        idPartida: id
    }
    stompClient.send("/game/partida/" + id, {}, JSON.stringify(obj));
    subscriptionRoom = stompClient.subscribe('/game/partida/' + id, function (data) {
        console.log(JSON.parse(data.body));
        showDadosPartida(JSON.parse(data.body))


    });
    $("#novaPartida").prop('disabled', true);
    
}
function showDadosPartida(partida) {
    console.log('chamou a funcao');
    $("#game").empty();
    let id = "<p>ID Partida: " + partida.id + "</p>";
    let jogadores = "<p>" + partida.jogadores.map(x => x.nome).join(',') + "</p>";
    $("#game").append(id + jogadores);
    if(subscriptionRoom!=null){
        $(".entrar-partida").attr("disabled", true);
    }


}
function requestCriarPartida(userName){
    $.ajax({
        type: "POST",
        url: 'http://127.0.0.1:8080/partida/'+userName,
        contentType : "application/json",
        success: (data)=>{
            console.log(data);
             stompClient.send("/game/partidas", {}, '');
             showDadosPartida(data);
        },
      });
}
function showPartidas(partida) {
    $("#partidas").empty();
    for (let i = 0; i < partida.length; i++) {
        let id = "<td>" + partida[i].id + "</td>";
        let jogadores = "<td>" + partida[i].jogadores.map(x => x.nome).join(',') + "</td>";
        let entrarPartida = "<td><button class='btn btn-success entrar-partida'  data-partida-id='" + partida[i].id + "'>Entrar</button></td>";
        $("#partidas").append("<tr>" + id + jogadores + entrarPartida + "</tr>");
    }

}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#connect").click(function () { 
        tentativas=0;
        connect(); });
    $("#disconnect").click(function () { disconnect(); });
    $("#novaPartida").click(function () { criarPartida(); });
    $("#partidas").on("click", ".entrar-partida", function () {

        let partidaId = $(this).data("partida-id");
        entrarPartida(partidaId);
    });
});
