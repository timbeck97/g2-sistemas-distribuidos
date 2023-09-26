import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Modal, Button, Form } from 'react-bootstrap';

const WebSocketModal = ({ show, onHide, onConnect }) => {
  const [name, setName] = useState('');

  const handleConnect = () => {
    if (name.trim() !== '') {
      onConnect(name);
      onHide();
    }
  };

  return (
    <Modal show={show} onHide={onHide}>
      <Modal.Header closeButton>
        <Modal.Title>WebSocket Connection</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <Form.Group controlId="name">
          <Form.Label>Nome do Jogador</Form.Label>
          <Form.Control
            type="text"
            placeholder="Digite seu Nome"
            value={name}
            onChange={(e) => setName(e.target.value)}
          />
        </Form.Group>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={onHide}>
          Cancelar
        </Button>
        <Button variant="primary" onClick={handleConnect}>
          Conectar
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default WebSocketModal;
