import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Modal, Button, Form } from 'react-bootstrap';

const Reconectar = ({ show, recuse, accept }) => {
  const [name, setName] = useState('');


  return (
    <Modal show={show} onHide={recuse}>
      <Modal.Header closeButton>
        <Modal.Title>Reconectar</Modal.Title>
      </Modal.Header>
      <Modal.Body>
        <h2>
            Deseja reconectar a partida?
        </h2>
      </Modal.Body>
      <Modal.Footer>
        <Button variant="secondary" onClick={recuse}>
          Recusar
        </Button>
        <Button variant="primary" onClick={accept}>
          Aceitar
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default Reconectar;
