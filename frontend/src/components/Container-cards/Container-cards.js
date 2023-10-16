import './Container-cards.css'
import Cards from '../Cards/Cards'
import Chat from '../Chat/Chat'
import { useEffect } from 'react';
import { useState } from 'react';


const Container = (props) =>{

    
    const [cards, setCards]= useState([]);
    const [rodada, setRodada]= useState({});
    const [chosenCard, setChosenCard]= useState(null);

    useEffect(() => {
        setCards(props.cards);
        setRodada(props.rodada);
        setChosenCard(props.chosenCard);
        

    }, [])
    useEffect(() => {
        setCards(props.cards);
        setRodada(props.rodada);
        setChosenCard(props.chosenCard);
           
    }, [props.cards, props.rodada, props.chosenCard])
    
    if(rodada && rodada.situacaoPartida==='AGUARDANDO'){
        return(
            <div className="container-cards">

            </div>
        )
    }
    return(
        <div>
            
            <div className="container-cards">
                
                {cards && cards.map((card, id) => (
                    <Cards  key={id} card={card} chosenCard={chosenCard} onSelectCard={props.onSelectCard}/>
                )
                )}
              
            </div>
        </div>
    )
}

export default Container