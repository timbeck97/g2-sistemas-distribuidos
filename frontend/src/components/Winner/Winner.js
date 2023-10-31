import React, { useState } from 'react';
import 'bootstrap/dist/css/bootstrap.min.css';
import { Modal, Button, Form } from 'react-bootstrap';

const Winner = ({ show, confirm, jogadores }) => {

    const getJogadores = () => {
        if(jogadores){
            let jogadoresSort= jogadores.sort((a,b)=>b.pontos-a.pontos);
            return jogadoresSort;
        }else{
            return []
        }
    }

    return (
        <Modal show={show} onHide={confirm}>
            <Modal.Header closeButton>
                <Modal.Title>Fim da Partida</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <table className="table table-bordered">
                    <thead>
                        <tr>
                            <th scope="col">Colocação</th>
                            <th scope="col">Participantes</th>
                            <th scope="col">Pontuação</th>
                        </tr>
                    </thead>
                    <tbody>
                        {getJogadores().map((jogador, idx) => (
                            <tr key={idx}>
                                <th scope="row">{idx+1}</th>
                                <th scope="row">{jogador.nome}</th>
                                <td>{jogador.pontos}</td>
                        
                            </tr>
                        ))}


                    </tbody>
                </table>
            </Modal.Body>
            <Modal.Footer>

                <Button variant="primary" onClick={confirm}>
                    Sair
                </Button>
            </Modal.Footer>
        </Modal>
    );
};

export default Winner;
