import React, { useEffect, useState } from "react";
import WebSocketModal from "../Modal-login/Modal-login";
import "./Page-login.css";
import '../Container-cards/Container-cards.css'
import Container from "../Container-cards/Container-cards";
import Chat from "../Chat/Chat";
import { Button } from 'react-bootstrap';
import Rooms from "../Rooms/Rooms";
import WebsocketService from "../Service/WebsocketService";
let webSocket=null;
function PageLogin() {
  const [showModal, setShowModal] = useState(false);
  const [connected, setConnected] = useState(false);
  const [userName, setUserName] = useState("");
  const [view, setView]= useState("ROOMS");
  const [partidas, setPartidas]= useState([]);
  const [cards, setCards]= useState([
    {value: '5'},
    {value: '-10'},
    {value: '15'},
    {value: '0'},
    {value: '20'},
  ]);

  useEffect(() => {
    webSocket=new WebsocketService();
    webSocket.connect().then(()=>{
      webSocket.subscribeToPartidas((partidas)=>{
        console.log('----------> PARTIDAS: ',partidas);
        setPartidas(partidas);
      })
      webSocket.carregarPartidas();
    })
   
   return () => {
      if(webSocket){
        webSocket.disconnect();
      }
   }
  },[])
  const criarPartida = () => {
    fetch('http://127.0.0.1:8080/partida/'+userName, {
      method: 'POST',
      headers: {'Content-Type': 'application/json'},
    })
      .then((response) => response.json())
      .then((data) => {
        console.log('Success:', data);
        webSocket.carregarPartidas();
        entrarPartida(data.id);
      })
      
  }
  const entrarPartida = (id) => {
    webSocket.entrarPartida(id, userName, (partida)=>{
      console.log('----------> partida: ',partida)
      setView('GAME');
    })
  }
  
  const handleConnect = (name) => {
    setConnected(true);
    setUserName(name);
  };
  const onSelectCard = (text) => {
    console.log(text);
  }
  if (!connected) {
    return (
      <div className="base">
        <h1>Bem vindo ao jogo de Sistemas Distribuídos!</h1>
        <h1>Para iniciar, efetue o login!</h1>
        <Button variant="primary" onClick={() => setShowModal(true)}>Conectar</Button>

        <WebSocketModal
          show={showModal}
          onHide={() => setShowModal(false)}
          onConnect={handleConnect}
        />
      </div>
    );
  }
  return (
    <div className="connected">
      <p className="user">Conectado como: {userName}</p> 
      {view === "ROOMS" ?<Rooms partidas={partidas} onCriarPartida={()=>criarPartida()} entrarPartida={(id)=>entrarPartida(id)}/> :  <Container cards={cards} onSelectCard={(text)=>onSelectCard(text)} />}
    
    </div>
  );
}
export default PageLogin;
