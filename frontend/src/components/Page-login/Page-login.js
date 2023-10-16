import React, { useEffect, useMemo, useRef, useState } from "react";
import WebSocketModal from "../Modal-login/Modal-login";
import "./Page-login.css";
import '../Container-cards/Container-cards.css'
import Container from "../Container-cards/Container-cards";
import Chat from "../Chat/Chat";
import { Button } from 'react-bootstrap';
import Rooms from "../Rooms/Rooms";
import WebsocketService from "../Service/WebsocketService";
let webSocket=null;
let interval=null;
let intervalPartida=null;
function PageLogin() {
  const [showModal, setShowModal] = useState(false);
  const [connected, setConnected] = useState(false);
  const [userName, setUserName] = useState("");
  const [view, setView]= useState("ROOMS");
  const [partidas, setPartidas]= useState([]);
  const [cards, setCards]= useState([]);
  const [rodada, setRodada]= useState({rodada:0});
  const [chosenCard, setChosenCard]= useState(null);

  const prevRodadaRef = useRef();

  useEffect(() => {
    initInterval();

    return () => {
      clearInterval(interval)
      clearInterval(intervalPartida)
    };
  },[])
  useEffect(() => {
    prevRodadaRef.current = rodada;
  },[rodada])
  const criarPartida = () => {
    fetch('http://127.0.0.1:8080/partida', {
      method: 'POST',
      headers: {'Content-Type': 'application/json'},
    })
      .then((response) => response.json())
      .then((data) => {
        entrarPartida(data.id);
        clearInterval(interval);
      })
      
  }
  const initInterval = () => {
    interval=setInterval(()=>{
      carregarPartidas();
    },1000);
  }
  const initPartidaInterval = (id) => {
    intervalPartida=setInterval(()=>{carregarDadosPartida(id)},1000);
    
  }
  const carregarPartidas = () => {
    fetch('http://127.0.0.1:8080/partida', {
      method: 'GET',
      headers: {'Content-Type': 'application/json'},
      
    })
      .then((response) => response.json())
      .then((data) => {
        setPartidas(data);
      })
  }
  const carregarDadosPartida = (id) => {
    fetch('http://127.0.0.1:8080/partida/'+id, {
      method: 'GET',
      headers: {'Content-Type': 'application/json'},
      
    })
      .then((response) => response.json())
      .then((data) => {
        const prevRodada = prevRodadaRef.current;
        setRodada(data);
        
   

        if(prevRodada.situacaoPartida==='ANDAMENTO'){
            console.log('PARTIDA EM ANDAMENTO AGORA');
            setCards(data.cartas);
            if(prevRodada.rodada!==data.rodada){
              console.log('RODADA MUDOU');
              setCards(data.cartas);
              setChosenCard(null);
            }
        }
    
      })
  }
  const entrarPartida = (id) => {
    fetch('http://127.0.0.1:8080/partida/entrar/'+id, {
      method: 'POST',
      headers: {'Content-Type': 'application/json'},
      body:JSON.stringify({
        idPartida:id,
        nomeParticipante:userName
      })
    })
      .then((response) => response.json())
      .then((data) => {
          setView('GAME');
          setRodada(data);
          setCards(data.cartas);

          clearInterval(interval);
          initPartidaInterval(id)
      })
   
  }
  
  const handleConnect = (name) => {
    setConnected(true);
    setUserName(name);
  };
  const onSelectCard = (text) => {
    setTimeout(()=>{
      fetch('http://127.0.0.1:8080/partida/jogar', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body:JSON.stringify({
          idPartida:rodada.idPartida,
          jogador:userName,
          value:text
        })
  
  
      })
        .then((response) =>{} )
        .then((data) => {
          console.log('Success:', data);
        })
      setChosenCard(text);
    },1000);
    
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
      <button onClick={()=>console.log(rodada)}>LOG RODADA</button>
      {view === "ROOMS" ?<Rooms partidas={partidas} onCriarPartida={()=>criarPartida()} entrarPartida={(id)=>entrarPartida(id)}/> :  <Container cards={cards} chosenCard={chosenCard} rodada={{}} onSelectCard={(text)=>onSelectCard(text)} />}
    
    </div>
  );
}
export default PageLogin;
