import './Container-cards.css'
import Cards from '../Cards/Cards'
import Chat from '../Chat/Chat'
import { useEffect } from 'react';
import { useState } from 'react';

const Container = (props) =>{

    const formatHora = (data) => {
        data= new Date(data);
        const horas = String(data.getHours()).padStart(2, '0');
        const minutos = String(data.getMinutes()).padStart(2, '0');
        const segundos = String(data.getSeconds()).padStart(2, '0');
        return `${horas}:${minutos}:${segundos}`;
    }
    const [cards, setCards]= useState([]);
    const [rodada, setRodada]= useState({});
    useEffect(() => {
        setCards(props.cards);
        setRodada(props.rodada);

    }, [])
    useEffect(() => {
        setCards(props.cards);
        setRodada(props.rodada);
    }, [props.cards, props.rodada])
   
    return(
        <div>
            <div className='boxHora'>
                <h2>{rodada && formatHora(rodada.hora)}</h2>
            </div>
            <div className="container-cards">
                {cards && cards.map((card) => (
                    <Cards card={card} onSelectCard={props.onSelectCard}/>
                )
                )}
              
            </div>
        </div>
    )
}

export default Container