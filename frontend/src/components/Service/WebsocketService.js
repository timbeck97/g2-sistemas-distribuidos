import { Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

class WebsocketService {
    constructor() {
        this.stompClient = null;
        this.subscriptionRoom = null;
        this.connected = false;
        this.tentativas = 0;
    }

    connect(){
        return new Promise((resolve, reject) => {
            if (this.tentativas > 5) return;
            const client = Stomp.over(new SockJS('http://127.0.0.1:8080/sistemasdistribuidos'));
            client.connect({}, ()=>{
                resolve();
            }, ()=>this.handle(), ()=>this.handleDisconnect());
            this.stompClient = client;
            console.log(client);
        })
        

    };
    handleConnect () {
        this.connected=true;
    };
    disconnect(){
        if (this.stompClient != null) {
            this.stompClient.disconnect();
        }
        console.log("Disconnected");
    }
    handle(){
        console.log('teste123 ');
    }
    handleDisconnect () {
        this.connected = false;
        console.log('Desconectado');
        setTimeout(() => {
            this.tentativas = this.tentativas + 1;
            alert('desconectou o websocket')
        // this.connect();
        }, 5000);
    };
    entrarPartida(id, nomeJogador, callback){
        let obj = {
            nomeParticipante: nomeJogador,
            idPartida: id
        }
        this.stompClient.send("/game/partida/entrar/" + id, {}, JSON.stringify(obj));
        this.subscriptionRoom = this.stompClient.subscribe('/game/partida/' + id, function (data) {
            callback(JSON.parse(data.body));
    
        });
    }
    subscribeToPartidas(callback){
        console.log(this.stompClient);
        if (this.stompClient) {
            this.stompClient.subscribe('/game/partidas', (data) => {
                try {
                    callback(JSON.parse(data.body));
                } catch (e) {
                    console.log(e);
                }
            });
        }
    };

    carregarPartidas(){
        if (this.stompClient) {
            this.stompClient.send('/game/partidas', {}, {});
        }
    };
}
export default WebsocketService;

