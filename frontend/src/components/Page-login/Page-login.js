import React, { useState } from "react";
import WebSocketModal from "../Modal-login/Modal-login";
import "./Page-login.css";
import '../Container-cards/Container-cards.css'
import Container from "../Container-cards/Container-cards";
import Chat from "../Chat/Chat";
import { Button } from 'react-bootstrap';

function PageLogin() {
  const [showModal, setShowModal] = useState(false);
  const [connected, setConnected] = useState(false);
  const [userName, setUserName] = useState("");

  const handleConnect = (name) => {
    setConnected(true);
    setUserName(name);
  };

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
      <Container />
      <Chat />
    </div>
  );
}
export default PageLogin;
