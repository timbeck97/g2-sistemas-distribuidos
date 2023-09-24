import React, { useState } from 'react';
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
          <Form.Label>User Name</Form.Label>
          <Form.Control
            type="text"
            placeholder="Enter your name"
            value={name}
            onChange={(e) => setName(e.target.value)}
          />
        </Form.Group>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={onHide}>
          Cancel
        </Button>
        <Button variant="primary" onClick={handleConnect}>
          Connect
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default WebSocketModal;
