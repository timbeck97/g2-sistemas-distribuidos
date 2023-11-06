import React, { useEffect, useMemo, useRef, useState } from "react";
import WebSocketModal from "../Modal-login/Modal-login";
import "./Page-login.css";
import '../Container-cards/Container-cards.css'
import Container from "../Container-cards/Container-cards";
import Chat from "../Chat/Chat";
import { Button } from 'react-bootstrap';
import Rooms from "../Rooms/Rooms";
import Reconectar from "../ReconectRoom/Reconectar";
import WebsocketService from "../Service/WebsocketService";
import Winner from "../Winner/Winner";
let webSocket=null;
let interval=null;
let intervalPartida=null;
function PageLogin() {
  const [showModal, setShowModal] = useState(false);
  const [showModalReconect, setShowModalReconect]= useState(false);
  const [connected, setConnected] = useState(false);
  const [userName, setUserName] = useState("");
  const [view, setView]= useState("ROOMS");
  const [partidas, setPartidas]= useState([]);
  const [cards, setCards]= useState([]);
  const [rodada, setRodada]= useState({rodada:0});
  const [chosenCard, setChosenCard]= useState(null);
  const [openWinners, setOpenWinners]= useState(false);
  const [partidaAberta, setPartidaAberta]= useState({});

  const prevRodadaRef = useRef();
  const port=8080;
  useEffect(() => {
    if(userName!==''){
      initInterval();
    }
    consultaPartidaEmAberto();
    return () => {
      clearInterval(interval)
      clearInterval(intervalPartida)
    };
  },[userName])
  useEffect(() => {
    prevRodadaRef.current = rodada;
  },[rodada])
  const criarPartida = () => {
    fetch(`http://127.0.0.1:${port}/partida`, {
      method: 'POST',
      headers: {'Content-Type': 'application/json'},
    })
      .then((response) => response.json())
      .then((data) => {
        entrarPartida(data.id);
        
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
    fetch(`http://127.0.0.1:${port}/partida?jogador=${userName}`, {
      method: 'GET',
      headers: {'Content-Type': 'application/json'},
      
    })
      .then((response) => response.json())
      .then((data) => {
        setPartidas(data);
      })
  }
  const carregarDadosPartida = (id) => {
    fetch(`http://127.0.0.1:${port}/partida/${id}`, {
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
        }else if(prevRodada.situacaoPartida==='FINALIZADA'){
          console.log('PARTIDA NAO ESTA EM ANDAMENTO');
          setCards([]);
          setChosenCard(null);
          setOpenWinners(true);
          clearInterval(intervalPartida);
          clearInterval(interval);
        }
    
      })
  }
  const entrarPartida = (id) => {
    clearInterval(interval);
    fetch(`http://127.0.0.1:${port}/partida/entrar/${id}`, {
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

          initPartidaInterval(id)
      })
   
  }
  const sairPartida = () => {
    let partida=partidas.find((partida)=>partida.situacaoPartida==='ANDAMENTO');
    let participante=partida.jogadores.find((participante)=>participante.nome===userName);
    fetch(`http://127.0.0.1:${port}/partida/sair/${partida.id}/user/${participante.id}`, {
      method: 'POST',
    })
      .then((response) => response.json())
      .then((data) => {
        setShowModalReconect(false);
        initInterval();

      })
  }
  const handleConnect = (name) => {
    setConnected(true);
    setUserName(name);


  };
  const onSelectCard = (text) => {
    setChosenCard(text);

    setTimeout(()=>{
      fetch(`http://127.0.0.1:${port}/partida/jogar`, {
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
      
    },1000);
    
  }
  const consultaPartidaEmAberto = () => {
    fetch(`http://127.0.0.1:${port}/partida/aberta?jogador=${userName}`, {
      method: 'GET',
      headers: {'Content-Type': 'application/json'},
      
    })
      .then((response) => response.json())
      .then((data) => {
        console.log('partida aberta: ',data);
        if(data.id){
          setPartidaAberta(data);
          setShowModalReconect(true);
          clearInterval(interval)
          clearInterval(intervalPartida)
        }
    
      })
  }
  const onReconect = () => {
    setShowModalReconect(false);
    setView('GAME');
    carregarDadosPartida(partidaAberta.id)
    initPartidaInterval(partidaAberta.id)
    
  }
  const onRecuseReconect = () => {
    sairPartida();
  }
  const confirmFinalPartida = () => {
    setOpenWinners(false);
    setView('ROOMS');
    setCards([]);
    setChosenCard(null);
    setPartidas([]);
    initInterval();
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
      {view === "ROOMS" ?<Rooms partidas={partidas} onCriarPartida={()=>criarPartida()} entrarPartida={(id)=>entrarPartida(id)}/> :  <Container cards={cards} chosenCard={chosenCard} rodada={{}} onSelectCard={(text)=>onSelectCard(text)} />}
      <Reconectar show={showModalReconect} recuse={onRecuseReconect} accept={onReconect} />
      <Winner show={openWinners} confirm={confirmFinalPartida} jogadores={rodada.jogadores}/>
    </div>
  );
}
export default PageLogin;
