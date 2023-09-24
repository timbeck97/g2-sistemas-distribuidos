import React, { useState, useEffect } from 'react';
import { Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

function Chat() {
  const [stompClient, setStompClient] = useState(null);
  const [connected, setConnected] = useState(false);
  const [partidas, setPartidas] = useState([]);
  const [name, setName] = useState('');
  const [tentativas, setTentativas] = useState(0);

  useEffect(() => {
    const connect = () => {
      if (tentativas > 5) return;
      const client = Stomp.over(new SockJS('http://127.0.0.1:8080/sistemasdistribuidos'));
      client.connect({}, handleConnect, () => handleDisconnect(), handleDisconnect);
      setStompClient(client);
      console.log(client);
    };

    const handleConnect = () => {
      setConnected(true);
      subscribeToPartidas();
      carregarPartidas();
    };

    const handleDisconnect = () => {
      if (stompClient) {
        stompClient.disconnect();
      }
      setConnected(false);
      console.log('Desconectado');
      setTimeout(() => {
        setTentativas(tentativas + 1);
        connect();
      }, 5000);
    };

    const subscribeToPartidas = () => {
      if (stompClient) {
        stompClient.subscribe('/game/partidas', (data) => {
          try {
            console.log('----------> PARTIDAS: ', JSON.parse(data.body));
            showPartidas(JSON.parse(data.body));
          } catch (e) {
            console.log(e);
          }
        });
      }
    };

    const carregarPartidas = () => {
      if (stompClient) {
        stompClient.send('/game/partidas', {}, {});
      }
    };

    const showPartidas = (partida) => {
      setPartidas(partida);
    };

    return () => {
      if (stompClient) {
        stompClient.disconnect();
      }
    };
  }, [tentativas, stompClient]);


  return (
    <div>
      { }
    </div>
  );
}

export default Chat;
